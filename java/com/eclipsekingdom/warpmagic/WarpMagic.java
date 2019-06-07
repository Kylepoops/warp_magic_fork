package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.data.PluginData;
import com.eclipsekingdom.warpmagic.warps.warp.CommandWarp;
import com.eclipsekingdom.warpmagic.warps.warp.stone.CommandWarpStone;
import com.eclipsekingdom.warpmagic.warps.warp.stone.WarpStoneListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class WarpMagic extends JavaPlugin {

    public static final ChatColor themeDark = ChatColor.DARK_GREEN;
    public static final ChatColor themeLight = ChatColor.GREEN;

    @Override
    public void onEnable() {
        // Plugin startup logic
        pluginData.load();

        new WarpStoneListener(this);

        this.getCommand("warp").setExecutor(CommandWarp.getInstance());
        this.getCommand("warpstone").setExecutor(new CommandWarpStone());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        pluginData.save();
    }

    private final PluginData pluginData = PluginData.getInstance();


}
