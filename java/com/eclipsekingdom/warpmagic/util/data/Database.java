package com.eclipsekingdom.warpmagic.util.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Database<KEY, DATA> {

    /* --- constructors ---*/

    public Database(DataType dataType, String fileName, String dirName) {
        this.dataType = dataType;
        this.header = fileName.toUpperCase().charAt(0) + fileName.toLowerCase().substring(1);
        this.fileName = fileName;
        this.dataFile = new File("plugins/WarpMagic"+dirName, fileName+".yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }


    /* --- interface ---*/

    public void save(){
        saveDataConfig();
    }

    public void store(KEY key, DATA data){
        String path = getPath(key);
        cleanSection(path);
        if(data != null){
            if(!dataType.isTrivial(data)){
                dataType.writeTo(path, data, dataConfig);
            }
        }
    }

    public DATA fetch(KEY key){
        String path = getPath(key);
        if(dataConfig.contains(path)){
            DATA data = (DATA) dataType.readFrom(path, dataConfig);
            return data;
        }else{
            return null;
        }
    }

    public List<String> getAllKeyPaths(){
        List<String> paths = new ArrayList<>();
        if(dataConfig.contains(header)){
            for(String path: dataConfig.getConfigurationSection(header).getKeys(false)){
                paths.add(path);
            }
        }
        return paths;
    }


    /* --- implementation ---*/

    private final DataType dataType;
    private final String fileName;
    private final File dataFile;
    private final FileConfiguration dataConfig;

    private void saveDataConfig(){
        try{
            dataConfig.save(dataFile);
        } catch (Exception e){
            Bukkit.getConsoleSender().sendMessage("Help saving "+fileName+".yml");
        }
    }

    private final String header;
    private final String getPath(KEY key){
        return (key.toString());
    }


    private void cleanSection(String path){
        if(dataConfig.contains(path)){
            dataConfig.set(path, null);
        }
    }

}
