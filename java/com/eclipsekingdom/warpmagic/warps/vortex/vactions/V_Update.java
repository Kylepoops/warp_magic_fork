package com.eclipsekingdom.warpmagic.warps.vortex.vactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.LocationValidation;
import com.eclipsekingdom.warpmagic.warps.vortex.Vortex;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexManager;
import org.bukkit.entity.Player;

public class V_Update extends CommandAction {

    private final VortexManager vortexManager = VortexManager.getInstance();

    @Override
    public void run(Player player, String[] args) {

        if(args.length > 1){
            String vortexName = args[1];
            if(vortexAlreadySet(player, vortexName)){
                LocationValidation.Status locationStatus = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
                if(locationStatus == LocationValidation.Status.VALID){
                    Vortex vortex = vortexManager.getVortexSetBy(player, vortexName);
                    vortex.updateLocation(player.getLocation());
                    vortexManager.trackUnsavedData(vortexName);
                    player.sendMessage(SUCCESSFUL_UPDATE_MESSAGE(vortex.getName()));
                }else{
                    Notifications.sendWarning(player, locationStatus.message);
                }
            }else{
                Notifications.sendWarning(player, NOT_FOUND_MESSAGE(vortexName));
            }
        }else {
            Notifications.sendFormat(player, "vortex update [vortex-name]");
        }


    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("vortex update [vortex-name]", "update vortex at location");
    }

    @Override
    protected String initID() {
        return "update";
    }


    private static final String NOT_FOUND_MESSAGE(String vortexName){
        return (vortexName + " not found");
    }

    private static final String SUCCESSFUL_UPDATE_MESSAGE(String vortexName){
        return (WarpMagic.themeLight + "Vortex "
                + WarpMagic.themeDark + vortexName
                + WarpMagic.themeLight + " updated"
        );
    }

    private boolean vortexAlreadySet(Player player, String name){
        for(Vortex vortex: vortexManager.getVortexesSetBy(player)){
            if(vortex.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }




}
