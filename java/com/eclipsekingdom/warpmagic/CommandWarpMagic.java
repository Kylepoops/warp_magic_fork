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
            if(Permissions.canSummonLoot(player)){
                PluginHelp.showSubList(player, stoneCommandInfos);
            }
        }
        return false;
    }

    private static final List<CommandInfo> commandInfos = buildCommandInfo();
    private static final List<CommandInfo> buildCommandInfo(){
        List<CommandInfo> commandInfoList = new ArrayList<>();
        commandInfoList.add(new CommandInfo("spawn", "teleport to spawn"));
        commandInfoList.add(new CommandInfo("hub", "teleport to hub"));
        commandInfoList.add(new CommandInfo("north", "teleport to the north warp point"));
        commandInfoList.add(new CommandInfo("south", "teleport to the south warp point"));
        commandInfoList.add(new CommandInfo("east", "teleport to the east warp point"));
        commandInfoList.add(new CommandInfo("west", "teleport to the west warp point"));
        commandInfoList.add(new CommandInfo("tpa [player]", "request to teleport to player"));
        commandInfoList.add(new CommandInfo("tpahere [player]", "request that a player teleport to you"));
        commandInfoList.add(new CommandInfo("home help", "show home commands"));
        commandInfoList.add(new CommandInfo("warp help", "show warp commands"));
        commandInfoList.add(new CommandInfo("vortex help", "show vortex commands"));
        commandInfoList.add(new CommandInfo("we", "select a warp effect"));
        return commandInfoList;
    }

    private static final List<CommandInfo> setCommandInfos = buildSetCommandInfo();
    private static final List<CommandInfo> buildSetCommandInfo(){
        List<CommandInfo> commandInfoList = new ArrayList<>();
        commandInfoList.add(new CommandInfo("setspawn", "set spawn location"));
        commandInfoList.add(new CommandInfo("sethub", "set hub location"));
        commandInfoList.add(new CommandInfo("setnorth", "set north location"));
        commandInfoList.add(new CommandInfo("setsouth", "set south location"));
        commandInfoList.add(new CommandInfo("seteast", "set east location"));
        commandInfoList.add(new CommandInfo("setwest", "set west location"));
        return commandInfoList;
    }

    private static final List<CommandInfo> stoneCommandInfos = buildStoneCommandInfos();
    private static final List<CommandInfo> buildStoneCommandInfos(){
        List<CommandInfo> commandInfoList = new ArrayList<>();
        commandInfoList.add(new CommandInfo("warpstone [integer]", "get warp stone(s)"));
        commandInfoList.add(new CommandInfo("vortexstone [integer]", "get vortex stone(s)"));
        commandInfoList.add(new CommandInfo("effectstone [effect-type] [integer]", "get effect stone(s)"));
        commandInfoList.add(new CommandInfo("effectstone list", "list all effect types"));
        return commandInfoList;
    }
}
