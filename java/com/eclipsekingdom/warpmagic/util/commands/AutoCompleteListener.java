package com.eclipsekingdom.warpmagic.util.commands;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.effect.EffectType;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import com.eclipsekingdom.warpmagic.warps.home.RelationsManager;
import com.eclipsekingdom.warpmagic.warps.vortex.Vortex;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexManager;
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.warps.warp.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteListener implements Listener {

    public AutoCompleteListener(WarpMagic plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onComplete(TabCompleteEvent e){
        if(e.getSender() instanceof Player){
            Player player = (Player) e.getSender();
            
            if(e.getBuffer().contains("/tpahere ")){
                e.setCompletions(getRefinedCompletions("/tpahere", e.getBuffer(), tpaCompletions(player)));
            }else if(e.getBuffer().contains("/tpa ")){
                e.setCompletions(getRefinedCompletions("/tpa", e.getBuffer(), tpaCompletions(player)));
            }else if(e.getBuffer().contains("/home invite ")){
                e.setCompletions(getRefinedCompletions("/home invite", e.getBuffer(),inviteCompletions(player)));
            }else if(e.getBuffer().contains("/home uninvite ")){
                e.setCompletions(getRefinedCompletions("/home uninvite", e.getBuffer(),uninviteCompletions(player)));
            }else if(e.getBuffer().contains("/home ")){
                e.setCompletions(getRefinedCompletions("/home", e.getBuffer(), homeCompletions));
            }else if(e.getBuffer().contains("/warp del ")){
                e.setCompletions(getRefinedCompletions("/warp del", e.getBuffer(),warpList(player)));
            }else if(e.getBuffer().contains("/warp ")){
                e.setCompletions(getRefinedCompletions("/warp", e.getBuffer(),warpCompletions(player)));
            }else if(e.getBuffer().contains("/vortex del ")){
                e.setCompletions(getRefinedCompletions("/vortex del", e.getBuffer(),vortexSetByList(player)));
            }else if(e.getBuffer().contains("/vortex ")){
                e.setCompletions(getRefinedCompletions("/vortex", e.getBuffer(),vortexCompletion()));
            }else if(e.getBuffer().contains("/fhome ")){
                e.setCompletions(getRefinedCompletions("/fhome", e.getBuffer(),fhomeCompletions(player)));
            }else if(e.getBuffer().contains("/effectstone ")){
                e.setCompletions(getRefinedCompletions("/effectstone", e.getBuffer(), effectCompletions()));
            }
        }
    }

    private static final HomeManager homeManager = HomeManager.getInstance();
    private static final WarpManager warpManager = WarpManager.getInstance();
    private static final VortexManager vortexManager = VortexManager.getInstance();
    private static final RelationsManager relationsManager = RelationsManager.getInstance();

    private List<String> getRefinedCompletions(String root, String buffer, List<String> completions){
        if(buffer.equalsIgnoreCase(root + " ")){
            return completions;
        }else{
            List<String> refinedCompletions = new ArrayList<>();
            String bufferFromRoot = buffer.split(root + " ")[1];
            for(String completion : completions){
                if(bufferFromRoot.length() < completion.length()){
                    if(completion.substring(0,bufferFromRoot.length()).equalsIgnoreCase(bufferFromRoot)){
                        refinedCompletions.add(completion);
                    }
                }
            }
            return refinedCompletions;
        }
    }

    private static final List<String> homeCompletions = buildHomeCompletions();
    private static final List<String> buildHomeCompletions(){
        List<String> completions = new ArrayList<>();
        completions.add("set");
        completions.add("del");
        completions.add("invite");
        completions.add("uninvite");
        completions.add("flist");
        completions.add("fclear");
        completions.add("help");
        return completions;
    }

    private static final List<String> uninviteCompletions(Player player){
        List<String> completions = new ArrayList<>();
        Home home = homeManager.getHome(player);
        if(home != null){
            completions.addAll(home.getFriends());
        }
        return completions;
    }

    private static List<String> inviteCompletions(Player player){
        List<String> onlinePlayerName = new ArrayList<>();
        Home playerHome = homeManager.getHome(player);
        List<String> playerFriends = new ArrayList<>();
        if(playerHome != null){
            playerFriends = playerHome.getFriends();
        }
        for(Player oPlayer: Bukkit.getOnlinePlayers()){
            if(oPlayer != player && !playerFriends.contains(oPlayer.getDisplayName())){
                onlinePlayerName.add(oPlayer.getDisplayName());
            }
        }
        return onlinePlayerName;
    }

    private static final List<String> warpCompletions(Player player){
        List<String> completions = new ArrayList<>();
        completions.addAll(warpList(player));
        completions.add("set");
        completions.add("del");
        completions.add("list");
        completions.add("help");
        return completions;
    }

    private static final List<String> warpList(Player player){
        List<String> completions = new ArrayList<>();
        for(Warp warp: warpManager.getWarps(player)){
            completions.add(warp.getName());
        }
        return completions;
    }

    private static final List<String> vortexCompletion(){
        List<String> completions = new ArrayList<>();
        completions.addAll(vortexList());
        completions.add("set");
        completions.add("del");
        completions.add("list");
        completions.add("mylist");
        completions.add("help");
        return completions;
    }

    private static final List<String> vortexSetByList(Player player){
        List<String> completions = new ArrayList<>();
        for(Vortex vortex: vortexManager.getVortexesSetBy(player)){
            completions.add(vortex.getName());
        }
        return completions;
    }

    private static final List<String> vortexList(){
        List<String> completions = new ArrayList<>();
        for(Vortex vortex: vortexManager.getVortexes()){
            completions.add(vortex.getName());
        }
        return completions;
    }

    private static final List<String> fhomeCompletions(Player player){
        List<String> completions = new ArrayList<>();
        completions.addAll(relationsManager.getReqirements(player.getDisplayName()));
        return completions;
    }

    private static List<String> tpaCompletions(Player player){
        List<String> onlinePlayerName = new ArrayList<>();
        for(Player oPlayer: Bukkit.getOnlinePlayers()){
            if(oPlayer != player){
                onlinePlayerName.add(oPlayer.getDisplayName());
            }
        }
        return onlinePlayerName;
    }

    private static List<String> effectCompletions(){
        List<String> effect = new ArrayList<>();
        for(EffectType effectType: EffectType.values()){
            if(effectType != EffectType.NONE && effectType != EffectType.UNKNOWN){
                effect.add(effectType.name().toLowerCase());
            }
        }
        return effect;
    }



}
