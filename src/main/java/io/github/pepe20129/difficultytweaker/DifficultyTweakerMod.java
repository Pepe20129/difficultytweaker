package io.github.pepe20129.difficultytweaker;

import io.github.pepe20129.difficultytweaker.utils.FabricCommonCommandRegistration;
import io.github.pepe20129.difficultytweaker.utils.FabricServerLifecycleEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class DifficultyTweakerMod implements ModInitializer {
	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTED.register(FabricServerLifecycleEvents::onStart);
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(FabricServerLifecycleEvents::onReloadCommand);

		//Brigadier nightmare.
		CommandRegistrationCallback.EVENT.register(FabricCommonCommandRegistration::register);
	}
}