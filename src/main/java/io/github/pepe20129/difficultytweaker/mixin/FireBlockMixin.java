package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Random;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin extends AbstractFireBlock {
    public FireBlockMixin(Settings settings, float damage) {
        super(settings, damage);
    }

    @Shadow private boolean areBlocksAroundFlammable(BlockView world, BlockPos pos) {
        throw new AssertionError();
    }
    @Shadow protected boolean isRainingAround(World world, BlockPos pos) {
        throw new AssertionError();
    }
    @Shadow private void trySpreadingFire(World world, BlockPos pos, int spreadFactor, Random rand, int currentAge) {}
    @Shadow private int getBurnChance(WorldView arg, BlockPos pos) {
        throw new AssertionError();
    }
    @Shadow private static int getFireTickDelay(Random random) {
        throw new AssertionError();
    }
    @Shadow private BlockState getStateWithAge(WorldAccess arg, BlockPos arg2, int i) {
        throw new AssertionError();
    }
    @Inject(at = @At("HEAD"), method = "scheduledTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", cancellable = true)
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
        world.getBlockTickScheduler().schedule(pos, this, getFireTickDelay(world.random));
        if (!world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            return;
        }

        if (!state.canPlaceAt((WorldView)world, pos)) {
            world.removeBlock(pos, false);
        }

        BlockState lv = world.getBlockState(pos.down());
        boolean bl = lv.isIn(world.getDimension().getInfiniburnBlocks());

        int i = (Integer) state.get(FireBlock.AGE);
        if (!bl && world.isRaining() && isRainingAround((World)world, pos) && random.nextFloat() < 0.2F + i * 0.03F) {
            world.removeBlock(pos, false);

            return;
        }
        int j = Math.min(15, i + random.nextInt(3) / 2);
        if (i != j) {
            state = (BlockState)state.with(FireBlock.AGE, Integer.valueOf(j));
            world.setBlockState(pos, state, 4);
        }

        if (!bl) {
            if (!areBlocksAroundFlammable((BlockView)world, pos)) {
                BlockPos lv2 = pos.down();
                if (!world.getBlockState(lv2).isSideSolidFullSquare((BlockView)world, lv2, Direction.UP) || i > 3) {
                    world.removeBlock(pos, false);
                }

                return;
            }

            if (i == 15 && random.nextInt(4) == 0 && !isFlammable(world.getBlockState(pos.down()))) {
                world.removeBlock(pos, false);

                return;
            }
        }
        boolean bl2 = world.hasHighHumidity(pos);
        int k = bl2 ? -50 : 0;

        trySpreadingFire((World)world, pos.east(), 300 + k, random, i);
        trySpreadingFire((World)world, pos.west(), 300 + k, random, i);
        trySpreadingFire((World)world, pos.down(), 250 + k, random, i);
        trySpreadingFire((World)world, pos.up(), 250 + k, random, i);
        trySpreadingFire((World)world, pos.north(), 300 + k, random, i);
        trySpreadingFire((World)world, pos.south(), 300 + k, random, i);

        BlockPos.Mutable lv3 = new BlockPos.Mutable();
        for (int l = -1; l <= 1; l++) {
            for (int m = -1; m <= 1; m++) {
                for (int n = -1; n <= 4; n++) {
                    if (l != 0 || n != 0 || m != 0) {

                        int o = 100;
                        if (n > 1) {
                            o += (n - 1) * 100;
                        }

                        lv3.set((Vec3i)pos, l, n, m);
                        int p = getBurnChance((WorldView)world, (BlockPos)lv3);
                        if (p > 0) {
                            int q = 0;
                            Reference.getConfig().loadConfig();
                            if (Reference.getConfig().fireActive) {
                                q = (p + Reference.getConfig().fireEncouragement) / (i + 30);
                            } else {
                                q = (p + 40 + world.getDifficulty().getId() * 7) / (i + 30);
                            }
                            if (bl2) {
                                q /= 2;
                            }
                            if (q > 0 && random.nextInt(o) <= q && (!world.isRaining() || !isRainingAround((World)world, (BlockPos)lv3))) {

                                int r = Math.min(15, i + random.nextInt(5) / 4);
                                world.setBlockState((BlockPos)lv3, getStateWithAge((WorldAccess)world, (BlockPos)lv3, r), 3);
                            }
                        }
                    }
                }
            }
        }
        return;
    }
}



/*
public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    world.getBlockTickScheduler().schedule(pos, this, method_26155(world.random));
    if (!world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
        return;
    }

    if (!state.canPlaceAt((WorldView)world, pos)) {
        world.removeBlock(pos, false);
    }

    BlockState lv = world.getBlockState(pos.down());
    boolean bl = lv.isIn(world.getDimension().getInfiniburnBlocks());

    int i = ((Integer)state.get((Property)AGE)).intValue();
    if (!bl && world.isRaining() && isRainingAround((World)world, pos) && random.nextFloat() < 0.2F + i * 0.03F) {
        world.removeBlock(pos, false);

        return;
    }
    int j = Math.min(15, i + random.nextInt(3) / 2);
    if (i != j) {
        state = (BlockState)state.with((Property)AGE, Integer.valueOf(j));
        world.setBlockState(pos, state, 4);
    }

    if (!bl) {
        if (!areBlocksAroundFlammable((BlockView)world, pos)) {
            BlockPos lv2 = pos.down();
            if (!world.getBlockState(lv2).isSideSolidFullSquare((BlockView)world, lv2, Direction.UP) || i > 3) {
                world.removeBlock(pos, false);
            }

            return;
        }

        if (i == 15 && random.nextInt(4) == 0 && !isFlammable(world.getBlockState(pos.down()))) {
            world.removeBlock(pos, false);

            return;
        }
    }
    boolean bl2 = world.hasHighHumidity(pos);
    int k = bl2 ? -50 : 0;

    trySpreadingFire((World)world, pos.east(), 300 + k, random, i);
    trySpreadingFire((World)world, pos.west(), 300 + k, random, i);
    trySpreadingFire((World)world, pos.down(), 250 + k, random, i);
    trySpreadingFire((World)world, pos.up(), 250 + k, random, i);
    trySpreadingFire((World)world, pos.north(), 300 + k, random, i);
    trySpreadingFire((World)world, pos.south(), 300 + k, random, i);

    BlockPos.Mutable lv3 = new BlockPos.Mutable();
    for (int l = -1; l <= 1; l++) {
        for (int m = -1; m <= 1; m++) {
            for (int n = -1; n <= 4; n++) {
                if (l != 0 || n != 0 || m != 0) {

                    int o = 100;
                    if (n > 1) {
                        o += (n - 1) * 100;
                    }

                    lv3.set((Vec3i)pos, l, n, m);
                    int p = getBurnChance((WorldView)world, (BlockPos)lv3);
                    if (p > 0) {
                        int q = (p + 40 + world.getDifficulty().getId() * 7) / (i + 30);
                        if (bl2) {
                            q /= 2;
                        }
                        if (q > 0 && random.nextInt(o) <= q && (!world.isRaining() || !isRainingAround((World)world, (BlockPos)lv3))) {

                            int r = Math.min(15, i + random.nextInt(5) / 4);
                            world.setBlockState((BlockPos)lv3, method_24855((WorldAccess)world, (BlockPos)lv3, r), 3);
                        }
                    }
                }
            }
        }
    }
}
*/