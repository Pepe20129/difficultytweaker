package io.github.pepe20129.difficultytweaker;

import org.quiltmc.loader.api.ModContainer;
//import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class QuiltDifficultyTweaker /*implements ModInitializer*/ {
	//@Override
	public void onInitialize(ModContainer modContainer) {
		DifficultyTweaker.onInitialize();
	}
}