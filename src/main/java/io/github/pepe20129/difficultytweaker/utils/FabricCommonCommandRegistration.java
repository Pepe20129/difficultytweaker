package io.github.pepe20129.difficultytweaker.utils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;

public class FabricCommonCommandRegistration {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		LiteralCommandNode<ServerCommandSource> difficultyTweakerNode = CommandManager
				.literal("difficultyTweaker")
				.requires(source -> source.hasPermissionLevel(2))
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.main"));
					return 1;
				}).build();

		LiteralCommandNode<ServerCommandSource> entityNode = CommandManager
				.literal("entity")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity"));
					return 1;
				}).build();

		LiteralCommandNode<ServerCommandSource> worldNode = CommandManager
				.literal("world")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.world"));
					return 1;
				}).build();

		//region Local Difficulty
		LiteralCommandNode<ServerCommandSource> localDifficultyNode = CommandManager
				.literal("localDifficulty")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.world.localdifficulty"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> ldActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().localDifficulty.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> ldActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().localDifficulty.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "local difficulty");
					return 1;
				}).build();
		//endregion

		//region Start
		LiteralCommandNode<ServerCommandSource> ldStartNode = CommandManager
				.literal("start")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().localDifficulty.start + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.world.localdifficulty.start", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> ldStartVNode = CommandManager
				.argument("start", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "start");
					ConfigHelper.getConfig().localDifficulty.start = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion

		//region DayTime
		LiteralCommandNode<ServerCommandSource> ldDayTimeClampMaxNode = CommandManager
				.literal("dayTimeClampMax")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().localDifficulty.dayTimeClampMax + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.world.localdifficulty.daytimeclampmax", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> ldDayTimeClampMaxVNode = CommandManager
				.argument("dtcm", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "dtcm");
					ConfigHelper.getConfig().localDifficulty.dayTimeClampMax = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion

		//region Chunk
		LiteralCommandNode<ServerCommandSource> ldChunkClampMaxNode = CommandManager
				.literal("chunkClampMax")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().localDifficulty.chunkClampMax + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.world.localdifficulty.chunkclampmax", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> ldChunkClampMaxVNode = CommandManager
				.argument("ccm", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "ccm");
					ConfigHelper.getConfig().localDifficulty.chunkClampMax = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion

		//region Moon
		LiteralCommandNode<ServerCommandSource> ldMoonNode = CommandManager
				.literal("moon")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().localDifficulty.moon + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.world.localdifficulty.moon", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> ldMoonVNode = CommandManager
				.argument("moon", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "moon");
					ConfigHelper.getConfig().localDifficulty.moon = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Bee
		LiteralCommandNode<ServerCommandSource> beeNode = CommandManager
				.literal("bee")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity.bee"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> beeActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().bee.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> beeActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().bee.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "bee");
					return 1;
				}).build();
		//endregion

		//region Length
		LiteralCommandNode<ServerCommandSource> beeLengthNode = CommandManager
				.literal("length")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().bee.length + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.bee.length", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Integer> beeLengthVNode = CommandManager
				.argument("length", IntegerArgumentType.integer())
				.executes(context -> {
					int a = getInteger(context, "length");
					ConfigHelper.getConfig().bee.length = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region CaveSpider
		LiteralCommandNode<ServerCommandSource> caveSpiderNode = CommandManager
				.literal("caveSpider")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity.cavespider"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> caveSpiderActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().caveSpider.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> caveSpiderActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().caveSpider.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "cave spider");
					return 1;
				}).build();
		//endregion

		//region Length
		LiteralCommandNode<ServerCommandSource> caveSpiderLengthNode = CommandManager
				.literal("length")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().caveSpider.length + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.cavespider.length", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Integer> caveSpiderLengthVNode = CommandManager
				.argument("length", IntegerArgumentType.integer())
				.executes(context -> {
					int a = getInteger(context, "length");
					ConfigHelper.getConfig().caveSpider.length = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Skeleton
		LiteralCommandNode<ServerCommandSource> skeletonNode = CommandManager
				.literal("skeleton")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity.skeleton"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> skeletonActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().skeleton.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> skeletonActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().skeleton.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "skeleton");
					return 1;
				}).build();
		//endregion

		//region Divergence
		LiteralCommandNode<ServerCommandSource> skeletonDivergenceNode = CommandManager
				.literal("divergence")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().skeleton.divergence + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.skeleton.divergence", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Integer> skeletonDivergenceVNode = CommandManager
				.argument("divergence", IntegerArgumentType.integer())
				.executes(context -> {
					int a = getInteger(context, "divergence");
					ConfigHelper.getConfig().skeleton.divergence = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion

		//region Cooldown
		LiteralCommandNode<ServerCommandSource> skeletonCooldownNode = CommandManager
				.literal("cooldown")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().skeleton.cooldown + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.skeleton.cooldown", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Integer> skeletonCooldownVNode = CommandManager
				.argument("cooldown", IntegerArgumentType.integer())
				.executes(context -> {
					int a = getInteger(context, "cooldown");
					ConfigHelper.getConfig().skeleton.cooldown = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Fire
		LiteralCommandNode<ServerCommandSource> fireNode = CommandManager
				.literal("fire")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.world.fire"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> fireActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().fire.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> fireActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().fire.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "fire");
					return 1;
				}).build();
		//endregion

		//region Encouragement
		LiteralCommandNode<ServerCommandSource> fireEncouragementNode = CommandManager
				.literal("encouragement")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().fire.encouragement + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.world.fire.encouragement", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Integer> fireEncouragementVNode = CommandManager
				.argument("encouragement", IntegerArgumentType.integer())
				.executes(context -> {
					int a = getInteger(context, "encouragement");
					ConfigHelper.getConfig().fire.encouragement = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Nether Portal
		LiteralCommandNode<ServerCommandSource> netherPortalNode = CommandManager
				.literal("netherPortal")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.world.netherportal"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> netherPortalActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().netherPortal.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> netherPortalActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().netherPortal.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "nether portal");
					return 1;
				}).build();
		//endregion

		//region Probability
		LiteralCommandNode<ServerCommandSource> netherPortalProbNode = CommandManager
				.literal("probability")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().netherPortal.probability + "/2000]§r";
					sendText(context, new TranslatableText("difficultytweaker.world.netherportal.probability", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Integer> netherPortalProbVNode = CommandManager
				.argument("probability", IntegerArgumentType.integer())
				.executes(context -> {
					int a = getInteger(context, "probability");
					ConfigHelper.getConfig().netherPortal.probability = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a + "/2000"));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Mob
		LiteralCommandNode<ServerCommandSource> mobNode = CommandManager
				.literal("mob")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity.mob"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> mobActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().mob.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> mobActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().mob.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "mob armor");
					return 1;
				}).build();
		//endregion

		//region Armor Probability
		LiteralCommandNode<ServerCommandSource> mobArmorValueNode = CommandManager
				.literal("prob")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().mob.armorProbability + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.mob.armorprobability", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> mobArmorValueVNode = CommandManager
				.argument("value", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "value");
					ConfigHelper.getConfig().mob.armorProbability = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion

		//region Fall
		LiteralCommandNode<ServerCommandSource> mobFallNode = CommandManager
				.literal("fall")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().mob.fall + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.mob.fall", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> mobFallVNode = CommandManager
				.argument("value", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "value");
					if (a > 1)
						a = 1;
					if (a < 0)
						a = 0;
					ConfigHelper.getConfig().mob.fall = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Projectile
		LiteralCommandNode<ServerCommandSource> projectileNode = CommandManager
				.literal("projectile")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity.projectile"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> projectileActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().projectile.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> projectileActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().projectile.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "projectile");
					return 1;
				}).build();
		//endregion

		//region Value
		LiteralCommandNode<ServerCommandSource> projectileValueNode = CommandManager
				.literal("value")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().projectile.bonus + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.projectile.damage", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> projectileValueVNode = CommandManager
				.argument("value", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "value");
					ConfigHelper.getConfig().projectile.bonus = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Player
		LiteralCommandNode<ServerCommandSource> playerNode = CommandManager
				.literal("player")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity.player"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> playerActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().player.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> playerActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().player.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "player");
					return 1;
				}).build();
		//endregion

		//region Multiplier
		LiteralCommandNode<ServerCommandSource> playerMultiplierNode = CommandManager
				.literal("value")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().player.multiplier + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.player.multiplier", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> playerMultiplierVNode = CommandManager
				.argument("value", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "value");
					ConfigHelper.getConfig().player.multiplier = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Raid
		LiteralCommandNode<ServerCommandSource> raidNode = CommandManager
				.literal("raid")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.world.raid"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> raidActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().raid.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> raidActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().raid.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "raid");
					return 1;
				}).build();
		//endregion

		//region Count
		LiteralCommandNode<ServerCommandSource> raidCountNode = CommandManager
				.literal("value")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().raid.count + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.world.raid.count", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Integer> raidCountVNode = CommandManager
				.argument("value", IntegerArgumentType.integer())
				.executes(context -> {
					int a = getInteger(context, "value");
					if (a > 15)
						a = 15;
					ConfigHelper.getConfig().raid.count = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Wither Skull
		LiteralCommandNode<ServerCommandSource> witherSkullNode = CommandManager
				.literal("witherSkull")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity.witherskull"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> witherSkullActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().witherSkull.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> witherSkullActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().witherSkull.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "wither skull");
					return 1;
				}).build();
		//endregion

		//region Length
		LiteralCommandNode<ServerCommandSource> witherSkullLengthNode = CommandManager
				.literal("value")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().witherSkull.length + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.witherskull.length", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Integer> witherSkullLengthVNode = CommandManager
				.argument("value", IntegerArgumentType.integer())
				.executes(context -> {
					int a = getInteger(context, "value");
					ConfigHelper.getConfig().witherSkull.length = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Zombie
		LiteralCommandNode<ServerCommandSource> zombieNode = CommandManager
				.literal("zombie")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity.zombie"));
					return 1;
				}).build();


		//region Active
		LiteralCommandNode<ServerCommandSource> zombieActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().zombie.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> zombieActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().zombie.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "zombie");
					return 1;
				}).build();
		//endregion

		//region Chance
		LiteralCommandNode<ServerCommandSource> zombieChanceNode = CommandManager
				.literal("value")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().zombie.weaponChance + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.zombie.chance", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Integer> zombieChanceVNode = CommandManager
				.argument("value", IntegerArgumentType.integer())
				.executes(context -> {
					int a = getInteger(context, "value");
					ConfigHelper.getConfig().zombie.weaponChance = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Guardian
		LiteralCommandNode<ServerCommandSource> guardianNode = CommandManager
				.literal("guardian")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity.guardian"));
					return 1;
				}).build();

		//region Active
		LiteralCommandNode<ServerCommandSource> guardianActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().guardian.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> guardianActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().guardian.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "guardian");
					return 1;
				}).build();
		//endregion

		//region Value
		LiteralCommandNode<ServerCommandSource> guardianValueNode = CommandManager
				.literal("value")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().guardian.amount + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.guardian.value", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> guardianValueVNode = CommandManager
				.argument("value", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "value");
					ConfigHelper.getConfig().guardian.amount = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Phantom
		LiteralCommandNode<ServerCommandSource> phantomNode = CommandManager
				.literal("phantom")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity.phantom"));
					return 1;
				}).build();

		//region Active
		LiteralCommandNode<ServerCommandSource> phantomActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().phantom.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> phantomActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().phantom.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "phantom");
					return 1;
				}).build();
		//endregion

		//region Min
		LiteralCommandNode<ServerCommandSource> phantomMinNode = CommandManager
				.literal("min")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().phantom.min + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.phantom.min", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Integer> phantomMinVNode = CommandManager
				.argument("value", IntegerArgumentType.integer())
				.executes(context -> {
					int a = getInteger(context, "value");
					if (a > ConfigHelper.getConfig().phantom.max)
						a = ConfigHelper.getConfig().phantom.max;
					ConfigHelper.getConfig().phantom.min = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion

		//region Max
		LiteralCommandNode<ServerCommandSource> phantomMaxNode = CommandManager
				.literal("max")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().phantom.max + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.phantom.max", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Integer> phantomMaxVNode = CommandManager
				.argument("value", IntegerArgumentType.integer())
				.executes(context -> {
					int a = getInteger(context, "value");
					if (a < ConfigHelper.getConfig().phantom.min)
						a = ConfigHelper.getConfig().phantom.min;
					ConfigHelper.getConfig().phantom.max = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Clamped Local Difficulty
		LiteralCommandNode<ServerCommandSource> clampedLocalDifficultyNode = CommandManager
				.literal("clampedLocalDifficulty")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.world.clampedlocaldifficulty"));
					return 1;
				}).build();

		//region Active
		LiteralCommandNode<ServerCommandSource> cldActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().clampedLocalDifficulty.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> cldActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().clampedLocalDifficulty.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "clamped local difficulty");
					return 1;
				}).build();
		//endregion

		//region Min Clamp Lim
		LiteralCommandNode<ServerCommandSource> cldMinClampLimNode = CommandManager
				.literal("minClampLim")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().clampedLocalDifficulty.minClampLim + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.world.clampedlocaldifficulty.minclampedlim", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> cldMinClampLimVNode = CommandManager
				.argument("value", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "value");
					ConfigHelper.getConfig().clampedLocalDifficulty.minClampLim = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion

		//region Max Clamp Lim
		LiteralCommandNode<ServerCommandSource> cldMaxClampLimNode = CommandManager
				.literal("maxClampLim")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().clampedLocalDifficulty.maxClampLim + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.world.clampedlocaldifficulty.maxclampedlim", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> cldMaxClampLimVNode = CommandManager
				.argument("value", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "value");
					ConfigHelper.getConfig().clampedLocalDifficulty.maxClampLim = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion

		//region Min Clamp Value
		LiteralCommandNode<ServerCommandSource> cldMinClampNode = CommandManager
				.literal("minClamp")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().clampedLocalDifficulty.minClamp + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.world.clampedlocaldifficulty.minclamped", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> cldMinClampVNode = CommandManager
				.argument("value", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "value");
					ConfigHelper.getConfig().clampedLocalDifficulty.minClamp = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion

		//region Max Clamp Value
		LiteralCommandNode<ServerCommandSource> cldMaxClampNode = CommandManager
				.literal("maxClamp")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().clampedLocalDifficulty.maxClamp + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.world.clampedlocaldifficulty.maxclamped", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> cldMaxClampVNode = CommandManager
				.argument("value", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "value");
					ConfigHelper.getConfig().clampedLocalDifficulty.maxClamp = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion

		//region Zombie Villager
		LiteralCommandNode<ServerCommandSource> zombieVillagerNode = CommandManager
				.literal("zombieVillager")
				.executes(context -> {
					sendText(context, new TranslatableText("difficultytweaker.entity.zombieVillager"));
					return 1;
				}).build();

		//region Active
		LiteralCommandNode<ServerCommandSource> zombieVillagerActiveNode = CommandManager
				.literal("active")
				.executes(context -> {
					String a = toDisplayableString(ConfigHelper.getConfig().zombieVillager.active);
					sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Boolean> zombieVillagerActiveVNode = CommandManager
				.argument("active", BoolArgumentType.bool())
				.executes(context -> {
					boolean a = getBool(context, "active");
					ConfigHelper.getConfig().zombieVillager.active = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendActiveStatus(context, a, "zombie villager");
					return 1;
				}).build();
		//endregion

		//region Chance
		LiteralCommandNode<ServerCommandSource> zombieVillagerChanceNode = CommandManager
				.literal("chance")
				.executes(context -> {
					String a = "§l[" + ConfigHelper.getConfig().zombieVillager.chance + "]§r";
					sendText(context, new TranslatableText("difficultytweaker.entity.zombievillager.chance", a));
					return 1;
				}).build();

		ArgumentCommandNode<ServerCommandSource, Float> zombieVillagerChanceVNode = CommandManager
				.argument("value", FloatArgumentType.floatArg())
				.executes(context -> {
					float a = getFloat(context, "value");
					ConfigHelper.getConfig().zombieVillager.chance = a;
					ConfigHelper.saveConfig(context.getSource().getServer());
					sendText(context, new TranslatableText("difficultytweaker.feedback", a));
					return 1;
				}).build();
		//endregion
		//endregion


		//region Command parenting
		//There has to be a better way to do this.
		dispatcher.getRoot().addChild(difficultyTweakerNode);

		difficultyTweakerNode.addChild(entityNode);
		difficultyTweakerNode.addChild(worldNode);

		worldNode.addChild(localDifficultyNode);
		localDifficultyNode.addChild(ldActiveNode);
		ldActiveNode.addChild(ldActiveVNode);
		localDifficultyNode.addChild(ldStartNode);
		ldStartNode.addChild(ldStartVNode);
		localDifficultyNode.addChild(ldDayTimeClampMaxNode);
		ldDayTimeClampMaxNode.addChild(ldDayTimeClampMaxVNode);
		localDifficultyNode.addChild(ldChunkClampMaxNode);
		ldChunkClampMaxNode.addChild(ldChunkClampMaxVNode);
		localDifficultyNode.addChild(ldMoonNode);
		ldMoonNode.addChild(ldMoonVNode);

		entityNode.addChild(beeNode);
		beeNode.addChild(beeActiveNode);
		beeActiveNode.addChild(beeActiveVNode);
		beeNode.addChild(beeLengthNode);
		beeLengthNode.addChild(beeLengthVNode);

		entityNode.addChild(caveSpiderNode);
		caveSpiderNode.addChild(caveSpiderActiveNode);
		caveSpiderActiveNode.addChild(caveSpiderActiveVNode);
		caveSpiderNode.addChild(caveSpiderLengthNode);
		caveSpiderLengthNode.addChild(caveSpiderLengthVNode);

		entityNode.addChild(skeletonNode);
		skeletonNode.addChild(skeletonActiveNode);
		skeletonActiveNode.addChild(skeletonActiveVNode);
		skeletonNode.addChild(skeletonDivergenceNode);
		skeletonDivergenceNode.addChild(skeletonDivergenceVNode);
		skeletonNode.addChild(skeletonCooldownNode);
		skeletonCooldownNode.addChild(skeletonCooldownVNode);

		worldNode.addChild(fireNode);
		fireNode.addChild(fireActiveNode);
		fireActiveNode.addChild(fireActiveVNode);
		fireNode.addChild(fireEncouragementNode);
		fireEncouragementNode.addChild(fireEncouragementVNode);

		worldNode.addChild(netherPortalNode);
		netherPortalNode.addChild(netherPortalActiveNode);
		netherPortalActiveNode.addChild(netherPortalActiveVNode);
		netherPortalNode.addChild(netherPortalProbNode);
		netherPortalProbNode.addChild(netherPortalProbVNode);

		entityNode.addChild(mobNode);
		mobNode.addChild(mobActiveNode);
		mobActiveNode.addChild(mobActiveVNode);
		mobNode.addChild(mobArmorValueNode);
		mobArmorValueNode.addChild(mobArmorValueVNode);
		mobNode.addChild(mobFallNode);
		mobFallNode.addChild(mobFallVNode);

		entityNode.addChild(projectileNode);
		projectileNode.addChild(projectileActiveNode);
		projectileActiveNode.addChild(projectileActiveVNode);
		projectileNode.addChild(projectileValueNode);
		projectileValueNode.addChild(projectileValueVNode);

		entityNode.addChild(playerNode);
		playerNode.addChild(playerActiveNode);
		playerActiveNode.addChild(playerActiveVNode);
		playerNode.addChild(playerMultiplierNode);
		playerMultiplierNode.addChild(playerMultiplierVNode);

		worldNode.addChild(raidNode);
		raidNode.addChild(raidActiveNode);
		raidActiveNode.addChild(raidActiveVNode);
		raidNode.addChild(raidCountNode);
		raidCountNode.addChild(raidCountVNode);

		entityNode.addChild(witherSkullNode);
		witherSkullNode.addChild(witherSkullActiveNode);
		witherSkullActiveNode.addChild(witherSkullActiveVNode);
		witherSkullNode.addChild(witherSkullLengthNode);
		witherSkullLengthNode.addChild(witherSkullLengthVNode);

		entityNode.addChild(zombieNode);
		zombieNode.addChild(zombieActiveNode);
		zombieActiveNode.addChild(zombieActiveVNode);
		zombieNode.addChild(zombieChanceNode);
		zombieChanceNode.addChild(zombieChanceVNode);

		entityNode.addChild(guardianNode);
		guardianNode.addChild(guardianActiveNode);
		guardianActiveNode.addChild(guardianActiveVNode);
		guardianNode.addChild(guardianValueNode);
		guardianValueNode.addChild(guardianValueVNode);

		entityNode.addChild(phantomNode);
		phantomNode.addChild(phantomActiveNode);
		phantomActiveNode.addChild(phantomActiveVNode);
		phantomNode.addChild(phantomMinNode);
		phantomMinNode.addChild(phantomMinVNode);
		phantomNode.addChild(phantomMaxNode);
		phantomMaxNode.addChild(phantomMaxVNode);

		entityNode.addChild(zombieVillagerNode);
		zombieVillagerNode.addChild(zombieVillagerActiveNode);
		zombieVillagerActiveNode.addChild(zombieVillagerActiveVNode);
		zombieVillagerNode.addChild(zombieVillagerChanceNode);
		zombieVillagerChanceNode.addChild(zombieVillagerChanceVNode);

		worldNode.addChild(clampedLocalDifficultyNode);
		clampedLocalDifficultyNode.addChild(cldActiveNode);
		cldActiveNode.addChild(cldActiveVNode);
		clampedLocalDifficultyNode.addChild(cldMinClampLimNode);
		cldMinClampLimNode.addChild(cldMinClampLimVNode);
		clampedLocalDifficultyNode.addChild(cldMaxClampLimNode);
		cldMaxClampLimNode.addChild(cldMaxClampLimVNode);
		clampedLocalDifficultyNode.addChild(cldMinClampNode);
		cldMinClampNode.addChild(cldMinClampVNode);
		clampedLocalDifficultyNode.addChild(cldMaxClampNode);
		cldMaxClampNode.addChild(cldMaxClampVNode);
		//endregion
	}

	private static void sendActiveStatus(CommandContext<ServerCommandSource> context, boolean active, String name) {
		sendText(context, new TranslatableText(active ? "difficultytweaker.activated" : "difficultytweaker.deactivated", name));
	}

	private static String toDisplayableString(boolean bool) {
		return bool ? "§a§l[true]§r" : "§c§l[false]§r";
	}

	private static void sendText(CommandContext<ServerCommandSource> context, TranslatableText text) {
		context.getSource().sendFeedback(text, true);
	}
}