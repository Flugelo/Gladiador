package me.flugel.vuccanegladiador.api;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import me.flugel.vuccanegladiador.objetos.Finalistas;
import me.flugel.vuccanegladiador.objetos.Participantes;
import yclans.api.yClansAPI;
import yclans.models.ClanPlayer;

import java.util.ArrayList;
import java.util.Arrays;

public class TopKills {
    public String getRank(int rank) {
        String Final = "(Jogador) ( Kill's )";
        try {
            ArrayList<Double> valores = new ArrayList<Double>();
            for (Finalistas player : VuccaneGladiador.getInstance().getManagerFinalistas().getFinalistas()) {
                    valores.add((double) player.getKills());
            }
            String nick = "N.A";
            Double[] listaValores = valores.toArray(new Double[valores.size()]);
            Arrays.sort(listaValores);
            for (Finalistas player : VuccaneGladiador.getInstance().getManagerFinalistas().getFinalistas()) {
                    if (player.getKills() == listaValores[listaValores.length - (rank)]) {
                        nick = player.getPlayer().getName();
                    }
            }
            // Final = String.valueOf(nick + ">" + listaValores[listaValores.length - (rank)]);
            Final = String.valueOf( nick + " ( " + listaValores[listaValores.length - (rank)] + " )");
        } catch (Exception e) {
        }
        return Final;
    }
}
