package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity {
	protected BeeEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyVariable(method = "tryAttack(Lnet/minecraft/entity/Entity;)Z", at = @At(value = "LOAD", ordinal = 0))
	private int modifyPoisonDuration(int original) {
		return ConfigHelper.getConfig().bee.active ?  ConfigHelper.getConfig().bee.length : original;
	}
}