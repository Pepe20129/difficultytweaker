package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CaveSpiderEntity.class)
public class CaveSpiderEntityMixin extends SpiderEntity {
    public CaveSpiderEntityMixin(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(at = @At(value = "LOAD", ordinal = 0), method = "tryAttack(Lnet/minecraft/entity/Entity;)Z")
    public int modifyPoisonDuration(int original) {
        return Reference.getConfig().caveSpiderActive ? Reference.getConfig().caveSpiderLength : original;
    }
}