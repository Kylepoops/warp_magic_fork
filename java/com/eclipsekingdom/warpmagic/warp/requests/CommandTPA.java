package com.eclipsekingdom.warpmagic.warp.requests;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.language.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandTPA implements CommandExecutor {

    private static final int LIVE_TIME = 25;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length > 0){
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null){
                    if(target != player){
                        Request request = new Request(player.getUniqueId(), RequestType.TPA);
                        UUID transactionID = request.getTransactionID();
                        LiveRequests.registerRequest(target, request);

                        target.sendMessage(ChatColor.GREEN + player.getDisplayName() + " has requested to teleport to you.");
                        target.sendMessage(ChatColor.DARK_GREEN + "Use /tpaccept to accept or /tpdeny to deny the request");
                        target.sendMessage(ChatColor.DARK_GREEN + "Request is valid for "+LIVE_TIME+" seconds.");

                        player.sendMessage(ChatColor.GREEN + "Request sent to " + target.getDisplayName());

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(WarpMagic.plugin, new Runnable() {
                            @Override
                            public void run() {
                                if(transactionID.equals(LiveRequests.getCurrentTransactionID(target))){
                                    LiveRequests.resolveRequest(target);
                                }
                            }
                        }, 20*LIVE_TIME);
                    }else{
                        player.sendMessage(Message.ERROR_TPA_SELF.get());
                    }
                }else{
                    player.sendMessage(Message.ERROR_PLAYER_NOT_FOUND.getFromPlayer(args[0]));
                }
            }else{
                player.sendMessage(Message.FORMAT_TPA.get());
            }
        }

        return false;
    }
}