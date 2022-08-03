package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "net.minecraft.entity.mob.GuardianEntity$FireBeamGoal")
public abstract class FireBeamGoalMixin {
	@ModifyVariable(at = @At("STORE"), method = "tick()V", ordinal = 0)
	private float modifyFireBeamDamage(float original) {
		return ConfigHelper.getConfig().guardian.active ? ConfigHelper.getConfig().guardian.extraDamage : original;
	}
}