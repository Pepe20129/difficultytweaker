package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.entity.LightningEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightningEntity.class)
public class LightningEntityMixin {
	@Redirect(
		method = "tick()V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;getDifficulty()Lnet/minecraft/world/Difficulty;"
		)
	)
	private Difficulty modifyAdditionalFire(World world) {
		return ConfigHelper.getConfig().lightning.active ? (ConfigHelper.getConfig().lightning.additionalFire ? Difficulty.HARD : Difficulty.EASY) : world.getDifficulty();
	}
}