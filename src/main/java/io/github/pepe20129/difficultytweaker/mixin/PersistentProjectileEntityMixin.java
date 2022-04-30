package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {
	@Redirect(
		method = "applyEnchantmentEffects(Lnet/minecraft/entity/LivingEntity;F)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Difficulty;getId()I")
	)
	private int getId(Difficulty difficulty) {
		return ConfigHelper.getConfig().projectile.active ? 1 : difficulty.getId();
	}

	@ModifyConstant(
		method = "applyEnchantmentEffects(Lnet/minecraft/entity/LivingEntity;F)V",
		constant = @Constant(floatValue = 0.11f)
	)
	private static float modifyMultiplier(float original) {
		return ConfigHelper.getConfig().projectile.active ? ConfigHelper.getConfig().projectile.bonus : original;
	}
}