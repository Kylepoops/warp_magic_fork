package com.eclipsekingdom.warpmagic.util;


/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Crypto Morin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import com.google.common.base.Enums;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* References
 *
 * * * GitHub: https://github.com/CryptoMorin/XSeries/blob/master/XSound.java
 * * XSeries: https://www.spigotmc.org/threads/378136/
 * 1.8: http://docs.codelanx.com/Bukkit/1.8/org/bukkit/Sound.html
 * Latest: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html
 * Basics: https://bukkit.org/threads/151517/
 * playSound command: https://minecraft.gamepedia.com/Commands/playsound
 */

/**
 * <b>XSound</b> - Universal Minecraft Sound Support<br>
 * Supports 1.8-1.15<br>
 * 1.13 and above as priority.
 * <p>
 * Sounds are thread-safe. But this doesn't mean you should
 * use a bukkit async scheduler for every {@link Player#playSound} call.
 * <p>
 * <b>Volume:</b> 0.0-∞ - 1.0f (normal) - Using higher values increase the distance from which the sound can be heard.<br>
 * <b>Pitch:</b> 0.5-2.0 - 1.0f (normal) - How fast the sound is play.
 *
 * @author Crypto Morin
 * @version 2.2.1
 * @see Sound
 */
public enum XSound {
    ENTITY_ENDERMAN_AMBIENT("ENDERMAN_IDLE", "ENTITY_ENDERMEN_AMBIENT"),
    ENTITY_ENDERMAN_DEATH("ENDERMAN_DEATH", "ENTITY_ENDERMEN_DEATH"),
    ENTITY_ENDERMAN_HURT("ENDERMAN_HIT", "ENTITY_ENDERMEN_HURT"),
    ENTITY_ENDERMAN_SCREAM("ENDERMAN_SCREAM", "ENTITY_ENDERMEN_SCREAM"),
    ENTITY_ENDERMAN_STARE("ENDERMAN_STARE", "ENTITY_ENDERMEN_STARE"),
    ENTITY_ENDERMAN_TELEPORT("ENDERMAN_TELEPORT", "ENTITY_ENDERMEN_TELEPORT"),
    ;


    /**
     * An immutable cached list of {@link XSound#values()} to avoid allocating memory for
     * calling the method every time.
     *
     * @since 2.0.0
     */
    public static final EnumSet<XSound> VALUES = EnumSet.allOf(XSound.class);

    private static final Cache<XSound, com.google.common.base.Optional<Sound>> CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .softValues()
            .build();
    /**
     * Pre-compiled RegEx pattern.
     * Include both replacements to avoid creating string multiple times and multiple RegEx checks.
     *
     * @since 1.0.0
     */
    private static final Pattern FORMAT_PATTERN = Pattern.compile("\\d+|\\W+");
    private final String[] legacy;

    XSound(String... legacy) {
        this.legacy = legacy;
    }

    /**
     * Attempts to build the string like an enum name.<br>
     * Removes all the spaces, numbers and extra non-English characters. Also removes some config/in-game based strings.
     *
     * @param name the sound name to modify.
     * @return a Sound enum name.
     * @since 1.0.0
     */

    private static String format(String name) {
        return FORMAT_PATTERN.matcher(
                name.trim().replace('-', '_').replace(' ', '_')).replaceAll("").toUpperCase(Locale.ENGLISH);
    }

    /**
     * In most cases your should be using {@link #name()} instead.
     *
     * @return a friendly readable string name.
     */
    @Override
    public String toString() {
        return WordUtils.capitalize(this.name().replace('_', ' ').toLowerCase(Locale.ENGLISH));
    }

    /**
     * Parses the XSound as a {@link Sound} based on the server version.
     *
     * @return the vanilla sound.
     * @since 1.0.0
     */

    @SuppressWarnings({"Guava", "OptionalAssignedToNull"})
    public Sound parseSound() {
        com.google.common.base.Optional<Sound> cachedSound = CACHE.getIfPresent(this);
        if (cachedSound != null) return cachedSound.orNull();
        com.google.common.base.Optional<Sound> sound;

        // Since Sound class doesn't have a getSound() method we'll use Guava so
        // it can cache it for us.
        sound = Enums.getIfPresent(Sound.class, this.name());

        if (!sound.isPresent()) {
            for (String legacy : this.legacy) {
                sound = Enums.getIfPresent(Sound.class, legacy);
                if (sound.isPresent()) break;
            }
        }

        // Put nulls too, because there's no point of parsing them again if it's going to give us null again.
        CACHE.put(this, sound);
        return sound.orNull();
    }

    /**
     * Checks if this sound is supported in the current Minecraft version.
     * <p>
     * An invocation of this method yields exactly the same result as the expression:
     * <p>
     * <blockquote>
     * {@link #parseSound()} != null
     * </blockquote>
     *
     * @return true if the current version has this sound, otherwise false.
     * @since 1.0.0
     */
    public boolean isSupported() {
        return this.parseSound() != null;
    }

    /**
     * Checks if the given string matches any of this sound's legacy sound names.
     *
     * @param name the sound name to check
     * @return true if it's one of the legacy names.
     * @since 1.0.0
     */
    public boolean anyMatchLegacy(String name) {
        Validate.notEmpty(name, "Cannot check for legacy name for null or empty sound name");
        return Arrays.asList(this.legacy).contains(format(name));
    }

    /**
     * Plays a sound in a location with the given volume and pitch.
     *
     * @param location the location to play this sound.
     * @param volume   the volume of the sound, 1 is normal.
     * @param pitch    the pitch of the sound, 0 is normal.
     * @since 2.0.0
     */
    public void playSound(Location location, float volume, float pitch) {
        Objects.requireNonNull(location, "Cannot play sound to null location");
        Sound sound = this.parseSound();
        Validate.isTrue(sound != null, "Unsupported sound type: ", this.name());
        location.getWorld().playSound(location, sound, volume, pitch);
    }
}