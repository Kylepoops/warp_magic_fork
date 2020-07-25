package com.eclipsekingdom.warpmagic.warp.requests;

import com.eclipsekingdom.warpmagic.sys.lang.Message;
import com.eclipsekingdom.warpmagic.warp.Teleportation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTPAccept implements CommandExecutor {

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
                        Teleportation.sendToRequest(requester, target.getLocation());
                    }else if(requestType == RequestType.TPAHERE){
                        Teleportation.sendToRequest(target, requester.getLocation());
                    }else{
                        target.sendMessage(ChatColor.RED + Message.WARN_UNKNOWN_TPA.toString());
                    }
                }else{
                    target.sendMessage(ChatColor.RED + Message.WARN_NOT_ONLINE.toString());
                }
            }else{
                target.sendMessage(ChatColor.RED + Message.WARN_NO_PENDING.toString());
            }
        }

        return false;
    }

}
