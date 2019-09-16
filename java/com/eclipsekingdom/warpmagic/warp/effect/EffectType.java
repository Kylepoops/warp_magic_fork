package com.eclipsekingdom.warpmagic.warp.effect;

import com.eclipsekingdom.warpmagic.warp.effect.list.*;

public enum EffectType {

    UNKNOWN(new Unknown()),
    NONE(new None()),
    FLAME(new Flame()),
    BATS(new Bats()),
    Electric(new Electric()),
    MELTING(new Melting()),
    ENDER(new Ender()),
    FARORES(new FaroresWind()),
    POOF(new Poof()),
    POOFDARK(new PoofDark());

    private final Effect effect;

    EffectType(Effect effect) {
        this.effect = effect;
    }

    public static final EffectType fromString(String string) {
        for (EffectType effectType : EffectType.values()) {
            if (effectType.name().equalsIgnoreCase(string)) {
                return effectType;
            }
        }
        return UNKNOWN;
    }

    public static final EffectType fromItemName(String string) {
        for (EffectType effectType : EffectType.values()) {
            if (effectType.getEffect().getName().equalsIgnoreCase(string)) {
                return effectType;
            }
        }
        return UNKNOWN;
    }

    public Effect getEffect() {
        return effect;
    }


}
