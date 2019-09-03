package com.eclipsekingdom.warpmagic.jinn.theme;

import com.eclipsekingdom.warpmagic.jinn.attack.Attacks;
import com.eclipsekingdom.warpmagic.jinn.cloak.JinnCloak;
import com.eclipsekingdom.warpmagic.jinn.head.JinnHead;
import com.eclipsekingdom.warpmagic.jinn.shield.JinnShield;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class JinnTheme implements IJinnTheme {

    private JinnHead head;
    private JinnShield shield;
    private JinnCloak cloak;
    private Attacks attacks;
    private JinnThemeType type;

    public enum JinnThemeType {
        WIND;
    }

    public static JinnTheme from(JinnThemeType jinnThemeTypen){
        switch (jinnThemeTypen){
            case WIND: return new WindTheme();
            default: return new WindTheme();
        }
    }

    private static Random random = new Random();
    public static JinnTheme random(){
        int index = random.nextInt(JinnThemeType.values().length);
        return JinnTheme.from(JinnThemeType.values()[index]);
    }


    public JinnTheme(JinnHead head, JinnShield shield, JinnCloak cloak, Attacks attacks, JinnThemeType type){
        this.head = head;
        this.shield = shield;
        this.cloak = cloak;
        this.attacks = attacks;
        this.type = type;
    }

    @Override
    public JinnHead getHead() {
        return head;
    }

    @Override
    public JinnCloak getCloak() {
        return cloak;
    }

    @Override
    public JinnShield getShield() {
        return shield;
    }

    @Override
    public Attacks getAttacks() {
        return attacks;
    }

    @Override
    public JinnThemeType getType() {
        return type;
    }


}
