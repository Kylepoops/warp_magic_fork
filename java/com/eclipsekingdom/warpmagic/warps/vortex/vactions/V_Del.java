package com.eclipsekingdom.warpmagic.warps.vortex.vactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.vortex.Vortex;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexManager;
import org.bukkit.entity.Player;

public class V_Del extends CommandAction {

    @Override
    public void run(Player player, String[] args) {
        if(args.length > 1){
            String vortexName = args[1];
            Vortex vortex = vortexManager.getVortexSetBy(player, vortexName);
            if(vortex != null) {
                vortexManager.removeVortex(vortex);
                player.sendMessage(SUCCESSFUL_DELETE_MESSAGE(vortex.getName()));
            }else{
                Notifications.sendNotFound(player, "Vortex", vortexName);
            }
        }else {
            Notifications.sendFormat(player, "vortex del [vortex-name]");
        }
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("vortex del [vortex-name]", "remove vortex");
    }

    @Override
    protected String initID() {
        return "del";
    }

    private static final String SUCCESSFUL_DELETE_MESSAGE(String vortexName){
        return (WarpMagic.themeLight + "Vortex "
                + WarpMagic.themeDark + vortexName
                + WarpMagic.themeLight + " was deleted"
        );
    }


    private final VortexManager vortexManager = VortexManager.getInstance();
}
