package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import io.github.pepe20129.difficultytweaker.CommandVars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        Reference.getConfig().loadConfig();
        if (source.isScaledWithDifficulty() && Reference.getConfig().playerActive) {
            amount = amount * Reference.getConfig().playerMultiplier;
            info.setReturnValue(super.damage(source, amount));
        }
    }
}