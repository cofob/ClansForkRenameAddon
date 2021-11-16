package ru.cofob.ClansForkRenameAddon;

import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {
    private FileConfiguration configuration;

    public Messages() {
        File file = new File(Main.getPlugin().getDataFolder(), "Settings/messages.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.configuration.options().copyDefaults(true);
        this.configuration.addDefault("Prefix", "&7[&cClans&7]");
        this.configuration.addDefault("Commands.DisplayRename", "%prefix% /clan %command% <name> | &eRename your Clan");
        this.configuration.addDefault("Rename.MessageFormat", "&7[%clan%&7] &7Your Clan Name has been changed to &e%clanName%");
        FileUtils.save(file, this.configuration);
    }

    public String getMessage(String path) {
        String value = ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path));
        value = value.replace("%prefix%", ChatColor.translateAlternateColorCodes('&', this.configuration.getString("Prefix")));
        return value;
    }
}
