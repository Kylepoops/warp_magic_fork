package com.eclipsekingdom.warpmagic.warps.vortex.actions;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.LocationValidation;
import com.eclipsekingdom.warpmagic.warps.NameValidation;
import com.eclipsekingdom.warpmagic.warps.vortex.Vortex;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexManager;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexNumManager;
import org.bukkit.entity.Player;

public class Set extends CommandAction {

    @Override
    public void run(Player player, String[] args) {

        if(args.length > 1){
            if((vortexManager.getUsedVortexCount(player) < vortexNumManager.getUnlockedVortexNum(player)) || Permissions.canBypassLimit(player)) {
                String vortexName = args[1];
                NameValidation.Status nameStatus = NameValidation.clean(player, vortexName);
                if(nameStatus == NameValidation.Status.VALID){
                    LocationValidation.Status locationStatus = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
                    if(locationStatus == LocationValidation.Status.VALID){
                        if(!vortexAlreadySet(player, vortexName)){
                            Vortex vortex = new Vortex(vortexName, player.getLocation(), player.getDisplayName());
                            vortexManager.registerVortex(vortex);
                            player.sendMessage(SUCCESSFUL_CLAIM_MESSAGE(vortex.getName()));
                        }else{
                            Vortex vortex = vortexManager.getVortexSetBy(player, vortexName);
                            vortex.updateLocation(player.getLocation());
                            vortexManager.trackUnsavedData(vortexName);
                            player.sendMessage(SUCCESSFUL_UPDATE_MESSAGE(vortex.getName()));
                        }
                    }else{
                        Notifications.sendWarning(player, locationStatus.message);
                    }
                }else{
                    Notifications.sendWarning(player, nameStatus.message);
                }
            }else{
                Notifications.sendWarning(player, VORTEX_LIMIT_ERROR);
            }
        }else {
            Notifications.sendFormat(player, "vortex set [vortex-name]");
        }


    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("vortex set [vortex-name]", "set vortex at location");
    }

    @Override
    protected String initID() {
        return "set";
    }

    private static final String VORTEX_LIMIT_ERROR = "Vortex limit reached";

    private static final String SUCCESSFUL_CLAIM_MESSAGE(String vortexName){
        return (WarpMagic.themeLight + "Vortex "
                + WarpMagic.themeDark + vortexName
                + WarpMagic.themeLight + " set"
        );
    }

    private static final String SUCCESSFUL_UPDATE_MESSAGE(String vortexName){
        return (WarpMagic.themeLight + "Vortex "
                + WarpMagic.themeDark + vortexName
                + WarpMagic.themeLight + " updated"
        );
    }


    private final VortexNumManager vortexNumManager = VortexNumManager.getInstance();

    private final VortexManager vortexManager = VortexManager.getInstance();

    private boolean vortexAlreadySet(Player player, String name){
        for(Vortex vortex: vortexManager.getVortexesSetBy(player)){
            if(vortex.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

}
