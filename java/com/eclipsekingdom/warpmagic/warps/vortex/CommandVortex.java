package com.eclipsekingdom.warpmagic.warps.vortex;

import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.RootCommand;
import com.eclipsekingdom.warpmagic.warps.vortex.actions.*;

import java.util.ArrayList;
import java.util.List;

public class CommandVortex extends RootCommand {

    /* --- constructors --- */

    private CommandVortex(){}

    private static final CommandVortex COMMAND_VORTEX = new CommandVortex();

    public static final CommandVortex getInstance(){
        return COMMAND_VORTEX;
    }


    /* --- implementation --- */

    @Override
    protected List<CommandAction> initCommandActions() {
        List<CommandAction> actions = new ArrayList<>();
        actions.add(new Default());
        actions.add(new Help());
        actions.add(new Set());
        actions.add(new Del());
        actions.add(new VList());
        actions.add(new MyList());
        return actions;
    }

    @Override
    protected CommandAction initDefaultAction() {
        return new Default();
    }

}
