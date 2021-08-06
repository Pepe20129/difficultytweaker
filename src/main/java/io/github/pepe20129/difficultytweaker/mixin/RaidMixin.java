package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Raid.class)
public class RaidMixin {
    @Inject(at = @At("RETURN"), method = "getMaxWaves(Lnet/minecraft/world/Difficulty;)I", cancellable = true)
    public void getMaxWaves(Difficulty difficulty, CallbackInfoReturnable<Integer> info) {
        if (Reference.getConfig().raidActive)
            info.setReturnValue(Reference.getConfig().raidCount);
    }
}