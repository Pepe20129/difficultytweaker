package io.github.pepe20129.difficultytweaker;

import io.github.pepe20129.difficultytweaker.utils.FabricCommonCommandRegistration;
import io.github.pepe20129.difficultytweaker.utils.FabricServerLifecycleEvents;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class QuiltDifficultyTweaker implements ModInitializer {
	@Override
	public void onInitialize(ModContainer mod) {
		ServerLifecycleEvents.SERVER_STARTED.register(FabricServerLifecycleEvents::onStart);
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(FabricServerLifecycleEvents::onReloadCommand);

		//Brigadier nightmare.
		CommandRegistrationCallback.EVENT.register(FabricCommonCommandRegistration::register);
	}
}