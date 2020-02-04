package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.VortexCache;
import com.eclipsekingdom.warpmagic.util.Amount;
import com.eclipsekingdom.warpmagic.util.InfoList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandVortexes implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            processList(player, args);
        }

        return false;

    }


    public static void processList(Player player, String[] args){
        List<String> items = new ArrayList<>();
        for(Vortex vortex: VortexCache.getVortexes()){
            String item = WarpMagic.themeDark + vortex.getName();
            if(!PluginConfig.getHiddenVortexNames().contains(vortex.getCreatorName())){
                item += (ChatColor.GRAY + " - " + vortex.getCreatorName());
            }
            items.add(item);
        }
        InfoList infoList = new InfoList(LIST_TITLE,items, 7, "vortex list");
        int page = args.length > 0 ? Amount.parse(args[0]): 1;
        infoList.displayTo(player, page);
    }

    private static String LIST_TITLE = WarpMagic.themeLight + "Vortexes:";

}
