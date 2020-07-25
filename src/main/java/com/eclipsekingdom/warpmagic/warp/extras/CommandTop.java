package com.eclipsekingdom.warpmagic.warp.extras;

import com.eclipsekingdom.warpmagic.sys.Permissions;
import com.eclipsekingdom.warpmagic.warp.Teleportation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.eclipsekingdom.warpmagic.sys.lang.Message.WARN_NOT_ALLOWED;

public class CommandTop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Permissions.hasExtras(player)) {
                Teleportation.sendTo(player, player.getWorld().getHighestBlockAt(player.getLocation()).getLocation().add(0.5, 0, 0.5));
            } else {
                player.sendMessage(WARN_NOT_ALLOWED.toString());
            }
        }
        return false;
    }
}
