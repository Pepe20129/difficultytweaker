package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(targets = "net.minecraft.village.raid.Raid$Member")
public abstract class RaidMemberMixin {
	@ModifyArg(
			method = "<clinit>",
			slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=VINDICATOR")),
			at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid$Member;<init>(Ljava/lang/String;ILnet/minecraft/entity/EntityType;[I)V", ordinal = 0)
	)
	private static int[] modifyVindicatorArray(int[] original) {
		return ConfigHelper.getConfig().raidMember.active ? ConfigHelper.getConfig().raidMember.vindicator : original;
	}

	@ModifyArg(
			method = "<clinit>",
			slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=EVOKER")),
			at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid$Member;<init>(Ljava/lang/String;ILnet/minecraft/entity/EntityType;[I)V", ordinal = 0)
	)
	private static int[] modifyEvokerArray(int[] original) {
		return ConfigHelper.getConfig().raidMember.active ? ConfigHelper.getConfig().raidMember.evoker : original;
	}

	@ModifyArg(
			method = "<clinit>",
			slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=PILLAGER")),
			at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid$Member;<init>(Ljava/lang/String;ILnet/minecraft/entity/EntityType;[I)V", ordinal = 0)
	)
	private static int[] modifyPillagerArray(int[] original) {
		return ConfigHelper.getConfig().raidMember.active ? ConfigHelper.getConfig().raidMember.pillager : original;
	}

	@ModifyArg(
			method = "<clinit>",
			slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=WITCH")),
			at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid$Member;<init>(Ljava/lang/String;ILnet/minecraft/entity/EntityType;[I)V", ordinal = 0)
	)
	private static int[] modifyWitchArray(int[] original) {
		return ConfigHelper.getConfig().raidMember.active ? ConfigHelper.getConfig().raidMember.witch : original;
	}

	@ModifyArg(
			method = "<clinit>",
			slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=RAVAGER")),
			at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid$Member;<init>(Ljava/lang/String;ILnet/minecraft/entity/EntityType;[I)V", ordinal = 0)
	)
	private static int[] modifyRavagerArray(int[] original) {
		return ConfigHelper.getConfig().raidMember.active ? ConfigHelper.getConfig().raidMember.ravager : original;
	}
}
