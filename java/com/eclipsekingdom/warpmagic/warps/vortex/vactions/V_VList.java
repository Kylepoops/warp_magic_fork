package com.eclipsekingdom.warpmagic.warps.vortex.vactions;

import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.InfoList;
import com.eclipsekingdom.warpmagic.warps.vortex.Vortex;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class V_VList extends CommandAction {

    @Override
    public void run(Player player, String[] args) {

        List<String> items = new ArrayList<>();
        for(Vortex vortex: vortexManager.getVortexes()){
            String item = WarpMagic.themeDark + vortex.getName();
            if(!pluginConfig.getHiddenVortexNames().contains(player.getDisplayName())){
                item += (ChatColor.GRAY + " - " + vortex.getCreatorName());
            }
            items.add(item); //add whitelist check
        }
        InfoList infoList = new InfoList(LIST_TITLE,items, 7, "vortex list");

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
        return new CommandInfo("vortex list", "list all vortexes");
    }

    @Override
    protected String initID() {
        return "list";
    }


    private static final String LIST_TITLE = WarpMagic.themeLight + "Vortexes:";

    private static final VortexManager vortexManager = VortexManager.getInstance();
    private static final PluginConfig pluginConfig = PluginConfig.getInstance();

}
