package me.flugel.vuccanegladiador.objetos;

import org.bukkit.entity.Player;

public class Participantes{

    private Player player;
    private int kill;

    public Participantes(Player player, int kill) {
        this.player = player;
        this.kill = kill;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public Player getPlayer() {
        return player;
    }

    public int getKill() {
        return kill;
    }
}
