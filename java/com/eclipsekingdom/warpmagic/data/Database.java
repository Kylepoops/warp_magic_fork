package com.eclipsekingdom.warpmagic.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Database<KEY, DATA> {

    /* --- constructors ---*/

    protected Database(DataType dataType, String header, String fileName, String dirName) {
        this.dataType = dataType;
        this.header = header;
        this.fileName = fileName;
        this.dirName = dirName;
        this.dataFile = new File("plugins/WarpMagic"+dirName, fileName+".yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        this.defaultValue = (DATA) dataType.getDefaultValue();
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
            if(data != null){
                if(!dataType.isTrivial(data)){
                    return data;
                }else{
                    return defaultValue;
                }
            }else{
                return defaultValue;
            }
        }else{
            return defaultValue;
        }
    }

    public List<KEY> getKeyList(){
        List<KEY> keys = new ArrayList<>();
        if(dataConfig.contains(header)){
            for(String path: dataConfig.getConfigurationSection(header).getKeys(false)){
                keys.add(convertPathToKey(path));
            }
        }
        return keys;
    }

    public void addName(KEY key, String name){
        dataConfig.set(getPath(key)+".name", name);
    }


    /* --- implementation ---*/

    protected abstract KEY convertPathToKey(String path);

    protected void cleanSection(String path){
        if(dataConfig.contains(path)){
            dataConfig.set(path, null);
        }
    }


    /* --- secret ---*/

    private final DataType dataType;
    private final String fileName;
    private final String dirName;
    private final File dataFile;
    private final FileConfiguration dataConfig;
    private final DATA defaultValue;

    private void saveDataConfig(){
        try{
            dataConfig.save(dataFile);
        } catch (Exception e){
            Bukkit.getConsoleSender().sendMessage("Help saving "+fileName+".yml");
        }
    }

    private final String header;
    private final String getPath(KEY key){
        return (header + key.toString());
    }
}
