package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Random;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin extends Block {
    public NetherPortalBlockMixin(Settings settings) {
        super(settings);
    }

    /**
     * @author Pepe20129/Pablo#1981
     */
    @Overwrite
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int v;
        if (Reference.getConfig().netherPortal.active)
            v = Reference.getConfig().netherPortal.probability;
        else
            v = world.getDifficulty().getId();

        if (world.getDimension().isNatural() && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && random.nextInt(2000) < v) {
            while (world.getBlockState(pos).isOf(this)) {
                pos = pos.down();
            }
            if (world.getBlockState(pos).allowsSpawning((BlockView)world, pos, EntityType.ZOMBIFIED_PIGLIN)) {
                Entity lv = EntityType.ZOMBIFIED_PIGLIN.spawn(world, null, null, null, pos.up(), SpawnReason.STRUCTURE, false, false);
                if (lv != null)
                    lv.resetNetherPortalCooldown();
            }
        }
    }
}