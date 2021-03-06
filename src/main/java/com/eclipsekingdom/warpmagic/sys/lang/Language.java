package com.eclipsekingdom.warpmagic.sys.lang;

import com.eclipsekingdom.warpmagic.sys.ConsoleSender;
import com.eclipsekingdom.warpmagic.sys.config.PluginConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static com.eclipsekingdom.warpmagic.sys.lang.Message.CONSOLE_FILE_ERROR;


public class Language {

    private static File file = new File("plugins/WarpMagic/Locale", PluginConfig.getLanguageFile() + ".yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    public Language() {
        load();
    }

    private static void load() {

        if (file.exists()) {
            try {
                for (Message message : Message.values()) {
                    MessageSetting setting = message.getMessageSetting();
                    if (config.contains(setting.getLabel())) {
                        setting.setMessage(config.getString(setting.getLabel(), setting.getMessage()));
                    } else {
                        config.set(setting.getLabel(), setting.getMessage());
                    }
                }
                saveConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveConfig() {
        try {
            config.save(file);
        } catch (Exception e) {
            ConsoleSender.sendMessage(CONSOLE_FILE_ERROR.fromFile(file.getName()));
        }
    }


}
