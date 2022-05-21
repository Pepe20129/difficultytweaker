package io.github.pepe20129.difficultytweaker.utils;

import io.github.pepe20129.difficultytweaker.utils.annotations.BoundedFloat;
import io.github.pepe20129.difficultytweaker.utils.annotations.BoundedInteger;
import io.github.pepe20129.difficultytweaker.utils.annotations.ConfigCategory;
import io.github.pepe20129.difficultytweaker.utils.annotations.NoCommand;
import io.github.pepe20129.difficultytweaker.utils.annotations.SpecialFormat;

public class Config {
	public static class ConfigEntry {
		public boolean active = false;
	}

	public Bee bee = new Bee();
	@ConfigCategory("entity")
	public static class Bee extends ConfigEntry {
		@BoundedInteger(min = 0)
		public int length = 18;
	}

	public CaveSpider caveSpider = new CaveSpider();
	@ConfigCategory("entity")
	public static class CaveSpider extends ConfigEntry {
		@BoundedInteger(min = 0)
		public int length = 15;
	}

	public ClampedLocalDifficulty clampedLocalDifficulty = new ClampedLocalDifficulty();
	@ConfigCategory("world")
	public static class ClampedLocalDifficulty extends ConfigEntry {
		public float minClampLim = 2f;
		public float maxClampLim = 4f;
		public float minClamp = 0f;
		public float maxClamp = 1f;
	}

	public Fire fire = new Fire();
	@ConfigCategory("world")
	public static class Fire extends ConfigEntry {
		public int encouragement = 61;
	}

	public Guardian guardian = new Guardian();
	@ConfigCategory("entity")
	public static class Guardian extends ConfigEntry {
		public float amount = 2f;
	}

	public Lightning lightning = new Lightning();
	@ConfigCategory("entity")
	public static class Lightning extends ConfigEntry {
		public boolean additionalFire = true;
	}

	public LocalDifficulty localDifficulty = new LocalDifficulty();
	@ConfigCategory("world")
	public static class LocalDifficulty extends ConfigEntry {
		public float start = 0.75f;
		public float dayTimeClampMax = 1f;
		public float chunkClampMax = 1f;
		public float moon = 0.25f;
	}

	public Mob mob = new Mob();
	@ConfigCategory("entity")
	public static class Mob extends ConfigEntry {
		@BoundedFloat(min = 0, max = 1)
		public float armorProbability = 0.1f;
		@BoundedFloat(min = 0, max = 1)
		public float fall = 0.33f;
	}

	public NetherPortal netherPortal = new NetherPortal();
	@ConfigCategory("world")
	public static class NetherPortal extends ConfigEntry {
		@SpecialFormat("§l[%s/2000]§r")
		@BoundedInteger(min = 0, max = 2000)
		public int probability = 3;
	}

	public Phantom phantom = new Phantom();
	@ConfigCategory("entity")
	public static class Phantom extends ConfigEntry {
		@BoundedInteger(min = 0)
		public int min = 1;
		@BoundedInteger(min = 0)
		public int max = 4;
	}

	public Player player = new Player();
	@ConfigCategory("entity")
	public static class Player extends ConfigEntry {
		@BoundedFloat(min = 0)
		public float multiplier = 1.5f;
		@BoundedInteger(min = 0)
		public int hungerDamageThreshold = 0;
	}

	public Projectile projectile = new Projectile();
	@ConfigCategory("entity")
	public static class Projectile extends ConfigEntry {
		public float bonus = 0.33f;
	}

	public Raid raid = new Raid();
	@ConfigCategory("world")
	public static class Raid extends ConfigEntry {
		public int count = 7;
	}

	public RaidMember raidMember = new RaidMember();
	@ConfigCategory("world")
	public static class RaidMember extends ConfigEntry {
		@NoCommand
		public int[] vindicator = {0, 0, 2, 0, 1, 4, 2, 5, 5, 5, 6, 6, 6, 7, 7, 7};
		@NoCommand
		public int[] evoker = {0, 0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6};
		@NoCommand
		public int[] pillager = {0, 4, 3, 3, 4, 4, 4, 2, 4, 5, 6, 6, 7, 8, 9, 9};
		@NoCommand
		public int[] witch = {0, 0, 0, 0, 3, 0, 0, 1, 3, 3, 4, 4, 4, 5, 5, 5};
		@NoCommand
		public int[] ravager = {0, 0, 0, 1, 0, 1, 0, 2, 2, 3, 3, 3, 3, 4, 4, 4};
	}

	public Skeleton skeleton = new Skeleton();
	@ConfigCategory("entity")
	public static class Skeleton extends ConfigEntry {
		@BoundedInteger(min = 0)
		public int divergence = 2;
		@BoundedInteger(min = 0)
		public int cooldown = 20;
	}

	public Spider spider = new Spider();
	@ConfigCategory("entity")
	public static class Spider extends ConfigEntry {
		public boolean effectActivation = true;
	}

	public Wither wither = new Wither();
	@ConfigCategory("entity")
	public static class Wither extends ConfigEntry {
		public boolean idleSkulls = true;
	}

	public WitherSkull witherSkull = new WitherSkull();
	@ConfigCategory("entity")
	public static class WitherSkull extends ConfigEntry {
		@BoundedInteger(min = 0)
		public int length = 40;
	}

	public Zombie zombie = new Zombie();
	@ConfigCategory("entity")
	public static class Zombie extends ConfigEntry {
		@BoundedFloat(min = 0, max = 1)
		public float weaponChance = 0.1f;
		public boolean spawnReinforcements = true;
		public boolean canBreakDoors = true;
	}

	public ZombieVillager zombieVillager = new ZombieVillager();
	@ConfigCategory("entity")
	public static class ZombieVillager extends ConfigEntry {
		@BoundedFloat(min = 0, max = 1)
		public float chance = 1f;
	}
}