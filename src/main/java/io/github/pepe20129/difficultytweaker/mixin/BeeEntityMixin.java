package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity {
    protected BeeEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow private void setHasStung(boolean hasStung) {}
    @Inject(at = @At("HEAD"), method = "tryAttack(Lnet/minecraft/entity/Entity;)Z", cancellable = true)
    public void tryAttack(Entity target, CallbackInfoReturnable<Boolean> info) {
        boolean bl = target.damage(DamageSource.sting((LivingEntity)this), (int)getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
        if (bl) {
            applyDamageEffects((LivingEntity)this, target);
            if (target instanceof LivingEntity) {
                ((LivingEntity)target).setStingerCount(((LivingEntity)target).getStingerCount() + 1);
                int i = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    i = 10;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    i = 18;
                }

                Reference.getConfig().loadConfig();
                if (Reference.getConfig().beeActive) {
                    i = Reference.getConfig().beeLength;
                }

                if (i > 0) {
                    ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, i * 20, 0));
                }
            }
            setHasStung(true);
            ((BeeEntity) (Object) this).setAttacker(null);
            ((BeeEntity) (Object) this).setAngryAt(null);
            ((BeeEntity) (Object) this).setTarget(null);
            ((BeeEntity) (Object) this).setAngerTime(0);

            playSound(SoundEvents.ENTITY_BEE_STING, 1.0F, 1.0F);
        }
        info.setReturnValue(bl);
    }
}