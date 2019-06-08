package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.util.commands.SummonLootCommand;
import com.eclipsekingdom.warpmagic.util.data.CacheListener;
import com.eclipsekingdom.warpmagic.util.data.PluginData;
import com.eclipsekingdom.warpmagic.util.loot.Loot;
import com.eclipsekingdom.warpmagic.warps.vortex.CommandVortex;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexStone;
import com.eclipsekingdom.warpmagic.warps.warp.CommandWarp;
import com.eclipsekingdom.warpmagic.util.loot.LootListener;
import com.eclipsekingdom.warpmagic.warps.warp.WarpStone;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class WarpMagic extends JavaPlugin {

    public static final ChatColor themeDark = ChatColor.DARK_GREEN;
    public static final ChatColor themeLight = ChatColor.GREEN;

    @Override
    public void onEnable() {
        // Plugin startup logic
        pluginData.load();

        new LootListener(this);
        new CacheListener(this);

        this.getCommand("warp").setExecutor(CommandWarp.getInstance());
        this.getCommand("warpstone").setExecutor(new SummonLootCommand() {
            @Override
            protected Loot initLoot() {
                return WarpStone.getInstance();
            }
        });

        this.getCommand("vortex").setExecutor(CommandVortex.getInstance());
        this.getCommand("vortexstone").setExecutor(new SummonLootCommand() {
            @Override
            protected Loot initLoot() {
                return VortexStone.getInstance();
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        pluginData.save();
    }

    private final PluginData pluginData = PluginData.getInstance();


}
