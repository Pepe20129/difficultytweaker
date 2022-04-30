package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends HostileEntity implements RangedAttackMob {
	protected AbstractSkeletonEntityMixin(EntityType<? extends HostileEntity> entityType, World world, MeleeAttackGoal meleeAttackGoal) {
		super(entityType, world);
	}

	@ModifyVariable(at = @At("LOAD"), method = "updateAttackType()V")
	private int modifySkeletonCooldown(int original) {
		return ConfigHelper.getConfig().skeleton.active ? ConfigHelper.getConfig().skeleton.cooldown : original;
	}

	@Redirect(
			method = "attack(Lnet/minecraft/entity/LivingEntity;F)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Difficulty;getId()I")
	)
	private int getId(Difficulty difficulty) {
		return ConfigHelper.getConfig().skeleton.active ? -(ConfigHelper.getConfig().skeleton.divergence - 14) / 4 : difficulty.getId();
	}
}