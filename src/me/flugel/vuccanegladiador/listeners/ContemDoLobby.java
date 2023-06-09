package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import me.flugel.vuccanegladiador.objetos.Participantes;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ContemDoLobby extends BukkitRunnable {
    int contador = VuccaneGladiador.getInstance().getConfig().getInt("Config.tempo_no_lobby");
    @Override
    public void run() {
        if(contador > 0){
            contador--;
            if(contador == 30){
                for (Participantes event : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                    event.getPlayer().sendMessage("");
                    event.getPlayer().sendMessage("§aPvP começara em "+ contador+" segundos");
                    event.getPlayer().sendMessage("");
                }
            }
            if(contador == 10){
                for (Participantes event : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                    event.getPlayer().sendMessage("");
                    event.getPlayer().sendMessage("§aPvP começara em "+ contador+" segundos");
                    event.getPlayer().sendMessage("");
                }
            }
            if(contador < 6){
                if(contador != 0){
                    for (Participantes event : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                        event.getPlayer().sendTitle("§aCOMEÇANDO EM:" , "§5" + contador);
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.NOTE_PLING,1,1);
                    }
                }
                if(contador == 0){
                    for (Participantes event : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                        event.getPlayer().sendTitle("§aCOMEÇANDO EM:" , "§5" + contador);
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ANVIL_LAND,1,1);
                    }
                    this.cancel();
                    for (Participantes event : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                        event.getPlayer().sendTitle("§aBATALHEMM" , "§7O--}=====>");
                    }
                    BukkitTask task = new StartEvent().runTaskTimer(VuccaneGladiador.getInstance(),0,20);
                    VuccaneGladiador.getInstance().bukkitTaskHashMap.put("Start", task.getTaskId());
                    VuccaneGladiador.getInstance().pvp = true;
                }
            }
        }
    }
}
