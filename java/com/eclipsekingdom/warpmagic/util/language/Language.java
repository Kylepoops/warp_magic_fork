package com.eclipsekingdom.warpmagic.util.language;

import com.eclipsekingdom.warpmagic.util.ConsoleSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Language {

    private static File file = new File("plugins/WarpMagic", "lang.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    public Language(){
        load();
    }

    private static void load(){

        if(file.exists()){
            try{
                for(Message message: Message.values()){
                    MessageSetting setting = message.getMessageSetting();
                    setting.setMessage(config.getString(setting.getMessageSetting()));
                }
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
            config.save(file);
        } catch (Exception e){
            ConsoleSender.sendMessage("Error saving lang.yml");
        }
    }

    private static void createDefault(){
        config.options().header("Use %p for player name, %w for warp point name, %e for effect name");
        for(Message message: Message.values()){
            MessageSetting setting = message.getMessageSetting();
            config.set(setting.getMessageSetting(), setting.getMessageDefault());
        }
        saveConfig();
    }

    private static void loadDefaults(){
        for(Message message: Message.values()){
            MessageSetting setting = message.getMessageSetting();
            setting.setMessage(setting.getMessageDefault());
        }
    }

}
