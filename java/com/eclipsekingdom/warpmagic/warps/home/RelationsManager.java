package com.eclipsekingdom.warpmagic.warps.home;

import com.eclipsekingdom.warpmagic.util.data.DataType;
import com.eclipsekingdom.warpmagic.util.data.Manager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class RelationsManager extends Manager<String, RelationShips> {

    /* --- constructors --- */

    private RelationsManager() {
        super(new DataType<RelationShips>(new RelationShips()) {
            @Override
            public void writeTo(String path, RelationShips data, FileConfiguration config) {
                String requirementsPath = path+"."+"requirements";
                config.set(requirementsPath, data.getRequirements());
                String dependantsPath = path+"."+"dependants";
                config.set(dependantsPath, data.getDependants());
            }

            @Override
            public RelationShips readFrom(String path, FileConfiguration config) {
                String requirementsPath = path+"."+"requirements";
                List<String> requirements = config.getStringList(requirementsPath);
                String dependantsPath = path+"."+"dependants";
                List<String> dependants = config.getStringList(dependantsPath);
                return new RelationShips(requirements, dependants);
            }
        }, "relations", "/data/home");
    }

    private static final RelationsManager RELATIONS_MANAGER = new RelationsManager();

    public static final RelationsManager getInstance(){
        return RELATIONS_MANAGER;
    }


    /* --- interface --- */

    @Override
    public void load() {
        List<String> playerNames = database.getAllKeyPaths();
        for(String playerName: playerNames){
            cache(playerName);
        }
    }

    public void registerFriendAdd(String hostName, String guestName){

        RelationShips guestRelationships = keyToData.get(guestName);
        if(guestRelationships != null){
            guestRelationships.addRequirement(hostName);
        }else{
            RelationShips relationShips = new RelationShips();
            relationShips.addRequirement(hostName);
            keyToData.put(guestName, relationShips);
        }

        trackUnsavedData(guestName);

        RelationShips hostRelationships = keyToData.get(hostName);
        if(hostRelationships != null){
            guestRelationships.addDependant(hostName);
        }else{
            RelationShips relationShips = new RelationShips();
            relationShips.addDependant(guestName);
            keyToData.put(hostName, relationShips);
        }

        trackUnsavedData(hostName);
    }

    public void registerFriendRemove(String hostName, String guestName){

        RelationShips guestRelationships = keyToData.get(guestName);
        if(guestRelationships != null){
            guestRelationships.remRequirement(hostName);
            trackUnsavedData(guestName);
        }

        RelationShips hostRelationships = keyToData.get(hostName);
        if(hostRelationships != null){
            guestRelationships.remDependant(hostName);
            trackUnsavedData(hostName);
        }

    }

    public List<String> getReqirements(String hostName){
        RelationShips relationShips = keyToData.get(hostName);
        if(relationShips != null){
            return relationShips.getRequirements();
        }else{
            return Collections.emptyList();
        }
    }

    public List<String> getDependants(String hostName){
        RelationShips relationShips = keyToData.get(hostName);
        if(relationShips != null){
            return relationShips.getDependants();
        }else{
            return Collections.emptyList();
        }
    }


    /* --- implementation --- */

    @Override
    protected boolean stillNeeded(String playerName, String leavingPlayerName) {
        return false;
    }

    @Override
    protected List<String> getRequirements(String playerName) {
        return Collections.emptyList();
    }


}
