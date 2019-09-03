package com.eclipsekingdom.warpmagic.warps.vortex.vactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.vortex.Vortex;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexManager;
import org.bukkit.entity.Player;

public class V_HideOwner extends CommandAction {
    private final VortexManager vortexManager = VortexManager.getInstance();

    @Override
    public void run(Player player, String[] args) {

        if(args.length > 1){
            String vortexName = args[1];
            if(vortexAlreadySet(player, vortexName)){
                Vortex vortex = vortexManager.getVortexSetBy(player, vortexName);
                vortex.setHideName(true);
                vortexManager.trackUnsavedData(vortexName);
                player.sendMessage(SUCCESSFUL_HIDE_MESSAGE);
            }else{
                Notifications.sendWarning(player, NOT_FOUND_MESSAGE(vortexName));
            }
        }else {
            Notifications.sendFormat(player, "vortex hideowner [vortex-name]");
        }


    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("vortex hideowner [vortex-name]", "hide owner name in vortex list");
    }

    @Override
    protected String initID() {
        return "hideowner";
    }


    private static final String NOT_FOUND_MESSAGE(String vortexName){
        return (vortexName + " not found");
    }

    private static final String SUCCESSFUL_HIDE_MESSAGE = WarpMagic.themeLight + "Vortex owner hidden";

    private boolean vortexAlreadySet(Player player, String name){
        for(Vortex vortex: vortexManager.getVortexesSetBy(player)){
            if(vortex.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }


}
