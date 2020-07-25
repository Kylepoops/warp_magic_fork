package com.eclipsekingdom.warpmagic.sys.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PluginConfig {

    private static File file = new File("plugins/WarpMagic", "config.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    private static String languageFileString = "Language file";
    private static String languageFile = "en";

    private static String baseWarpNumString = "Base Warp Amount";
    private static int baseWarpNum = 3;

    private static String maxWarpNumString = "Maximum Warp Amount";
    private static int maxWarpNum = 18;

    private static String baseVortexNumString = "Base Vortex Amount";
    private static int baseVortexNum = 0;

    private static String maxVortexNumString = "Maximum Vortex Amount";
    private static int maxVortexNum = 3;

    private static String chargeSecondsString = "Teleport Charge Up Seconds";
    private static int chargeUpSeconds = 0;

    private static String cooldownSecondsString = "Teleport Cooldown Seconds";
    private static int cooldownSeconds = 10;

    private static String tpRequestCooldownString = "Teleport Request Cooldown Seconds";
    private static int tpRequestCooldown = 10;

    private static String soundString = "Play Sound";
    private static boolean sound = true;

    private static String particleString = "Play Particles";
    private static boolean particles = true;

    private static String useDynmapString = "Use dynmap";
    private static boolean useDynmap = true;

    private static String showWarpMarkersByDefaultString = "Show Warp Markers by Default";
    private static boolean showWarpMarkersByDefault = true;


    public PluginConfig() {
        load();
    }

    private void load() {
        if (file.exists()) {
            try {
                languageFile = config.getString(languageFileString);
                baseWarpNum = config.getInt(baseWarpNumString);
                maxWarpNum = config.getInt(maxWarpNumString);
                baseVortexNum = config.getInt(baseVortexNumString);
                maxVortexNum = config.getInt(maxVortexNumString);
                chargeUpSeconds = config.getInt(chargeSecondsString);
                cooldownSeconds = config.getInt(cooldownSecondsString);
                tpRequestCooldown = config.getInt(tpRequestCooldownString);
                sound = config.getBoolean(soundString);
                particles = config.getBoolean(particleString);
                useDynmap = config.getBoolean(useDynmapString);
                showWarpMarkersByDefault = config.getBoolean(showWarpMarkersByDefaultString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getLanguageFile() {
        return languageFile;
    }

    public static int getBaseWarpNum() {
        return baseWarpNum;
    }

    public static int getMaxWarpNum() {
        return maxWarpNum;
    }

    public static int getBaseVortexNum() {
        return baseVortexNum;
    }

    public static int getMaxVortexNum() {
        return maxVortexNum;
    }

    public static int getChargeUpSeconds() {
        return chargeUpSeconds;
    }

    public static int getCooldownSeconds() {
        return cooldownSeconds;
    }

    public static int getTpRequestCooldown() {
        return tpRequestCooldown;
    }

    public static boolean isParticles() {
        return particles;
    }

    public static boolean isSound() {
        return sound;
    }

    public static boolean isUseDynmap() {
        return useDynmap;
    }

    public static boolean isShowWarpMarkersByDefault() {
        return showWarpMarkersByDefault;
    }

}
