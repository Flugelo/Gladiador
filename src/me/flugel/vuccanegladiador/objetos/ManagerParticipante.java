package me.flugel.vuccanegladiador.objetos;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ManagerParticipante {
    public List<Participantes> participantes;

    public ManagerParticipante(List<Participantes> participantes) {
        this.participantes = participantes;
    }

    public ManagerParticipante() {
        this.participantes = new ArrayList<>();
    }

    public List<Participantes> getParticipante() {
        return  participantes;
    }

    public Participantes getByPlayer(Player player){
        return participantes.stream().filter(part -> part.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public void saveParticipante(Participantes gladiador){
        participantes.add(gladiador);
    }

    public void removerParticipante(Participantes gladiador){
        participantes.remove(gladiador);
    }
}
