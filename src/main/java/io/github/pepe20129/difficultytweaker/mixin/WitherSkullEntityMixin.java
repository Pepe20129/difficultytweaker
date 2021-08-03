package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkullEntity.class)
public class WitherSkullEntityMixin extends ExplosiveProjectileEntity {
    protected WitherSkullEntityMixin(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V", cancellable = true)
    protected void onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
        boolean bl2 = false;
        super.onEntityHit(entityHitResult);
        if (this.world.isClient)
            return;  Entity lv = entityHitResult.getEntity();
        Entity lv2 = getOwner();

        if (lv2 instanceof LivingEntity) {
            LivingEntity lv3 = (LivingEntity)lv2;
            boolean bl = lv.damage(DamageSource.witherSkull((WitherSkullEntity) (Object) this, (Entity)lv3), 8.0F);
            if (bl) {
                if (lv.isAlive()) {
                    applyDamageEffects(lv3, lv);
                } else {
                    lv3.heal(5.0F);
                }
            }
        } else {
            bl2 = lv.damage(DamageSource.MAGIC, 5.0F);
        }
        if (bl2 && lv instanceof LivingEntity) {
            int i = 0;
            if (this.world.getDifficulty() == Difficulty.NORMAL) {
                i = 10;
            } else if (this.world.getDifficulty() == Difficulty.HARD) {
                i = 40;
            }

            Reference.getConfig().loadConfig();
            if (Reference.getConfig().witherSkullActive) {
                i = Reference.getConfig().witherSkullLength;
            }

            if (i > 0) {
                ((LivingEntity)lv).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20 * i, 1));
            }
        }
        return;
    }
}