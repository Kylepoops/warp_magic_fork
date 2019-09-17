package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.util.ConsoleSender;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PluginConfig {

    private static String header = "Magic Options";

    private static File file = new File("plugins/WarpMagic", "config.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    private static String startingWarpNumString = "Starting Warp Amount";
    private static int startingWarpNumDefault = 3;
    private static int startingWarpNum;

    private static String maxWarpNumString = "Maximum Warp Amount";
    private static int maxWarpNumDefault = 18;
    private static int maxWarpNum;

    private static String startingVortexNumString = "Starting Vortex Amount";
    private static int startingVortexNumDefault = 0;
    private static int startingVortexNum;

    private static String maxVortexNumString = "Maximum Vortex Amount";
    private static int maxVortexNumDefault = 3;
    private static int maxVortexNum;

    private static String validWorldsString = "Valid Worlds";
    private static ImmutableList<String> validWorldsDefault = new ImmutableList.Builder<String>()
            .add("world")
            .add("world_nether")
            .add("world_the_end")
            .build();
    private static List<String> validWorlds;

    private static String hiddenVortexNamesString = "Hidden Vortex Owner Names";
    private static List<String> hiddenVortexNamesDefault = Collections.singletonList("example");
    private static List<String> hiddenVortexNames;

    public PluginConfig() {
        load();
    }

    private void load() {
        if (file.exists()) {
            try {
                startingWarpNum = config.getInt(header + "."+startingWarpNumString);
                maxWarpNum = config.getInt(header + "."+maxWarpNumString);
                startingVortexNum = config.getInt(header + "."+startingVortexNumString);
                maxVortexNum = config.getInt(header + "."+maxVortexNumString);
                validWorlds = config.getStringList(header + "."+validWorldsString);
                hiddenVortexNames = config.getStringList(header + "."+hiddenVortexNamesString);
            } catch (Exception e) {
                loadDefaults();
            }
        } else {
            loadDefaults();
            createDefault();
        }
        loadWorlds();
    }

    public static int getStartingWarpNum() {
        return startingWarpNum;
    }

    public static int getMaxWarpNum() {
        return maxWarpNum;
    }

    public static int getStartingVortexNum() {
        return startingVortexNum;
    }

    public static int getMaxVortexNum() {
        return maxVortexNum;
    }

    public static List<String> getValidWorlds() {
        return validWorlds;
    }

    public static List<String> getHiddenVortexNames() {
        return hiddenVortexNames;
    }


    private static void saveConfig() {
        try {
            config.save(file);
        } catch (Exception e) {
            ConsoleSender.sendMessage("Error saving config.yml");
        }
    }

    private static void createDefault() {
        config.set(header + "."+startingWarpNumString, startingWarpNumDefault);
        config.set(header + "."+maxWarpNumString, maxWarpNumDefault);
        config.set(header + "."+startingVortexNumString, startingVortexNumDefault);
        config.set(header + "."+maxVortexNumString, maxVortexNumDefault);
        config.set(header + "."+hiddenVortexNamesString, hiddenVortexNamesDefault);
        config.set(header + "."+validWorldsString, validWorldsDefault);
        saveConfig();
    }

    private static void loadDefaults() {
        startingWarpNum = startingWarpNumDefault;
        maxWarpNum = maxWarpNumDefault;
        startingVortexNum = startingVortexNumDefault;
        maxVortexNum = maxVortexNumDefault;
        validWorlds = validWorldsDefault;
        hiddenVortexNames = hiddenVortexNamesDefault;
    }

    private static void loadWorlds(){
        for(String worldString: validWorlds){
            if(Bukkit.getWorld(worldString)  == null){
                World world = Bukkit.getServer().createWorld(new WorldCreator(worldString));
                Bukkit.getServer().getWorlds().add(world);
            }
        }
    }

}
