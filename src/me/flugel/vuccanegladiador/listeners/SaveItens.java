package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import me.flugel.vuccanegladiador.objetos.Arquivo;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Arrays;

public class SaveItens implements Listener {
    @EventHandler
    public void saveInv(InventoryCloseEvent e) {
       if (e.getInventory().getTitle().equals("§5         Setar os premios")) {
            Arquivo arquivo = VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao");
            FileConfiguration config = arquivo.getConfig();
            String base64 = VuccaneGladiador.getInstance().setBase64(e.getInventory().getContents());
            config.set("itensPremios", base64);
            VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
            e.getPlayer().sendMessage("§aItens da premiação setadas com sucesso!");
        }
    }
}
