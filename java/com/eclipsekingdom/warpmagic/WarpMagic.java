package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.effect.gui.InputListener;
import com.eclipsekingdom.warpmagic.effect.list.Bats;
import com.eclipsekingdom.warpmagic.global.NewPlayerListener;
import com.eclipsekingdom.warpmagic.jinn.CommandJinn;
import com.eclipsekingdom.warpmagic.jinn.JinnConfig;
import com.eclipsekingdom.warpmagic.jinn.JinnLife;
import com.eclipsekingdom.warpmagic.jinn.JinnListener;
import com.eclipsekingdom.warpmagic.util.commands.AutoCompleteListener;
import com.eclipsekingdom.warpmagic.util.commands.PluginCommands;
import com.eclipsekingdom.warpmagic.util.data.CacheListener;
import com.eclipsekingdom.warpmagic.util.data.PluginData;
import com.eclipsekingdom.warpmagic.util.loot.LootListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class WarpMagic extends JavaPlugin {

    private PluginData pluginData;
    private PluginConfig pluginConfig;
    private JinnConfig jinnConfig;
    private Teleportation teleportation;
    private PluginCommands pluginCommands;

    public static final ChatColor themeDark = ChatColor.DARK_GREEN;
    public static final ChatColor themeLight = ChatColor.GREEN;

    public static WarpMagic plugin;

    @Override
    public void onEnable() {
        this.plugin = this;

        pluginConfig = PluginConfig.getInstance();
        pluginConfig.load();
        pluginData = PluginData.getInstance();
        pluginData.load();
        jinnConfig = new JinnConfig();
        jinnConfig.load();

        teleportation = new Teleportation();
        pluginCommands = new PluginCommands(this);

        new LootListener(this);
        new CacheListener(this);
        new RespawnListener(this);
        new AutoCompleteListener(this);
        new InputListener(this);
        new NewPlayerListener(this);
        new JinnListener();

        this.getCommand("jinn").setExecutor(new CommandJinn());

        pluginCommands.registerAll();

    }

    @Override
    public void onDisable() {
        pluginData.save();
        Bats.removeEntities();
        JinnLife.remvoeAllJinn();
    }

    public Teleportation getTeleportation(){
        return teleportation;
    }

    public PluginCommands getPluginCommands(){
        return pluginCommands;
    }

    public JinnConfig getJinnConfig(){
        return jinnConfig;
    }


}
