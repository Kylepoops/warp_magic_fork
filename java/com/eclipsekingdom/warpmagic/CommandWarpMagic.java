package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.PluginHelp;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandWarpMagic implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            PluginHelp.show(player, WarpMagic.themeLight +""+ ChatColor.BOLD + "Warp Magic", commandInfos);
            if(Permissions.canSetGlobalPoints(player)){
                PluginHelp.showSubList(player, setCommandInfos);
            }
        }
        return false;
    }

    private static final List<CommandInfo> commandInfos = buildCommandInfo();
    private static final List<CommandInfo> buildCommandInfo(){
        List<CommandInfo> commandInfoList = new ArrayList<>();
        commandInfoList.add(new CommandInfo("spawn", "teleport to spawn"));
        commandInfoList.add(new CommandInfo("hub", "teleport to hub"));
        commandInfoList.add(new CommandInfo("home help", "show home commands"));
        commandInfoList.add(new CommandInfo("warp help", "show warp commands"));
        commandInfoList.add(new CommandInfo("vortex help", "show vortex commands"));
        return commandInfoList;
    }

    private static final List<CommandInfo> setCommandInfos = buildSetCommandInfo();
    private static final List<CommandInfo> buildSetCommandInfo(){
        List<CommandInfo> commandInfoList = new ArrayList<>();
        commandInfoList.add(new CommandInfo("setspawn", "set spawn location"));
        commandInfoList.add(new CommandInfo("sethub", "set hub location"));
        return commandInfoList;
    }
}
