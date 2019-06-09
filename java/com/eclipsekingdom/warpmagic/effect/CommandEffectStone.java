package com.eclipsekingdom.warpmagic.effect;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandEffectStone implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(Permissions.canSummonLoot(player)){
                if(args.length > 0){
                    if(!args[0].equalsIgnoreCase("list")){
                        EffectType effectType = EffectType.fromString(args[0]);
                        if(validEffectType(effectType)){
                            int amt = 1;
                            if(args.length > 1){
                                try{
                                    amt = Integer.parseInt(args[1]);
                                }catch (Exception e){
                                    //do nothing
                                }
                            }
                            ItemStack item = new WarpEffectStone(effectType.getEffect()).asItem();
                            item.setAmount(amt);
                            player.getInventory().addItem(item);
                        }else{
                            Notifications.sendNotFound(player, "Effect", args[0]);
                            Notifications.sendTip(player, "effectstone list", "for a list of effect types");
                        }
                    }else{
                        player.sendMessage(getEffectsAsString());
                    }
                }else{
                    Notifications.sendFormat(player, "effectstone [effect-type]");
                }
            }else{
                Notifications.sendWarning(player, "You do not have permission for this command");
            }
        }

        return false;
    }


    private String getEffectsAsString(){
        String effects = "";
        for(EffectType effectType: EffectType.values()){
            if(validEffectType(effectType)){
                effects += (", " + ChatColor.GRAY + effectType.toString().toLowerCase());
            }
        }
        if(effects.length() > 2){
            return effects.substring(2);
        }else{
            return "-";
        }
    }

    private boolean validEffectType(EffectType effectType){
        return (effectType != EffectType.UNKNOWN && effectType != EffectType.NONE);
    }

}