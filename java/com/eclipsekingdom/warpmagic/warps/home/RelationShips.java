package com.eclipsekingdom.warpmagic.warps.home;

import java.util.Collections;
import java.util.List;

public class RelationShips {

    public RelationShips(){

    }

    public RelationShips(List<String> requirements, List<String> dependants){
        this.requirements = requirements;
        this.dependants = dependants;
    }


    public List<String> getRequirements() {
        return requirements;
    }

    public List<String> getDependants() {
        return dependants;
    }

    public void addRequirement(String playerName){
        if(!requirements.contains(playerName)){
            requirements.add(playerName);
        }
    }

    public void remRequirement(String playerName){
        while (requirements.contains(playerName)){
            requirements.remove(playerName);
        }
    }

    public void addDependant(String playerName){
        if(!dependants.contains(playerName)){
            dependants.add(playerName);
        }
    }

    public void remDependant(String playerName){
        while (dependants.contains(playerName)){
            dependants.remove(playerName);
        }
    }

    public void setDependants(List<String> dependants) {
        this.dependants = dependants;
    }

    private List<String> requirements = Collections.emptyList();// the requirements of a player are those whose data the player needs
    private List<String> dependants = Collections.emptyList();//The dependants of a player are those that need the player's data
}
