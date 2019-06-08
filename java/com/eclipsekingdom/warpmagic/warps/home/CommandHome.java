package com.eclipsekingdom.warpmagic.warps.home;

import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.RootCommand;
import com.eclipsekingdom.warpmagic.warps.home.actions.*;

import java.util.ArrayList;
import java.util.List;

public class CommandHome extends RootCommand {

    /* --- constructors --- */

    private CommandHome(){}

    private static final CommandHome COMMAND_HOME = new CommandHome();

    public static final CommandHome getInstance(){
        return COMMAND_HOME;
    }


    /* --- implementation --- */

    @Override
    protected List<CommandAction> initCommandActions() {
        List<CommandAction> actions = new ArrayList<>();
        actions.add(new Default());
        actions.add(new FHome());
        actions.add(new Help());
        actions.add(new Set());
        actions.add(new Del());
        actions.add(new Invite());
        actions.add(new Uninvite());
        actions.add(new FList());
        actions.add(new FClear());
        return actions;
    }

    @Override
    protected CommandAction initDefaultAction() {
        return new Default();
    }

}
