package io.github.pepe20129.difficultytweaker.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    public void saveConfig(MinecraftServer server) {
        File configFile = ConfigHelper.getConfigFile(server);
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(configFile);
            fileWriter.write(gson.toJson(this));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocalDifficulty localDifficulty = new LocalDifficulty();
    public static class LocalDifficulty {
        public boolean active = false;
        public float start = 0.75f;
        public float dayTimeClampMax = 1f;
        public float chunkClampMax = 1f;
        public float moon = 0.25f;
    }

    public Bee bee = new Bee();
    public static class Bee {
        public boolean active = false;
        public int length = 18;
    }

    public CaveSpider caveSpider = new CaveSpider();
    public static class CaveSpider {
        public boolean active = false;
        public int length = 15;
    }

    public Skeleton skeleton = new Skeleton();
    public static class Skeleton {
        public boolean active = false;
        public int divergence = 2;
        public int cooldown = 20;
    }

    public Fire fire = new Fire();
    public static class Fire {
        public boolean active = false;
        public int encouragement = 61;
    }

    public NetherPortal netherPortal = new NetherPortal();
    public static class NetherPortal {
        public boolean active = true;
        public int probability = 3;
    }

    public Mob mob = new Mob();
    public static class Mob {
        public boolean active = false;
        public float armorProbability = 0.1f;
        public float fall = 0.33f;
    }

    public Projectile projectile = new Projectile();
    public static class Projectile {
        public boolean active = false;
        public float bonus = 0.33f;
    }

    public Player player = new Player();
    public static class Player {
        public boolean active = false;
        public float multiplier = 1.5f;
    }

    public Raid raid = new Raid();
    public static class Raid {
        public boolean active = false;
        public int count = 7;
    }

    public WitherSkull witherSkull = new WitherSkull();
    public static class WitherSkull {
        public boolean active = false;
        public int length = 40;
    }

    public Zombie zombie = new Zombie();
    public static class Zombie {
        public boolean active = false;
        public float weaponChance = 0.1f;
    }

    public Guardian guardian = new Guardian();
    public static class Guardian {
        public boolean active = false;
        public float amount = 2f;
    }

    public Phantom phantom = new Phantom();
    public static class Phantom {
        public boolean active = false;
        public int min = 1;
        public int max = 4;
    }

    public ClampedLocalDifficulty clampedLocalDifficulty = new ClampedLocalDifficulty();
    public static class ClampedLocalDifficulty {
        public boolean active = false;
        public float minClampLim = 2f;
        public float maxClampLim = 4f;
        public float minClamp = 0f;
        public float maxClamp = 1f;
    }

    public ZombieVillager zombieVillager = new ZombieVillager();
    public static class ZombieVillager {
        public boolean active = false;
        public float chance = 1f;
    }
}