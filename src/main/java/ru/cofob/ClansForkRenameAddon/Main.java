package ru.cofob.ClansForkRenameAddon;

import ru.cofob.Clans.AddonHandler.AddonAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main m;
    private Messages messages;
    private Settings settings;

    public Main() {
    }

    public void onEnable() {
        m = this;
        this.messages = new Messages();
        this.settings = new Settings();
        AddonAPI.registerAddonHook(new RenameHook(this.getSettings().getValue("Command"), this.getMessages().getMessage("Commands.DisplayRename")));
    }

    public void onDisable() {
    }

    public static Main getPlugin() {
        return m;
    }

    public Messages getMessages() {
        return this.messages;
    }

    public Settings getSettings() {
        return this.settings;
    }
}
