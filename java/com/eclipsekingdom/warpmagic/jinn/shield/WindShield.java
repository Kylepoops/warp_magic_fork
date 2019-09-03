package com.eclipsekingdom.warpmagic.jinn.shield;

import com.eclipsekingdom.warpmagic.jinn.shield.builder.WindHurtShieldBuilder;
import com.eclipsekingdom.warpmagic.jinn.shield.builder.WindShieldBuilder;

public class WindShield extends JinnShield {

    public WindShield() {
        super(new WindShieldBuilder(), new WindHurtShieldBuilder());
    }

}
