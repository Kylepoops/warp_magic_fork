package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.util.CommandInfo;
import com.eclipsekingdom.warpmagic.util.PluginHelp;
import com.google.common.collect.ImmutableList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWarpMagic implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            PluginHelp.show(player, WarpMagic.themeLight + "" + ChatColor.BOLD + "Warp Magic", commandInfos);
            if (Permissions.canSetGlobalPoints(player)) {
                PluginHelp.showSubList(player, setCommandInfos);
            }
            if (Permissions.canSummonLoot(player)) {
                PluginHelp.showSubList(player, stoneCommandInfos);
            }
        }
        return false;
    }

    private static final ImmutableList<CommandInfo> commandInfos = ImmutableList.<CommandInfo>builder()
            .add(new CommandInfo("spawn", "teleport to spawn"))
            .add(new CommandInfo("hub", "teleport to hub"))
            .add(new CommandInfo("north", "teleport to the north warp point"))
            .add(new CommandInfo("south", "teleport to the south warp point"))
            .add(new CommandInfo("east", "teleport to the east warp point"))
            .add(new CommandInfo("west", "teleport to the west warp point"))
            .add(new CommandInfo("tpa [player]", "request to teleport to player"))
            .add(new CommandInfo("tpahere [player]", "request that a player teleport to you"))
            .add(new CommandInfo("home help", "show home commands"))
            .add(new CommandInfo("warp help", "show warp commands"))
            .add(new CommandInfo("vortex help", "show vortex commands"))
            .add(new CommandInfo("we", "select a warp effect"))
            .build();

    private static final ImmutableList<CommandInfo> setCommandInfos = ImmutableList.<CommandInfo>builder()
            .add(new CommandInfo("setspawn", "set spawn location"))
            .add(new CommandInfo("sethub", "set hub location"))
            .add(new CommandInfo("setnorth", "set north location"))
            .add(new CommandInfo("setsouth", "set south location"))
            .add(new CommandInfo("seteast", "set east location"))
            .add(new CommandInfo("setwest", "set west location"))
            .build();

    private static final ImmutableList<CommandInfo> stoneCommandInfos = ImmutableList.<CommandInfo>builder()
            .add(new CommandInfo("warpstone [integer]", "get warp stone(s)"))
            .add(new CommandInfo("vortexstone [integer]", "get vortex stone(s)"))
            .add(new CommandInfo("effectstone [effect-type] [integer]", "get effect stone(s)"))
            .add(new CommandInfo("effectstone list", "list all effect types"))
            .build();

}
