package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "getSafeFallDistance()I", cancellable = true)
    public void getSafeFallDistance(CallbackInfoReturnable<Integer> info) {
        if (Reference.getConfig().mobActive) {
            int i = (int)(getHealth() - getMaxHealth() * Reference.getConfig().mobFall);
            info.setReturnValue(i + 3);
        }
    }

    @ModifyVariable(at = @At("STORE"), method = "initEquipment(Lnet/minecraft/world/LocalDifficulty;)V", ordinal = 0)
    float modifyArmorChance(float original) {
        return Reference.getConfig().mobActive ? Reference.getConfig().mobArmorProb : original;
    }
}