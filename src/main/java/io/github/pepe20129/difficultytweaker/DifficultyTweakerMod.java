package io.github.pepe20129.difficultytweaker;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;

public class DifficultyTweakerMod implements ModInitializer {
	public void sendText(CommandContext<ServerCommandSource> context, String string) throws CommandSyntaxException {
		context.getSource().getMinecraftServer().getPlayerManager().broadcastChatMessage(Text.of(string), MessageType.CHAT, context.getSource().getPlayer().getUuid());
	}

	@Override
	public void onInitialize() {
		//Brigadier nightmare.
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			LiteralCommandNode<ServerCommandSource> extraDifficultyNode = CommandManager
					.literal("difficultyTweaker")
					.requires(source -> source.hasPermissionLevel(2))
					.executes(context -> {
						sendText(context, "\nThe main command of the §lDifficulty Tweaker§r mod.\nIt currently allows you to change values that are used on this mod's mixins and turn them on or off.\n§c§l§nThis command is not finished yet!§r\n§7§oIt doesn't even save upon leaving the world yet. This means if you want different settings from the default ones you'll have to change them every time you load the world.");
						return 1;
					}).build();

		//region LocalDifficulty
			LiteralCommandNode<ServerCommandSource> localDifficultyNode = CommandManager
					.literal("localDifficulty")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change the parameters of the local difficulty mixin.");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> ldActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.ldActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> ldActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.ldActive = a;
						if(a) {
							sendText(context, "\nThe local difficulty mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe local difficulty mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Start
			LiteralCommandNode<ServerCommandSource> ldStartNode = CommandManager
					.literal("start")
					.executes(context -> {
						String a = "§l[" + CommandVars.ldStart + "]§r";
						sendText(context, "\nSets the starting value for the mixin.\nDefault value: §l[0.75]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> ldStartVNode = CommandManager
					.argument("start", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "start");
						CommandVars.ldStart = a;
						sendText(context, "\nThe local difficulty mixin's start value is now " + a);
						return 1;
					}).build();
			//endregion

			//region DayTime
			LiteralCommandNode<ServerCommandSource> ldDayTimeClampMaxNode = CommandManager
					.literal("dayTimeClampMax")
					.executes(context -> {
						String a = "§l[" + CommandVars.ldDayTimeClampMax + "]§r";
						sendText(context, "\nSets the maximum clamping value of the day time factor for the mixin.\nDefault value: §l[1.0]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> ldDayTimeClampMaxVNode = CommandManager
					.argument("dtcm", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "dtcm");
						CommandVars.ldDayTimeClampMax = a;
						sendText(context, "\nThe local difficulty mixin's maximum clamping value of the day time factor is now " + a);
						return 1;
					}).build();
			//endregion

			//region Chunk
			LiteralCommandNode<ServerCommandSource> ldChunkClampMaxNode = CommandManager
					.literal("chunkClampMax")
					.executes(context -> {
						String a = "§l[" + CommandVars.ldChunkClampMax + "]§r";
						sendText(context, "\nSets the maximum clamping value of the chunk factor for the mixin.\nDefault value: §l[1.0]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> ldChunkClampMaxVNode = CommandManager
					.argument("ccm", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "ccm");
						CommandVars.ldChunkClampMax = a;
						sendText(context, "\nThe local difficulty mixin's maximum clamping value of the chunk factor is now " + a);
						return 1;
					}).build();
			//endregion

			//region Moon
			LiteralCommandNode<ServerCommandSource> ldMoonNode = CommandManager
					.literal("moon")
					.executes(context -> {
						String a = "§l[" + CommandVars.ldMoon + "]§r";
						sendText(context, "\nSets the value that the moon factor is multiplied by in the mixin.\nDefault value: §l[0.25]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> ldMoonVNode = CommandManager
					.argument("moon", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "moon");
						CommandVars.ldMoon = a;
						sendText(context, "\nThe local difficulty mixin's factor multiplier is now " + a);
						return 1;
					}).build();
			//endregion
		//endregion

		//region Bee
			LiteralCommandNode<ServerCommandSource> beeNode = CommandManager
					.literal("bee")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change the length of bees' poison.");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> beeActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.beeActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> beeActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.beeActive = a;
						if(a) {
							sendText(context, "\nThe bee mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe bee mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Length
			LiteralCommandNode<ServerCommandSource> beeLengthNode = CommandManager
					.literal("length")
					.executes(context -> {
						String a = "§l[" + CommandVars.beeLength + "]§r";
						sendText(context, "\nSets the seconds that bees poison you for.\nDefault value: §l[18]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Integer> beeLengthVNode = CommandManager
					.argument("length", IntegerArgumentType.integer())
					.executes(context -> {
						int a = getInteger(context, "length");
						CommandVars.beeLength = a;
						sendText(context, "\nThe bee poison length is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region CaveSpider
			LiteralCommandNode<ServerCommandSource> caveSpiderNode = CommandManager
					.literal("caveSpider")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change the length of cave spiders' poison.");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> caveSpiderActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.caveSpiderActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> caveSpiderActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.caveSpiderActive = a;
						if(a) {
							sendText(context, "\nThe cave spider mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe cave spider mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Length
			LiteralCommandNode<ServerCommandSource> caveSpiderLengthNode = CommandManager
					.literal("length")
					.executes(context -> {
						String a = "§l[" + CommandVars.caveSpiderLength + "]§r";
						sendText(context, "\nSets the seconds that cave spiders poison you for.\nDefault value: §l[15]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Integer> caveSpiderLengthVNode = CommandManager
					.argument("length", IntegerArgumentType.integer())
					.executes(context -> {
						int a = getInteger(context, "length");
						CommandVars.caveSpiderLength = a;
						sendText(context, "\nThe cave spider poison length is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Skeleton
			LiteralCommandNode<ServerCommandSource> skeletonNode = CommandManager
					.literal("skeleton")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change values about skeletons.");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> skeletonActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.skeletonActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> skeletonActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.skeletonActive = a;
						if(a) {
							sendText(context, "\nThe skeleton mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe skeleton mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Divergence
			LiteralCommandNode<ServerCommandSource> skeletonDivergenceNode = CommandManager
					.literal("divergence")
					.executes(context -> {
						String a = "§l[" + CommandVars.skeletonDivergence + "]§r";
						sendText(context, "\nSets skeletons' arrows divergence.\nDefault value: §l[2]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Integer> skeletonDivergenceVNode = CommandManager
					.argument("divergence", IntegerArgumentType.integer())
					.executes(context -> {
						int a = getInteger(context, "divergence");
						CommandVars.skeletonDivergence = a;
						sendText(context, "\nSkeletons' arrows divergence is §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion

			//region Cooldown
			LiteralCommandNode<ServerCommandSource> skeletonCooldownNode = CommandManager
					.literal("cooldown")
					.executes(context -> {
						String a = "§l[" + CommandVars.skeletonCooldown + "]§r";
						sendText(context, "\nSets skeletons' cooldown.\nDefault value: §l[20]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Integer> skeletonCooldownVNode = CommandManager
					.argument("cooldown", IntegerArgumentType.integer())
					.executes(context -> {
						int a = getInteger(context, "cooldown");
						CommandVars.skeletonCooldown = a;
						sendText(context, "\nSkeletons' cooldown is §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Fire
			LiteralCommandNode<ServerCommandSource> fireNode = CommandManager
					.literal("fire")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change the extra encouragement of fire.");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> fireActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.fireActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> fireActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.fireActive = a;
						if(a) {
							sendText(context, "\nThe fire mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe fire mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Encouragement
			LiteralCommandNode<ServerCommandSource> fireEncouragementNode = CommandManager
					.literal("encouragement")
					.executes(context -> {
						String a = "§l[" + CommandVars.fireEncouragement + "]§r";
						sendText(context, "\nSets the extra value of encouragement for fire before accounting for rain & environmental factors.\nDefault value: §l[61]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Integer> fireEncouragementVNode = CommandManager
					.argument("encouragement", IntegerArgumentType.integer())
					.executes(context -> {
						int a = getInteger(context, "encouragement");
						CommandVars.fireEncouragement = a;
						sendText(context, "\nThe extra fire encouragement is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Nether Portal
			LiteralCommandNode<ServerCommandSource> netherPortalNode = CommandManager
					.literal("netherPortal")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change the probability of zombified piglin spawning on nether portals.");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> netherPortalActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.netherPortalActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> netherPortalActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.netherPortalActive = a;
						if(a) {
							sendText(context, "\nThe nether portal mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe nether portal mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Probability
			LiteralCommandNode<ServerCommandSource> netherPortalProbNode = CommandManager
					.literal("probability")
					.executes(context -> {
						String a = "§l[" + CommandVars.netherPortalProb + "/2000]§r";
						sendText(context, "\nSets the probability of a zombified piglin spawning on a nether portal.\nDefault value: §l[3/2000]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Integer> netherPortalProbVNode = CommandManager
					.argument("probability", IntegerArgumentType.integer())
					.executes(context -> {
						int a = getInteger(context, "probability");
						CommandVars.netherPortalProb = a;
						sendText(context, "\nThe probability is now §l[" + a + "/2000]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Mob
			LiteralCommandNode<ServerCommandSource> mobNode = CommandManager
					.literal("mob")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change parameters of mobEntity mixins.\nYou can change the distance mobs are willing to fall & the probability of the method that decides the armor of mobs to break.\nTLDR: The lower this value is the more armor that mobs will spawn with.");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> mobActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.mobActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> mobActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.mobActive = a;
						if(a) {
							sendText(context, "\nThe mob armor mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe mob armor mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Armor Probability
			LiteralCommandNode<ServerCommandSource> mobArmorValueNode = CommandManager
					.literal("prob")
					.executes(context -> {
						String a = "§l[" + CommandVars.mobArmorProb + "]§r";
						sendText(context, "\nSets the value used for the mob armor calculation.\nDefault value: §l[0.1]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> mobArmorValueVNode = CommandManager
					.argument("value", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "value");
						CommandVars.mobArmorProb = a;
						sendText(context, "\nThe probability is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion

			//region Fall
			LiteralCommandNode<ServerCommandSource> mobFallNode = CommandManager
					.literal("fall")
					.executes(context -> {
						String a = "§l[" + CommandVars.mobFall + "]§r";
						sendText(context, "\nSets the distance mobs are willing to fall in % from their max health (0-1).\nDefault value: §l[0.33]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> mobFallVNode = CommandManager
					.argument("value", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "value");
						if(a > 1)
							a = 1;
						if(a < 0)
							a = 0;
						CommandVars.mobFall = a;
						sendText(context, "\nThe value is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Projectile
			LiteralCommandNode<ServerCommandSource> projectileNode = CommandManager
					.literal("projectile")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change the extra damage value added to projectiles.");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> projectileActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.projectileActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> projectileActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.projectileActive = a;
						if(a) {
							sendText(context, "\nThe projectile mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe projectile mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Value
			LiteralCommandNode<ServerCommandSource> projectileValueNode = CommandManager
					.literal("value")
					.executes(context -> {
						String a = "§l[" + CommandVars.projectileBonus + "]§r";
						sendText(context, "\nSets the bonus damage for projectiles.\nDefault value: §l[0.33]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> projectileValueVNode = CommandManager
					.argument("value", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "value");
						CommandVars.projectileBonus = a;
						sendText(context, "\nThe damage bonus is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Player
			LiteralCommandNode<ServerCommandSource> playerNode = CommandManager
					.literal("player")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change the multiplier of damage taken by a player.");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> playerActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.playerActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> playerActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.playerActive = a;
						if(a) {
							sendText(context, "\nThe player mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe player mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Multiplier
			LiteralCommandNode<ServerCommandSource> playerMultiplierNode = CommandManager
					.literal("value")
					.executes(context -> {
						String a = "§l[" + CommandVars.playerMultiplier + "]§r";
						sendText(context, "\nSets the bonus dmg for projectiles.\nDefault value: §l[1.0]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> playerMultiplierVNode = CommandManager
					.argument("value", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "value");
						CommandVars.playerMultiplier = a;
						sendText(context, "\nThe damage multiplier is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Raid
			LiteralCommandNode<ServerCommandSource> raidNode = CommandManager
					.literal("raid")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change the number of waves that raids have (capped at 15).");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> raidActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.raidActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> raidActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.raidActive = a;
						if(a) {
							sendText(context, "\nThe raid mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe raid mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Count
			LiteralCommandNode<ServerCommandSource> raidCountNode = CommandManager
					.literal("value")
					.executes(context -> {
						String a = "§l[" + CommandVars.raidCount + "]§r";
						sendText(context, "\nSets the amount of waves (capped to 15).\nDefault value: §l[7]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Integer> raidCountVNode = CommandManager
					.argument("value", IntegerArgumentType.integer())
					.executes(context -> {
						int a = getInteger(context, "value");
						if (a > 15)
							a = 15;
						CommandVars.raidCount = a;
						sendText(context, "\nThe raid count is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Wither Skull
			LiteralCommandNode<ServerCommandSource> witherSkullNode = CommandManager
					.literal("witherSkull")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change length of wither skulls' wither effect.");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> witherSkullActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.witherSkullActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> witherSkullActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.witherSkullActive = a;
						if(a) {
							sendText(context, "\nThe wither skull mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe wither skull mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Length
			LiteralCommandNode<ServerCommandSource> witherSkullLengthNode = CommandManager
					.literal("value")
					.executes(context -> {
						String a = "§l[" + CommandVars.witherSkullLength + "]§r";
						sendText(context, "\nSets the effect's length.\nDefault value: §l[40]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Integer> witherSkullLengthVNode = CommandManager
					.argument("value", IntegerArgumentType.integer())
					.executes(context -> {
						int a = getInteger(context, "value");
						CommandVars.witherSkullLength = a;
						sendText(context, "\nThe effect's length is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Zombie
			LiteralCommandNode<ServerCommandSource> zombieNode = CommandManager
					.literal("zombie")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change probability of zombies spawning with weapons..");
						return 1;
					}).build();


			//region Active
			LiteralCommandNode<ServerCommandSource> zombieActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.zombieActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> zombieActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.zombieActive = a;
						if(a) {
							sendText(context, "\nThe zombie mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe zombie mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Chance
			LiteralCommandNode<ServerCommandSource> zombieChanceNode = CommandManager
					.literal("value")
					.executes(context -> {
						String a = "§l[" + CommandVars.zombieWeaponChance + "]§r";
						sendText(context, "\nSets the effect's length.\nDefault value: §l[0.1]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Integer> zombieChanceVNode = CommandManager
					.argument("value", IntegerArgumentType.integer())
					.executes(context -> {
						int a = getInteger(context, "value");
						CommandVars.zombieWeaponChance = a;
						sendText(context, "\nThe weapon chance is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Guardian
			LiteralCommandNode<ServerCommandSource> guardianNode = CommandManager
					.literal("guardian")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change parameters from the guardian mixin.");
						return 1;
					}).build();

			//region Active
			LiteralCommandNode<ServerCommandSource> guardianActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.guardianActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> guardianActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.guardianActive = a;
						if(a) {
							sendText(context, "\nThe guardian mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe guardian mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Value
			LiteralCommandNode<ServerCommandSource> guardianValueNode = CommandManager
					.literal("value")
					.executes(context -> {
						String a = "§l[" + CommandVars.guardianAmount + "]§r";
						sendText(context, "\nSets the bonus damage for guardian laser before damage scaling.\nDefault value: §l[0.0]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> guardianValueVNode = CommandManager
					.argument("value", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "value");
						CommandVars.guardianAmount = a;
						sendText(context, "\nThe damage bonus is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Phantom
			LiteralCommandNode<ServerCommandSource> phantomNode = CommandManager
					.literal("phantom")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change parameters from the phantom mixin.");
						return 1;
					}).build();

			//region Active
			LiteralCommandNode<ServerCommandSource> phantomActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.phantomActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> phantomActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.phantomActive = a;
						if(a) {
							sendText(context, "\nThe phantom mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe phantom mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Min
			LiteralCommandNode<ServerCommandSource> phantomMinNode = CommandManager
					.literal("min")
					.executes(context -> {
						String a = "§l[" + CommandVars.phantomMin + "]§r";
						sendText(context, "\nSets the minimum amount of phantoms that spawn in each group.\nForced to be smaller than the maximum value.\nDefault value: §l[1]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Integer> phantomMinVNode = CommandManager
					.argument("value", IntegerArgumentType.integer())
					.executes(context -> {
						int a = getInteger(context, "value");
						if(a >= CommandVars.phantomMax)
							a = CommandVars.phantomMax - 1;
						CommandVars.phantomMin = a;
						sendText(context, "\nThe minimum amount is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion

			//region Max
			LiteralCommandNode<ServerCommandSource> phantomMaxNode = CommandManager
					.literal("max")
					.executes(context -> {
						String a = "§l[" + CommandVars.phantomMax + "]§r";
						sendText(context, "\nSets the maximum amount of phantoms that spawn in each group.\nForced to be bigger than the minimum value.\nDefault value: §l[2]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Integer> phantomMaxVNode = CommandManager
					.argument("value", IntegerArgumentType.integer())
					.executes(context -> {
						int a = getInteger(context, "value");
						if(a <= CommandVars.phantomMin)
							a = CommandVars.phantomMin + 1;
						CommandVars.phantomMax = a;
						sendText(context, "\nThe maximum amount is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Clamped Regional Difficulty
			LiteralCommandNode<ServerCommandSource> clampedLocalDifficultyNode = CommandManager
					.literal("clampedLocalDifficulty")
					.executes(context -> {
						sendText(context, "\nThis command allows you to change the parameters of the clamped local difficulty mixin.");
						return 1;
					}).build();

			//region Active
			LiteralCommandNode<ServerCommandSource> cldActiveNode = CommandManager
					.literal("active")
					.executes(context -> {
						String a;
						if(CommandVars.cldActive) {
							a = "§a§l[true]§r";
						} else {
							a = "§c§l[false]§r";
						}
						sendText(context, "\nActivates and deactivates the mixin.\nDefault value: §c§l[false]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Boolean> cldActiveVNode = CommandManager
					.argument("active", BoolArgumentType.bool())
					.executes(context -> {
						boolean a = getBool(context, "active");
						CommandVars.cldActive = a;
						if(a) {
							sendText(context, "\nThe clamped local difficulty mixin is now §aactivated§r.");
						} else {
							sendText(context, "\nThe clamped local difficulty mixin is now §cdeactivated§r.");
						}
						return 1;
					}).build();
			//endregion

			//region Min Clamp Lim
			LiteralCommandNode<ServerCommandSource> cldMinClampLimNode = CommandManager
					.literal("minClampLim")
					.executes(context -> {
						String a = "§l[" + CommandVars.cldMinClampLim + "]§r";
						sendText(context, "\n.\nDefault value: §l[2.0]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> cldMinClampLimVNode = CommandManager
					.argument("value", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "value");
						CommandVars.cldMinClampLim = a;
						sendText(context, "\nThe lower clamping limit is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion

			//region Max Clamp Lim
			LiteralCommandNode<ServerCommandSource> cldMaxClampLimNode = CommandManager
					.literal("maxClampLim")
					.executes(context -> {
						String a = "§l[" + CommandVars.cldMaxClampLim + "]§r";
						sendText(context, "\n.\nDefault value: §l[4.0]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> cldMaxClampLimVNode = CommandManager
					.argument("value", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "value");
						CommandVars.cldMaxClampLim = a;
						sendText(context, "\nThe upper clamping limit is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion

			//region Min Clamp Value
			LiteralCommandNode<ServerCommandSource> cldMinClampNode = CommandManager
					.literal("minClamp")
					.executes(context -> {
						String a = "§l[" + CommandVars.cldMinClamp + "]§r";
						sendText(context, "\n.\nDefault value: §l[0.0]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> cldMinClampVNode = CommandManager
					.argument("value", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "value");
						CommandVars.cldMinClamp = a;
						sendText(context, "\nThe lower clamping value is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion

			//region Max Clamp Value
			LiteralCommandNode<ServerCommandSource> cldMaxClampNode = CommandManager
					.literal("maxClamp")
					.executes(context -> {
						String a = "§l[" + CommandVars.cldMaxClamp + "]§r";
						sendText(context, "\n.\nDefault value: §l[1.0]§r\nCurrent value: " + a);
						return 1;
					}).build();

			ArgumentCommandNode<ServerCommandSource, Float> cldMaxClampVNode = CommandManager
					.argument("value", FloatArgumentType.floatArg())
					.executes(context -> {
						float a = getFloat(context, "value");
						CommandVars.cldMaxClamp = a;
						sendText(context, "\nThe upper clamping value is now §l[" + a + "]§r.");
						return 1;
					}).build();
			//endregion
		//endregion

		//region Command parenting
		//There has to be a better way to do this.
			dispatcher.getRoot().addChild(extraDifficultyNode);

				extraDifficultyNode.addChild(localDifficultyNode);
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

				extraDifficultyNode.addChild(beeNode);
					beeNode.addChild(beeActiveNode);
						beeActiveNode.addChild(beeActiveVNode);
					beeNode.addChild(beeLengthNode);
						beeLengthNode.addChild(beeLengthVNode);

				extraDifficultyNode.addChild(caveSpiderNode);
					caveSpiderNode.addChild(caveSpiderActiveNode);
						caveSpiderActiveNode.addChild(caveSpiderActiveVNode);
					caveSpiderNode.addChild(caveSpiderLengthNode);
						caveSpiderLengthNode.addChild(caveSpiderLengthVNode);

				extraDifficultyNode.addChild(skeletonNode);
					skeletonNode.addChild(skeletonActiveNode);
						skeletonActiveNode.addChild(skeletonActiveVNode);
					skeletonNode.addChild(skeletonDivergenceNode);
						skeletonDivergenceNode.addChild(skeletonDivergenceVNode);
					skeletonNode.addChild(skeletonCooldownNode);
						skeletonCooldownNode.addChild(skeletonCooldownVNode);

				extraDifficultyNode.addChild(fireNode);
					fireNode.addChild(fireActiveNode);
						fireActiveNode.addChild(fireActiveVNode);
					fireNode.addChild(fireEncouragementNode);
						fireEncouragementNode.addChild(fireEncouragementVNode);

				extraDifficultyNode.addChild(netherPortalNode);
					netherPortalNode.addChild(netherPortalActiveNode);
						netherPortalActiveNode.addChild(netherPortalActiveVNode);
					netherPortalNode.addChild(netherPortalProbNode);
						netherPortalProbNode.addChild(netherPortalProbVNode);

				extraDifficultyNode.addChild(mobNode);
					mobNode.addChild(mobActiveNode);
						mobActiveNode.addChild(mobActiveVNode);
					mobNode.addChild(mobArmorValueNode);
						mobArmorValueNode.addChild(mobArmorValueVNode);
					mobNode.addChild(mobFallNode);
						mobFallNode.addChild(mobFallVNode);

				extraDifficultyNode.addChild(projectileNode);
					projectileNode.addChild(projectileActiveNode);
						projectileActiveNode.addChild(projectileActiveVNode);
					projectileNode.addChild(projectileValueNode);
						projectileValueNode.addChild(projectileValueVNode);

				extraDifficultyNode.addChild(playerNode);
					playerNode.addChild(playerActiveNode);
						playerActiveNode.addChild(playerActiveVNode);
					playerNode.addChild(playerMultiplierNode);
						playerMultiplierNode.addChild(playerMultiplierVNode);

				extraDifficultyNode.addChild(raidNode);
					raidNode.addChild(raidActiveNode);
						raidActiveNode.addChild(raidActiveVNode);
					raidNode.addChild(raidCountNode);
						raidCountNode.addChild(raidCountVNode);

				extraDifficultyNode.addChild(witherSkullNode);
					witherSkullNode.addChild(witherSkullActiveNode);
						witherSkullActiveNode.addChild(witherSkullActiveVNode);
					witherSkullNode.addChild(witherSkullLengthNode);
						witherSkullLengthNode.addChild(witherSkullLengthVNode);

				extraDifficultyNode.addChild(zombieNode);
					zombieNode.addChild(zombieActiveNode);
						zombieActiveNode.addChild(zombieActiveVNode);
					zombieNode.addChild(zombieChanceNode);
						zombieChanceNode.addChild(zombieChanceVNode);

				extraDifficultyNode.addChild(guardianNode);
					guardianNode.addChild(guardianActiveNode);
						guardianActiveNode.addChild(guardianActiveVNode);
					guardianNode.addChild(guardianValueNode);
						guardianValueNode.addChild(guardianValueVNode);

				extraDifficultyNode.addChild(phantomNode);
					phantomNode.addChild(phantomActiveNode);
						phantomActiveNode.addChild(phantomActiveVNode);
					phantomNode.addChild(phantomMinNode);
						phantomMinNode.addChild(phantomMinVNode);
					phantomNode.addChild(phantomMaxNode);
						phantomMaxNode.addChild(phantomMaxVNode);

				extraDifficultyNode.addChild(clampedLocalDifficultyNode);
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
		});
	}
}