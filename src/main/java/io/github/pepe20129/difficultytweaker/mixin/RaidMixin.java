package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Raid.class)
public class RaidMixin {
	@Inject(at = @At("RETURN"), method = "getMaxWaves(Lnet/minecraft/world/Difficulty;)I", cancellable = true)
	private void getMaxWaves(Difficulty difficulty, CallbackInfoReturnable<Integer> cir) {
		if (ConfigHelper.getConfig().raid.active)
			cir.setReturnValue(ConfigHelper.getConfig().raid.waveCount);
	}

	@Shadow @Final private int waveCount;
	@Inject(at = @At("HEAD"), method = "getCount(Lnet/minecraft/village/raid/Raid$Member;IZ)I", cancellable = true)
	private void preventOutOfBoundsException(Raid.Member member, int wave, boolean extra, CallbackInfoReturnable<Integer> cir) {
		try {
			int temp = member.countInWave[waveCount];
			temp = member.countInWave[wave];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Prevented OutOfBounds exception on Raid.getCount");
			cir.setReturnValue(0);
		}
	}
}