package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "net.minecraft.entity.mob.GuardianEntity$FireBeamGoal")
public abstract class FireBeamGoalMixin {
    @ModifyVariable(at = @At("STORE"), method = "tick()V", ordinal = 0)
    private float modifyFireBeamDamage(float original) {
        return Reference.getConfig().guardianActive ? Reference.getConfig().guardianAmount : original;
    }
}