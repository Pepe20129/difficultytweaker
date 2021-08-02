package io.github.pepe20129.difficultytweaker;

import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CommandVars {

    public static void loadConfig() {
        File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), "difficultytweaker.properties");
        try {
            if(!file.exists() || !file.canRead()) {
                saveConfig();
            }
            FileInputStream fis = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fis);
            fis.close();
            ldActive = Boolean.parseBoolean((String)properties.computeIfAbsent("ldActive", a -> "false"));
            ldStart = Float.parseFloat((String)properties.computeIfAbsent("ldStart", a -> "0.75F"));
            ldDayTimeClampMax = Float.parseFloat((String)properties.computeIfAbsent("ldDayTimeClampMax", a -> "1F"));
            ldChunkClampMax = Float.parseFloat((String)properties.computeIfAbsent("ldChunkClampMax", a -> "1F"));
            ldMoon = Float.parseFloat((String)properties.computeIfAbsent("ldMoon", a -> "0.25F"));
            beeActive = Boolean.parseBoolean((String)properties.computeIfAbsent("beeActive", a -> "false"));
            beeLength = Integer.parseInt((String)properties.computeIfAbsent("beeLength", a -> "18"));
            caveSpiderActive = Boolean.parseBoolean((String)properties.computeIfAbsent("caveSpiderActive", a -> "false"));
            caveSpiderLength = Integer.parseInt((String)properties.computeIfAbsent("caveSpiderLength", a -> "15"));
            skeletonActive = Boolean.parseBoolean((String)properties.computeIfAbsent("skeletonActive", a -> "false"));
            skeletonDivergence = Integer.parseInt((String)properties.computeIfAbsent("skeletonDivergence", a -> "2"));
            skeletonCooldown = Integer.parseInt((String)properties.computeIfAbsent("skeletonCooldown", a -> "20"));
            fireActive = Boolean.parseBoolean((String)properties.computeIfAbsent("fireActive", a -> "false"));
            fireEncouragement = Integer.parseInt((String)properties.computeIfAbsent("fireEncouragement", a -> "61"));
            netherPortalActive = Boolean.parseBoolean((String)properties.computeIfAbsent("netherPortalActive", a -> "false"));
            netherPortalProb = Integer.parseInt((String)properties.computeIfAbsent("netherPortalProb", a -> "3"));
            mobActive = Boolean.parseBoolean((String)properties.computeIfAbsent("mobActive", a -> "false"));
            mobArmorProb = Float.parseFloat((String)properties.computeIfAbsent("mobArmorProb", a -> "0.1F"));
            mobFall = Float.parseFloat((String)properties.computeIfAbsent("mobFall", a -> "0.33F"));
            projectileActive = Boolean.parseBoolean((String)properties.computeIfAbsent("projectileActive", a -> "false"));
            projectileBonus = Float.parseFloat((String)properties.computeIfAbsent("projectileBonus", a -> "0.33F"));
            playerActive = Boolean.parseBoolean((String)properties.computeIfAbsent("playerActive", a -> "false"));
            playerMultiplier = Float.parseFloat((String)properties.computeIfAbsent("playerMultiplier", a -> "1.5F"));
            raidActive = Boolean.parseBoolean((String)properties.computeIfAbsent("raidActive", a -> "false"));
            raidCount = Integer.parseInt((String)properties.computeIfAbsent("raidCount", a -> "7"));
            witherSkullActive = Boolean.parseBoolean((String)properties.computeIfAbsent("witherSkullActive", a -> "false"));
            witherSkullLength = Integer.parseInt((String)properties.computeIfAbsent("witherSkullLength", a -> "40"));
            zombieActive = Boolean.parseBoolean((String)properties.computeIfAbsent("zombieActive", a -> "false"));
            zombieWeaponChance = Float.parseFloat((String)properties.computeIfAbsent("zombieWeaponChance", a -> "0.1F"));
            guardianActive = Boolean.parseBoolean((String)properties.computeIfAbsent("guardianActive", a -> "false"));
            guardianAmount = Float.parseFloat((String)properties.computeIfAbsent("guardianAmount", a -> "0F"));
            phantomActive = Boolean.parseBoolean((String)properties.computeIfAbsent("phantomActive", a -> "false"));
            phantomMin = Integer.parseInt((String)properties.computeIfAbsent("phantomMin", a -> "1"));
            phantomMax = Integer.parseInt((String)properties.computeIfAbsent("phantomMax", a -> "4"));
            cldActive = Boolean.parseBoolean((String)properties.computeIfAbsent("cldActive", a -> "false"));
            cldMinClampLim = Float.parseFloat((String)properties.computeIfAbsent("cldMinClampLim", a -> "2F"));
            cldMaxClampLim = Float.parseFloat((String)properties.computeIfAbsent("cldMaxClampLim", a -> "4F"));
            cldMinClamp = Float.parseFloat((String)properties.computeIfAbsent("cldMinClamp", a -> "0F"));
            cldMaxClamp = Float.parseFloat((String)properties.computeIfAbsent("cldMaxClamp", a -> "1F"));
            saveConfig();
        } catch(Exception e) {
            e.printStackTrace();
            ldActive = false;
            ldStart = 0.75F;
            ldDayTimeClampMax = 1.0F;
            ldChunkClampMax = 1.0F;
            ldMoon = 0.25F;
            beeActive = false;
            beeLength = 18;
            caveSpiderActive = false;
            caveSpiderLength = 15;
            skeletonActive = false;
            skeletonDivergence = 2;
            skeletonCooldown = 20;
            fireActive = false;
            fireEncouragement = 61;
            netherPortalActive = false;
            netherPortalProb = 3;
            mobActive = false;
            mobArmorProb = 0.1F;
            mobFall = 0.33F;
            projectileActive = false;
            projectileBonus = 0.33F;
            playerActive = false;
            playerMultiplier = 1.5F;
            raidActive = false;
            raidCount = 7;
            witherSkullActive = false;
            witherSkullLength = 40;
            zombieActive = false;
            zombieWeaponChance = 0.1F;
            guardianActive = false;
            guardianAmount = 0F;
            phantomActive = false;
            phantomMin = 1;
            phantomMax = 4;
            cldActive = false;
            cldMinClampLim = 2F;
            cldMaxClampLim = 4F;
            cldMinClamp = 0F;
            cldMaxClamp = 1F;
        }
    }

    public static void saveConfig() {
        try(FileOutputStream fos = new FileOutputStream(new File(FabricLoader.getInstance().getConfigDir().toFile(), "difficultytweaker.properties"), false)) {
            //Make sure to write values for every value in your config!
            fos.write(("ldActive = " + ldActive + "\n").getBytes());
            fos.write(("ldStart = " + ldStart + "\n").getBytes());
            fos.write(("ldDayTimeClampMax = " + ldDayTimeClampMax + "\n").getBytes());
            fos.write(("ldChunkClampMax = " + ldChunkClampMax + "\n").getBytes());
            fos.write(("ldMoon = " + ldMoon + "\n").getBytes());
            fos.write(("beeActive = " + beeActive + "\n").getBytes());
            fos.write(("beeLength = " + beeLength + "\n").getBytes());
            fos.write(("caveSpiderActive = " + caveSpiderActive + "\n").getBytes());
            fos.write(("caveSpiderLength = " + caveSpiderLength + "\n").getBytes());
            fos.write(("skeletonActive = " + skeletonActive + "\n").getBytes());
            fos.write(("skeletonDivergence = " + skeletonDivergence + "\n").getBytes());
            fos.write(("skeletonCooldown = " + skeletonCooldown + "\n").getBytes());
            fos.write(("fireActive = " + fireActive + "\n").getBytes());
            fos.write(("fireEncouragement = " + fireEncouragement + "\n").getBytes());
            fos.write(("netherPortalActive = " + netherPortalActive + "\n").getBytes());
            fos.write(("netherPortalProb = " + netherPortalProb + "\n").getBytes());
            fos.write(("mobActive = " + mobActive + "\n").getBytes());
            fos.write(("mobArmorProb = " + mobArmorProb + "\n").getBytes());
            fos.write(("mobFall = " + mobFall + "\n").getBytes());
            fos.write(("projectileActive = " + projectileActive + "\n").getBytes());
            fos.write(("projectileBonus = " + projectileBonus + "\n").getBytes());
            fos.write(("playerActive = " + playerActive + "\n").getBytes());
            fos.write(("playerMultiplier = " + playerMultiplier + "\n").getBytes());
            fos.write(("raidActive = " + raidActive + "\n").getBytes());
            fos.write(("raidCount = " + raidCount + "\n").getBytes());
            fos.write(("witherSkullActive = " + witherSkullActive + "\n").getBytes());
            fos.write(("witherSkullLength = " + witherSkullLength + "\n").getBytes());
            fos.write(("zombieActive = " + zombieActive + "\n").getBytes());
            fos.write(("zombieWeaponChance = " + zombieWeaponChance + "\n").getBytes());
            fos.write(("guardianActive = " + guardianActive + "\n").getBytes());
            fos.write(("guardianAmount = " + guardianAmount + "\n").getBytes());
            fos.write(("phantomActive = " + phantomActive + "\n").getBytes());
            fos.write(("phantomMin = " + phantomMin + "\n").getBytes());
            fos.write(("phantomMax = " + phantomMax + "\n").getBytes());
            fos.write(("cldActive = " + cldActive + "\n").getBytes());
            fos.write(("cldMinClampLim = " + cldMinClampLim + "\n").getBytes());
            fos.write(("cldMaxClampLim = " + cldMaxClampLim + "\n").getBytes());
            fos.write(("cldMinClamp = " + cldMinClamp + "\n").getBytes());
            fos.write(("cldMaxClamp = " + cldMaxClamp + "\n").getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    //region LocalDifficulty
    public static boolean ldActive = false;
    public static float ldStart = 0.75F;
    public static float ldDayTimeClampMax = 1F;
    public static float ldChunkClampMax = 1F;
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
    public static float guardianAmount = 0F;
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