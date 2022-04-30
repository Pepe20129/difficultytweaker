package io.github.pepe20129.difficultytweaker.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import java.util.Arrays;

@Mixin(targets = "net.minecraft.village.raid.Raid$Member")
public abstract class RaidMemberMixin {
	@ModifyArg(
			method = "<clinit>",
			slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=VINDICATOR")),
			at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid$Member;<init>(Ljava/lang/String;ILnet/minecraft/entity/EntityType;[I)V", ordinal = 0)
	)
	private static int[] modifyVindicatorArray(int[] arr) {
		int length = arr.length;
		int[] arr2 = {5, 5, 6, 6, 6, 7, 7, 7};
		int length2 = arr2.length;
		arr = Arrays.copyOf(arr, length + length2);
		for (int i = 0; i < length2; i += 1) {
			arr[length + i] = arr2[i];
		}
		return arr;
	}

	@ModifyArg(
			method = "<clinit>",
			slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=EVOKER")),
			at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid$Member;<init>(Ljava/lang/String;ILnet/minecraft/entity/EntityType;[I)V", ordinal = 0)
	)
	private static int[] modifyEvokerArray(int[] arr) {
		int length = arr.length;
		int[] arr2 = {2, 3, 3, 4, 4, 5, 5, 6};
		int length2 = arr2.length;
		arr = Arrays.copyOf(arr, length + length2);
		for (int i = 0; i < length2; i += 1) {
			arr[length + i] = arr2[i];
		}
		return arr;
	}

	@ModifyArg(
			method = "<clinit>",
			slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=PILLAGER")),
			at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid$Member;<init>(Ljava/lang/String;ILnet/minecraft/entity/EntityType;[I)V", ordinal = 0)
	)
	private static int[] modifyPillagerArray(int[] arr) {
		int length = arr.length;
		int[] arr2 = {4, 5, 6, 6, 7, 8, 9, 9};
		int length2 = arr2.length;
		arr = Arrays.copyOf(arr, length + length2);
		for (int i = 0; i < length2; i += 1) {
			arr[length + i] = arr2[i];
		}
		return arr;
	}

	@ModifyArg(
			method = "<clinit>",
			slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=WITCH")),
			at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid$Member;<init>(Ljava/lang/String;ILnet/minecraft/entity/EntityType;[I)V", ordinal = 0)
	)
	private static int[] modifyWitchArray(int[] arr) {
		int length = arr.length;
		int[] arr2 = {3, 3, 4, 4, 4, 5, 5, 5};
		int length2 = arr2.length;
		arr = Arrays.copyOf(arr, length + length2);
		for (int i = 0; i < length2; i += 1) {
			arr[length + i] = arr2[i];
		}
		return arr;
	}

	@ModifyArg(
			method = "<clinit>",
			slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=RAVAGER")),
			at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid$Member;<init>(Ljava/lang/String;ILnet/minecraft/entity/EntityType;[I)V", ordinal = 0)
	)
	private static int[] modifyRavagerArray(int[] arr) {
		int length = arr.length;
		int[] arr2 = {2, 3, 3, 3, 3, 4, 4, 4};
		int length2 = arr2.length;
		arr = Arrays.copyOf(arr, length + length2);
		for (int i = 0; i < length2; i += 1) {
			arr[length + i] = arr2[i];
		}
		return arr;
	}
}
