package com.eclipsekingdom.warpmagic;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginConfig {

    /* --- constructors --- */

    private PluginConfig() {}

    public static PluginConfig getInstance(){
        return PLUGIN_CONFIG_INSTANCE;
    }


    /* --- interface --- */

    public void load(){
        if(config.contains(PLUGIN_CONFIG_HEADER)){
            try{
                String key = PLUGIN_CONFIG_HEADER;
                startingWarpNum = config.getInt(key+"."+STARTING_WARP_NUM_SETTING);
                maxWarpNum = config.getInt(key+"."+MAX_WARP_NUM_SETTING);
                validWorlds = config.getStringList(key+"."+VALID_WORLDS_SETTING);
                loadWorlds(validWorlds);
            }catch (Exception e){
                loadDefaults();
            }
        }else{
            loadDefaults();
            createDefault();
        }
        loaded = true;
    }


    public int getStartingWarpNum(){
        if(!loaded){
            load();
        }
        return startingWarpNum;
    }

    public int getMaxWarpNum(){
        if(!loaded){
            load();
        }
        return maxWarpNum;
    }

    public List<String> getValidWorlds(){
        if(!loaded){
            load();
        }
        return validWorlds;
    }


    /* --- implementation --- */

    private boolean loaded = false;

    private static final PluginConfig PLUGIN_CONFIG_INSTANCE = new PluginConfig();

    private static final String PLUGIN_CONFIG_HEADER = "Magic Options";

    private static final File configFile = new File("plugins/WarpMagic", "config.yml");
    private static final FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    private static final String STARTING_WARP_NUM_SETTING = "Starting Warp Amount";
    private static final int STARTING_WARP_NUM_DEFAULT = 3;
    private static int startingWarpNum;

    private static final String MAX_WARP_NUM_SETTING = "Maximum Plot Amount";
    private static final int MAX_WARP_NUM_DEFAULT = 18;
    private static int maxWarpNum;

    private static final String VALID_WORLDS_SETTING = "Valid Worlds";
    private static final List<String> VALID_WORLDS_DEFAULT = buildWorldDefaults();
    private static List<String> validWorlds;



    private static void saveConfig(){
        try{
            config.save(configFile);
        } catch (Exception e){
            Bukkit.getConsoleSender().sendMessage("[PlayerPlot] Error saving config.yml");
        }
    }

    private static void createDefault(){
        String key = PLUGIN_CONFIG_HEADER;
        config.set(key+"."+STARTING_WARP_NUM_SETTING, STARTING_WARP_NUM_DEFAULT);
        config.set(key+"."+MAX_WARP_NUM_SETTING, MAX_WARP_NUM_DEFAULT);
        config.set(key+"."+VALID_WORLDS_SETTING, VALID_WORLDS_DEFAULT);
        saveConfig();
    }

    private static void loadDefaults(){
        startingWarpNum = STARTING_WARP_NUM_DEFAULT;
        maxWarpNum = MAX_WARP_NUM_DEFAULT;
        validWorlds = VALID_WORLDS_DEFAULT;
    }

    private static List<String> buildWorldDefaults(){
        List<String> validWorlds = new ArrayList<>();
        validWorlds.add("world");
        validWorlds.add("world_nether");
        validWorlds.add("world_the_end");
        return validWorlds;
    }


    public void loadWorlds(List<String> worldStrings){
        for(String worldString: worldStrings){
            if(Bukkit.getWorld(worldString)  == null){
                World world = Bukkit.getServer().createWorld(new WorldCreator(worldString));
                Bukkit.getServer().getWorlds().add(world);
            }
        }
    }


}
