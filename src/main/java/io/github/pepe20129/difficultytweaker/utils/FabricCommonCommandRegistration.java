package io.github.pepe20129.difficultytweaker.utils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.network.MessageType;
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

        //region LocalDifficulty
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
                    String a;
                    if (Reference.getConfig().localDifficulty.ldActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> ldActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().localDifficulty.ldActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "local difficulty"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "local difficulty"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Start
        LiteralCommandNode<ServerCommandSource> ldStartNode = CommandManager
                .literal("start")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().localDifficulty.ldStart + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.world.localdifficulty.start", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> ldStartVNode = CommandManager
                .argument("start", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "start");
                    Reference.getConfig().localDifficulty.ldStart = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    sendText(context, new TranslatableText("difficultytweaker.feedback", a));
                    return 1;
                }).build();
        //endregion

        //region DayTime
        LiteralCommandNode<ServerCommandSource> ldDayTimeClampMaxNode = CommandManager
                .literal("dayTimeClampMax")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().localDifficulty.ldDayTimeClampMax + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.world.localdifficulty.daytimeclampmax", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> ldDayTimeClampMaxVNode = CommandManager
                .argument("dtcm", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "dtcm");
                    Reference.getConfig().localDifficulty.ldDayTimeClampMax = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    sendText(context, new TranslatableText("difficultytweaker.feedback", a));
                    return 1;
                }).build();
        //endregion

        //region Chunk
        LiteralCommandNode<ServerCommandSource> ldChunkClampMaxNode = CommandManager
                .literal("chunkClampMax")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().localDifficulty.ldChunkClampMax + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.world.localdifficulty.chunkclampmax", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> ldChunkClampMaxVNode = CommandManager
                .argument("ccm", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "ccm");
                    Reference.getConfig().localDifficulty.ldChunkClampMax = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    sendText(context, new TranslatableText("difficultytweaker.feedback", a));
                    return 1;
                }).build();
        //endregion

        //region Moon
        LiteralCommandNode<ServerCommandSource> ldMoonNode = CommandManager
                .literal("moon")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().localDifficulty.ldMoon + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.world.localdifficulty.moon", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> ldMoonVNode = CommandManager
                .argument("moon", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "moon");
                    Reference.getConfig().localDifficulty.ldMoon = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().beeActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> beeActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().beeActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "bee"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "bee"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Length
        LiteralCommandNode<ServerCommandSource> beeLengthNode = CommandManager
                .literal("length")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().beeLength + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.bee.length", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Integer> beeLengthVNode = CommandManager
                .argument("length", IntegerArgumentType.integer())
                .executes(context -> {
                    int a = getInteger(context, "length");
                    Reference.getConfig().beeLength = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().caveSpiderActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> caveSpiderActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().caveSpiderActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated","cave spider"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "cave spider"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Length
        LiteralCommandNode<ServerCommandSource> caveSpiderLengthNode = CommandManager
                .literal("length")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().caveSpiderLength + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.cavespider.length", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Integer> caveSpiderLengthVNode = CommandManager
                .argument("length", IntegerArgumentType.integer())
                .executes(context -> {
                    int a = getInteger(context, "length");
                    Reference.getConfig().caveSpiderLength = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().skeletonActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> skeletonActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().skeletonActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "skeleton"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "skeleton"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Divergence
        LiteralCommandNode<ServerCommandSource> skeletonDivergenceNode = CommandManager
                .literal("divergence")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().skeletonDivergence + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.skeleton.divergence", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Integer> skeletonDivergenceVNode = CommandManager
                .argument("divergence", IntegerArgumentType.integer())
                .executes(context -> {
                    int a = getInteger(context, "divergence");
                    Reference.getConfig().skeletonDivergence = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    sendText(context, new TranslatableText("difficultytweaker.feedback", a));
                    return 1;
                }).build();
        //endregion

        //region Cooldown
        LiteralCommandNode<ServerCommandSource> skeletonCooldownNode = CommandManager
                .literal("cooldown")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().skeletonCooldown + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.skeleton.cooldown", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Integer> skeletonCooldownVNode = CommandManager
                .argument("cooldown", IntegerArgumentType.integer())
                .executes(context -> {
                    int a = getInteger(context, "cooldown");
                    Reference.getConfig().skeletonCooldown = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().fireActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> fireActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().fireActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "fire"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "fire"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Encouragement
        LiteralCommandNode<ServerCommandSource> fireEncouragementNode = CommandManager
                .literal("encouragement")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().fireEncouragement + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.world.fire.encouragement", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Integer> fireEncouragementVNode = CommandManager
                .argument("encouragement", IntegerArgumentType.integer())
                .executes(context -> {
                    int a = getInteger(context, "encouragement");
                    Reference.getConfig().fireEncouragement = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().netherPortalActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> netherPortalActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().netherPortalActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "nether portal"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "nether portal"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Probability
        LiteralCommandNode<ServerCommandSource> netherPortalProbNode = CommandManager
                .literal("probability")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().netherPortalProb + "/2000]§r";
                    sendText(context, new TranslatableText("difficultytweaker.world.netherportal.probability", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Integer> netherPortalProbVNode = CommandManager
                .argument("probability", IntegerArgumentType.integer())
                .executes(context -> {
                    int a = getInteger(context, "probability");
                    Reference.getConfig().netherPortalProb = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().mobActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> mobActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().mobActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "mob armor"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "mob armor"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Armor Probability
        LiteralCommandNode<ServerCommandSource> mobArmorValueNode = CommandManager
                .literal("prob")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().mobArmorProb + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.mob.armorprobability", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> mobArmorValueVNode = CommandManager
                .argument("value", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "value");
                    Reference.getConfig().mobArmorProb = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    sendText(context, new TranslatableText("difficultytweaker.feedback", a));
                    return 1;
                }).build();
        //endregion

        //region Fall
        LiteralCommandNode<ServerCommandSource> mobFallNode = CommandManager
                .literal("fall")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().mobFall + "]§r";
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
                    Reference.getConfig().mobFall = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().projectileActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> projectileActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().projectileActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "projectile"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "projectile"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Value
        LiteralCommandNode<ServerCommandSource> projectileValueNode = CommandManager
                .literal("value")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().projectileBonus + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.projectile.damage", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> projectileValueVNode = CommandManager
                .argument("value", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "value");
                    Reference.getConfig().projectileBonus = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().playerActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> playerActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().playerActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "player"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "player"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Multiplier
        LiteralCommandNode<ServerCommandSource> playerMultiplierNode = CommandManager
                .literal("value")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().playerMultiplier + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.player.multiplier", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> playerMultiplierVNode = CommandManager
                .argument("value", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "value");
                    Reference.getConfig().playerMultiplier = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().raidActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> raidActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().raidActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "raid"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "raid"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Count
        LiteralCommandNode<ServerCommandSource> raidCountNode = CommandManager
                .literal("value")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().raidCount + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.world.raid.count", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Integer> raidCountVNode = CommandManager
                .argument("value", IntegerArgumentType.integer())
                .executes(context -> {
                    int a = getInteger(context, "value");
                    if (a > 15)
                        a = 15;
                    Reference.getConfig().raidCount = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().witherSkullActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> witherSkullActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().witherSkullActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "wither skull"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "wither skull"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Length
        LiteralCommandNode<ServerCommandSource> witherSkullLengthNode = CommandManager
                .literal("value")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().witherSkullLength + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.witherskull.length", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Integer> witherSkullLengthVNode = CommandManager
                .argument("value", IntegerArgumentType.integer())
                .executes(context -> {
                    int a = getInteger(context, "value");
                    Reference.getConfig().witherSkullLength = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().zombieActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> zombieActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().zombieActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "zombie"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "zombie"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Chance
        LiteralCommandNode<ServerCommandSource> zombieChanceNode = CommandManager
                .literal("value")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().zombieWeaponChance + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.zombie.chance", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Integer> zombieChanceVNode = CommandManager
                .argument("value", IntegerArgumentType.integer())
                .executes(context -> {
                    int a = getInteger(context, "value");
                    Reference.getConfig().zombieWeaponChance = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().guardianActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> guardianActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().guardianActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "guardian"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "guardian"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Value
        LiteralCommandNode<ServerCommandSource> guardianValueNode = CommandManager
                .literal("value")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().guardianAmount + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.guardian.value", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> guardianValueVNode = CommandManager
                .argument("value", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "value");
                    Reference.getConfig().guardianAmount = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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
                    String a;
                    if (Reference.getConfig().phantomActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> phantomActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().phantomActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "phantom"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "phantom"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Min
        LiteralCommandNode<ServerCommandSource> phantomMinNode = CommandManager
                .literal("min")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().phantomMin + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.phantom.min", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Integer> phantomMinVNode = CommandManager
                .argument("value", IntegerArgumentType.integer())
                .executes(context -> {
                    int a = getInteger(context, "value");
                    if (a >= Reference.getConfig().phantomMax)
                        a = Reference.getConfig().phantomMax - 1;
                    Reference.getConfig().phantomMin = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    sendText(context, new TranslatableText("difficultytweaker.feedback", a));
                    return 1;
                }).build();
        //endregion

        //region Max
        LiteralCommandNode<ServerCommandSource> phantomMaxNode = CommandManager
                .literal("max")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().phantomMax + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.entity.phantom.max", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Integer> phantomMaxVNode = CommandManager
                .argument("value", IntegerArgumentType.integer())
                .executes(context -> {
                    int a = getInteger(context, "value");
                    if (a <= Reference.getConfig().phantomMin)
                        a = Reference.getConfig().phantomMin + 1;
                    Reference.getConfig().phantomMax = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    sendText(context, new TranslatableText("difficultytweaker.feedback", a));
                    return 1;
                }).build();
        //endregion
        //endregion

        //region Clamped Regional Difficulty
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
                    String a;
                    if (Reference.getConfig().clampedRegionalDifficulty.cldActive) {
                        a = "§a§l[true]§r";
                    } else {
                        a = "§c§l[false]§r";
                    }
                    sendText(context, new TranslatableText("difficultytweaker.active", "§c§l[false]§r", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Boolean> cldActiveVNode = CommandManager
                .argument("active", BoolArgumentType.bool())
                .executes(context -> {
                    boolean a = getBool(context, "active");
                    Reference.getConfig().clampedRegionalDifficulty.cldActive = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    if (a) {
                        sendText(context, new TranslatableText("difficultytweaker.activated", "clamped local difficulty"));
                    } else {
                        sendText(context, new TranslatableText("difficultytweaker.deactivated", "clamped local difficulty"));
                    }
                    return 1;
                }).build();
        //endregion

        //region Min Clamp Lim
        LiteralCommandNode<ServerCommandSource> cldMinClampLimNode = CommandManager
                .literal("minClampLim")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().clampedRegionalDifficulty.cldMinClampLim + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.world.clampedlocaldifficulty.minclampedlim", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> cldMinClampLimVNode = CommandManager
                .argument("value", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "value");
                    Reference.getConfig().clampedRegionalDifficulty.cldMinClampLim = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    sendText(context, new TranslatableText("difficultytweaker.feedback", a));
                    return 1;
                }).build();
        //endregion

        //region Max Clamp Lim
        LiteralCommandNode<ServerCommandSource> cldMaxClampLimNode = CommandManager
                .literal("maxClampLim")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().clampedRegionalDifficulty.cldMaxClampLim + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.world.clampedlocaldifficulty.maxclampedlim", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> cldMaxClampLimVNode = CommandManager
                .argument("value", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "value");
                    Reference.getConfig().clampedRegionalDifficulty.cldMaxClampLim = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    sendText(context, new TranslatableText("difficultytweaker.feedback", a));
                    return 1;
                }).build();
        //endregion

        //region Min Clamp Value
        LiteralCommandNode<ServerCommandSource> cldMinClampNode = CommandManager
                .literal("minClamp")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().clampedRegionalDifficulty.cldMinClamp + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.world.clampedlocaldifficulty.minclamped", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> cldMinClampVNode = CommandManager
                .argument("value", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "value");
                    Reference.getConfig().clampedRegionalDifficulty.cldMinClamp = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
                    sendText(context, new TranslatableText("difficultytweaker.feedback", a));
                    return 1;
                }).build();
        //endregion

        //region Max Clamp Value
        LiteralCommandNode<ServerCommandSource> cldMaxClampNode = CommandManager
                .literal("maxClamp")
                .executes(context -> {
                    String a = "§l[" + Reference.getConfig().clampedRegionalDifficulty.cldMaxClamp + "]§r";
                    sendText(context, new TranslatableText("difficultytweaker.world.clampedlocaldifficulty.maxclamped", a));
                    return 1;
                }).build();

        ArgumentCommandNode<ServerCommandSource, Float> cldMaxClampVNode = CommandManager
                .argument("value", FloatArgumentType.floatArg())
                .executes(context -> {
                    float a = getFloat(context, "value");
                    Reference.getConfig().clampedRegionalDifficulty.cldMaxClamp = a;
                    Reference.getConfig().saveConfig(context.getSource().getServer());
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

    private static void sendText(CommandContext<ServerCommandSource> context, TranslatableText text) throws CommandSyntaxException {
        context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, context.getSource().getPlayer().getUuid());
    }
}