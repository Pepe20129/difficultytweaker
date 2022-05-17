package io.github.pepe20129.difficultytweaker.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class ConfigHelper {
	private static final Logger LOGGER = LogManager.getLogger("difficulty_tweaker");

	private static Config config = new Config();

	public static Config getConfig() {
		return config;
	}

	public static void setConfig(Config config) {
		ConfigHelper.config = config;
	}

	public static void saveConfig(MinecraftServer server) {
		File configFile = ConfigHelper.getConfigFile(server);
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter fileWriter = new FileWriter(configFile);
			fileWriter.write(gson.toJson(getConfig()));
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void reloadConfig(MinecraftServer server) {
		Config config = new Config();
		File configFile = getConfigFile(server);
		try {
			if (configFile.exists()) {
				Gson gson = new GsonBuilder().create();
				config = gson.fromJson(Files.readString(configFile.toPath()), Config.class);
			} else {
				config = new Config();
			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter fileWriter = new FileWriter(configFile);
			fileWriter.write(gson.toJson(config));
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setConfig(config);
	}

	private static File getConfigFile(MinecraftServer server) {
		if (server.isDedicated()) {
			return new File(FabricLoader.getInstance().getConfigDir().toFile(), "difficultytweaker.json");
		}

		// Integrated Server
		Path rootSavePath = ((MinecraftServerAccessor)server).getSession().getWorldDirectory(World.OVERWORLD);
		File configPath = new File(String.valueOf(rootSavePath), "config");

		if (!configPath.mkdirs()) {
			LOGGER.debug("Failed to create " + configPath + ". Does this directory already exist?");
		}
		return new File(configPath, "difficultytweaker.json");
	}
}