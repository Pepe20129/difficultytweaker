package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "net.minecraft.entity.mob.GuardianEntity$FireBeamGoal")
public abstract class FireBeamGoalMixin {
    @ModifyVariable(at = @At("STORE"), method = "tick()V", ordinal = 0)
    float f(float f) {
        Reference.getConfig().loadConfig();
        if(Reference.getConfig().guardianActive)
            return 1.0f + Reference.getConfig().guardianAmount;
        return 1.0f;
    }
}