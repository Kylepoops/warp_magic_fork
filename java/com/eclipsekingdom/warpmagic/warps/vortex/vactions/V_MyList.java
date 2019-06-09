package com.eclipsekingdom.warpmagic.warps.vortex.vactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.InfoList;
import com.eclipsekingdom.warpmagic.warps.vortex.Vortex;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexManager;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexNumManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class V_MyList extends CommandAction {

    @Override
    public void run(Player player, String[] args) {

        List<String> items = new ArrayList<>();
        for(Vortex vortex: vortexManager.getVortexesSetBy(player)){
            items.add(WarpMagic.themeDark + vortex.getName());
        }
        InfoList infoList = new InfoList(LIST_TITLE(player),items, 7, "vortex mylist");

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
        return new CommandInfo("vortex mylist", "list vortexes set by you");
    }

    @Override
    protected String initID() {
        return "mylist";
    }


    private static final String LIST_TITLE(Player player){
        int vortexesUsed = vortexManager.getUsedVortexCount(player);
        int vortexesMax = vortexNumManager.getUnlockedVortexNum(player);
        return (WarpMagic.themeLight + "Your Vortexes (" + vortexesUsed + "/" + vortexesMax + "):");
    }

    private static final VortexManager vortexManager = VortexManager.getInstance();
    private static final VortexNumManager vortexNumManager = VortexNumManager.getInstance();
}
