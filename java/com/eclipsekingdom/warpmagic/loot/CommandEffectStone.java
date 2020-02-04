package com.eclipsekingdom.warpmagic.loot;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.util.Amount;
import com.eclipsekingdom.warpmagic.util.language.Message;
import com.eclipsekingdom.warpmagic.warp.effect.EffectType;
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
                            ItemStack itemStack = WarpEffectStone.build(effectType);
                            int amount = args.length > 1 ? Amount.parse(args[1]): 1;
                            itemStack.setAmount(amount);
                            player.getInventory().addItem(itemStack);
                        }else{
                            player.sendMessage(Message.ERROR_EFFECT_NOT_FOUND.getFromEffect(args[0]));
                        }
                    }else{
                        player.sendMessage(getEffectsAsString());
                    }
                }else{
                    player.sendMessage(Message.FORMAT_EFFECT_STONE.get());
                }
            }else{
                player.sendMessage(Message.ERROR_NOT_ALLOWED.get());
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