package com.eclipsekingdom.warpmagic.util.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class RootCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                getAction(args[0]).run(player, args);
            } else {
                defaultAction.run(player, args);
            }
        }

        return false;
    }

    public List<CommandInfo> getInfoList() {
        return infoList;
    }

    protected abstract List<CommandAction> initCommandActions();
    private final List<CommandAction> actions = initCommandActions();

    protected abstract CommandAction initDefaultAction();
    private final CommandAction defaultAction = initDefaultAction();

    private List<CommandInfo> infoList = buildCommandInfoList();
    private List<CommandInfo> buildCommandInfoList(){
        List<CommandInfo> infoList = new ArrayList<>();
        for(CommandAction action: actions){
            infoList.add(action.getInfo());
        }
        return infoList;
    }

    private CommandAction getAction(String arg){
        CommandAction action = defaultAction;
        for(CommandAction a: actions){
            if(a.ID().equalsIgnoreCase(arg)){
                action = a;
                break;
            }
        }
        return action;
    }


}
