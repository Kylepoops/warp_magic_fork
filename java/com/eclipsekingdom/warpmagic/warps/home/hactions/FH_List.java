package com.eclipsekingdom.warpmagic.warps.home.hactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.InfoList;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import com.eclipsekingdom.warpmagic.warps.home.RelationsManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FH_List extends CommandAction {
    @Override
    public void run(Player player, String[] args) {
        List<String> items = new ArrayList<>();
        for(String friendName: relationsManager.getReqirements(player.getDisplayName())){
            Home friendHome = homeManager.getHome(friendName);
            if(friendHome != null && friendHome.trusts(player.getDisplayName())){
                items.add(WarpMagic.themeDark + friendName);
            }
        }
        InfoList infoList = new InfoList(LIST_TITLE,items, 7, "fhome list");

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
        return new CommandInfo("fhome list", "list homes you are invited to");
    }

    @Override
    protected String initID() {
        return "list";
    }

    private static final String LIST_TITLE = WarpMagic.themeLight + "Friend Homes:";

    private final RelationsManager relationsManager = RelationsManager.getInstance();
    private final HomeManager homeManager = HomeManager.getInstance();

}
