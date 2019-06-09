package com.eclipsekingdom.warpmagic.requests;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTPAccept implements CommandExecutor {

    public CommandTPAccept(WarpMagic plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player target = (Player) sender;
            Request request = LiveRequests.getRequest(target);
            if(request != null){
                RequestType requestType = request.getType();
                LiveRequests.resolveRequest(target);
                Player requester = Bukkit.getPlayer(request.getRequesterID());
                if(requester != null && requester.isOnline()){
                    if(requestType == RequestType.TPA){
                        plugin.getTeleportation().sendTo(requester, target.getLocation());
                    }else if(requestType == RequestType.TPAHERE){
                        plugin.getTeleportation().sendTo(target, requester.getLocation());
                    }else{
                        Notifications.sendWarning(target, UNRECOGNIZED_ERROR);
                    }
                }else{
                   Notifications.sendWarning(target, NOT_ONLINE_ERROR);
                }
            }else{
                Notifications.sendWarning(target, NO_REQUEST_ERROR);
            }
        }

        return false;
    }


    private static final String NO_REQUEST_ERROR = "You do not have any pending teleport requests";
    private static final String NOT_ONLINE_ERROR = "requester is no longer online";
    private static final String UNRECOGNIZED_ERROR = "request type not recognized";

    private final WarpMagic plugin;
}
