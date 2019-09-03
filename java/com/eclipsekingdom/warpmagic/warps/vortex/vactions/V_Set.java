package com.eclipsekingdom.warpmagic.warps.vortex.vactions;

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
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.warps.warp.WarpManager;
import com.eclipsekingdom.warpmagic.warps.warp.WarpNumManager;
import org.bukkit.entity.Player;

public class V_Set extends CommandAction {

    private final VortexNumManager vortexNumManager = VortexNumManager.getInstance();
    private final VortexManager vortexManager = VortexManager.getInstance();

    @Override
    public void run(Player player, String[] args) {

        if(args.length > 1){
            if((vortexManager.getUsedVortexCount(player) < vortexNumManager.getUnlockedVortexNum(player)) || Permissions.canBypassLimit(player)) {
                String vortexName = args[1];
                NameValidation.Status nameStatus = NameValidation.clean(player, vortexName);
                if(nameStatus == NameValidation.Status.VALID){
                    LocationValidation.Status locationStatus = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
                    if(locationStatus == LocationValidation.Status.VALID){
                        if(!vortexAlreadySet(vortexName)){
                            Vortex vortex = new Vortex(vortexName, player.getLocation(), player.getDisplayName());
                            vortexManager.registerVortex(vortex);
                            player.sendMessage(SUCCESSFUL_CLAIM_MESSAGE(vortex.getName()));
                        }else{
                            Notifications.sendWarning(player, ALREADY_SET_MESSAGE(vortexName));
                            Notifications.sendTip(player, "vortex update [vortex-name]", "to update a vortex");
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

    private boolean vortexAlreadySet(String name){
        for(Vortex vortex: vortexManager.getVortexes()){
            if(vortex.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    private static final String ALREADY_SET_MESSAGE(String vortexName){
        return ("Warp " + vortexName + " is already set");
    }



}
