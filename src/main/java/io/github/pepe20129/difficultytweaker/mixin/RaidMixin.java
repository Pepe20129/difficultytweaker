package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Raid.class)
public class RaidMixin {
	@Inject(at = @At("RETURN"), method = "getMaxWaves(Lnet/minecraft/world/Difficulty;)I", cancellable = true)
	private void getMaxWaves(Difficulty difficulty, CallbackInfoReturnable<Integer> info) {
		if (ConfigHelper.getConfig().raid.active)
			info.setReturnValue(ConfigHelper.getConfig().raid.count);
	}
}