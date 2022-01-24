package io.github.pepe20129.difficultytweaker.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.pepe20129.difficultytweaker.Reference;
import io.github.pepe20129.difficultytweaker.mixin.MinecraftServerAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class ConfigHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    static void reloadConfig(MinecraftServer server) {
        ModConfig config = new ModConfig();
        File configFile = getConfigFile(server);
        try {
            if (configFile.exists()) {
                Gson gson = new GsonBuilder().create();
                LOGGER.debug(Files.readString(configFile.toPath()));
                config = gson.fromJson(Files.readString(configFile.toPath()), ModConfig.class);
            } else {
                config = new ModConfig();
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(configFile);
            fileWriter.write(gson.toJson(config));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Reference.setConfig(config);
    }

    static File getConfigFile(MinecraftServer server) {
        if(server.isDedicated()) {
            return new File(FabricLoader.getInstance().getConfigDir().toFile(), "difficultytweaker.json");
        }

        // Integrated Server
        Path rootSavePath = ((MinecraftServerAccessor)server).getSession().getWorldDirectory(World.OVERWORLD);
        File configPath = new File(String.valueOf(rootSavePath), "config");

        if(!configPath.mkdirs()) {
            LOGGER.debug("Failed to create " + configPath + ". Does this directory already exist?");
        }
        return new File(configPath, "difficultytweaker.json");
    }
}
