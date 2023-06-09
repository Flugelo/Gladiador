package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

public class DiminuirBorda extends BukkitRunnable {
    World world = VuccaneGladiador.getInstance().unserialize(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("locCentro")).getWorld();

    double anterior;
    int peneira;
    int agora;
    double contador = 0;

    public DiminuirBorda(int anterior, int agora) {
        this.anterior = anterior;
        this.agora = agora;
        this.peneira = anterior;

    }

    @Override
    public void run() {
        if (contador < dif(peneira, agora)) {
            contador = contador + 0.1;
            anterior = anterior - 0.1;
            WorldBorder borda = world.getWorldBorder();
            borda.setSize(anterior);

        } else {
            this.cancel();
        }
    }
    public int dif(int antes, int agora){
        return antes - agora;
    }
}
