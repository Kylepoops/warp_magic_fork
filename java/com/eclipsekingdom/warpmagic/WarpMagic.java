package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.data.DataUpdater;
import com.eclipsekingdom.warpmagic.data.GlobalCache;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.VortexCache;
import com.eclipsekingdom.warpmagic.data.group.GroupData;
import com.eclipsekingdom.warpmagic.data.group.Groups;
import com.eclipsekingdom.warpmagic.jinn.CommandJinn;
import com.eclipsekingdom.warpmagic.jinn.JinnConfig;
import com.eclipsekingdom.warpmagic.jinn.JinnLife;
import com.eclipsekingdom.warpmagic.jinn.JinnListener;
import com.eclipsekingdom.warpmagic.loot.CommandEffectStone;
import com.eclipsekingdom.warpmagic.loot.CommandVortexStone;
import com.eclipsekingdom.warpmagic.loot.CommandWarpStone;
import com.eclipsekingdom.warpmagic.loot.LootListener;
import com.eclipsekingdom.warpmagic.util.AutoCompleteListener;
import com.eclipsekingdom.warpmagic.util.PluginBase;
import com.eclipsekingdom.warpmagic.util.language.Language;
import com.eclipsekingdom.warpmagic.warp.*;
import com.eclipsekingdom.warpmagic.warp.effect.CommandWarpEffect;
import com.eclipsekingdom.warpmagic.warp.effect.gui.InputListener;
import com.eclipsekingdom.warpmagic.warp.effect.list.Bats;
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
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class WarpMagic extends JavaPlugin {


    public static final ChatColor themeDark = ChatColor.DARK_GREEN;
    public static final ChatColor themeLight = ChatColor.GREEN;

    public static WarpMagic plugin;

    @Override
    public void onEnable() {
        this.plugin = this;

        new Language();
        new PluginConfig();
        new JinnConfig();

        DataUpdater.convert();

        new GlobalCache();
        new UserCache();
        new VortexCache();
        new Groups();

        new PluginBase();

        registerListeners();
        registerCommands();

    }

    @Override
    public void onDisable() {
        UserCache.save();
        VortexCache.save();
        GlobalCache.save();
        Bats.removeEntities();
        JinnLife.remvoeAllJinn();
        PluginBase.disableDependencies();
    }

    private void registerListeners() {
        new LootListener(this);
        new RespawnListener();
        new AutoCompleteListener();
        new InputListener(this);
        new JinnListener();
    }

    private void registerCommands() {

        getCommand("warpmagic").setExecutor(new CommandWarpMagic());

        getCommand("home").setExecutor(new CommandHome());
        getCommand("fhome").setExecutor(new CommandFHome());
        getCommand("warp").setExecutor(new CommandWarp());
        getCommand("warps").setExecutor(new CommandWarps());
        getCommand("vortex").setExecutor(new CommandVortex());
        getCommand("vortexes").setExecutor(new CommandVortexes());

        getCommand("jinn").setExecutor(new CommandJinn());

        getCommand("warpstone").setExecutor(new CommandWarpStone());
        getCommand("vortexstone").setExecutor(new CommandVortexStone());
        getCommand("effectstone").setExecutor(new CommandEffectStone());

        getCommand("we").setExecutor(new CommandWarpEffect());

        getCommand("north").setExecutor(new CommandToPoint(GlobalPoint.NORTH));
        getCommand("south").setExecutor(new CommandToPoint(GlobalPoint.SOUTH));
        getCommand("east").setExecutor(new CommandToPoint(GlobalPoint.EAST));
        getCommand("west").setExecutor(new CommandToPoint(GlobalPoint.WEST));
        getCommand("spawn").setExecutor(new CommandToPoint(GlobalPoint.SPAWN));
        getCommand("hub").setExecutor(new CommandToPoint(GlobalPoint.HUB));

        getCommand("setnorth").setExecutor(new CommandSetPoint(GlobalPoint.NORTH));
        getCommand("setsouth").setExecutor(new CommandSetPoint(GlobalPoint.SOUTH));
        getCommand("seteast").setExecutor(new CommandSetPoint(GlobalPoint.EAST));
        getCommand("setwest").setExecutor(new CommandSetPoint(GlobalPoint.WEST));
        getCommand("setspawn").setExecutor(new CommandSetPoint(GlobalPoint.SPAWN));
        getCommand("sethub").setExecutor(new CommandSetPoint(GlobalPoint.HUB));

        getCommand("delnorth").setExecutor(new CommandDelPoint(GlobalPoint.NORTH));
        getCommand("delsouth").setExecutor(new CommandDelPoint(GlobalPoint.SOUTH));
        getCommand("deleast").setExecutor(new CommandDelPoint(GlobalPoint.EAST));
        getCommand("delwest").setExecutor(new CommandDelPoint(GlobalPoint.WEST));
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


}
