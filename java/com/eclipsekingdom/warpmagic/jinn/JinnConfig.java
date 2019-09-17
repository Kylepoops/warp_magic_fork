package com.eclipsekingdom.warpmagic.jinn;

import com.eclipsekingdom.warpmagic.util.ConsoleSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class JinnConfig {

    private static File configFile = new File("plugins/WarpMagic", "jinn.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    private static String spawnRateSetting = "Spawn Rate";
    private static double spawnRateDefault = 0.06;
    private static double spawnRate;

    private static String warpRateSetting = "Warp Stone Drop Rate";
    private static double warpRateDefault = 0.18;
    private static double warpRate;


    private static String vortexRateSetting = "Vortex Stone Drop Rate";
    private static double vortexRateDefault = 0.0;
    private static double vortexRate;


    public static double getSpawnRate(){
        return spawnRate;
    }

    public static double getWarpRate(){
        return warpRate;
    }

    public static double getVortexRate(){
        return vortexRate;
    }

    public JinnConfig(){
        load();
    }

    private void load(){
        if(config.contains(spawnRateSetting)){
            try{
                spawnRate = config.getDouble(spawnRateSetting);
                warpRate = config.getDouble(warpRateSetting);
                vortexRate = config.getDouble(vortexRateSetting);
            }catch (Exception e){
                loadDefaults();
            }
        }else{
            loadDefaults();
            createDefault();
        }
    }

    private static void saveConfig(){
        try{
            config.save(configFile);
        } catch (Exception e){
            ConsoleSender.sendMessage("Error saving jinn.yml");
        }
    }

    private static void createDefault(){
        config.set(spawnRateSetting, spawnRateDefault);
        config.set(warpRateSetting, warpRateDefault);
        config.set(vortexRateSetting, vortexRateDefault);
        saveConfig();
    }

    private static void loadDefaults(){
        spawnRate = spawnRateDefault;
        warpRate = warpRateDefault;
        vortexRate = vortexRateDefault;
    }

}
