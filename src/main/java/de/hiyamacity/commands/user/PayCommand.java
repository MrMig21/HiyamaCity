package de.hiyamacity.commands.user;

import de.hiyamacity.misc.Distances;
import de.hiyamacity.objects.User;
import de.hiyamacity.lang.LanguageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.ResourceBundle;

public class PayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player p)) return true;
        ResourceBundle rs = LanguageHandler.getResourceBundle(p.getUniqueId());

        if (args.length != 2) {
            p.sendMessage(rs.getString("payUsage"));
            return true;
        }
        Player t = Bukkit.getPlayer(args[0]);

        if (t == null) {
            p.sendMessage(rs.getString("playerNotFound").replace("%target%", args[0]));
            return true;
        }

        if (p.getName().equals(t.getName())) {
            p.sendMessage(rs.getString("payCantPaySelf"));
            return true;
        }

        if (p.getLocation().distanceSquared(t.getLocation()) > Distances.CHAT_MESSAGE_NEAREST) {
            p.sendMessage(rs.getString("playerTooFarAway"));
            return true;
        }

        int amount = Integer.parseInt(args[1]);

        if (amount < 0) {
            p.sendMessage(rs.getString("payNonNegative"));
            return true;
        }

        Optional<User> pUser = User.getUser(p.getUniqueId());
        Optional<User> tUser = User.getUser(t.getUniqueId());
        if (pUser.isEmpty() || tUser.isEmpty()) return true;
        long moneyUser = pUser.map(User::getPurse).orElse(Long.MIN_VALUE);
        if (moneyUser < amount) {
            p.sendMessage(rs.getString("payInsufficientAmount"));
            return true;
        }

        pUser.ifPresent(user -> user.setPurse(user.getPurse() - amount));
        p.sendMessage(rs.getString("paySend").replace("%target%", t.getName()).replace("%amount%", "" + amount));
        p.sendMessage("§c-" + amount + "$");
        tUser.ifPresent(user -> user.setPurse(user.getPurse() + amount));
        ResourceBundle trs = LanguageHandler.getResourceBundle(t.getUniqueId());
        t.sendMessage(trs.getString("payReceive").replace("%player%", p.getName()).replace("%amount%", "" + amount));
        t.sendMessage("§a+" + amount + "$");

        pUser.ifPresent(User::update);
        tUser.ifPresent(User::update);

        return false;
    }
}