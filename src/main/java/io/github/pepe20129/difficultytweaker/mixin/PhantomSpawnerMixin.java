package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.*;
import net.minecraft.world.gen.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {

    /*
    @ModifyVariable(at = @At("STORE"), method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", ordinal = 3)
    int l(int l) {
        if(Reference.getConfig().phantomActive)
            return Reference.getConfig().phantomMin + ThreadLocalRandom.current().nextInt(Reference.getConfig().phantomMax - Reference.getConfig().phantomMin);
        return 1 + ThreadLocalRandom.current().nextInt(lv3.getGlobalDifficulty().getId() + 1);
    }
     */


    @Shadow private int ticksUntilNextSpawn;
    @Inject(at = @At("HEAD"), method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", cancellable = true)
    public void spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> info) {
        if(!spawnMonsters) {
            info.setReturnValue(0);
            }

        if(!world.getGameRules().getBoolean(GameRules.DO_INSOMNIA)) {
            info.setReturnValue(0);
        }

        Random random = world.random;

        this.ticksUntilNextSpawn--;
        if(this.ticksUntilNextSpawn > 0) {
            info.setReturnValue(0);
        }
        this.ticksUntilNextSpawn += (60 + random.nextInt(60)) * 20;

        if(world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight()) {
            info.setReturnValue(0);
        }

        int i = 0;
        for(PlayerEntity lv : world.getPlayers()) {
            if(lv.isSpectator()) {
                continue;
            }
            BlockPos lv2 = lv.getBlockPos();
            if(world.getDimension().hasSkyLight() && (lv2.getY() < world.getSeaLevel() || !world.isSkyVisible(lv2))) {
                continue;
            }
            LocalDifficulty lv3 = world.getLocalDifficulty(lv2);
            if(!lv3.isHarderThan(random.nextFloat() * 3.0F)) {
                continue;
            }

            ServerStatHandler lv4 = ((ServerPlayerEntity)lv).getStatHandler();
            int j = MathHelper.clamp(lv4.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, 2147483647);
            int k = 24000;
            if(random.nextInt(j) < 72000) {
                continue;
            }

            BlockPos lv5 = lv2.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21));
            BlockState lv6 = world.getBlockState(lv5);
            FluidState lv7 = world.getFluidState(lv5);
            if(!SpawnHelper.isClearForSpawn((BlockView)world, lv5, lv6, lv7, EntityType.PHANTOM)) {
                continue;
            }

            EntityData lv8 = null;
            int l;
            Reference.getConfig().loadConfig();
            if(Reference.getConfig().phantomActive) {
                l = Reference.getConfig().phantomMin + random.nextInt(Reference.getConfig().phantomMax - Reference.getConfig().phantomMin);
            } else {
                l = 1 + random.nextInt(lv3.getGlobalDifficulty().getId() + 1);
            }
            for(int m = 0; m < l; m++) {
                PhantomEntity lv9 = (PhantomEntity) EntityType.PHANTOM.create((World)world);
                lv9.refreshPositionAndAngles(lv5, 0.0F, 0.0F);
                lv8 = lv9.initialize((ServerWorldAccess)world, lv3, SpawnReason.NATURAL, lv8, null);
                world.spawnEntityAndPassengers((Entity)lv9);
            }
            i += l;
        }
        info.setReturnValue(i);
    }
}

/*
public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
  if (!spawnMonsters)
    return 0;
  if (!world.getGameRules().getBoolean(GameRules.DO_INSOMNIA))
    return 0;
  Random random = world.random;
  this.ticksUntilNextSpawn--;
  if (this.ticksUntilNextSpawn > 0)
    return 0;
  this.ticksUntilNextSpawn += (60 + random.nextInt(60)) * 20;
  if (world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight())
    return 0;
  int i = 0;//0
  for (PlayerEntity lv : world.getPlayers()) {
    if (lv.isSpectator())
      continue;
    BlockPos lv2 = lv.getBlockPos();
    if (world.getDimension().hasSkyLight() && (lv2.getY() < world.getSeaLevel() || !world.isSkyVisible(lv2)))
      continue;
    LocalDifficulty lv3 = world.getLocalDifficulty(lv2);
    if (!lv3.isHarderThan(random.nextFloat() * 3.0F))
      continue;
    ServerStatHandler lv4 = ((ServerPlayerEntity)lv).getStatHandler();
    int j = MathHelper.clamp(lv4.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, 2147483647);//1
    int k = 24000;//2
    if (random.nextInt(j) < 72000)
      continue;
    BlockPos lv5 = lv2.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21));
    BlockState lv6 = world.getBlockState(lv5);
    FluidState lv7 = world.getFluidState(lv5);
    if (!SpawnHelper.isClearForSpawn((BlockView)world, lv5, lv6, lv7, EntityType.PHANTOM))
      continue;
    EntityData lv8 = null;
    int l = 1 + random.nextInt(lv3.getGlobalDifficulty().getId() + 1);//3
    for (int m = 0; m < l; m++) {
      PhantomEntity lv9 = (PhantomEntity)EntityType.PHANTOM.create((World)world);
      lv9.refreshPositionAndAngles(lv5, 0.0F, 0.0F);
      lv8 = lv9.initialize((ServerWorldAccess)world, lv3, SpawnReason.NATURAL, lv8, null);
      world.spawnEntityAndPassengers((Entity)lv9);
    }
    i += l;
  }
  return i;
}
 */