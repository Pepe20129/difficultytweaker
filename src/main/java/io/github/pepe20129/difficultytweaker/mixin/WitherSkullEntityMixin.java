package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WitherSkullEntity.class)
public class WitherSkullEntityMixin extends ExplosiveProjectileEntity {
    protected WitherSkullEntityMixin(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V", at = @At(value = "LOAD", ordinal = 0))
    private int modifyWitherDuration(int original) {
        return Reference.getConfig().witherSkullActive ? Reference.getConfig().witherSkullLength : original;
    }
}