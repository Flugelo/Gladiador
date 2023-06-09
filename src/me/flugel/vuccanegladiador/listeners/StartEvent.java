package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import me.flugel.vuccanegladiador.objetos.Participantes;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

public class StartEvent extends BukkitRunnable {
    int tempo = VuccaneGladiador.getInstance().tempo;
    int contagem1 = (tempo*75) / 100;
    int contagem2 = (tempo*50) / 100;
    int contagem3 = (tempo*25) / 100;

    int borda = VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getInt("tamanho");
    int borda1 = (borda * 75) / 100;
    int borda2 = (borda * 50) / 100;
    int borda3 = (borda * 25) / 100;

    @Override
    public void run() {
        if(tempo > 0){
            tempo--;
            if(tempo == contagem1 +10){
                for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                    participantes.getPlayer().sendMessage("§cRestam §410 segundo §cpara borda diminuir");
                    participantes.getPlayer().playSound(participantes.getPlayer().getLocation(), Sound.ANVIL_LAND,1,1);
                }
            }

            if(tempo == contagem1){
                for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                    participantes.getPlayer().sendMessage("§cCUIDADO! A borda esta diminuindo!!!");
                    participantes.getPlayer().playSound(participantes.getPlayer().getLocation(), Sound.ANVIL_LAND,1,1);
                }
                new DiminuirBorda(borda, borda1).runTaskTimer(VuccaneGladiador.getInstance(), 0, 1);
            }

            if(tempo == contagem2 +10){
                for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                    participantes.getPlayer().sendMessage("§cRestam §410 segundo §cpara borda diminuir");
                    participantes.getPlayer().playSound(participantes.getPlayer().getLocation(), Sound.ANVIL_LAND,1,1);
                }

            }

            if(tempo == contagem2){
                for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                    participantes.getPlayer().sendMessage("§cCUIDADO! A borda esta diminuindo!!!");
                    participantes.getPlayer().playSound(participantes.getPlayer().getLocation(), Sound.ANVIL_LAND,1,1);
                }
                new DiminuirBorda(borda1, borda2).runTaskTimer(VuccaneGladiador.getInstance(), 0, 1);
            }

            if(tempo == contagem3 +10){
                for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                    participantes.getPlayer().sendMessage("§cRestam §410 segundo §cpara borda diminuir");
                    participantes.getPlayer().playSound(participantes.getPlayer().getLocation(), Sound.ANVIL_LAND,1,1);
                }

            }
            if(tempo == contagem3){
                for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                    participantes.getPlayer().sendMessage("§cCUIDADO! A borda esta diminuindo!!!");
                    participantes.getPlayer().playSound(participantes.getPlayer().getLocation(), Sound.ANVIL_LAND,1,1);
                }
                new DiminuirBorda(borda2, borda3).runTaskTimer(VuccaneGladiador.getInstance(), 0, 1);
            }
        }else{
            this.cancel();
            if(VuccaneGladiador.getInstance().bukkitTaskHashMap.containsKey("Start"))
                VuccaneGladiador.getInstance().bukkitTaskHashMap.clear();
        }

    }
}
