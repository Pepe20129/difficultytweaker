package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
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
		return ConfigHelper.getConfig().zombie.active ? ((random.nextFloat() < ConfigHelper.getConfig().zombie.weaponChance) ? 0f : 1f) : random.nextFloat();
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

		return shouldSpawnZombieVillager ? Difficulty.HARD : Difficulty.PEACEFUL;
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


	@Redirect(
		method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;getDifficulty()Lnet/minecraft/world/Difficulty;"
		)
	)
	private Difficulty modifySpawnReinforcements(World world) {
		return ConfigHelper.getConfig().zombie.active ? (ConfigHelper.getConfig().zombie.spawnReinforcements ? Difficulty.HARD : Difficulty.NORMAL) : world.getDifficulty();
	}


	private final BreakDoorGoal customBreakDoorsGoal = new BreakDoorGoal(this, difficulty -> ConfigHelper.getConfig().zombie.active ? ConfigHelper.getConfig().zombie.canBreakDoors : difficulty == Difficulty.HARD);

	@Redirect(
		method = "setCanBreakDoors(Z)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V"
		)
	)
	private void redirectAddBreakDoorsGoal(GoalSelector goalSelector, int priority, Goal goal) {
		goalSelector.add(1, customBreakDoorsGoal);
	}

	@Redirect(
		method = "setCanBreakDoors(Z)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ai/goal/GoalSelector;remove(Lnet/minecraft/entity/ai/goal/Goal;)V"
		)
	)
	private void redirectRemoveBreakDoorsGoal(GoalSelector goalSelector, Goal goal) {
		goalSelector.remove(customBreakDoorsGoal);
	}
}