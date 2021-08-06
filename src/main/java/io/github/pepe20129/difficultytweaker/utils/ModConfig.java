package io.github.pepe20129.difficultytweaker.utils;

import com.moandjiezana.toml.TomlWriter;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.io.IOException;

public class ModConfig {

    public void saveConfig(MinecraftServer server) {
        File configFile = ConfigHelper.getConfigFile(server);
        try {
            new TomlWriter().write(this, configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //region LocalDifficulty
    public LocalDifficulty localDifficulty = new LocalDifficulty();
    public static class LocalDifficulty {
        public boolean ldActive = false;
        public float ldStart = 0.75f;
        public float ldDayTimeClampMax = 1f;
        public float ldChunkClampMax = 1f;
        public float ldMoon = 0.25f;
    }
    //endregion

    //region Bee
    public boolean beeActive = false;
    public int beeLength = 18;
    //endregion

    //region CaveSpider
    public boolean caveSpiderActive = false;
    public int caveSpiderLength = 15;
    //endregion

    //region Skeleton
    public boolean skeletonActive = false;
    public int skeletonDivergence = 2;
    public int skeletonCooldown = 20;
    //endregion

    //region Fire
    public boolean fireActive = false;
    public int fireEncouragement = 61;
    //endregion

    //region Nether Portal
    public boolean netherPortalActive = false;
    public int netherPortalProb = 3;
    //endregion

    //region Mob
    public boolean mobActive = false;
    public float mobArmorProb = 0.1f;
    public float mobFall = 0.33f;
    //endregion

    //region Projectile
    public boolean projectileActive = false;
    public float projectileBonus = 0.33f;
    //endregion

    //region Player
    public boolean playerActive = false;
    public float playerMultiplier = 1.5f;
    //endregion

    //region Raid
    public boolean raidActive = false;
    public int raidCount = 7;
    //endregion

    //region Wither Skull
    public boolean witherSkullActive = false;
    public int witherSkullLength = 40;
    //endregion

    //region Zombie
    public boolean zombieActive = false;
    public float zombieWeaponChance = 0.1f;
    //endregion

    //region Guardian
    public boolean guardianActive = false;
    public float guardianAmount = 2f;
    //endregion

    //region Phantom
    public boolean phantomActive = false;
    public int phantomMin = 1;
    public int phantomMax = 4;
    //endregion

    //region Clamped Regional Difficulty
    public ClampedRegionalDifficulty clampedRegionalDifficulty = new ClampedRegionalDifficulty();
    public static class ClampedRegionalDifficulty {
        public boolean cldActive = false;
        public float cldMinClampLim = 2f;
        public float cldMaxClampLim = 4f;
        public float cldMinClamp = 0f;
        public float cldMaxClamp = 1f;
    }
    //endregion
}