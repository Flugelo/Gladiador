package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import me.flugel.vuccanegladiador.objetos.Participantes;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import yclans.api.yClansAPI;
import yclans.models.ClanPlayer;

public class Contador extends BukkitRunnable {
    int tempo =35;

    @Override
    public void run() {
        if(tempo > 0){
            tempo--;
            switch (tempo){
                case 180:
                    for(Player online : Bukkit.getOnlinePlayers()){
                        online.sendMessage(String.valueOf(VuccaneGladiador.getInstance().divu).replace("(tempo)", String.valueOf(tempo))
                                .replace("[", "")
                                .replace("]", "")
                                .replace(",", "\n"));
                    }
                    break;
                case 150:
                    for(Player online : Bukkit.getOnlinePlayers()){
                        online.sendMessage(String.valueOf(VuccaneGladiador.getInstance().divu).replace("(tempo)", String.valueOf(tempo))
                                .replace("[", "")
                                .replace("]", "")
                                .replace(",", "\n"));
                    }
                    break;
                case 120:
                    for(Player online : Bukkit.getOnlinePlayers()){
                        online.sendMessage(String.valueOf(VuccaneGladiador.getInstance().divu).replace("(tempo)", String.valueOf(tempo))
                                .replace("[", "")
                                .replace("]", "")
                                .replace(",", "\n"));
                    }
                    break;
                case 90:
                    for(Player online : Bukkit.getOnlinePlayers()){
                        online.sendMessage(String.valueOf(VuccaneGladiador.getInstance().divu).replace("(tempo)", String.valueOf(tempo))
                                .replace("[", "")
                                .replace("]", "")
                                .replace(",", "\n"));
                    }
                    break;
                case 60:
                    for(Player online : Bukkit.getOnlinePlayers()){
                        online.sendMessage(String.valueOf(VuccaneGladiador.getInstance().divu).replace("(tempo)", String.valueOf(tempo))
                                .replace("[", "")
                                .replace("]", "")
                                .replace(",", "\n"));
                    }
                    break;
                case 30:
                    for(Player online : Bukkit.getOnlinePlayers()){
                        online.sendMessage(String.valueOf(VuccaneGladiador.getInstance().divu).replace("(tempo)", String.valueOf(tempo))
                                .replace("[", "")
                                .replace("]", "")
                                .replace(",", "\n"));
                    }
                    break;
            }
        }else{
            this.cancel();
            int quantidadeNecessaria = VuccaneGladiador.getInstance().getConfig().getInt("Config.quantidade_clan");
            try {
                if(ClansParticipando() >= quantidadeNecessaria){
                    Location loc = VuccaneGladiador.unserialize(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("locCentro"));
                    WorldBorder borda = loc.getWorld().getWorldBorder();
                    borda.setCenter(loc.getX(), loc.getZ());
                    borda.setWarningDistance(5);
                    borda.setDamageAmount(20);
                    borda.setSize(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getInt("tamanho"));
                    VuccaneGladiador.getInstance().acontecendo = true;
                    new ContemDoLobby().runTaskTimer(VuccaneGladiador.getInstance(),0,20);
                    for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                        participantes.getPlayer().teleport(loc);
                    }
                }else{
                    Location loc = VuccaneGladiador.unserialize(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("locSpawn"));
                    if(VuccaneGladiador.getInstance().kit){
                        for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                            participantes.getPlayer().getInventory().clear();
                            participantes.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
                            participantes.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
                            participantes.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
                            participantes.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
                            participantes.getPlayer().teleport(loc);
                        }
                    }
                    for (Player ons : Bukkit.getOnlinePlayers()) {
                        ons.sendMessage("");
                        ons.sendMessage("§cEvento Gladidor cancelado!");
                        ons.sendMessage("§4MOTIVO: §cPoucos clans participando no momento");
                        ons.sendMessage("§cÉ preciso uma quantia minima de: §6" + quantidadeNecessaria + " §cclans");
                        ons.sendMessage("§cClans no momento: §6" + ClansParticipando());
                        ons.sendMessage("");
                    }


                    VuccaneGladiador.getInstance().getManagerParticipante().getParticipante().clear();
                    VuccaneGladiador.getInstance().start = false;
                    VuccaneGladiador.getInstance().acontecendo = false;

                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    public int ClansParticipando() {
        yClansAPI clans = yClansAPI.yclansapi;
        String cCheck1 = null;
        int contagem = 0;

        for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
            cCheck1 = clans.getPlayer(participantes.getPlayer()).getClanTag();
            contagem++;
            break;
        }
        ClanPlayer cCheck2;
        for (Participantes player : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
            cCheck2 = clans.getPlayer(player.getPlayer());
            String tag = cCheck2.getClanTag();
            if (!tag.equalsIgnoreCase(cCheck1)) {
                contagem++;
            }
        }
        return  contagem;
    }
}
