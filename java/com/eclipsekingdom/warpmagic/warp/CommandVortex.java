package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.data.VortexCache;
import com.eclipsekingdom.warpmagic.loot.Amount;
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

public class CommandVortex implements CommandExecutor {

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
                    case "mylist": processMyList(player, args); break;
                    default: processTeleport(player, args); break;
                }

            }else{
                player.sendMessage(Message.FORMAT_VORTEX.get());
            }
        }

        return false;
    }

    private void processSet(Player player, String[] args){
        if(args.length > 1){
            UserData userData = UserCache.getData(player);
            if(VortexCache.getVortexesSetBy(player).size() < userData.getTotalVortexNumber(player) || Permissions.canBypassLimit(player)){
                String vortexName = args[1];
                NameStatus nameStatus = NameValidation.clean(vortexName);
                if(nameStatus == NameStatus.VALID){
                    LocationStatus locationStatus = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
                    if(locationStatus == LocationStatus.VALID){
                        if(VortexCache.getVortex(vortexName) == null){
                            Vortex vortex = new Vortex(vortexName, player.getLocation(), player.getName());
                            VortexCache.registerVortex(vortex);
                            player.sendMessage(Message.SUCCESS_WARP_SET.getFromWarp(vortex.getName()));
                        }else{
                            player.sendMessage(Message.ERROR_VORTEX_ALREADY_SET.getFromWarp(vortexName));
                            player.sendMessage(Message.SUGGEST_VORTEX_UPDATE.get());
                        }
                    }else{
                        player.sendMessage(locationStatus.message);
                    }
                }else{
                    player.sendMessage(nameStatus.message);
                }
            }else{
                player.sendMessage(Message.ERROR_VORTEX_LIMIT.get());
            }
        }else{
            player.sendMessage(Message.FORMAT_VORTEX.get());
        }
    }

    private void processUpdate(Player player, String[] args){
        if(args.length > 1){
            String vortexName = args[1];
            Vortex vortex = VortexCache.getVortex(vortexName);
            if(vortex != null){
                if(vortex.getCreatorName().equals(player.getName())){
                    LocationStatus locationStatus = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
                    if(locationStatus == LocationStatus.VALID){
                        vortex.updateLocation(player.getLocation());
                        player.sendMessage(Message.SUCCESS_VORTEX_UPDATE.getFromWarp(vortex.getName()));
                    }else{
                        player.sendMessage(locationStatus.message);
                    }
                }else{
                    player.sendMessage(Message.ERROR_VORTEX_NOT_OWNER.getFromWarp(vortex.getName()));
                }
            }else{
                player.sendMessage(Message.ERROR_VORTEX_NOT_FOUND.getFromWarp(vortexName));
            }
        }else{
            player.sendMessage(Message.FORMAT_VORTEX_UPDATE.get());
        }
    }

    private void processDel(Player player, String[] args){
        if(args.length > 1){
            String vortexName = args[1];
            Vortex vortex = VortexCache.getVortex(vortexName);
            if(vortex != null){
                if(vortex.getCreatorName().equals(player.getName())){
                    VortexCache.removeVortex(vortex);
                    player.sendMessage(Message.SUCCESS_VORTEX_DEL.getFromWarp(vortex.getName()));
                }else{
                    player.sendMessage(Message.ERROR_VORTEX_NOT_OWNER.getFromWarp(vortex.getName()));
                }
            }else{
                player.sendMessage(Message.ERROR_VORTEX_NOT_FOUND.getFromWarp(vortexName));
            }
        }else{
            player.sendMessage(Message.FORMAT_VORTEX_DEL.get());
        }
    }

    private void processList(Player player, String[] args){
        List<String> items = new ArrayList<>();
        for(Vortex vortex: VortexCache.getVortexes()){
            String item = WarpMagic.themeDark + vortex.getName();
            if(!PluginConfig.getHiddenVortexNames().contains(vortex.getCreatorName())){
                item += (ChatColor.GRAY + " - " + vortex.getCreatorName());
            }
            items.add(item);
        }
        InfoList infoList = new InfoList(LIST_TITLE,items, 7, "vortex list");
        int page = args.length > 1 ? Amount.parse(args[1]): 1;
        infoList.displayTo(player, page);
    }

    private static String LIST_TITLE = WarpMagic.themeLight + "Vortexes:";

    private void processMyList(Player player, String[] args){
        List<String> items = new ArrayList<>();
        for(Vortex vortex: VortexCache.getVortexesSetBy(player)){
            items.add(WarpMagic.themeDark + vortex.getName());
        }
        InfoList infoList = new InfoList(getMyListTitle(player, UserCache.getData(player)),items, 7, "vortex mylist");
        int page = args.length > 1 ? Amount.parse(args[1]): 1;
        infoList.displayTo(player, page);
    }

    private final String getMyListTitle(Player player, UserData userData){
        int vortexesUsed = VortexCache.getVortexesSetBy(player).size();
        int vortexesMax = userData.getTotalVortexNumber(player);
        return (WarpMagic.themeLight + "Your Vortexes (" + vortexesUsed + "/" + vortexesMax + "):");
    }

    private void processTeleport(Player player, String[] args){
        String vortexName = args[0];
        Vortex vortex = VortexCache.getVortex(vortexName);
        if(vortex != null) {
            Teleportation.sendTo(player, vortex.getLocation());
        }else{
            player.sendMessage(Message.ERROR_VORTEX_NOT_FOUND.getFromWarp(vortexName));
        }
    }

    private void processHelp(Player player){
        PluginHelp.show(player, HELP_TITLE, vortexInfoList);
    }

    private static String HELP_TITLE = WarpMagic.themeLight + "" + ChatColor.BOLD + "WarpMagic - Vortexes";

    private static ImmutableList<CommandInfo> vortexInfoList = new ImmutableList.Builder<CommandInfo>()
            .add(new CommandInfo("vortex [vortex]", "teleport to vortex"))
            .add(new CommandInfo("vortex help", "display vortex commands"))
            .add(new CommandInfo("vortex set [vortex]", "set vortex at location"))
            .add(new CommandInfo("vortex update [vortex]", "update vortex at location"))
            .add(new CommandInfo("vortex del [vortex]", "delete vortex"))
            .add(new CommandInfo("vortex list", "list all vortexes"))
            .add(new CommandInfo("vortex mylist", "list all vortexes set by you"))
            .build();
}
