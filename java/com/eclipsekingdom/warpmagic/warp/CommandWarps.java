package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.loot.Amount;
import com.eclipsekingdom.warpmagic.util.InfoList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandWarps implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            processList(player, args);
        }

        return false;

    }

    private void processList(Player player, String[] args){
        UserData userData = UserCache.getData(player);
        List<String> items = new ArrayList<>();

        for(Warp warp: userData.getWarps()){
            items.add(WarpMagic.themeDark + warp.getName());
        }

        InfoList infoList = new InfoList(getListTitle(player,userData),items, 7, "warp list");
        int page = args.length > 0 ? Amount.parse(args[0]): 1;
        infoList.displayTo(player, page);
    }

    private String getListTitle(Player player, UserData userData) {
        int warpsUsed = userData.getWarps().size();
        int warpsMax = userData.getTotalWarpNumber(player);
        return (WarpMagic.themeLight + "Your Warps (" + warpsUsed + "/" + warpsMax + "):");
    }

}
