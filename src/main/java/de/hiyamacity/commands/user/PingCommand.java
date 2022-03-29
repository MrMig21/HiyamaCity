package de.hiyamacity.commands.user;

import de.hiyamacity.util.LanguageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.UUID;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(LanguageHandler.getResourceBundle().getString("onlyAsPlayer"));
            return true;
        }
        UUID uuid = p.getUniqueId();
        int ping = ((CraftPlayer) p).getHandle().ping;
        try {
            sender.sendMessage(LanguageHandler.getResourceBundle(uuid).getString("pingMessage").replace("%target%", p.getName()).replace("%ping%", "" + ping));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
