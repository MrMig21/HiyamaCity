package de.hiyamacity.util;

import de.hiyamacity.database.MySqlPointer;
import de.hiyamacity.main.Main;
import de.hiyamacity.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class PlaytimeTracker {

    public static void startPlaytimeTracker() {

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    User user = MySqlPointer.getUser(player.getUniqueId());
                    long minutes = user.getPlayedMinutes();
                    long hours = user.getPlayedHours();

                    minutes++;
                    if (minutes >= 60) {
                        minutes = 0;
                        hours++;
                        user.setPlayedHours(hours);
                    }
                    user.setPlayedMinutes(minutes);
                    MySqlPointer.updateUser(player.getUniqueId(), user);
                });
            }
        };

        runnable.runTaskTimer(Main.getInstance(), 20 * 60, 20 * 60);

    }

}