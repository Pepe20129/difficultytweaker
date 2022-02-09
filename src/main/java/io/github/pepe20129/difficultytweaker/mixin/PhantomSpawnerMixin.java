package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.world.gen.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {

    @ModifyVariable(at = @At(value = "STORE"), method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", ordinal = 3)
    private int modifyPhantomSpawns(int original) {
        if (Reference.getConfig().phantom.active) {
            if (Reference.getConfig().phantom.min >= Reference.getConfig().phantom.max)
                return Reference.getConfig().phantom.max;
            return Reference.getConfig().phantom.min + ThreadLocalRandom.current().nextInt(Reference.getConfig().phantom.max - Reference.getConfig().phantom.min);
        }
        return original;
    }
}