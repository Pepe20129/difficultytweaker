package io.github.pepe20129.difficultytweaker.utils;

import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;

public class FabricServerLifecycleEvents {
    public static void onStart(MinecraftServer minecraftServer) {
        ConfigHelper.reloadConfig(minecraftServer);
    }

    @SuppressWarnings("unused")
    public static void onReloadCommand(MinecraftServer minecraftServer, ServerResourceManager serverResourceManager, boolean b) {
        ConfigHelper.reloadConfig(minecraftServer);
    }
}
