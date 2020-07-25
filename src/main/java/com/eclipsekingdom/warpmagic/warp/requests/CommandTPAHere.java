package com.eclipsekingdom.warpmagic.warp.requests;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.sys.config.PluginConfig;
import com.eclipsekingdom.warpmagic.sys.lang.Message;
import com.eclipsekingdom.warpmagic.util.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandTPAHere implements CommandExecutor {

    private static final int LIVE_TIME = 25;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerID = player.getUniqueId();
            if (!CommandTPA.playersOnCooldown.containsKey(playerID)) {
                if (args.length > 0) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (target != player) {
                            Request request = new Request(player.getUniqueId(), RequestType.TPAHERE);
                            UUID transactionID = request.getTransactionID();
                            LiveRequests.registerRequest(target, request);

                            target.sendMessage(ChatColor.GREEN + Message.REQUEST_TPAHERE.fromPlayer(player.getDisplayName()));
                            target.sendMessage(ChatColor.DARK_GREEN + Message.REQUEST_COMMANDS.toString());
                            target.sendMessage(ChatColor.DARK_GREEN + Message.REQUEST_TIMEOUT.fromSeconds(String.valueOf(LIVE_TIME)));

                            player.sendMessage(ChatColor.GREEN + Message.REQUEST_SENT.fromPlayer(target.getDisplayName()));

                            if (PluginConfig.getTpRequestCooldown() > 0) {
                                CommandTPA.playersOnCooldown.put(playerID, new Cooldown(PluginConfig.getTpRequestCooldown(), () -> {
                                    CommandTPA.playersOnCooldown.remove(playerID);
                                }));
                            }

                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(WarpMagic.getPlugin(), () -> {
                                if (transactionID.equals(LiveRequests.getCurrentTransactionID(target))) {
                                    LiveRequests.resolveRequest(target);
                                }
                            }, 20 * LIVE_TIME);
                        } else {
                            player.sendMessage(ChatColor.RED + Message.WARN_TPA_SELF.toString());
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + Message.WARN_PLAYER_NOT_FOUND.fromPlayer(args[0]));
                    }

                } else {
                    player.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/tpahere [" + Message.ARG_PLAYER + "]"));
                }
            } else {
                Cooldown cooldown = CommandTPA.playersOnCooldown.get(playerID);
                player.sendMessage(ChatColor.RED + Message.WARN_REQUEST_COOLDOWN.fromSeconds(String.valueOf(cooldown.getCount())));
            }

        }

        return false;
    }

}