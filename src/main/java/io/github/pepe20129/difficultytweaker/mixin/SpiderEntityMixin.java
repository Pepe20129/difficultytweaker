package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SpiderEntity.class)
public class SpiderEntityMixin {
	@Redirect(
		method = "initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/EntityData;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/ServerWorldAccess;getDifficulty()Lnet/minecraft/world/Difficulty;"
		)
	)
	private Difficulty modifySpiderEffectActivation(ServerWorldAccess world) {
		return ConfigHelper.getConfig().spider.active ? (ConfigHelper.getConfig().spider.effectActivation ? Difficulty.HARD : Difficulty.NORMAL) : world.getDifficulty();
	}
}