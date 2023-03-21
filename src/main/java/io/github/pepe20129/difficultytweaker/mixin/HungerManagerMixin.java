package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
	@ModifyConstant(method = "update(Lnet/minecraft/entity/player/PlayerEntity;)V", constant = @Constant(floatValue = 1, ordinal = 3), allow = 1, require = 1)
	private float preventVanillaDamage(float original, PlayerEntity player) {
		return 0;
	}

	@Inject(at = @At(value = "FIELD", ordinal = 11, target = "Lnet/minecraft/entity/player/HungerManager;foodTickTimer:I"), method = "update(Lnet/minecraft/entity/player/PlayerEntity;)V")
	private void modifyHungerDamageLimit(PlayerEntity player, CallbackInfo ci) {
		if (ConfigHelper.getConfig().player.active) {
			if (player.getHealth() > ConfigHelper.getConfig().player.hungerDamageThreshold)
				player.damage(player.world.getDamageSources().starve(), 1F);
		} else {
			if (player.getHealth() > 10F || player.world.getDifficulty() == Difficulty.HARD || player.getHealth() > 1F && player.world.getDifficulty() == Difficulty.NORMAL)
				player.damage(player.world.getDamageSources().starve(), 1F);
		}
	}
}
