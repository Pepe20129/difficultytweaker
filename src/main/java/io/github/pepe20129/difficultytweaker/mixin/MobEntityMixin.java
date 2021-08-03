package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import io.github.pepe20129.difficultytweaker.CommandVars;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
        Reference.getConfig().loadConfig();
        if (Reference.getConfig().mobActive) {
            int i = (int)(getHealth() - getMaxHealth() * Reference.getConfig().mobFall);
            info.setReturnValue(i + 3);
        }
    }

    @ModifyVariable(at = @At("STORE"), method = "initEquipment(Lnet/minecraft/world/LocalDifficulty;)V", ordinal = 0)
    float f(float f) {
        Reference.getConfig().loadConfig();
        if(Reference.getConfig().mobActive)
            return Reference.getConfig().mobArmorProb;
        return (this.world.getDifficulty() == Difficulty.HARD) ? 0.1F : 0.25F;
    }
}