package ru.cofob.ClansForkRenameAddon;

import ru.cofob.Clans.AddonHandler.CustomAddonHook;
import java.io.File;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RenameHook extends CustomAddonHook {
    public RenameHook(String command, String message) {
        super(command, message);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if (args.length == 0 && this.getClanConfiguration().hasPermission(player, Main.getPlugin().getSettings().getValue("PermissionClanRename"))) {
            sender.sendMessage(this.getMessage().replace("%command%", this.getCommand()));
        }

        if (args.length >= 2 && args[0].equalsIgnoreCase(this.getCommand())) {
            if (!this.getClanConfiguration().hasPermission(player, Main.getPlugin().getSettings().getValue("PermissionClanRename"))) {
                player.sendMessage(this.getClanConfiguration().getMessages().getMessage("Commands.NoPermission"));
                return false;
            }

            String name = args[1];
            String clan = this.getClanConfiguration().getClan(player);
            if (clan == null) {
                player.sendMessage(this.getClanConfiguration().getMessages().getMessage("Commands.MessageNoClan"));
                return false;
            }

            if (!this.getClanConfiguration().getClanNameMatcher().equals("") && !name.matches(this.getClanConfiguration().getClanNameMatcher())) {
                player.sendMessage(this.getClanConfiguration().getMessages().getMessage("Commands.MessageCreateNameMatcherTriggered"));
                return false;
            }

            if (this.getClanConfiguration().getClanOwner(clan).equals(this.getClanConfiguration().isOnlineMode() ? player.getUniqueId().toString() : player.getName())) {
                if (this.getClanConfiguration().getBlacklistedNames().contains(name.toLowerCase())) {
                    player.sendMessage(this.getClanConfiguration().getMessages().getMessage("Commands.MessageClanNameBlacklisted"));
                    return false;
                }

                if (name.toCharArray().length > this.getClanConfiguration().getClanNameLength()) {
                    player.sendMessage(this.getClanConfiguration().getMessages().getMessage("Commands.MessageTooLongClanName").replace("%length%", String.valueOf(this.getClanConfiguration().getClanNameLength())));
                    return false;
                }

                if (this.getClanConfiguration().exists(name)) {
                    player.sendMessage(this.getClanConfiguration().getMessages().getMessage("Commands.MessageCreateFailedExist").replace("%clan%", name));
                    return false;
                }

                (new File(ru.cofob.Clans.Main.getPlugin().getDataFolder(), clan + ".yml")).renameTo(new File(ru.cofob.Clans.Main.getPlugin().getDataFolder(), name + ".yml"));
                player.sendMessage(Main.getPlugin().getMessages().getMessage("Rename.MessageFormat").replace("%clanName%", name).replace("%clan%", name));
            } else {
                player.sendMessage(this.getClanConfiguration().getMessages().getMessage("Commands.MessageNoClanOwner"));
            }
        }

        return false;
    }
}
