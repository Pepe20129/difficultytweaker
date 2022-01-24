package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends HostileEntity {
    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @author Pepe20129/Pablo#1981
     */
    @Overwrite
    public void initEquipment(LocalDifficulty difficulty) {
        super.initEquipment(difficulty);
        float v;
        if (this.world.getDifficulty() == Difficulty.HARD) {
            v = 0.05F;
        } else {
            v = 0.01F;
        }

        if (Reference.getConfig().zombie.active) {
            v = Reference.getConfig().zombie.weaponChance;
        }
        if (this.random.nextFloat() < v) {
            int i = this.random.nextInt(3);
            if (i == 0) {
                equipStack(EquipmentSlot.MAINHAND, new ItemStack((ItemConvertible)Items.IRON_SWORD));
            } else {
                equipStack(EquipmentSlot.MAINHAND, new ItemStack((ItemConvertible)Items.IRON_SHOVEL));
            }
        }
    }

    /**
     * @author Pepe20129/Pablo#1981
     */
    @Overwrite
    public void onKilledOther(ServerWorld world, LivingEntity other) {
        super.onKilledOther(world, other);
        if ((Reference.getConfig().zombieVillager.active || (world.getDifficulty() == Difficulty.NORMAL || world.getDifficulty() == Difficulty.HARD)) && other instanceof VillagerEntity) {
            if (Reference.getConfig().zombieVillager.active && this.random.nextFloat() >= Reference.getConfig().zombieVillager.chance) {
                return;
            } else {
                if (world.getDifficulty() != Difficulty.HARD && this.random.nextBoolean())
                    return;
            }

            VillagerEntity villagerEntity = (VillagerEntity)other;
            ZombieVillagerEntity zombieVillagerEntity = (ZombieVillagerEntity)villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
            zombieVillagerEntity.initialize(
                    world,
                    world.getLocalDifficulty(zombieVillagerEntity.getBlockPos()),
                    SpawnReason.CONVERSION,
                    new ZombieEntity.ZombieData(false, true),
                    (NbtCompound)null
            );
            zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
            zombieVillagerEntity.setGossipData((NbtElement)villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
            zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
            zombieVillagerEntity.setXp(villagerEntity.getExperience());
            if (!this.isSilent()) {
                world.syncWorldEvent((PlayerEntity)null, 1026, this.getBlockPos(), 0);
            }
        }
    }
}