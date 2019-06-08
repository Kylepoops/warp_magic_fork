package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.global.CommandHub;
import com.eclipsekingdom.warpmagic.global.CommandSetHub;
import com.eclipsekingdom.warpmagic.global.CommandSetSpawn;
import com.eclipsekingdom.warpmagic.global.CommandSpawn;
import com.eclipsekingdom.warpmagic.requests.CommandTPA;
import com.eclipsekingdom.warpmagic.requests.CommandTPAHere;
import com.eclipsekingdom.warpmagic.requests.CommandTPAccept;
import com.eclipsekingdom.warpmagic.requests.CommandTPDeny;
import com.eclipsekingdom.warpmagic.util.commands.AutoCompleteListener;
import com.eclipsekingdom.warpmagic.util.commands.SummonLootCommand;
import com.eclipsekingdom.warpmagic.util.data.CacheListener;
import com.eclipsekingdom.warpmagic.util.data.PluginData;
import com.eclipsekingdom.warpmagic.util.loot.Loot;
import com.eclipsekingdom.warpmagic.warps.home.CommandFHome;
import com.eclipsekingdom.warpmagic.warps.home.CommandHome;
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
        new RespawnListener(this);
        new AutoCompleteListener(this);

        this.getCommand("warpmagic").setExecutor(new CommandWarpMagic());
        this.getCommand("spawn").setExecutor(new CommandSpawn());
        this.getCommand("setspawn").setExecutor(new CommandSetSpawn());
        this.getCommand("hub").setExecutor(new CommandHub());
        this.getCommand("sethub").setExecutor(new CommandSetHub());

        this.getCommand("home").setExecutor(CommandHome.getInstance());
        this.getCommand("fhome").setExecutor(CommandFHome.getInstance());
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

        this.getCommand("tpa").setExecutor(new CommandTPA(this));
        this.getCommand("tpahere").setExecutor(new CommandTPAHere(this));
        this.getCommand("tpaccept").setExecutor(new CommandTPAccept());
        this.getCommand("tpdeny").setExecutor(new CommandTPDeny());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        pluginData.save();
    }

    private final PluginData pluginData = PluginData.getInstance();


}
