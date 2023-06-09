package me.flugel.vuccanegladiador.comandos;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import me.flugel.vuccanegladiador.gui.GladiadorGui;
import me.flugel.vuccanegladiador.objetos.Participantes;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gladiador implements CommandExecutor {

    VuccaneGladiador main = VuccaneGladiador.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if(!(sender instanceof Player)){
            return true;
        }

        Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("gladiador")){
            if(p.hasPermission("gladiador.staff")){
                main.getGladiadorGui().invAdmin(p); p.playSound(p.getLocation(), Sound.NOTE_PLING,1,1);
                return true;
            }
            main.getGladiadorGui().invPlayer(p);

        }
        if(cmd.getName().equalsIgnoreCase("/sair")){
            if(VuccaneGladiador.getInstance().getEspectador().contains(p) || VuccaneGladiador.getInstance().getEspectador().contains(p)){
                p.sendMessage("§aVocê saiu do evento!");
                p.setGameMode(GameMode.SURVIVAL);
                if(main.getEspectador().contains(p))
                    main.getEspectador().remove(p);

                if(main.getManagerParticipante().getByPlayer(p) != null){
                    Participantes player = main.getManagerParticipante().getByPlayer(p);
                    main.getManagerParticipante().removerParticipante(player);
                }
            }
        }
        return false;
    }
}
