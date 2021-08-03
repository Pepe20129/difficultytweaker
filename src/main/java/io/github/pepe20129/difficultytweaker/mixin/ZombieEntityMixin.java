package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import io.github.pepe20129.difficultytweaker.CommandVars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends HostileEntity {
    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "initEquipment(Lnet/minecraft/world/LocalDifficulty;)V", cancellable = true)
    protected void initEquipment(LocalDifficulty difficulty, CallbackInfo info) {
        super.initEquipment(difficulty);
        float v;
        if (this.world.getDifficulty() == Difficulty.HARD) {
            v = 0.05F;
        } else {
            v = 0.01F;
        }

        Reference.getConfig().loadConfig();
        if (Reference.getConfig().zombieActive) {
            v = Reference.getConfig().zombieWeaponChance;
        }
        if (this.random.nextFloat() < v) {
            int i = this.random.nextInt(3);
            if (i == 0) {
                equipStack(EquipmentSlot.MAINHAND, new ItemStack((ItemConvertible)Items.IRON_SWORD));
            } else {
                equipStack(EquipmentSlot.MAINHAND, new ItemStack((ItemConvertible)Items.IRON_SHOVEL));
            }
        }
        return;
    }
}