package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.util.Amount;
import com.eclipsekingdom.warpmagic.util.CommandInfo;
import com.eclipsekingdom.warpmagic.util.InfoList;
import com.eclipsekingdom.warpmagic.util.PluginHelp;
import com.eclipsekingdom.warpmagic.util.language.Message;
import com.eclipsekingdom.warpmagic.warp.validation.LocationValidation;
import com.eclipsekingdom.warpmagic.warp.validation.NameValidation;
import com.eclipsekingdom.warpmagic.warp.validation.LocationStatus;
import com.eclipsekingdom.warpmagic.warp.validation.NameStatus;
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

        if(sender instanceof Player){

            Player player = (Player) sender;

            if(args.length > 0){
                String subCommand = args[0];
                switch (subCommand.toLowerCase()){
                    case "help": processHelp(player); break;
                    case "set": processSet(player, args); break;
                    case "update": processUpdate(player, args); break;
                    case "del": processDel(player, args); break;
                    case "list": processList(player, args); break;
                    default: processTeleport(player, args); break;
                }
            }else{
                player.sendMessage(Message.FORMAT_WARP.get());
            }

        }

        return false;
    }

    private void processSet(Player player, String[] args){
        if(args.length > 1){
            UserData userData = UserCache.getData(player);
            if(userData.getWarps().size() < userData.getTotalWarpNumber(player) || Permissions.canBypassLimit(player)){
                String warpName = args[1];
                NameStatus nameStatus = NameValidation.clean(warpName);
                if(nameStatus == NameStatus.VALID){
                    LocationStatus locationStatus = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
                    if(locationStatus == LocationStatus.VALID){
                        if(!userData.isWarp(warpName)){
                            Warp warp = new Warp(warpName, player.getLocation());
                            userData.addWarp(warp);
                            player.sendMessage(Message.SUCCESS_WARP_SET.getFromWarp(warp.getName()));
                        }else{
                            player.sendMessage(Message.ERROR_WARP_ALREADY_SET.getFromWarp(warpName));
                            player.sendMessage(Message.SUGGEST_WARP_UPDATE.get());
                        }
                    }else{
                        player.sendMessage(locationStatus.message);
                    }
                }else{
                    player.sendMessage(nameStatus.message);
                }
            }else{
                player.sendMessage(Message.ERROR_WARP_LIMIT.get());
                player.sendMessage(Message.SUGGEST_WARP_DEL.get());
            }
        }else{
            player.sendMessage(Message.FORMAT_WARP_SET.get());
        }
    }

    private void processUpdate(Player player, String[] args){
        if(args.length > 1){
            UserData userData = UserCache.getData(player);
            String warpName = args[1];
            if(userData.isWarp(warpName)){
                LocationStatus locationStatus = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
                if(locationStatus == LocationStatus.VALID){
                    Warp warp = userData.getWarp(warpName);
                    warp.updateLocation(player.getLocation());
                    player.sendMessage(Message.SUCCESS_WARP_UPDATE.getFromWarp(warp.getName()));
                }else{
                    player.sendMessage(locationStatus.message);
                }
            }else{
                player.sendMessage(Message.ERROR_WARP_NOT_FOUND.getFromWarp(warpName));
            }
        }else {
            player.sendMessage(Message.FORMAT_WARP_UPDATE.get());
        }
    }

    private void processDel(Player player, String[] args){
        if(args.length > 1){
            UserData userData = UserCache.getData(player);
            String warpName = args[1];
            if(userData.isWarp(warpName)){
                Warp warp = userData.getWarp(warpName);
                userData.removeWarp(warp);
                player.sendMessage(Message.SUCCESS_WARP_DEL.getFromWarp(warp.getName()));
            }else{
                player.sendMessage(Message.ERROR_WARP_NOT_FOUND.getFromWarp(warpName));
            }
        }else {
            player.sendMessage(Message.FORMAT_WARP_DEL.get());
        }
    }

    private void processList(Player player, String[] args){
        UserData userData = UserCache.getData(player);
        List<String> items = new ArrayList<>();

        for(Warp warp: userData.getWarps()){
            items.add(WarpMagic.themeDark + warp.getName());
        }

        InfoList infoList = new InfoList(getListTitle(player,userData),items, 7, "warp list");
        int page = args.length > 1 ? Amount.parse(args[1]): 1;
        infoList.displayTo(player, page);
    }

    private String getListTitle(Player player, UserData userData) {
        int warpsUsed = userData.getWarps().size();
        int warpsMax = userData.getTotalWarpNumber(player);
        return (WarpMagic.themeLight + "Your Warps (" + warpsUsed + "/" + warpsMax + "):");
    }

    private void processTeleport(Player player, String[] args){
        UserData userData = UserCache.getData(player);
        String warpName = args[0];
        Warp warp = userData.getWarp(warpName);
        if(warp != null){
            Teleportation.sendTo(player, warp.getLocation());
        }else{
            player.sendMessage(Message.ERROR_WARP_NOT_FOUND.getFromWarp(warpName));
        }
    }

    private void processHelp(Player player){
        PluginHelp.show(player, getHelpTitle(player) , warpInfoList);
    }

    private String getHelpTitle(Player player) {
        UserData userData = UserCache.getData(player);
        int warpsUsed = userData.getWarps().size();
        int warpsMax = userData.getTotalWarpNumber(player);
        return WarpMagic.themeLight + "" + ChatColor.BOLD + "WarpMagic - Warps" + ChatColor.ITALIC + "" + WarpMagic.themeDark + " - your warps: (" + warpsUsed + "/" + warpsMax + ")";
    }

    private static ImmutableList<CommandInfo> warpInfoList = new ImmutableList.Builder<CommandInfo>()
            .add(new CommandInfo("warp [warp]", "teleport to warp"))
            .add(new CommandInfo("warp help", "display warp commands"))
            .add(new CommandInfo("warp set [warp]", "set warp at location"))
            .add(new CommandInfo("warp update [warp]", "update warp at location"))
            .add(new CommandInfo("warp del [warp]", "delete warp"))
            .add(new CommandInfo("warp list", "list all warps"))
            .build();

}
