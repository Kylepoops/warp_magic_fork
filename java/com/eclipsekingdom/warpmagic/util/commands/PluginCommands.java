package com.eclipsekingdom.warpmagic.util.commands;

import com.eclipsekingdom.warpmagic.CommandWarpMagic;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.effect.CommandEffectStone;
import com.eclipsekingdom.warpmagic.effect.CommandWarpEffect;
import com.eclipsekingdom.warpmagic.global.CommandHub;
import com.eclipsekingdom.warpmagic.global.CommandSetHub;
import com.eclipsekingdom.warpmagic.global.CommandSetSpawn;
import com.eclipsekingdom.warpmagic.global.CommandSpawn;
import com.eclipsekingdom.warpmagic.requests.CommandTPA;
import com.eclipsekingdom.warpmagic.requests.CommandTPAHere;
import com.eclipsekingdom.warpmagic.requests.CommandTPAccept;
import com.eclipsekingdom.warpmagic.requests.CommandTPDeny;
import com.eclipsekingdom.warpmagic.util.commands.extras.CommandBottom;
import com.eclipsekingdom.warpmagic.util.commands.extras.CommandJump;
import com.eclipsekingdom.warpmagic.util.commands.extras.CommandTop;
import com.eclipsekingdom.warpmagic.util.loot.Loot;
import com.eclipsekingdom.warpmagic.warps.home.hactions.*;
import com.eclipsekingdom.warpmagic.warps.home.hactions.H_Del;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexStone;
import com.eclipsekingdom.warpmagic.warps.vortex.vactions.*;
import com.eclipsekingdom.warpmagic.warps.warp.WarpStone;
import com.eclipsekingdom.warpmagic.warps.warp.wactions.*;

import java.util.ArrayList;
import java.util.List;

public class PluginCommands {

    public PluginCommands(WarpMagic plugin){
        this.plugin = plugin;
    }

    public RootCommand getCommandHome(){
        return commandHome;
    }

    public RootCommand getCommandFHome(){
        return commandFHome;
    }

    public RootCommand getCommandWarp(){
        return commandWarp;
    }

    public RootCommand getCommandVortex(){
        return commandVortex;
    }

    public void registerAll(){

        List<CommandAction> FHActions = new ArrayList<>();
        FHActions.add(new FH_Default(plugin));
        FHActions.add(new FH_List());
        commandFHome = new RootCommand(new FH_Default(plugin), FHActions);
        plugin.getCommand("fhome").setExecutor(commandFHome);

        List<CommandAction> HActions = new ArrayList<>();
        HActions.add(new H_Default(plugin));
        HActions.add(new FH_Default(plugin));
        HActions.add(new H_Help(plugin));
        HActions.add(new H_Set());
        HActions.add(new H_Del());
        HActions.add(new H_Invite());
        HActions.add(new H_Uninvite());
        HActions.add(new H_FList());
        HActions.add(new H_FClear());
        commandHome = new RootCommand(new H_Default(plugin), HActions);
        plugin.getCommand("home").setExecutor(commandHome);

        List<CommandAction> VActions = new ArrayList<>();
        VActions.add(new V_Default(plugin));
        VActions.add(new V_Help(plugin));
        VActions.add(new V_Set());
        VActions.add(new V_Del());
        VActions.add(new V_VList());
        VActions.add(new V_MyList());
        commandVortex = new RootCommand(new V_Default(plugin), VActions);
        plugin.getCommand("vortex").setExecutor(commandVortex);


        List<CommandAction> WActions = new ArrayList<>();
        WActions.add(new W_Default(plugin));
        WActions.add(new W_Help(plugin));
        WActions.add(new W_Set());
        WActions.add(new W_Del());
        WActions.add(new W_List());

        commandWarp = new RootCommand(new W_Default(plugin), WActions);
        plugin.getCommand("warp").setExecutor(commandWarp);


        plugin.getCommand("warpstone").setExecutor(new SummonLootCommand() {
            @Override
            protected Loot initLoot() {
                return WarpStone.getInstance();
            }
        });
        plugin.getCommand("vortexstone").setExecutor(new SummonLootCommand() {
            @Override
            protected Loot initLoot() {
                return VortexStone.getInstance();
            }
        });



        plugin.getCommand("warpmagic").setExecutor(new CommandWarpMagic());
        plugin.getCommand("spawn").setExecutor(new CommandSpawn(plugin));
        plugin.getCommand("setspawn").setExecutor(new CommandSetSpawn());
        plugin.getCommand("hub").setExecutor(new CommandHub(plugin));
        plugin.getCommand("sethub").setExecutor(new CommandSetHub());
        plugin.getCommand("tpa").setExecutor(new CommandTPA(plugin));
        plugin.getCommand("tpahere").setExecutor(new CommandTPAHere(plugin));
        plugin.getCommand("tpaccept").setExecutor(new CommandTPAccept(plugin));
        plugin.getCommand("tpdeny").setExecutor(new CommandTPDeny());
        plugin.getCommand("we").setExecutor(new CommandWarpEffect());
        plugin.getCommand("effectstone").setExecutor(new CommandEffectStone());

        plugin.getCommand("jump").setExecutor(new CommandJump(plugin));
        plugin.getCommand("top").setExecutor(new CommandTop(plugin));
        plugin.getCommand("bottom").setExecutor(new CommandBottom(plugin));


    }

    private final WarpMagic plugin;


    private RootCommand commandHome;
    private RootCommand commandFHome;
    private RootCommand commandWarp;
    private RootCommand commandVortex;
}
