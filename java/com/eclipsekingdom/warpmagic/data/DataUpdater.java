package com.eclipsekingdom.warpmagic.data;

import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.util.ConsoleSender;
import com.eclipsekingdom.warpmagic.warp.Friend;
import com.eclipsekingdom.warpmagic.warp.Home;
import com.eclipsekingdom.warpmagic.warp.Vortex;
import com.eclipsekingdom.warpmagic.warp.Warp;
import com.eclipsekingdom.warpmagic.warp.effect.EffectType;
import com.eclipsekingdom.warpmagic.warp.global.GlobalPoint;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.Location;

import java.io.File;
import java.util.*;

public class DataUpdater {

    private static File legacyEffect = new File("plugins/WarpMagic/data/effect", "effects.yml");
    private static File legacyEffectDir = new File("plugins/WarpMagic/data/effect");

    private static File legacyHome = new File("plugins/WarpMagic/data/home", "homes.yml");
    private static File legacyRelation = new File("plugins/WarpMagic/data/home", "relations.yml");
    private static File legacyHomeDir = new File("plugins/WarpMagic/data/home");

    private static File legacyVortex = new File("plugins/WarpMagic/data/vortex", "vortexes.yml");
    private static File legacyVortexNum = new File("plugins/WarpMagic/data/vortex", "vortexNum.yml");
    private static File legacyVortexDir = new File("plugins/WarpMagic/data/vortex");

    private static File legacyWarp = new File("plugins/WarpMagic/data/warp", "warps.yml");
    private static File legacyWarpNum = new File("plugins/WarpMagic/data/warp", "warpNum.yml");
    private static File legacyWarpDir = new File("plugins/WarpMagic/data/warp");

    private static File dataDir = new File("plugins/WarpMagic/data");

    public static void convert() {
        convertEffect();
        convertHome();
        convertVortex();
        convertWarp();

        if(!legacyEffectDir.exists() && !legacyHomeDir.exists() && !legacyVortexDir.exists() && !legacyWarpDir.exists()){
            dataDir.delete();
        }
    }

    private static void convertEffect() {
        if (legacyEffect.exists()) {
            ConsoleSender.sendMessage("Legacy effect data detected. Beginning conversion.");
            FileConfiguration config = YamlConfiguration.loadConfiguration(legacyEffect);
            try {
                for (String playerIDString : config.getRoot().getKeys(false)) {
                    UUID playerID = UUID.fromString(playerIDString);
                    UserData userData = UserFlatFile.fetch(playerID);
                    if(!userData.hasCurrentEffect()){
                        EffectType current = EffectType.fromString(config.getString(playerIDString + ".current"));
                        userData.setCurentEffect(current);
                    }
                    for (String string : config.getStringList(playerIDString + ".effects")) {
                        EffectType effect = EffectType.fromString(string);
                        if(!userData.getEffects().contains(effect)){
                            userData.addEffect(effect);
                        }
                    }
                    UserFlatFile.store(playerID, userData);
                }
                legacyEffect.delete();
                legacyEffectDir.delete();
                ConsoleSender.sendMessage("Effect data converted");
            } catch (Exception e) {
                e.printStackTrace();
                ConsoleSender.sendMessage(ChatColor.RED + "Error converting " + legacyEffect.getName());
            }
        }
    }

    private static void convertHome() {
        if (legacyHome.exists()) {
            ConsoleSender.sendMessage("Legacy home data detected. Beginning conversion.");
            FileConfiguration config = YamlConfiguration.loadConfiguration(legacyHome);
            try {
                for (String playerName : config.getRoot().getKeys(false)) {
                    UUID playerID = getUUID(playerName);
                    if(playerID != null){
                        UserData userData = UserFlatFile.fetch(playerID);
                        if(!userData.hasHome()){
                            Location location = StorageString.convertToLocation(config.getString(playerName+".location"));
                            if(location != null && location.getWorld() != null){
                                List<Friend> friends = new ArrayList<>();
                                if(config.contains(playerName+".friends")){
                                    for(String friendName: config.getStringList(playerName+".friends")){
                                        UUID friendID = getUUID(friendName);
                                        friends.add(new Friend(friendID, friendName));
                                        if(friendID != null){
                                            UserData friendData = UserFlatFile.fetch(friendID);
                                            friendData.addFriend(new Friend(playerID, playerName));
                                            UserFlatFile.store(friendID, friendData);
                                        }
                                    }
                                }
                                Home home = new Home(location, friends);
                                userData.setHome(home);
                                UserFlatFile.store(playerID, userData);
                            }
                        }
                    }
                }
                legacyHome.delete();
                legacyRelation.delete();
                legacyHomeDir.delete();
                ConsoleSender.sendMessage("Home data converted");
            } catch (Exception e) {
                e.printStackTrace();
                ConsoleSender.sendMessage(ChatColor.RED + "Error converting " + legacyHome.getName());
            }
        }
    }

