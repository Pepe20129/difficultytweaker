package io.github.pepe20129.difficultytweaker;

import io.github.pepe20129.difficultytweaker.utils.ConfigHelper;
import io.github.pepe20129.difficultytweaker.utils.CommandRegistration;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class DifficultyTweaker {
	public static void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTED.register(ConfigHelper::reloadConfig);
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success) -> ConfigHelper.reloadConfig(server));
		CommandRegistrationCallback.EVENT.register(CommandRegistration::register);
	}
}