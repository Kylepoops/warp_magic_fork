package com.eclipsekingdom.warpmagic.util.commands;

import org.bukkit.entity.Player;

public abstract class CommandAction {

    public CommandAction(){
        this.info = initCommandInfo();
        this.ID = initID();
    }

    public abstract void run(Player player, String[] args);


    public CommandInfo getInfo() {
        return info;
    }

    public String ID(){
        return ID;
    }

    protected abstract CommandInfo initCommandInfo();
    private final CommandInfo info;

    protected abstract String initID();
    private final String ID;

}
