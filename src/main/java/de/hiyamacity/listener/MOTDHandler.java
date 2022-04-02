package de.hiyamacity.listener;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import de.hiyamacity.util.VanishHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@SuppressWarnings("deprecation")
public class MOTDHandler implements Listener {

    @EventHandler
    public void onEvent(PaperServerListPingEvent e) {
        e.setNumPlayers(Bukkit.getOnlinePlayers().size() - VanishHandler.getVanishPlayerCount());
        e.setMotd("§d§l✧ §bHiyamaCity §8| §7Fantasy & Reallife Roleplay §8| §6[1.16.5]\n" +
                " §7» §e§lIndev");
    }
}