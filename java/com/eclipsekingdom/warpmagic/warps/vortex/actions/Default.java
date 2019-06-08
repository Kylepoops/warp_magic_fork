package com.eclipsekingdom.warpmagic.warps.vortex.actions;

import com.eclipsekingdom.warpmagic.Teleportation;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.vortex.Vortex;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexManager;
import org.bukkit.entity.Player;

public class Default extends CommandAction {

    @Override
    public void run(Player player, String[] args) {
        if(args.length > 0){
            String vortexName = args[0];
            Vortex vortex = vortexManager.getVortex(vortexName);
            if(vortex != null) {
                Teleportation.sendTo(player, vortex.getLocation());
            }else{
                Notifications.sendNotFound(player, "Vortex", vortexName);
            }
        }else {
            Notifications.sendFormat(player, "vortex [vortex-name]");
        }
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("vortex [vortex-name]", "teleport to [vortex-name]");
    }

    @Override
    protected String initID() {
        return "";
    }

    private final VortexManager vortexManager = VortexManager.getInstance();
}
