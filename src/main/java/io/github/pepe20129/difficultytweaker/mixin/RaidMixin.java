package io.github.pepe20129.difficultytweaker.mixin;

import net.minecraft.village.raid.Raid;
import net.minecraft.world.Difficulty;
import io.github.pepe20129.difficultytweaker.CommandVars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Raid.class)
public class RaidMixin {
    @Inject(at = @At("HEAD"), method = "getMaxWaves(Lnet/minecraft/world/Difficulty;)I", cancellable = true)
    public void getMaxWaves(Difficulty difficulty, CallbackInfoReturnable<Integer> info) {
        int v = 0;
        if (difficulty == Difficulty.EASY) {
            v = 3;
        }
        if (difficulty == Difficulty.NORMAL) {
            v = 5;
        }
        if (difficulty == Difficulty.HARD) {
            v = 7;
        }
        CommandVars.loadConfig();
        if (CommandVars.raidActive) {
            v = CommandVars.raidCount;
        }
        info.setReturnValue(v);
    }
}