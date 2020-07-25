package com.eclipsekingdom.warpmagic.sys.config;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.google.common.collect.ImmutableList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigLoader {

    private static final String pluginFolder = "WarpMagic";

    private static ImmutableList<String> configs = new ImmutableList.Builder<String>()
            .add("config")
            .build();

    private static ImmutableList<String> languages = new ImmutableList.Builder<String>()
            .add("en")
            .build();

    public static void load() {
        try {
            for (String config : configs) {
                File target = new File("plugins/" + pluginFolder, config + ".yml");
                if (!target.exists()) {
                    load("Config/" + config + ".yml", target);
                }
            }
            for (String lang : languages) {
                File target = new File("plugins/" + pluginFolder + "/Locale", lang + ".yml");
                if (!target.exists()) {
                    load("Locale/" + lang + ".yml", target);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void load(String resource, File file) throws IOException {
        file.getParentFile().mkdirs();
        InputStream in = WarpMagic.getPlugin().getResource(resource);
        Files.copy(in, file.toPath());
        in.close();
    }


}
