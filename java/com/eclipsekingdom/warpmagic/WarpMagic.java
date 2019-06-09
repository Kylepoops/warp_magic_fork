package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.effect.gui.InputListener;
import com.eclipsekingdom.warpmagic.effect.list.Bats;
import com.eclipsekingdom.warpmagic.util.commands.AutoCompleteListener;
import com.eclipsekingdom.warpmagic.util.commands.PluginCommands;
import com.eclipsekingdom.warpmagic.util.data.CacheListener;
import com.eclipsekingdom.warpmagic.util.data.PluginData;
import com.eclipsekingdom.warpmagic.util.loot.LootListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class WarpMagic extends JavaPlugin {

    public static final ChatColor themeDark = ChatColor.DARK_GREEN;
    public static final ChatColor themeLight = ChatColor.GREEN;

    public Teleportation getTeleportation(){
        return teleportation;
    }
    public PluginCommands getPluginCommands(){
        return pluginCommands;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        pluginData.load();

        new LootListener(this);
        new CacheListener(this);
        new RespawnListener(this);
        new AutoCompleteListener(this);
        new InputListener(this);

        pluginCommands.registerAll();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        pluginData.save();
        Bats.removeEntities();
    }

    private final PluginData pluginData = PluginData.getInstance();
    private final Teleportation teleportation = new Teleportation(this);
    private final PluginCommands pluginCommands = new PluginCommands(this);


}
