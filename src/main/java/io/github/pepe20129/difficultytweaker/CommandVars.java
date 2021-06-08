package io.github.pepe20129.difficultytweaker;

public class CommandVars {

    //region LocalDifficulty
    public static boolean ldActive = false;
    public static float ldStart = 0.75F;
    public static float ldDayTimeClampMax = 1.0F;
    public static float ldChunkClampMax = 1.0F;
    public static float ldMoon = 0.25F;
    //endregion

    //region Bee
    public static boolean beeActive = false;
    public static int beeLength = 18;
    //endregion

    //region CaveSpider
    public static boolean caveSpiderActive = false;
    public static int caveSpiderLength = 15;
    //endregion

    //region Skeleton
    public static boolean skeletonActive = false;
    public static int skeletonDivergence = 2;
    public static int skeletonCooldown = 20;
    //endregion

    //region Fire
    public static boolean fireActive = false;
    public static int fireEncouragement = 61;
    //endregion

    //region Nether Portal
    public static boolean netherPortalActive = false;
    public static int netherPortalProb = 3;
    //endregion

    //region Mob
    public static boolean mobActive = false;
    public static float mobArmorProb = 0.1F;
    public static float mobFall = 0.33F;
    //endregion

    //region Projectile
    public static boolean projectileActive = false;
    public static float projectileBonus = 0.33F;
    //endregion

    //region Player
    public static boolean playerActive = false;
    public static float playerMultiplier = 1.5F;
    //endregion

    //region Raid
    public static boolean raidActive = false;
    public static int raidCount = 7;
    //endregion

    //region Wither Skull
    public static boolean witherSkullActive = false;
    public static int witherSkullLength = 40;
    //endregion

    //region Zombie
    public static boolean zombieActive = false;
    public static float zombieWeaponChance = 0.1F;
    //endregion

    //region Guardian
    public static boolean guardianActive = false;
    public static float guardianAmount = 0.0f;
    //endregion

    //region Phantom
    public static boolean phantomActive = false;
    public static int phantomMin = 1;
    public static int phantomMax = 4;
    //endregion

    //region Clamped Regional Difficulty
    public static boolean cldActive = false;
    public static float cldMinClampLim = 2F;
    public static float cldMaxClampLim = 4F;
    public static float cldMinClamp = 0F;
    public static float cldMaxClamp = 1F;
    //endregion
}