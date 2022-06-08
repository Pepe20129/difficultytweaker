package io.github.pepe20129.difficultytweaker.utils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.pepe20129.difficultytweaker.utils.annotations.BoundedFloat;
import io.github.pepe20129.difficultytweaker.utils.annotations.BoundedInteger;
import io.github.pepe20129.difficultytweaker.utils.annotations.ConfigCategory;
import io.github.pepe20129.difficultytweaker.utils.annotations.NoCommand;
import io.github.pepe20129.difficultytweaker.utils.annotations.SpecialFormat;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;

public class CommandRegistration {
	private static final Logger LOGGER = LoggerFactory.getLogger("difficultytweaker");

	private static final Config NEW_CONFIG = new Config();

	//can't implement this in the annotations because java requires constant values in annotation initialization
	private static final Map<String, Function<Object, Object>> SPECIAL_VALUE_MODIFIERS = Map.of(
		"entity.phantom.min", value -> (int)value > ConfigHelper.getConfig().phantom.max ? ConfigHelper.getConfig().phantom.max : value,
		"entity.phantom.max", value -> (int)value < ConfigHelper.getConfig().phantom.min ? ConfigHelper.getConfig().phantom.min : value
	);

	@SuppressWarnings({"unused", "unchecked"})
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
		LiteralCommandNode<ServerCommandSource> difficultyTweakerNode = CommandManager
			.literal("difficultyTweaker")
			.requires(source -> source.hasPermissionLevel(2))
			.executes(context -> {
				sendText(context, Text.translatable("difficultytweaker.main"));
				return 1;
			}).build();
		dispatcher.getRoot().addChild(difficultyTweakerNode);

		Map<String, LiteralCommandNode<ServerCommandSource>> categoryNodes = new HashMap<>();

