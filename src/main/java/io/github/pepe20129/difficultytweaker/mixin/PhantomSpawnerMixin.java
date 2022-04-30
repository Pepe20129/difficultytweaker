package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
	@ModifyVariable(at = @At(value = "STORE"), method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", ordinal = 3)
	private int modifyPhantomSpawns(int original) {
		if (ConfigHelper.getConfig().phantom.active) {
			if (ConfigHelper.getConfig().phantom.min >= ConfigHelper.getConfig().phantom.max)
				return ConfigHelper.getConfig().phantom.max;
			return ConfigHelper.getConfig().phantom.min + ThreadLocalRandom.current().nextInt(ConfigHelper.getConfig().phantom.max - ConfigHelper.getConfig().phantom.min);
		}
		return original;
	}
}