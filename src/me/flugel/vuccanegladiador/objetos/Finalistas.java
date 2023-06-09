package me.flugel.vuccanegladiador.objetos;

import org.bukkit.entity.Player;

public class Finalistas {
    private Player player;
    private int kills;

    public Finalistas(Player player, int kills) {
        this.player = player;
        this.kills = kills;
    }

    public Player getPlayer() {
        return player;
    }

    public int getKills() {
        return kills;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }
}
