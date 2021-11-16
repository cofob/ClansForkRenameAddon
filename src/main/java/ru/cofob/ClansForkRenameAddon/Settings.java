package ru.cofob.ClansForkRenameAddon;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Settings {
    private FileConfiguration configuration;

    public Settings() {
        File file = new File(Main.getPlugin().getDataFolder(), "Settings/config.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.configuration.options().copyDefaults(true);
        this.configuration.addDefault("Command", "rename");
        this.configuration.addDefault("PermissionClanRename", "clans.rename");
        FileUtils.save(file, this.configuration);
    }

    public String getValue(String path) {
        return this.configuration.getString(path);
    }
}
