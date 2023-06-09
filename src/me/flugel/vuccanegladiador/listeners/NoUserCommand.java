package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import me.flugel.vuccanegladiador.objetos.Participantes;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

public class NoUserCommand implements Listener {
    @EventHandler
    public void noCommand(PlayerCommandPreprocessEvent e){
        if(VuccaneGladiador.getInstance().start){
            if(VuccaneGladiador.getInstance().getManagerParticipante().getByPlayer(e.getPlayer()) != null || VuccaneGladiador.getInstance().espectador.contains(e.getPlayer())){
                String[] args = e.getMessage().split(" ");
                if(args[0].equalsIgnoreCase("/sair")){
                    e.setCancelled(false);
                    e.getPlayer().sendMessage("§aVocê saiu do evento GLADIADOR!");
                    e.getPlayer().teleport(VuccaneGladiador.unserialize(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("locSpawn")));
                    VuccaneGladiador.getInstance().espectador.remove(e.getPlayer());
                    Participantes participantes = VuccaneGladiador.getInstance().getManagerParticipante().getByPlayer(e.getPlayer());
                    e.getPlayer().setGameMode(GameMode.SURVIVAL);
                    VuccaneGladiador.getInstance().getManagerParticipante().removerParticipante(participantes);
                    if(VuccaneGladiador.getInstance().kit){
                        if(!VuccaneGladiador.getInstance().espectador.contains(e.getPlayer()))
                            e.getPlayer().getInventory().clear();
                        e.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
                        e.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
                        e.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
                        e.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
                    }
                }else{
                    e.setCancelled(true);
                    e.getPlayer().sendMessage("§cComando negado, para executar outros comandos você deve sair do evento! /sair");
                }
            }
        }
    }
}
