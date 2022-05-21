package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.block.Block;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin extends Block {
	public NetherPortalBlockMixin(Settings settings) {
		super(settings);
	}

	@Redirect(
			method = "randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Difficulty;getId()I")
	)
	private int getId(Difficulty difficulty) {
		return ConfigHelper.getConfig().netherPortal.active ? ConfigHelper.getConfig().netherPortal.probability : difficulty.getId();
	}
}