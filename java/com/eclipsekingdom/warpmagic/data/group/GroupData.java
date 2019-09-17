package com.eclipsekingdom.warpmagic.data.group;

public class GroupData {

    private int bonusWarps;
    private int bonusVortexes;

    public GroupData(int bonusWarps, int bonusVortexes){
        this.bonusWarps = bonusWarps;
        this.bonusVortexes = bonusVortexes;
    }

    public int getBonusWarps(){
        return bonusWarps;
    }

    public int getBonusVortexes(){
        return bonusVortexes;
    }

}
