package com.eclipsekingdom.warpmagic.warp.effect;

import com.eclipsekingdom.warpmagic.sys.Version;
import com.eclipsekingdom.warpmagic.sys.config.PluginConfig;
import com.eclipsekingdom.warpmagic.util.XSound;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Effect {

    public static IEffect iEffect = selectEffect();

    public static IEffect selectEffect() {
        int version = Version.current.value;
        if (version >= 109) {
            return new Effect_V1_9();
        } else {
            return new Effect_V1_8();
        }
    }

    public static void play(Player player) {
        if (PluginConfig.isSound()) playSound(player.getLocation());
        if (PluginConfig.isParticles()) iEffect.playParticles(player);
    }

    public static void playSound(Location location) {
        if (XSound.ENTITY_ENDERMAN_TELEPORT.isSupported()) {
            location.getWorld().playSound(location, XSound.ENTITY_ENDERMAN_TELEPORT.parseSound(), 0.5f, 1.3f);
        }

    }

}
