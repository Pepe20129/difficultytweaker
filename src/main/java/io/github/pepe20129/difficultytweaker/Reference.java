package io.github.pepe20129.difficultytweaker;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;

public class Reference {
    private static CommandVars config = new CommandVars();

    static {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "difficultytweaker.toml");;
        try {
            if(configFile.exists()) {

                config = new Toml().read(configFile).to(CommandVars.class);
            }
            new TomlWriter().write(new CommandVars(), configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CommandVars getConfig() {
        return config;
    }
}
