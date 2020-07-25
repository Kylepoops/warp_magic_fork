package com.eclipsekingdom.warpmagic.warp.extras;

import com.eclipsekingdom.warpmagic.sys.Permissions;
import com.eclipsekingdom.warpmagic.warp.Teleportation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.eclipsekingdom.warpmagic.sys.lang.Message.WARN_NOT_ALLOWED;

public class CommandJump implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Permissions.hasExtras(player)) {
                Teleportation.sendTo(player, player.getTargetBlock(null, 100).getLocation().setDirection(player.getLocation().getDirection()).add(0, 1, 0));
            } else {
                player.sendMessage(WARN_NOT_ALLOWED.toString());
            }
        }
        return false;
    }
}
