package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.sys.Permissions;
import com.eclipsekingdom.warpmagic.util.CommandInfo;
import com.eclipsekingdom.warpmagic.sys.PluginHelp;
import com.google.common.collect.ImmutableList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.eclipsekingdom.warpmagic.sys.lang.Message.*;

public class CommandWarpMagic implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            PluginHelp.show(player, ChatColor.GREEN + "" + ChatColor.BOLD + "Warp Magic", commandInfos);
            if (Permissions.canSetGlobalPoints(player)) {
                PluginHelp.showSubList(player, setCommandInfos);
            }
            if (Permissions.canSummonLoot(player)) {
                PluginHelp.showSubList(player, stoneCommandInfos);
            }
            if (Permissions.hasExtras(player)) {
                PluginHelp.showSubList(player, extraCommandInfos);
            }
        }
        return false;
    }

    private static final ImmutableList<CommandInfo> commandInfos = ImmutableList.<CommandInfo>builder()
            .add(new CommandInfo("spawn", HELP_SPAWN.toString()))
            .add(new CommandInfo("hub", HELP_HUB.toString()))
            .add(new CommandInfo("tpa [" + ARG_PLAYER + "]", HELP_TPA.toString()))
            .add(new CommandInfo("tpahere [" + ARG_PLAYER + "]", HELP_TPAHERE.toString()))
            .add(new CommandInfo("home help", HELP_HOME.toString()))
            .add(new CommandInfo("warp help", HELP_WARP.toString()))
            .add(new CommandInfo("vortex help", HELP_VORTEX.toString()))
            .build();

    private static final ImmutableList<CommandInfo> setCommandInfos = ImmutableList.<CommandInfo>builder()
            .add(new CommandInfo("setspawn", HELP_SETSPAWN.toString()))
            .add(new CommandInfo("sethub", HELP_SETHUB.toString()))
            .add(new CommandInfo("delspawn", HELP_DELSPAWN.toString()))
            .add(new CommandInfo("delhub", HELP_DELHUB.toString()))
            .build();


    private static final ImmutableList<CommandInfo> extraCommandInfos = ImmutableList.<CommandInfo>builder()
            .add(new CommandInfo("jump", HELP_JUMP.toString()))
            .add(new CommandInfo("top", HELP_TOP.toString()))
            .add(new CommandInfo("bottom", HELP_BOTTOM.toString()))
            .build();

    private static final ImmutableList<CommandInfo> stoneCommandInfos = ImmutableList.<CommandInfo>builder()
            .add(new CommandInfo("warpstone [" + ARG_PLAYER + "] [" + ARG_AMOUNT + "]", HELP_WARPSTONE.toString()))
            .add(new CommandInfo("vortexstone [" + ARG_PLAYER + "] [" + ARG_AMOUNT + "]", HELP_VORTEXSTONE.toString()))
            .build();

}