package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends HostileEntity implements RangedAttackMob {
    protected AbstractSkeletonEntityMixin(EntityType<? extends HostileEntity> entityType, World world, MeleeAttackGoal meleeAttackGoal) {
        super(entityType, world);
    }

    @ModifyVariable(at = @At("LOAD"), method = "updateAttackType()V", ordinal = -1)
    private int modifySkeletonCooldown(int original) {
        return Reference.getConfig().skeleton.active ? Reference.getConfig().skeleton.cooldown : original;
    }

    @ModifyVariable(at = @At("LOAD"), method = "updateAttackType()V", ordinal = 0)
    private int modifySkeletonCooldownNotHard(int original) {
        return Reference.getConfig().skeleton.active ? Reference.getConfig().skeleton.cooldown : original;
    }

    /*
    TODO: Fix the Inject and find a way to get persistentProjectileEntity
    @Inject(at = @At(value = "INVOKE", ordinal = 11, target = "attack(Lnet/minecraft/entity/LivingEntity;F)V", shift = @At.Shift("AFTER")), method = "attack(Lnet/minecraft/entity/LivingEntity;F)V", cancellable = true)
    private void modifyDivergence(LivingEntity target, float pullProgress, CallbackInfo ci) {
        if (Reference.getConfig().skeletonActive) {
            double d = target.getX() - getX();
            double e = target.getBodyY(0.3333333333333333D) - persistentProjectileEntity.getY();
            double g = target.getZ() - getZ();
            double h = Math.sqrt(d * d + g * g);
            persistentProjectileEntity.setVelocity(d, e + h * 0.20000000298023224D, g, 1.6F, Reference.getConfig().skeletonDivergence);
        }
    }
    */

    @Shadow protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        throw new AssertionError();
    }
    /**
     * @author Pepe20129/Pablo#1981
     */
    @Overwrite
    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = getArrowType(getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
        PersistentProjectileEntity persistentProjectileEntity = createArrowProjectile(itemStack, pullProgress);

        double d = target.getX() - getX();
        double e = target.getBodyY(0.3333333333333333D) - persistentProjectileEntity.getY();
        double g = target.getZ() - getZ();
        double h = Math.sqrt(d * d + g * g);
        if (Reference.getConfig().skeleton.active) {
            persistentProjectileEntity.setVelocity(d, e + h * 0.20000000298023224D, g, 1.6F, Reference.getConfig().skeleton.divergence);
        } else {
            persistentProjectileEntity.setVelocity(d, e + h * 0.20000000298023224D, g, 1.6F, (14 - this.world.getDifficulty().getId() * 4));
        }
        playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
        ((AbstractSkeletonEntity)(Object)this).world.spawnEntity(persistentProjectileEntity);
    }
}