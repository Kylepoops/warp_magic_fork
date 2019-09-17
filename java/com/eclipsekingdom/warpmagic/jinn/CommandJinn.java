package com.eclipsekingdom.warpmagic.jinn;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.jinn.theme.JinnTheme;
import com.eclipsekingdom.warpmagic.jinn.util.CustomSpawn;
import com.eclipsekingdom.warpmagic.util.language.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

public class CommandJinn implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(Permissions.canSummonJinn(player)){
                CustomSpawn.spawnJinn(JinnTheme.from(JinnTheme.JinnThemeType.WIND), player.getLocation());
            }else{
                player.sendMessage(Message.ERROR_NOT_ALLOWED.get());
            }

        }
        return false;
    }
}
