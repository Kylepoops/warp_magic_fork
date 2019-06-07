package com.eclipsekingdom.warpmagic.warps.warp.actions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.InfoList;
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.warps.warp.WarpManager;
import com.eclipsekingdom.warpmagic.warps.warp.WarpNumManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WList extends CommandAction {

    @Override
    public void run(Player player, String[] args) {

        List<String> items = new ArrayList<>();
        for(Warp warp: warpManager.getWarps(player)){
            items.add(WarpMagic.themeDark + warp.getName());
        }
        InfoList infoList = new InfoList(LIST_TITLE(player),items, 7, "warp list");

        if(args.length == 1){
            infoList.displayTo(player, 1);
        }else{
            int pageNum = 1;
            try{
                pageNum = Integer.parseInt(args[1]);
            }catch (Exception e){}

            infoList.displayTo(player, pageNum);
        }
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("warp list", "list all warps");
    }

    @Override
    protected String initID() {
        return "list";
    }


    private static final String LIST_TITLE(Player player){
        int plotsUsed = warpManager.getUsedWarpCount(player);
        int plotsMax = warpNumManager.getUnlockedWarpNum(player);
        return (WarpMagic.themeLight + "Your Warps (" + plotsUsed + "/" + plotsMax + "):");
    }

    private static final WarpManager warpManager = WarpManager.getInstance();
    private static final WarpNumManager warpNumManager = WarpNumManager.getInstance();

}
