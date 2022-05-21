package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin extends AbstractFireBlock {
	public FireBlockMixin(Settings settings, float damage) {
		super(settings, damage);
	}

	@Redirect(
			method = "scheduledTick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Difficulty;getId()I")
	)
	private int getId(Difficulty difficulty) {
		return ConfigHelper.getConfig().fire.active ? (ConfigHelper.getConfig().fire.encouragement - 40) / 7 : difficulty.getId();
	}
}