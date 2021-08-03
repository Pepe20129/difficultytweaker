package io.github.pepe20129.difficultytweaker;

import com.moandjiezana.toml.TomlWriter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;

public class ModConfig {

    public void loadConfig() {
        // Is this really needed?
    }

    public void saveConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "difficultytweaker.toml");;
        try {
            new TomlWriter().write(new ModConfig(), configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //region LocalDifficulty
    public boolean ldActive = false;
    public float ldStart = 0.75F;
    public float ldDayTimeClampMax = 1F;
    public float ldChunkClampMax = 1F;
    public float ldMoon = 0.25F;
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
    public float mobArmorProb = 0.1F;
    public float mobFall = 0.33F;
    //endregion

    //region Projectile
    public boolean projectileActive = false;
    public float projectileBonus = 0.33F;
    //endregion

    //region Player
    public boolean playerActive = false;
    public float playerMultiplier = 1.5F;
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
    public float zombieWeaponChance = 0.1F;
    //endregion

    //region Guardian
    public boolean guardianActive = false;
    public float guardianAmount = 0F;
    //endregion

    //region Phantom
    public boolean phantomActive = false;
    public int phantomMin = 1;
    public int phantomMax = 4;
    //endregion

    //region Clamped Regional Difficulty
    public boolean cldActive = false;
    public float cldMinClampLim = 2F;
    public float cldMaxClampLim = 4F;
    public float cldMinClamp = 0F;
    public float cldMaxClamp = 1F;
    //endregion
}