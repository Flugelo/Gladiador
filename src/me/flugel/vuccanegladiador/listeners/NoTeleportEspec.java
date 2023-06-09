package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class NoTeleportEspec implements Listener {
    @EventHandler
    public void noTeleport(PlayerTeleportEvent e){
        if(VuccaneGladiador.getInstance().getEspectador().contains(e.getPlayer())){
            if(e.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)){
                e.setCancelled(true);
                e.getPlayer().sendMessage("§cVocê não pode usar esse tipo de teleport");
            }
        }
    }
}
