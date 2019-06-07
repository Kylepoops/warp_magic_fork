package com.eclipsekingdom.warpmagic.warps.warp;

import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.RootCommand;
import com.eclipsekingdom.warpmagic.warps.warp.actions.*;

import java.util.ArrayList;
import java.util.List;

public class CommandWarp extends RootCommand {

    /* --- constructors --- */

    private CommandWarp(){}

    private static final CommandWarp COMMAND_WARP = new CommandWarp();

    public static final CommandWarp getInstance(){
        return COMMAND_WARP;
    }


    /* --- implementation --- */

    @Override
    protected List<CommandAction> initCommandActions() {
        List<CommandAction> actions = new ArrayList<>();
        actions.add(new Default());
        actions.add(new Help());
        actions.add(new Set());
        actions.add(new Del());
        actions.add(new WList());
        return actions;
    }

    @Override
    protected CommandAction initDefaultAction() {
        return new Default();
    }


}
