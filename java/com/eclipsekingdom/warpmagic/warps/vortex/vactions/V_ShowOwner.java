package com.eclipsekingdom.warpmagic.warps.vortex.vactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.vortex.Vortex;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexManager;
import org.bukkit.entity.Player;

public class V_ShowOwner extends CommandAction {
    private final VortexManager vortexManager = VortexManager.getInstance();

    @Override
    public void run(Player player, String[] args) {

        if(args.length > 1){
            String vortexName = args[1];
            if(vortexAlreadySet(player, vortexName)){
                Vortex vortex = vortexManager.getVortexSetBy(player, vortexName);
                vortex.setHideName(false);
                vortexManager.trackUnsavedData(vortexName);
                player.sendMessage(SUCCESSFUL_SHOW_MESSAGE);
            }else{
                Notifications.sendWarning(player, NOT_FOUND_MESSAGE(vortexName));
            }
        }else {
            Notifications.sendFormat(player, "vortex showowner [vortex-name]");
        }


    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("vortex showowner [vortex-name]", "display owner name in vortex list");
    }

    @Override
    protected String initID() {
        return "showowner";
    }


    private static final String NOT_FOUND_MESSAGE(String vortexName){
        return (vortexName + " not found");
    }

    private static final String SUCCESSFUL_SHOW_MESSAGE = WarpMagic.themeLight + "Vortex owner visible";

    private boolean vortexAlreadySet(Player player, String name){
        for(Vortex vortex: vortexManager.getVortexesSetBy(player)){
            if(vortex.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
}
