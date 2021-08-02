package io.github.pepe20129.difficultytweaker.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import io.github.pepe20129.difficultytweaker.CommandVars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Random;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {

    protected final Random random = new Random();
    @Shadow public void setDamage(double damage) {}
    @Shadow public double getDamage() {
        throw new AssertionError();
    }
    @Shadow public void setPunch(int punch) {}
    @Inject(at = @At("HEAD"), method = "applyEnchantmentEffects(Lnet/minecraft/entity/LivingEntity;F)V", cancellable = true)
    public void applyEnchantmentEffects(LivingEntity entity, float damageModifier, CallbackInfo ci) {
        int i = EnchantmentHelper.getEquipmentLevel(Enchantments.POWER, entity);
        int j = EnchantmentHelper.getEquipmentLevel(Enchantments.PUNCH, entity);
        CommandVars.loadConfig();
        if (CommandVars.projectileActive) {
            setDamage((damageModifier * 2.0F) + random.nextGaussian() * 0.25D + CommandVars.projectileBonus);
        } else {
            setDamage((damageModifier * 2.0F) + random.nextGaussian() * 0.25D + (((ProjectileEntity) (Object) this).world.getDifficulty().getId() * 0.11F));
        }

        if (i > 0) {
            setDamage(getDamage() + i * 0.5D + 0.5D);
        }
        if (j > 0) {
            setPunch(j);
        }
        if (EnchantmentHelper.getEquipmentLevel(Enchantments.FLAME, entity) > 0) {
            ((ProjectileEntity) (Object) this).setOnFireFor(100);
        }
        return;
    }
}