package com.eclipsekingdom.warpmagic.effect;

import java.util.ArrayList;
import java.util.List;

public class EffectInfo {

    public EffectInfo(){}

    public EffectInfo(Effect currentEffect, List<Effect> effects){
        this.effects = effects;
        this.currentEffect = currentEffect;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void unlockEffect(Effect effect){
        if(!effects.contains(effect)){
            effects.add(effect);
        }
    }

    public Effect getCurrentEffect() {
        return currentEffect;
    }

    public void setCurrentEffect(Effect effect){
        currentEffect = effect;
    }

    private List<Effect> effects = new ArrayList<>();
    private Effect currentEffect = EffectType.NONE.getEffect();
}
