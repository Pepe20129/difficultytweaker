package io.github.pepe20129.difficultytweaker.mixin;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import io.github.pepe20129.difficultytweaker.CommandVars;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalDifficulty.class)
public class LocalDifficultyMixin {

    @Shadow @Final private float localDifficulty;
    @Shadow @Final private Difficulty globalDifficulty;
    @Inject(at = @At("HEAD"), method = "setLocalDifficulty(Lnet/minecraft/world/Difficulty;JJF)F", cancellable = true)
	private void setLocalDifficulty(Difficulty difficulty, long timeOfDay, long inhabitedTime, float moonSize, CallbackInfoReturnable<Float> info) {
        CommandVars.loadConfig();
        if(CommandVars.ldActive) {
            float everything = CommandVars.ldStart;

            //1 day = 24k ticks
            //(timeOfDay-3days/60days clamped between 0 & 1.5) *.25
            float dayTimeFactor = MathHelper.clamp(((float)timeOfDay + -72000.0F) / 1440000.0F, 0.0F, CommandVars.ldDayTimeClampMax) * 0.25F;
            everything += dayTimeFactor;
            float chunkAndMoonFactor = 0.0F;

            //Add between 0 & 1.5 depending on Inhabitation. max 150 days
            chunkAndMoonFactor += MathHelper.clamp((float)inhabitedTime / 3600000.0F, 0.0F, CommandVars.ldChunkClampMax) * 1.25F;

            //Add between 0 & dayTimeFactor depending on moonSize
            chunkAndMoonFactor += MathHelper.clamp(moonSize * CommandVars.ldMoon, 0.0F, dayTimeFactor);
            everything += chunkAndMoonFactor;

            info.setReturnValue(3 * everything);
        } else {
            if(difficulty == Difficulty.PEACEFUL) {
                info.setReturnValue(0F);
            }

            boolean isHard = (difficulty == Difficulty.HARD);
            float everything = 0.75F;

            //1 day = 24k ticks
            //(timeOfDay-3days/60days clamped between 0 & 1) *.25
            float dayTimeFactor = MathHelper.clamp(((float)timeOfDay + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
            everything += dayTimeFactor;

            float chunkAndMoonFactor = 0.0F;

            //Add between 0 & 1 depending on Inhabitation max 150 days     //if not hard (*.75)
            chunkAndMoonFactor += MathHelper.clamp((float)inhabitedTime / 3600000.0F, 0.0F, 1.0F) * (isHard ? 1.0F : 0.75F);

            //Add between 0 & dayTimeFactor depending on moonSize
            chunkAndMoonFactor += MathHelper.clamp(moonSize * 0.25F, 0.0F, dayTimeFactor);

            if (difficulty == Difficulty.EASY) {
                chunkAndMoonFactor *= 0.5F;
            }
            everything += chunkAndMoonFactor;

            info.setReturnValue(difficulty.getId() * everything);
        }
	}

    @Inject(at = @At("HEAD"), method = "getClampedLocalDifficulty()F", cancellable = true)
    private void getClampedLocalDifficulty(CallbackInfoReturnable<Float> info) {
        float v = (this.localDifficulty - 2F) / 2F;
        CommandVars.loadConfig();
        if(CommandVars.cldActive) {
            if(this.localDifficulty < CommandVars.cldMinClampLim) {
                v = CommandVars.cldMinClamp;
            }
            if(this.localDifficulty > CommandVars.cldMaxClampLim) {
                v = CommandVars.cldMaxClamp;
            }
        } else {
            if(this.localDifficulty < 2F) {
                v = 0F;
            }
            if(this.localDifficulty > 4F) {
                v = 1F;
            }
        }
        info.setReturnValue(v);
    }
}