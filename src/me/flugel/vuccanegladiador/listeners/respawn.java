package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class respawn implements Listener {
    public void respawn(PlayerRespawnEvent e){
        if(VuccaneGladiador.getInstance().start){
            if(VuccaneGladiador.getInstance().getEspectador().contains(e.getPlayer())){
                Location loc = VuccaneGladiador.unserialize(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("locCentro"));
                e.getPlayer().teleport(loc);
                e.getPlayer().sendMessage("§aVocê entrou em modo espectador, para sair digite /sair");
            }
        }
    }
}
