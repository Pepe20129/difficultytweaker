package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends HostileEntity {
	protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Redirect(
		method = "initEquipment(Lnet/minecraft/world/LocalDifficulty;)V",
		at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F")
	)
	private float modifyInitEquipment(Random random) {
		if (ConfigHelper.getConfig().zombie.active) {
			return (random.nextFloat() < ConfigHelper.getConfig().zombie.weaponChance) ? 0 : 1;
		} else {
			return random.nextFloat();
		}
	}

	@Redirect(
		method = "onKilledOther(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;",
			ordinal = 0
		)
	)
	private Difficulty firstGetDifficulty(ServerWorld world) {
		return Difficulty.HARD;
	}

	@Redirect(
		method = "onKilledOther(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;",
			ordinal = 1
		)
	)
	private Difficulty secondGetDifficulty(ServerWorld world) {
		boolean shouldSpawnZombieVillager;
		if (ConfigHelper.getConfig().zombieVillager.active) {
			shouldSpawnZombieVillager = random.nextFloat() < ConfigHelper.getConfig().zombieVillager.chance;
		} else {
			shouldSpawnZombieVillager = (world.getDifficulty() == Difficulty.NORMAL && random.nextBoolean()) || world.getDifficulty() == Difficulty.HARD;
		}

		if (shouldSpawnZombieVillager) {
			return Difficulty.HARD;
		} else {
			return Difficulty.PEACEFUL;
		}
	}

	@Redirect(
		method = "onKilledOther(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;",
			ordinal = 2
		)
	)
	private Difficulty thirdGetDifficulty(ServerWorld world) {
		return Difficulty.HARD;
	}
}