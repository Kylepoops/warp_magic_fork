package com.eclipsekingdom.warpmagic.warps.home;

import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.RootCommand;
import com.eclipsekingdom.warpmagic.warps.home.actions.*;

import java.util.ArrayList;
import java.util.List;

public class CommandFHome extends RootCommand {

    /* --- constructors --- */

    private CommandFHome(){}

    private static final CommandFHome COMMAND_F_HOME = new CommandFHome();

    public static final CommandFHome getInstance(){
        return COMMAND_F_HOME;
    }

    @Override
    protected List<CommandAction> initCommandActions() {
        List<CommandAction> actions = new ArrayList<>();
        actions.add(new FHome());
        actions.add(new FHList());
        return actions;
    }

    @Override
    protected CommandAction initDefaultAction() {
        return new FHome();
    }
}
