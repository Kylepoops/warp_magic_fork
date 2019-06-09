package com.eclipsekingdom.warpmagic.effect;

import com.eclipsekingdom.warpmagic.effect.gui.LiveSessions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWarpEffect implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            liveSessions.launch(player);

        }


        return false;
    }

    private LiveSessions liveSessions = LiveSessions.getInstance();
}