    private static UUID getUUID(String playerName){
        Player player = Bukkit.getPlayer(playerName);
        if(player != null){
            return player.getUniqueId();
        }else{
            for(OfflinePlayer offlinePlayer: Bukkit.getOfflinePlayers()){
                if(offlinePlayer.getName().equals(playerName)){
                    return offlinePlayer.getUniqueId();
                }
            }
            return null;
        }
    }

    private static void convertVortex(){

        boolean fileFound = false;
        if(legacyVortexNum.exists() || legacyVortex.exists()){
            ConsoleSender.sendMessage("Legacy vortex data detected. Beginning conversion.");
            fileFound = true;
        }

        if (legacyVortex.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(legacyVortex);
            try {
                for (String vortexName : config.getRoot().getKeys(false)) {
                    Location location = StorageString.convertToLocation(config.getString(vortexName+".location"));
                    if(location != null && location.getWorld() != null){
                        String creatorName = config.getString(vortexName+".creatorName");
                        Vortex vortex = new Vortex(vortexName, location, creatorName);
                        VortexFlatFile.store(vortex);
                    }
                }
                legacyVortex.delete();
            } catch (Exception e) {
                e.printStackTrace();
                ConsoleSender.sendMessage(ChatColor.RED + "Error converting " + legacyVortex.getName());
            }
        }

        if(legacyVortexNum.exists()){
            FileConfiguration config = YamlConfiguration.loadConfiguration(legacyVortexNum);
            try {
                for (String playerIDString : config.getRoot().getKeys(false)) {
                    UUID playerID = UUID.fromString(playerIDString);
                    int amount = config.getInt(playerIDString);
                    int startingVortexNum = PluginConfig.getStartingVortexNum();
                    amount -= startingVortexNum;
                    if(amount > 0){
                        UserData userData = UserFlatFile.fetch(playerID);
                        userData.incrementUnlockedVortexes(amount);
                        UserFlatFile.store(playerID, userData);
                    }
                }
                legacyVortexNum.delete();
            } catch (Exception e) {
                e.printStackTrace();
                ConsoleSender.sendMessage(ChatColor.RED + "Error converting " + legacyVortexNum.getName());
            }
        }

        if(fileFound && !legacyVortex.exists() && !legacyVortexNum.exists()){
            legacyVortexDir.delete();
            ConsoleSender.sendMessage("Vortex data converted");
        }

    }

    private static void convertWarp(){

        boolean fileFound = false;
        if(legacyWarpNum.exists() || legacyWarp.exists()){
            ConsoleSender.sendMessage("Legacy warp data detected. Beginning conversion.");
            fileFound = true;
        }

        if (legacyWarp.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(legacyWarp);
            try {
                for (String playerIDString : config.getRoot().getKeys(false)) {
                    UUID playerID = UUID.fromString(playerIDString);
                    UserData userData = UserFlatFile.fetch(playerID);
                    for(String warpName: config.getConfigurationSection(playerIDString).getKeys(false)){
                        Location location = StorageString.convertToLocation(config.getString(playerIDString + "."+warpName));
                        if(location != null && location.getWorld() != null){
                            if(userData.getWarp(warpName) == null){
                                userData.addWarp(new Warp(warpName, location));
                            }
                        }
                    }
                    UserFlatFile.store(playerID, userData);
                }
                legacyWarp.delete();
            } catch (Exception e) {
                e.printStackTrace();
                ConsoleSender.sendMessage(ChatColor.RED + "Error converting " + legacyWarp.getName());
            }
        }

        if(legacyWarpNum.exists()){
            FileConfiguration config = YamlConfiguration.loadConfiguration(legacyWarpNum);
            try {
                for (String playerIDString : config.getRoot().getKeys(false)) {
                    UUID playerID = UUID.fromString(playerIDString);
                    int amount = config.getInt(playerIDString);
                    int startingWarpNum = PluginConfig.getStartingWarpNum();
                    amount -= startingWarpNum;
                    if(amount > 0){
                        UserData userData = UserFlatFile.fetch(playerID);
                        userData.incrementUnlockedWarps(amount);
                        UserFlatFile.store(playerID, userData);
                    }
                }
                legacyWarpNum.delete();
            } catch (Exception e) {
                e.printStackTrace();
                ConsoleSender.sendMessage(ChatColor.RED + "Error converting " + legacyWarpNum.getName());
            }
        }

        if(fileFound && !legacyWarp.exists() && !legacyWarpNum.exists()){
            legacyWarpDir.delete();
            ConsoleSender.sendMessage("Warp data converted");
        }

    }


}
