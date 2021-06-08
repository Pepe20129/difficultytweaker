package io.github.pepe20129.difficultytweaker.mixin;

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
import io.github.pepe20129.difficultytweaker.CommandVars;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends HostileEntity implements RangedAttackMob {
    protected AbstractSkeletonEntityMixin(EntityType<? extends HostileEntity> entityType, World world, MeleeAttackGoal meleeAttackGoal) {
        super(entityType, world);
    }

    @Shadow @Final private MeleeAttackGoal meleeAttackGoal;
    @Shadow @Final private BowAttackGoal<AbstractSkeletonEntity> bowAttackGoal;
    @Inject(at = @At("HEAD"), method = "updateAttackType()V", cancellable = true)
    public void updateAttackType(CallbackInfo ci) {
        if (this.world == null || this.world.isClient) {
            return;
        }

        this.goalSelector.remove((Goal)this.meleeAttackGoal);
        this.goalSelector.remove((Goal)this.bowAttackGoal);

        ItemStack lv = getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW));
        if (lv.getItem() == Items.BOW) {

            int i = 20;
            if (this.world.getDifficulty() != Difficulty.HARD) {
                i = 40;
            }
            if (CommandVars.skeletonActive) {
                i = CommandVars.skeletonCooldown; //This seems to not work? Maybe not??
            }
            this.bowAttackGoal.setAttackInterval(i);
            this.goalSelector.add(4, (Goal)this.bowAttackGoal);
        } else {
            this.goalSelector.add(4, (Goal)this.meleeAttackGoal);
        }
        return;
    }

    @Shadow protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        throw new AssertionError();
    }
    @Inject(at = @At("HEAD"), method = "attack(Lnet/minecraft/entity/LivingEntity;F)V", cancellable = true)
    public void attack(LivingEntity target, float pullProgress, CallbackInfo ci) {
        ItemStack lv = getArrowType(getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
        PersistentProjectileEntity lv2 = createArrowProjectile(lv, pullProgress);

        double d = target.getX() - getX();
        double e = target.getBodyY(0.3333333333333333D) - lv2.getY();
        double g = target.getZ() - getZ();
        double h = Math.sqrt(d * d + g * g);
        if (CommandVars.skeletonActive) {
            lv2.setVelocity(d, e + h * 0.20000000298023224D, g, 1.6F, CommandVars.skeletonDivergence); //This seems to not work?
        } else {
            lv2.setVelocity(d, e + h * 0.20000000298023224D, g, 1.6F, (14 - this.world.getDifficulty().getId() * 4));
        }
        playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
        return;
    }
}

/*
public void attack(LivingEntity target, float pullProgress) {
    ItemStack lv = getArrowType(getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
    PersistentProjectileEntity lv2 = createArrowProjectile(lv, pullProgress);
    double d = target.getX() - getX();
    double e = target.getBodyY(0.3333333333333333D) - lv2.getY();
    double g = target.getZ() - getZ();
    double h = Math.sqrt(d * d + g * g);
    lv2.setVelocity(d, e + h * 0.20000000298023224D, g, 1.6F, (14 - this.world.getDifficulty().getId() * 4));
    playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
    this.world.spawnEntity((Entity)lv2);
}
 */