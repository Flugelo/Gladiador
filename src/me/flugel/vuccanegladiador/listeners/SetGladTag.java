package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SetGladTag implements Listener {
    @EventHandler
    public void setTag(AsyncPlayerChatEvent e){
        if(VuccaneGladiador.getInstance().setTag.containsKey("gladiador")){
            e.setCancelled(true);
            String mensagem = e.getMessage();
            if(!mensagem.contains(" ")){
                if(!mensagem.equalsIgnoreCase("cancelar")){
                    String message = e.getMessage();
                    VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().set("tagGlad", message);
                    VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                    e.getPlayer().sendMessage("§aTag alterada com sucesso!!");
                    if(VuccaneGladiador.getInstance().setTag.containsValue(e.getPlayer())){
                        VuccaneGladiador.getInstance().setTag.clear();
                    }
                }
            }else{
                e.getPlayer().sendMessage("§cA tag não pode conter ESPAÇOS! Digite novamente a tag");
                e.setCancelled(true);
            }
        }
        if(VuccaneGladiador.getInstance().setTag.containsKey("matador")){
            e.setCancelled(true);
            String mensagem = e.getMessage();
            if(!mensagem.contains(" ")){
                if(!mensagem.equalsIgnoreCase("cancelar")){
                    String message = e.getMessage();
                    VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().set("tagMatador", message);
                    VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                    VuccaneGladiador.getInstance().StringtagMat = VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("tagMatador").replace("&","§");
                    e.getPlayer().sendMessage("§aTag alterada com sucesso!!");
                    if(VuccaneGladiador.getInstance().setTag.containsValue(e.getPlayer())){
                        VuccaneGladiador.getInstance().setTag.clear();
                    }
                }
            }else{
                e.getPlayer().sendMessage("§cA tag não pode conter ESPAÇOS! Digite novamente a tag");
                e.setCancelled(true);
            }
        }
    }
}
