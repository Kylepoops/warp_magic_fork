package com.eclipsekingdom.warpmagic.data;

import com.eclipsekingdom.warpmagic.sys.ConsoleSender;
import com.eclipsekingdom.warpmagic.sys.lang.Message;
import com.eclipsekingdom.warpmagic.util.StorageString;
import com.eclipsekingdom.warpmagic.warp.Vortex;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VortexFlatFile {

    private static String header = "Vortexes";
    private static File file = new File("plugins/WarpMagic", "vortex.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    public static List<Vortex> fetch() {
        ArrayList<Vortex> vortexes = new ArrayList<>();
        if (config.contains(header)) {
            for (String name : config.getConfigurationSection(header).getKeys(false)) {
                String key = header + "." + name;
                Location location = StorageString.convertToLocation(config.getString(key + ".location"));
                String creatorName = config.contains(key + ".creatorName")? config.getString(key + ".creatorName") : null;
                if (location != null) {
                    vortexes.add(new Vortex(name, location, creatorName));
                }
            }
        }
        return vortexes;
    }

    public static void store(Collection<Vortex> vortexes) {
        config.set(header, null);
        for (Vortex vortex : vortexes) {
            String key = header + "." + vortex.getName();
            config.set(key + ".location", StorageString.from(vortex.getLocation()));
            if (vortex.getCreatorName() != null) config.set(key + ".creatorName", vortex.getCreatorName());
        }
        save();
    }

    private static void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            ConsoleSender.sendMessage(Message.CONSOLE_FILE_ERROR.fromFile(file.getName()));
        }
    }

}
