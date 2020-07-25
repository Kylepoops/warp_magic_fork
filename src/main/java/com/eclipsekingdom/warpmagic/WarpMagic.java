package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.data.GlobalCache;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.VortexCache;
import com.eclipsekingdom.warpmagic.loot.CommandStone;
import com.eclipsekingdom.warpmagic.loot.LootListener;
import com.eclipsekingdom.warpmagic.loot.LootType;
import com.eclipsekingdom.warpmagic.sys.PluginBase;
import com.eclipsekingdom.warpmagic.sys.Version;
import com.eclipsekingdom.warpmagic.sys.config.ConfigLoader;
import com.eclipsekingdom.warpmagic.sys.config.PluginConfig;
import com.eclipsekingdom.warpmagic.sys.lang.Language;
import com.eclipsekingdom.warpmagic.util.AutoCompleteListener;
import com.eclipsekingdom.warpmagic.warp.*;
import com.eclipsekingdom.warpmagic.warp.extras.CommandBottom;
import com.eclipsekingdom.warpmagic.warp.extras.CommandJump;
import com.eclipsekingdom.warpmagic.warp.extras.CommandTop;
import com.eclipsekingdom.warpmagic.warp.global.CommandDelPoint;
import com.eclipsekingdom.warpmagic.warp.global.CommandSetPoint;
import com.eclipsekingdom.warpmagic.warp.global.CommandToPoint;
import com.eclipsekingdom.warpmagic.warp.global.GlobalPoint;
import com.eclipsekingdom.warpmagic.warp.requests.CommandTPA;
import com.eclipsekingdom.warpmagic.warp.requests.CommandTPAHere;
import com.eclipsekingdom.warpmagic.warp.requests.CommandTPAccept;
import com.eclipsekingdom.warpmagic.warp.requests.CommandTPDeny;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class WarpMagic extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        ConfigLoader.load();
        new PluginConfig();
        new Language();
        new GlobalCache();
        new UserCache();
        new VortexCache();
        new PluginBase();

        new Teleportation();
        new LootListener();
        new RespawnListener();
        if (Version.current.value >= 109) new AutoCompleteListener();

        getCommand("warpmagic").setExecutor(new CommandWarpMagic());
        getCommand("home").setExecutor(new CommandHome());
        getCommand("fhome").setExecutor(new CommandFHome());
        getCommand("warp").setExecutor(new CommandWarp());
        getCommand("vortex").setExecutor(new CommandVortex());
        getCommand("warpstone").setExecutor(new CommandStone(LootType.WARP_STONE.getLoot()));
        getCommand("vortexstone").setExecutor(new CommandStone(LootType.VORTEX_STONE.getLoot()));
        getCommand("spawn").setExecutor(new CommandToPoint(GlobalPoint.SPAWN));
        getCommand("hub").setExecutor(new CommandToPoint(GlobalPoint.HUB));
        getCommand("setspawn").setExecutor(new CommandSetPoint(GlobalPoint.SPAWN));
        getCommand("sethub").setExecutor(new CommandSetPoint(GlobalPoint.HUB));
        getCommand("delspawn").setExecutor(new CommandDelPoint(GlobalPoint.SPAWN));
        getCommand("delhub").setExecutor(new CommandDelPoint(GlobalPoint.HUB));
        getCommand("tpa").setExecutor(new CommandTPA());
        getCommand("tpahere").setExecutor(new CommandTPAHere());
        getCommand("tpaccept").setExecutor(new CommandTPAccept());
        getCommand("tpdeny").setExecutor(new CommandTPDeny());
        getCommand("jump").setExecutor(new CommandJump());
        getCommand("top").setExecutor(new CommandTop());
        getCommand("bottom").setExecutor(new CommandBottom());

    }

    @Override
    public void onDisable() {
        UserCache.save();
        VortexCache.save();
        GlobalCache.save();
        PluginBase.disableDependencies();
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
