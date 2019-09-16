package com.eclipsekingdom.warpmagic.jinn.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public enum Heads {
    JINN("Jinn Head", "eyJ0aW1lc3RhbXAiOjE1NjczNjI3NDE4NzgsInByb2ZpbGVJZCI6IjQ0MDNkYzU0NzViYzRiMTVhNTQ4Y2ZkYTZiMGViN2Q5IiwicHJvZmlsZU5hbWUiOiJHR0dhbWVyc1lUIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85ZWQzMjE0ZDdlMjJlNDU4ODc4MWEzMGQyYmQ3ZDczNjdjNDgxZTQ3N2UzNjFhNDAzZDUzYzVkMmM5YjZjOTExIn19fQ"),
    JINN_HURT("Jinn Head", "eyJ0aW1lc3RhbXAiOjE1NjczODM4NzQ0NDMsInByb2ZpbGVJZCI6ImZkNjBmMzZmNTg2MTRmMTJiM2NkNDdjMmQ4NTUyOTlhIiwicHJvZmlsZU5hbWUiOiJSZWFkIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9hNmFhYjk0ZmNhMDFmMDQ5OGQ2MjZkODYwZjZmNjM4YjcwMzFlNWI2ZWU5NzgwNGQyNzE1MDBhYWI3M2ZmYTkyIn19fQ");
    private ItemStack itemStack;

    Heads(String name, String texture) {
        this.itemStack = createSkull(texture, name);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static ItemStack createSkull(String url, String name) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (!url.isEmpty()) {
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
            skullMeta.setDisplayName(name);
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", url));
            try {
                Field profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, profile);
            } catch (IllegalArgumentException | SecurityException | NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            head.setItemMeta(skullMeta);
            return head;
        } else {
            return head;
        }
    }

}
