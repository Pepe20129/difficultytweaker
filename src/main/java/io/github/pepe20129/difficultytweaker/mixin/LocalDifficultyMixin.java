package io.github.pepe20129.difficultytweaker.mixin;

import io.github.pepe20129.difficultytweaker.utils.Config;
import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LocalDifficulty.class)
public class LocalDifficultyMixin {
	@Shadow @Final private float localDifficulty;
	/**
	 * @author Pepe20129/Pablo#1981
	 *
	 * @reason Made specific parameters configurable
	 */
	@Overwrite
	private float setLocalDifficulty(Difficulty difficulty, long timeOfDay, long inhabitedTime, float moonSize) {
		Config config = ConfigHelper.getConfig();
		if (config.localDifficulty.active) {
			float everything = config.localDifficulty.start;

			//1 day = 24k ticks
			//(timeOfDay-3days/60days clamped between 0 & 1.5) *.25
			float dayTimeFactor = MathHelper.clamp(((float)timeOfDay + -72000.0F) / 1440000.0F, 0.0F, config.localDifficulty.dayTimeClampMax) * 0.25F;
			everything += dayTimeFactor;
			float chunkAndMoonFactor = 0.0F;

			//Add between 0 & 1.5 depending on Inhabitation. max 150 days
			chunkAndMoonFactor += MathHelper.clamp((float)inhabitedTime / 3600000.0F, 0.0F, config.localDifficulty.chunkClampMax) * 1.25F;

			//Add between 0 & dayTimeFactor depending on moonSize
			chunkAndMoonFactor += MathHelper.clamp(moonSize * config.localDifficulty.moon, 0.0F, dayTimeFactor);
			everything += chunkAndMoonFactor;

			return 3 * everything;
		} else {
			if (difficulty == Difficulty.PEACEFUL) {
				return 0;
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

			return difficulty.getId() * everything;
		}
	}

	/**
	 * @author Pepe20129/Pablo#1981
	 *
	 * @reason Made specific parameters configurable
	 */
	@Overwrite
	public float getClampedLocalDifficulty() {
		Config config = ConfigHelper.getConfig();
		float v = (localDifficulty - 2F) / 2F;
		if (config.clampedLocalDifficulty.active) {
			if (localDifficulty < config.clampedLocalDifficulty.minClampLim) {
				v = config.clampedLocalDifficulty.minClamp;
			}
			if (localDifficulty > config.clampedLocalDifficulty.maxClampLim) {
				v = config.clampedLocalDifficulty.maxClamp;
			}
		} else {
			if (localDifficulty < 2F) {
				v = 0F;
			}
			if (localDifficulty > 4F) {
				v = 1F;
			}
		}
		return v;
	}
}