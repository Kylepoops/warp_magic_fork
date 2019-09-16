package com.eclipsekingdom.warpmagic.warp.requests;

import com.eclipsekingdom.warpmagic.util.language.Message;
import com.eclipsekingdom.warpmagic.warp.Teleportation;
import org.bukkit.Bukkit;
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
                        Teleportation.sendTo(requester, target.getLocation());
                    }else if(requestType == RequestType.TPAHERE){
                        Teleportation.sendTo(target, requester.getLocation());
                    }else{
                        target.sendMessage(Message.ERROR_UNKNOWN_TPA.get());
                    }
                }else{
                    target.sendMessage(Message.ERROR_NOT_ONLINE.get());
                }
            }else{
                target.sendMessage(Message.ERROR_NO_PENDING.get());
            }
        }

        return false;
    }

}