		for (Class<?> configClass : Config.class.getDeclaredClasses()) {
			if (configClass.getSuperclass() == Config.ConfigEntry.class) {
				if (configClass.getAnnotation(ConfigCategory.class) != null) {
					String configClassName = configClass.getSimpleName();
					configClassName = Character.toLowerCase(configClassName.charAt(0)) + configClassName.substring(1);

					String category = configClass.getAnnotation(ConfigCategory.class).value();
					if (!categoryNodes.containsKey(category)) {
						LiteralCommandNode<ServerCommandSource> featureNode = createCategoryNode(category);
						categoryNodes.put(category, featureNode);
						difficultyTweakerNode.addChild(featureNode);
					}

					createFeatureNodes(categoryNodes.get(category), (Class<? extends Config.ConfigEntry>)configClass, category, configClassName);
				} else {
					LOGGER.debug("Feature category not recognized for feature \"" + configClass.getName() + "\"");
				}
			}
		}
	}

	private static LiteralCommandNode<ServerCommandSource> createCategoryNode(String categoryName) {
		return CommandManager
			.literal(categoryName)
			.executes(context -> {
				sendText(context, Text.translatable("difficultytweaker." + categoryName));
				return 1;
			}).build();
	}

	private static void createFeatureNodes(LiteralCommandNode<ServerCommandSource> categoryNode, Class<? extends Config.ConfigEntry> featureClass, String featureCategory, String featureName) {
		LiteralCommandNode<ServerCommandSource> featureNode = CommandManager.literal(featureName)
			.executes(context -> {
				sendText(context, Text.translatable("difficultytweaker." + featureCategory + "." + featureName));
				return 1;
			}).build();

		createFeatureActiveNodes(featureNode, featureName);

		for (Field field : featureClass.getDeclaredFields()) {
			if (field.getAnnotation(NoCommand.class) == null) {
				createFeatureOptionNodes(
					featureNode,
					featureClass,
					featureCategory,
					featureName,
					field.getName(),
					field.getAnnotation(SpecialFormat.class) != null ? field.getAnnotation(SpecialFormat.class).value() : "§l[%s]§r"
				);
			}
		}

		categoryNode.addChild(featureNode);
	}

	private static void createFeatureActiveNodes(LiteralCommandNode<ServerCommandSource> featureMainNode, String featureName) {
		LiteralCommandNode<ServerCommandSource> activeNode = CommandManager.literal("active")
			.executes(context -> {
				ConfigHelper.reloadConfig(context.getSource().getServer());
				String value = toDisplayableString(CommandRegistration.<Config.ConfigEntry>getField(ConfigHelper.getConfig(), featureName).active);
				sendText(context, Text.translatable("difficultytweaker.active", value));
				return 1;
			})
			.build();

		ArgumentCommandNode<ServerCommandSource, Boolean> activeValueNode = CommandManager.argument("active", BoolArgumentType.bool())
			.executes(context -> {
				boolean a = getBool(context, "active");
				CommandRegistration.<Config.ConfigEntry>getField(ConfigHelper.getConfig(), featureName).active = a;
				ConfigHelper.saveConfig(context.getSource().getServer());
				sendActiveStatus(context, a, featureName);
				return a ? 1 : 0;
			}).build();

		featureMainNode.addChild(activeNode);
		activeNode.addChild(activeValueNode);
	}

	public static void createFeatureOptionNodes(LiteralCommandNode<ServerCommandSource> featureMainNode, Class<? extends Config.ConfigEntry> featureClass, String featureCategory, String featureName, String optionName, String format) {
		LiteralCommandNode<ServerCommandSource> featureNode = createFeatureOptionNode(featureClass, featureCategory, featureName, optionName, format);
		featureMainNode.addChild(featureNode);

		Object option = getField(featureClass.cast(getField(ConfigHelper.getConfig(), featureName)), optionName);

		ArgumentCommandNode<ServerCommandSource, ?> featureValueNode;
		if (option instanceof Integer) {
			featureValueNode = createFeatureOptionIntegerValueNode(featureClass, featureCategory, featureName, optionName, format);
		} else if (option instanceof Float) {
			featureValueNode = createFeatureOptionFloatValueNode(featureClass, featureCategory, featureName, optionName, format);
		} else if (option instanceof Boolean) {
			featureValueNode = createFeatureOptionBooleanValueNode(featureClass, featureCategory, featureName, optionName);
		} else {
			LOGGER.debug("Found option \"" + optionName + "\" with unsupported type");
			return;
		}

		featureNode.addChild(featureValueNode);
	}

	private static LiteralCommandNode<ServerCommandSource> createFeatureOptionNode(Class<? extends Config.ConfigEntry> featureClass, String featureCategory, String featureName, String optionName, String format) {
		return CommandManager.literal(optionName)
			.executes(context -> {
				ConfigHelper.reloadConfig(context.getSource().getServer());
				Object currentValue = getField(featureClass.cast(getField(ConfigHelper.getConfig(), featureName)), optionName);
				String currentValueString = currentValue instanceof Boolean ? toDisplayableString((Boolean)currentValue) : String.format(format, currentValue);
				Object defaultValue = getField(featureClass.cast(getField(NEW_CONFIG, featureName)), optionName);
				String defaultValueString = defaultValue instanceof Boolean ? toDisplayableString((Boolean)defaultValue) : String.format(format, defaultValue);
				sendText(context, Text.translatable("difficultytweaker.option", Text.translatable("difficultytweaker." + featureCategory + "." + featureName + "." + optionName), defaultValueString, currentValueString));
				return 1;
			})
			.build();
	}

	private static ArgumentCommandNode<ServerCommandSource, Float> createFeatureOptionFloatValueNode(Class<? extends Config.ConfigEntry> featureClass, String featureCategory, String featureName, String optionName, String format) {
		return CommandManager.argument(optionName, FloatArgumentType.floatArg())
			.executes(context -> {
				float value = getFloat(context, optionName);
				value = (float)SPECIAL_VALUE_MODIFIERS.getOrDefault(featureCategory + "." + featureName + "." + optionName, v -> v).apply(value);

				BoundedFloat boundedFloat = null;
				try {
					boundedFloat = featureClass.getDeclaredField(optionName).getAnnotation(BoundedFloat.class);
				} catch (NoSuchFieldException ignored) {}
				if (boundedFloat != null) {
					if (value > boundedFloat.max())
						value = boundedFloat.max();
					if (value < boundedFloat.min())
						value = boundedFloat.min();
				}
				if (!setField(featureClass.cast(getField(ConfigHelper.getConfig(), featureName)), optionName, value)) {
					LOGGER.debug("Failed to set option \"" + optionName + "\" for feature \"" + featureName + "\"");
				}
				ConfigHelper.saveConfig(context.getSource().getServer());
				sendText(context, Text.translatable("difficultytweaker.feedback", String.format(format, value)));
				return 1;
			}).build();
	}

	private static ArgumentCommandNode<ServerCommandSource, Integer> createFeatureOptionIntegerValueNode(Class<? extends Config.ConfigEntry> featureClass, String featureCategory, String featureName, String optionName, String format) {
		return CommandManager.argument(optionName, IntegerArgumentType.integer())
			.executes(context -> {
				int value = getInteger(context, optionName);
				value = (int)SPECIAL_VALUE_MODIFIERS.getOrDefault(featureCategory + "." + featureName + "." + optionName, v -> v).apply(value);

				BoundedInteger boundedInteger = null;
				try {
					boundedInteger = featureClass.getDeclaredField(optionName).getAnnotation(BoundedInteger.class);
				} catch (NoSuchFieldException ignored) {}
				if (boundedInteger != null) {
					if (value > boundedInteger.max())
						value = boundedInteger.max();
					if (value < boundedInteger.min())
						value = boundedInteger.min();
				}
				if (!setField(featureClass.cast(getField(ConfigHelper.getConfig(), featureName)), optionName, value)) {
					LOGGER.debug("Failed to set option \"" + optionName + "\" for feature \"" + featureName + "\"");
				}
				ConfigHelper.saveConfig(context.getSource().getServer());
				sendText(context, Text.translatable("difficultytweaker.feedback", String.format(format, value)));
				return 1;
			}).build();
	}

	private static ArgumentCommandNode<ServerCommandSource, Boolean> createFeatureOptionBooleanValueNode(Class<? extends Config.ConfigEntry> featureClass, String featureCategory, String featureName, String optionName) {
		return CommandManager.argument(optionName, BoolArgumentType.bool())
			.executes(context -> {
				boolean value = getBool(context, optionName);
				value = (boolean)SPECIAL_VALUE_MODIFIERS.getOrDefault(featureCategory + "." + featureName + "." + optionName, v -> v).apply(value);

				if (!setField(featureClass.cast(getField(ConfigHelper.getConfig(), featureName)), optionName, value)) {
					LOGGER.debug("Failed to set option \"" + optionName + "\" for feature \"" + featureName + "\"");
				}
				ConfigHelper.saveConfig(context.getSource().getServer());
				sendText(context, Text.translatable("difficultytweaker.feedback", toDisplayableString(value)));
				return 1;
			}).build();
	}


	private static boolean setField(Object object, String fieldName, Object fieldValue) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(object, fieldValue);
				return true;
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static <V> V getField(Object object, String fieldName) {
    	Class<?> clazz = object.getClass();
    	while (clazz != null) {
    	    try {
    	        Field field = clazz.getDeclaredField(fieldName);
    	        field.setAccessible(true);
    	        return (V) field.get(object);
    	    } catch (NoSuchFieldException e) {
    	        clazz = clazz.getSuperclass();
    	    } catch (Exception e) {
    	        throw new IllegalStateException(e);
     	   }
    	}
    	return null;
	}


	private static void sendActiveStatus(CommandContext<ServerCommandSource> context, boolean active, String featureName) {
		sendText(context, Text.translatable(active ? "difficultytweaker.activated" : "difficultytweaker.deactivated", featureName));
	}

	private static String toDisplayableString(boolean bool) {
		return bool ? "§a§l[true]§r" : "§c§l[false]§r";
	}

	private static void sendText(CommandContext<ServerCommandSource> context, MutableText text) {
		context.getSource().sendFeedback(text, true);
	}
}