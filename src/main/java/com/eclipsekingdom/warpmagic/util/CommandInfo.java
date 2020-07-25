package com.eclipsekingdom.warpmagic.util;

public class CommandInfo {

    public CommandInfo(String command, String description){
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    private final String command;
    private final String description;
}
