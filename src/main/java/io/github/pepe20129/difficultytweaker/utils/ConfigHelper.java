package io.github.pepe20129.difficultytweaker.utils;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import io.github.pepe20129.difficultytweaker.Reference;
import io.github.pepe20129.difficultytweaker.mixin.MinecraftServerAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

class ConfigHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    static void reloadConfig(MinecraftServer server) {
        ModConfig config = new ModConfig();
        File configFile = getConfigFile(server);
        try {
            if (configFile.exists()) {
                config = new Toml().read(configFile).to(ModConfig.class);
            } else {
                config = new ModConfig();
            }
            new TomlWriter().write(config, configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Reference.setConfig(config);
    }

    static File getConfigFile(MinecraftServer server) {
        if(server.isDedicated()) {
            return new File(FabricLoader.getInstance().getConfigDir().toFile(), "difficultytweaker.toml");
        }

        // Integrated Server
        File rootSavePath = ((MinecraftServerAccessor)server).getSession().getWorldDirectory(World.OVERWORLD);
        File configPath = new File(rootSavePath, "config");

        if(!configPath.mkdirs()) {
            LOGGER.debug("Failed to create " + configPath + ". Does this directory already exist?");
        }
        return new File(configPath, "difficultytweaker.toml");
    }
}
