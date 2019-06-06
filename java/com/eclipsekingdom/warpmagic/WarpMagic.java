package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.data.PluginConfig;
import com.eclipsekingdom.warpmagic.data.PluginData;
import org.bukkit.plugin.java.JavaPlugin;

public final class WarpMagic extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        pluginConfig.load();
        pluginData.load();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        pluginData.save();
    }

    private final PluginData pluginData = PluginData.getInstance();
    private final PluginConfig pluginConfig = PluginConfig.getInstance();


}
