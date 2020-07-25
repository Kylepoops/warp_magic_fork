package com.eclipsekingdom.warpmagic.util;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.data.VortexCache;
import com.eclipsekingdom.warpmagic.sys.Permissions;
import com.eclipsekingdom.warpmagic.warp.Home;
import com.eclipsekingdom.warpmagic.warp.Vortex;
import com.eclipsekingdom.warpmagic.warp.Warp;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteListener implements Listener {

    public AutoCompleteListener() {
        Plugin plugin = WarpMagic.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onComplete(TabCompleteEvent e) {
        if (e.getSender() instanceof Player) {
            Player player = (Player) e.getSender();

            if (e.getBuffer().contains("/tpahere ")) {
                e.setCompletions(getRefinedCompletions("/tpahere", e.getBuffer(), tpaCompletions(player)));
            } else if (e.getBuffer().contains("/tpa ")) {
                e.setCompletions(getRefinedCompletions("/tpa", e.getBuffer(), tpaCompletions(player)));
            } else if (e.getBuffer().contains("/home invite ")) {
                e.setCompletions(getRefinedCompletions("/home invite", e.getBuffer(), inviteCompletions(player)));
            } else if (e.getBuffer().contains("/home uninvite ")) {
                e.setCompletions(getRefinedCompletions("/home uninvite", e.getBuffer(), uninviteCompletions(player)));
            } else if (e.getBuffer().contains("/home ")) {
                e.setCompletions(getRefinedCompletions("/home", e.getBuffer(), homeCompletions));
            } else if (e.getBuffer().contains("/warp del ")) {
                e.setCompletions(getRefinedCompletions("/warp del", e.getBuffer(), warpList(player)));
            } else if (e.getBuffer().contains("/warp update ")) {
                e.setCompletions(getRefinedCompletions("/warp update", e.getBuffer(), warpList(player)));
            } else if (e.getBuffer().contains("/warp ")) {
                List<String> completions = warpList(player);
                completions.addAll(warpCompletions);
                e.setCompletions(getRefinedCompletions("/warp", e.getBuffer(), completions));
            } else if (e.getBuffer().contains("/vortex del ")) {
                e.setCompletions(getRefinedCompletions("/vortex del", e.getBuffer(), vortexSetByList(player)));
            } else if (e.getBuffer().contains("/vortex update ")) {
                e.setCompletions(getRefinedCompletions("/vortex update", e.getBuffer(), vortexSetByList(player)));
            } else if (e.getBuffer().contains("/vortex ")) {
                List<String> completions = vortexList();
                completions.addAll(vortexCompletions);
                e.setCompletions(getRefinedCompletions("/vortex", e.getBuffer(), completions));
            } else if (e.getBuffer().contains("/fhome ")) {
                e.setCompletions(getRefinedCompletions("/fhome", e.getBuffer(), fhomeCompletions(player)));
            }
        }
    }

    private List<String> getRefinedCompletions(String root, String buffer, List<String> completions) {
        if (buffer.equalsIgnoreCase(root + " ")) {
            return completions;
        } else {
            List<String> refinedCompletions = new ArrayList<>();
            String bufferFromRoot = buffer.split(root + " ")[1];
            for (String completion : completions) {
                if (bufferFromRoot.length() < completion.length()) {
                    if (completion.substring(0, bufferFromRoot.length()).equalsIgnoreCase(bufferFromRoot)) {
                        refinedCompletions.add(completion);
                    }
                }
            }
            return refinedCompletions;
        }
    }

    private static final List<String> homeCompletions = buildHomeCompletions();

    private static final List<String> buildHomeCompletions() {
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

    private List<String> uninviteCompletions(Player player) {
        List<String> completions = new ArrayList<>();
        UserData userData = UserCache.getData(player);
        if (userData.hasHome()) {
            for (Friend friend : userData.getHome().getFriends()) {
                completions.add(friend.getName());
            }
        }
        return completions;
    }

    private List<String> inviteCompletions(Player player) {
        List<String> onlinePlayerName = new ArrayList<>();
        UserData userData = UserCache.getData(player);
        if (userData.hasHome()) {
            Home home = userData.getHome();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer != player && !home.isFriend(onlinePlayer.getUniqueId())) {
                    onlinePlayerName.add(onlinePlayer.getName());
                }
            }
        }
        return onlinePlayerName;
    }

    private static final ImmutableList<String> warpCompletions = new ImmutableList.Builder<String>()
            .add("set")
            .add("del")
            .add("list")
            .add("help")
            .build();

    private List<String> warpList(Player player) {
        List<String> completions = new ArrayList<>();
        UserData userData = UserCache.getData(player);
        for (Warp warp : userData.getWarps()) {
            completions.add(warp.getName());
        }
        return completions;
    }


    private static final ImmutableList<String> vortexCompletions = new ImmutableList.Builder<String>()
            .add("set")
            .add("setserver")
            .add("del")
            .add("list")
            .add("mylist")
            .add("help")
            .build();

    private List<String> vortexSetByList(Player player) {
        List<String> completions = new ArrayList<>();
        for (Vortex vortex : VortexCache.getVortexesSetBy(player)) {
            completions.add(vortex.getName());
        }
        if(Permissions.canSetGlobalPoints(player)){
            for (Vortex vortex: VortexCache.getVortexes()){
                if(vortex.getCreatorName() == null){
                    completions.add(vortex.getName());
                }
            }
        }
        return completions;
    }

    private List<String> vortexList() {
        List<String> completions = new ArrayList<>();
        for (Vortex vortex : VortexCache.getVortexes()) {
            completions.add(vortex.getName());
        }
        return completions;
    }

    private List<String> fhomeCompletions(Player player) {
        List<String> completions = new ArrayList<>();
        UserData userData = UserCache.getData(player);
        for (Friend friend : userData.getFriends()) {
            completions.add(friend.getName());
        }
        return completions;
    }

    private static List<String> tpaCompletions(Player player) {
        List<String> onlinePlayerName = new ArrayList<>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer != player) {
                onlinePlayerName.add(onlinePlayer.getName());
            }
        }
        return onlinePlayerName;
    }

}
