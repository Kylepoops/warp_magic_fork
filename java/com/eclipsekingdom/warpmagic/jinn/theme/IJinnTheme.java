package com.eclipsekingdom.warpmagic.jinn.theme;

import com.eclipsekingdom.warpmagic.jinn.attack.Attacks;
import com.eclipsekingdom.warpmagic.jinn.cloak.JinnCloak;
import com.eclipsekingdom.warpmagic.jinn.head.JinnHead;
import com.eclipsekingdom.warpmagic.jinn.shield.JinnShield;

public interface IJinnTheme {
    JinnHead getHead();
    JinnCloak getCloak();
    JinnShield getShield();
    Attacks getAttacks();
    JinnTheme.JinnThemeType getType();
}
