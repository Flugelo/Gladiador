package me.flugel.vuccanegladiador.objetos;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ManagerFinalistas {
    public List<Finalistas> finalistas;

    public ManagerFinalistas(List<Finalistas> participantes) {
        this.finalistas = participantes;
    }

    public ManagerFinalistas() {
        this.finalistas = new ArrayList<>();
    }

    public List<Finalistas> getFinalistas() {
        return  finalistas;
    }

    public Finalistas getByPlayer(Player player){
        return finalistas.stream().filter(part -> part.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public void saveFinalistas(Finalistas finalistas){
        this.finalistas.add(finalistas);
    }

    public void removerFinalistas(Finalistas finalistas){
        this.finalistas.remove(finalistas);
    }
}
