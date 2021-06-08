package io.github.pepe20129.difficultytweaker.mixin;

import net.minecraft.entity.ai.goal.Goal;
import io.github.pepe20129.difficultytweaker.CommandVars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "net.minecraft.entity.mob.GuardianEntity$FireBeamGoal")
public abstract class FireBeamGoalMixin extends Goal {
    @ModifyVariable(at = @At("STORE"), method = "tick()V", ordinal = 0)
    float f(float f) {
        if(CommandVars.guardianActive)
            return 1.0f + CommandVars.guardianAmount;
        return 1.0f;
    }
}