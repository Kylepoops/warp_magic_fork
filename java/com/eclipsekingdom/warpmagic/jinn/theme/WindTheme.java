package com.eclipsekingdom.warpmagic.jinn.theme;

import com.eclipsekingdom.warpmagic.jinn.attack.WindAttacks;
import com.eclipsekingdom.warpmagic.jinn.cloak.WindCloak;
import com.eclipsekingdom.warpmagic.jinn.head.WindHead;
import com.eclipsekingdom.warpmagic.jinn.shield.WindShield;

public class WindTheme extends JinnTheme {
    public WindTheme() {
        super(new WindHead(), new WindShield(), new WindCloak(), new WindAttacks(), JinnThemeType.WIND);
    }
}
