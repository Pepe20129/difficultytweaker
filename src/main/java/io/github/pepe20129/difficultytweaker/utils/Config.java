package io.github.pepe20129.difficultytweaker.utils;

public class Config {
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

	public ClampedLocalDifficulty clampedLocalDifficulty = new ClampedLocalDifficulty();
	public static class ClampedLocalDifficulty {
		public boolean active = false;
		public float minClampLim = 2f;
		public float maxClampLim = 4f;
		public float minClamp = 0f;
		public float maxClamp = 1f;
	}

	public Fire fire = new Fire();
	public static class Fire {
		public boolean active = false;
		public int encouragement = 61;
	}

	public Guardian guardian = new Guardian();
	public static class Guardian {
		public boolean active = false;
		public float amount = 2f;
	}

	public Lightning lightning = new Lightning();
	public static class Lightning {
		public boolean active = false;
		public boolean additionalFire = true;
	}

	public LocalDifficulty localDifficulty = new LocalDifficulty();
	public static class LocalDifficulty {
		public boolean active = false;
		public float start = 0.75f;
		public float dayTimeClampMax = 1f;
		public float chunkClampMax = 1f;
		public float moon = 0.25f;
	}

	public Mob mob = new Mob();
	public static class Mob {
		public boolean active = false;
		public float armorProbability = 0.1f;
		public float fall = 0.33f;
	}

	public NetherPortal netherPortal = new NetherPortal();
	public static class NetherPortal {
		public boolean active = true;
		public int probability = 3;
	}

	public Phantom phantom = new Phantom();
	public static class Phantom {
		public boolean active = false;
		public int min = 1;
		public int max = 4;
	}

	public Player player = new Player();
	public static class Player {
		public boolean active = false;
		public float multiplier = 1.5f;
		public int hungerDamageThreshold = 0;
	}

	public Projectile projectile = new Projectile();
	public static class Projectile {
		public boolean active = false;
		public float bonus = 0.33f;
	}

	public Raid raid = new Raid();
	public static class Raid {
		public boolean active = false;
		public int count = 7;
	}

	public RaidMember raidMember = new RaidMember();
	public static class RaidMember {
		public boolean active = false;
		public int[] vindicator = {0, 0, 2, 0, 1, 4, 2, 5, 5, 5, 6, 6, 6, 7, 7, 7};
		public int[] evoker = {0, 0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6};
		public int[] pillager = {0, 4, 3, 3, 4, 4, 4, 2, 4, 5, 6, 6, 7, 8, 9, 9};
		public int[] witch = {0, 0, 0, 0, 3, 0, 0, 1, 3, 3, 4, 4, 4, 5, 5, 5};
		public int[] ravager = {0, 0, 0, 1, 0, 1, 0, 2, 2, 3, 3, 3, 3, 4, 4, 4};
	}

	public Skeleton skeleton = new Skeleton();
	public static class Skeleton {
		public boolean active = false;
		public int divergence = 2;
		public int cooldown = 20;
	}

	public Spider spider = new Spider();
	public static class Spider {
		public boolean active = false;
		public boolean effectActivation = true;
	}

	public Wither wither = new Wither();
	public static class Wither {
		public boolean active = false;
		public boolean idleSkulls = true;
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
		public boolean spawnReinforcements = true;
		public boolean canBreakDoors = true;
	}

	public ZombieVillager zombieVillager = new ZombieVillager();
	public static class ZombieVillager {
		public boolean active = false;
		public float chance = 1f;
	}
}