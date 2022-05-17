package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WitherEntity.class)
public class WitherEntityMixin {
	@Redirect(
		method = "mobTick()V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;getDifficulty()Lnet/minecraft/world/Difficulty;"
		)
	)
	private Difficulty modifyAdditionalFire(World world) {
		return ConfigHelper.getConfig().wither.active ? (ConfigHelper.getConfig().wither.idleSkulls ? Difficulty.HARD : Difficulty.EASY) : world.getDifficulty();
	}
}