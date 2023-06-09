package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class SaveInvKit implements Listener {
    @EventHandler
    public void setItensKit(InventoryCloseEvent e){
        if (e.getInventory().getTitle().equals("§a Setar inventario do jogador")) {
            String base64 = VuccaneGladiador.getInstance().setBase64(e.getInventory().getContents());
            VuccaneGladiador.getInstance().setInv.put("Inv", base64);
            e.getPlayer().sendMessage("§aInventario setado com sucesso");
            VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().set("kitInv", base64);
            VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
        }
        if (e.getInventory().getTitle().equals("§a Setar armadura do jogador")) {
            String base64 = VuccaneGladiador.getInstance().setBase64(e.getInventory().getContents());
            VuccaneGladiador.getInstance().setKit.put("Armor",base64);
            e.getPlayer().sendMessage("§aArmadura setada com sucesso");
            VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().set("KitArmor", base64);
            VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
        }
    }
}
