package io.github.pepe20129.difficultytweaker;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;

public class Reference {
    private static ModConfig config = new ModConfig();

    static {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "difficultytweaker.toml");;
        try {
            if(configFile.exists()) {

                config = new Toml().read(configFile).to(ModConfig.class);
            }
            new TomlWriter().write(new ModConfig(), configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ModConfig getConfig() {
        return config;
    }
}
