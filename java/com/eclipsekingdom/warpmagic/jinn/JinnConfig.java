package com.eclipsekingdom.warpmagic.jinn;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class JinnConfig {

    private File configFile = new File("plugins/WarpMagic", "jinn.yml");
    private FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    private String spawnRateSetting = "Spawn Rate";
    private double spawnRateDefault = 0.06;
    private double spawnRate;

    private String warpRateSetting = "Warp Stone Drop Rate";
    private double warpRateDefault = 0.18;
    private double warpRate;


    private String vortexRateSetting = "Vortex Stone Drop Rate";
    private double vortexRateDefault = 0.0;
    private double vortexRate;


    public double getSpawnRate(){
        return spawnRate;
    }

    public double getWarpRate(){
        return warpRate;
    }

    public double getVortexRate(){
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

    private void saveConfig(){
        try{
            config.save(configFile);
        } catch (Exception e){
            Bukkit.getConsoleSender().sendMessage("Error saving dova.yml");
        }
    }

    private void createDefault(){
        config.set(spawnRateSetting, spawnRateDefault);
        config.set(warpRateSetting, warpRateDefault);
        config.set(vortexRateSetting, vortexRateDefault);
        saveConfig();
    }

    private void loadDefaults(){
        spawnRate = spawnRateDefault;
        warpRate = warpRateDefault;
        vortexRate = vortexRateDefault;
    }

}
