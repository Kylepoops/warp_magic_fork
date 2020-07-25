package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.sys.PluginHelp;
import com.eclipsekingdom.warpmagic.sys.config.PluginConfig;
import com.eclipsekingdom.warpmagic.sys.lang.Message;
import com.eclipsekingdom.warpmagic.util.*;
import com.eclipsekingdom.warpmagic.warp.validation.NameStatus;
import com.eclipsekingdom.warpmagic.warp.validation.NameValidation;
import com.google.common.collect.ImmutableList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandWarp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String subCommand = args[0];
                switch (subCommand.toLowerCase()) {
                    case "help":
                        processHelp(player);
                        break;
                    case "set":
                        processSet(player, args);
                        break;
                    case "del":
                        processDel(player, args);
                        break;
                    case "list":
                        processList(player, args);
                        break;
                    default:
                        processTeleport(player, args);
                        break;
                }
            } else {
                player.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/warp [warp]"));
            }
        }
        return false;
    }

    private void processSet(Player player, String[] args) {
        if (args.length > 1) {
            String warpName = args[1];
            UserData userData = UserCache.getData(player);
            PermInfo permInfo = UserCache.getPerms(player);
            if (userData.isWarp(warpName)) {
                Warp warp = userData.getWarp(warpName);
                warp.updateLocation(player.getLocation());
                player.sendMessage(ChatColor.GREEN + Message.SUCCESS_WARP_UPDATE.fromWarp(warp.getName()));
            } else if (userData.getWarps().size() < userData.getUnlockedWarps() + PluginConfig.getBaseWarpNum() + permInfo.getWarpBonus()) {
                NameStatus nameStatus = NameValidation.clean(warpName);
                if (nameStatus == NameStatus.VALID) {
                    Warp warp = new Warp(warpName, player.getLocation());
                    userData.addWarp(warp);
                    player.sendMessage(ChatColor.GREEN + Message.SUCCESS_WARP_SET.fromWarp(warp.getName()));
                } else {
                    player.sendMessage(ChatColor.RED + nameStatus.message);
                }
            } else {
                player.sendMessage(ChatColor.RED + Message.WARN_WARP_LIMIT.toString());
            }
        } else {
            player.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/warp set [warp]"));
        }
    }

    private void processDel(Player player, String[] args) {
        if (args.length > 1) {
            UserData userData = UserCache.getData(player);
            String warpName = args[1];
            if (userData.isWarp(warpName)) {
                Warp warp = userData.getWarp(warpName);
                userData.removeWarp(warp);
                player.sendMessage(ChatColor.GREEN + Message.SUCCESS_WARP_DEL.fromWarp(warp.getName()));
            } else {
                player.sendMessage(ChatColor.RED + Message.WARN_WARP_NOT_FOUND.fromWarp(warpName));
            }
        } else {
            player.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/warp del [warp]"));
        }
    }

    private void processList(Player player, String[] args) {
        UserData userData = UserCache.getData(player);
        PermInfo permInfo = UserCache.getPerms(player);
        List<String> items = new ArrayList<>();
        for (Warp warp : userData.getWarps()) {
            items.add(warp.getName());
        }

        InfoList infoList = new InfoList(getListTitle(userData, permInfo), items, 7);
        int page = args.length > 1 ? Amount.parse(args[1]) : 1;
        infoList.displayTo(player, page);
    }

    private String getListTitle(UserData userData, PermInfo permInfo) {
        int warpsUsed = userData.getWarps().size();
        int warpsMax = userData.getUnlockedWarps() + PluginConfig.getBaseWarpNum() + permInfo.getWarpBonus();
        return (ChatColor.GREEN + "Warps (" + warpsUsed + "/" + warpsMax + "):");
    }

    private void processTeleport(Player player, String[] args) {
        UserData userData = UserCache.getData(player);
        String warpName = args[0];
        Warp warp = userData.getWarp(warpName);
        if (warp != null) {
            Teleportation.sendTo(player, warp.getLocation());
        } else {
            player.sendMessage(ChatColor.RED + Message.WARN_WARP_NOT_FOUND.fromWarp(warpName));
        }
    }

    private void processHelp(Player player) {
        PluginHelp.show(player, getHelpTitle(player), warpInfoList);
    }

    private String getHelpTitle(Player player) {
        UserData userData = UserCache.getData(player);
        PermInfo permInfo = UserCache.getPerms(player);
        int warpsUsed = userData.getWarps().size();
        int warpsMax = userData.getUnlockedWarps() + PluginConfig.getBaseWarpNum() + permInfo.getWarpBonus();
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Warps" + ChatColor.ITALIC + "" + ChatColor.DARK_GREEN + " - (" + warpsUsed + "/" + warpsMax + ")";
    }

    private static ImmutableList<CommandInfo> warpInfoList = new ImmutableList.Builder<CommandInfo>()
            .add(new CommandInfo("warp [warp]", Message.HELP_TOWARP.toString()))
            .add(new CommandInfo("warp set [warp]", Message.HELP_WARP_SET.toString()))
            .add(new CommandInfo("warp del [warp]", Message.HELP_WARP_DEL.toString()))
            .add(new CommandInfo("warp list", Message.HELP_WARP_LIST.toString()))
            .build();

}
