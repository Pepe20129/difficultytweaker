package io.github.pepe20129.difficultytweaker;

import io.github.pepe20129.difficultytweaker.utils.ModConfig;

public class Reference {
    private static ModConfig config = new ModConfig();

    public static ModConfig getConfig() {
        return config;
    }

    public static void setConfig(ModConfig config) {
        Reference.config = config;
    }
}