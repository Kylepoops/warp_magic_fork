package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.data.VortexCache;
import com.eclipsekingdom.warpmagic.sys.Permissions;
import com.eclipsekingdom.warpmagic.sys.PluginBase;
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

public class CommandVortex implements CommandExecutor {

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
                    case "setserver":
                        processSetServer(player, args);
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
                    case "mylist":
                        processMyList(player, args);
                        break;
                    default:
                        processTeleport(player, args);
                        break;
                }
            } else {
                player.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/vortex [vortex]"));
            }
        }
        return false;
    }

    private void processSetServer(Player player, String[] args) {
        if (Permissions.canSetGlobalPoints(player)) {
            if (args.length > 1) {
                String vortexName = args[1];
                Vortex vortex = VortexCache.getVortex(vortexName);
                if (vortex != null) {
                    if (vortex.getCreatorName() == null) {
                        vortex.updateLocation(player.getLocation());
                        if (PluginBase.isUsingDynmap()) {
                            PluginBase.getDynmap().setVortexIcon(vortex);
                        }
                        player.sendMessage(ChatColor.GREEN + Message.SUCCESS_VORTEX_UPDATE.fromWarp(vortex.getName()));
                    } else {
                        player.sendMessage(ChatColor.RED + Message.WARN_VORTEX_NOT_SERVER.fromWarp(vortex.getName()));
                    }
                } else {
                    NameStatus nameStatus = NameValidation.clean(vortexName);
                    if (nameStatus == NameStatus.VALID) {
                        vortex = new Vortex(vortexName, player.getLocation(), null);
                        VortexCache.registerVortex(vortex);
                        if (PluginBase.isUsingDynmap()) {
                            PluginBase.getDynmap().setVortexIcon(vortex);
                        }
                        player.sendMessage(ChatColor.GREEN + Message.SUCCESS_VORTEX_SET.fromWarp(vortex.getName()));
                    } else {
                        player.sendMessage(ChatColor.RED + nameStatus.message);
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/vortex setserver [vortex]"));
            }
        } else {
            player.sendMessage(ChatColor.RED + Message.WARN_NOT_ALLOWED.toString());
        }
    }

    private void processSet(Player player, String[] args) {
        if (args.length > 1) {
            UserData userData = UserCache.getData(player);
            PermInfo permInfo = UserCache.getPerms(player);
            String vortexName = args[1];
            Vortex vortex = VortexCache.getVortex(vortexName);
            if (vortex != null) {
                if (vortex.getCreatorName().equals(player.getName())) {
                    vortex.updateLocation(player.getLocation());
                    if (PluginBase.isUsingDynmap()) {
                        PluginBase.getDynmap().setVortexIcon(vortex);
                    }
                    player.sendMessage(ChatColor.GREEN + Message.SUCCESS_VORTEX_UPDATE.fromWarp(vortex.getName()));
                } else {
                    player.sendMessage(ChatColor.RED + Message.WARN_VORTEX_NOT_OWNER.fromWarp(vortex.getName()));
                }
            } else if (VortexCache.getVortexesSetBy(player).size() < userData.getUnlockedVortexes() + PluginConfig.getBaseVortexNum() + permInfo.getVortexBonus()) {
                NameStatus nameStatus = NameValidation.clean(vortexName);
                if (nameStatus == NameStatus.VALID) {
                    vortex = new Vortex(vortexName, player.getLocation(), player.getName());
                    VortexCache.registerVortex(vortex);
                    if (PluginBase.isUsingDynmap()) {
                        PluginBase.getDynmap().setVortexIcon(vortex);
                    }
                    player.sendMessage(ChatColor.GREEN + Message.SUCCESS_VORTEX_SET.fromWarp(vortex.getName()));
                } else {
                    player.sendMessage(ChatColor.RED + nameStatus.message);
                }
            } else {
                player.sendMessage(ChatColor.RED + Message.WARN_VORTEX_LIMIT.toString());
            }
        } else {
            player.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/vortex set [vortex]"));
        }
    }

    private void processDel(Player player, String[] args) {
        if (args.length > 1) {
            String vortexName = args[1];
            Vortex vortex = VortexCache.getVortex(vortexName);
            if (vortex != null) {
                if (vortex.getCreatorName() == null) {
                    if (Permissions.canSetGlobalPoints(player)) {
                        VortexCache.removeVortex(vortex);
                        if (PluginBase.isUsingDynmap()) PluginBase.getDynmap().removeVortexIcon(vortex.getName());
                        player.sendMessage(ChatColor.GREEN + Message.SUCCESS_VORTEX_DEL.fromWarp(vortex.getName()));
                    } else {
                        player.sendMessage(ChatColor.RED + Message.WARN_VORTEX_NOT_OWNER.fromWarp(vortex.getName()));
                    }
                } else if (vortex.getCreatorName().equals(player.getName())) {
                    VortexCache.removeVortex(vortex);
                    if (PluginBase.isUsingDynmap()) PluginBase.getDynmap().removeVortexIcon(vortex.getName());
                    player.sendMessage(ChatColor.GREEN + Message.SUCCESS_VORTEX_DEL.fromWarp(vortex.getName()));
                } else {
                    player.sendMessage(ChatColor.RED + Message.WARN_VORTEX_NOT_OWNER.fromWarp(vortex.getName()));
                }
            } else {
                player.sendMessage(ChatColor.RED + Message.WARN_VORTEX_NOT_FOUND.fromWarp(vortexName));
            }
        } else {
            player.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/vortex del [vortex]"));
        }
    }

    private void processList(Player player, String[] args) {
        List<String> items = new ArrayList<>();
        for (Vortex vortex : VortexCache.getVortexes()) {
            String item = vortex.getName();
            String creatorName = vortex.getCreatorName();
            if (creatorName != null) item += (ChatColor.GRAY + " - " + vortex.getCreatorName());
            items.add(item);
        }
        InfoList infoList = new InfoList(LIST_TITLE, items, 7);
        int page = args.length > 1 ? Amount.parse(args[1]) : 1;
        infoList.displayTo(player, page);
    }

    private String LIST_TITLE = ChatColor.GREEN + "Vortexes:";

    private void processMyList(Player player, String[] args) {
        UserData userData = UserCache.getData(player);
        PermInfo permInfo = UserCache.getPerms(player);
        List<String> items = new ArrayList<>();
        for (Vortex vortex : VortexCache.getVortexesSetBy(player)) {
            items.add(vortex.getName());
        }
        InfoList infoList = new InfoList(getMyListTitle(player, userData, permInfo), items, 7);
        int page = args.length > 1 ? Amount.parse(args[1]) : 1;
        infoList.displayTo(player, page);
    }

    private final String getMyListTitle(Player player, UserData userData, PermInfo permInfo) {
        int vortexesUsed = VortexCache.getVortexesSetBy(player).size();
        int vortexesMax = userData.getUnlockedVortexes() + PluginConfig.getBaseVortexNum() + permInfo.getVortexBonus();
        return (ChatColor.GREEN + "Vortexes (" + vortexesUsed + "/" + vortexesMax + "):");
    }

    private void processTeleport(Player player, String[] args) {
        String vortexName = args[0];
        Vortex vortex = VortexCache.getVortex(vortexName);
        if (vortex != null) {
            Teleportation.sendTo(player, vortex.getLocation());
        } else {
            player.sendMessage(ChatColor.RED + Message.WARN_VORTEX_NOT_FOUND.fromWarp(vortexName));
        }
    }

    private void processHelp(Player player) {
        PluginHelp.show(player, getHelpTitle(player), vortexInfoList);
    }

    private String getHelpTitle(Player player) {
        UserData userData = UserCache.getData(player);
        PermInfo permInfo = UserCache.getPerms(player);
        int vortexesUsed = VortexCache.getVortexesSetBy(player).size();
        int vortexesMax = userData.getUnlockedVortexes() + PluginConfig.getBaseVortexNum() + permInfo.getVortexBonus();
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Vortexes" + ChatColor.ITALIC + "" + ChatColor.DARK_GREEN + " - (" + vortexesUsed + "/" + vortexesMax + ")";
    }

    private static ImmutableList<CommandInfo> vortexInfoList = new ImmutableList.Builder<CommandInfo>()
            .add(new CommandInfo("vortex [vortex]", Message.HELP_TOVORTEX.toString()))
            .add(new CommandInfo("vortex set [vortex]", Message.HELP_VORTEX_SET.toString()))
            .add(new CommandInfo("vortex setserver [vortex]", Message.HELP_VORTEX_SETSERVER.toString()))
            .add(new CommandInfo("vortex del [vortex]", Message.HELP_VORTEX_DEL.toString()))
            .add(new CommandInfo("vortex list", Message.HELP_VORTEX_LIST.toString()))
            .add(new CommandInfo("vortex mylist", Message.HELP_VORTEX_MYLIST.toString()))
            .build();
}
