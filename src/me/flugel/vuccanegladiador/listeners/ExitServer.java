package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import me.flugel.vuccanegladiador.objetos.Finalistas;
import me.flugel.vuccanegladiador.objetos.Participantes;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import yclans.api.yClansAPI;
import yclans.models.Clan;
import yclans.models.ClanPlayer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExitServer implements Listener {
    @EventHandler
    public void aoSair(PlayerQuitEvent e) {
        if (VuccaneGladiador.getInstance().getManagerParticipante().getByPlayer(e.getPlayer()) != null) {
            e.setQuitMessage("§c" + e.getPlayer().getName() + " §edesistiu do evento");
            e.getPlayer().getInventory().clear();
            Participantes participantes = VuccaneGladiador.getInstance().getManagerParticipante().getByPlayer(e.getPlayer());
            VuccaneGladiador.getInstance().getManagerParticipante().removerParticipante(participantes);
            e.getPlayer().teleport(VuccaneGladiador.unserialize(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("locSpawn")));
            yClansAPI clans = yClansAPI.yclansapi;
            ClanPlayer cDeath = clans.getPlayer(e.getPlayer());
            String cDeathTag = cDeath.getClanTag();

            if(VuccaneGladiador.getInstance().kit){
                e.getPlayer().getInventory().clear();
                e.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
                e.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
                e.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
                e.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
            }

            if (checkClan(cDeathTag)) {
                for (Player ons : Bukkit.getOnlinePlayers()) {
                    ons.sendMessage(VuccaneGladiador.getInstance().ClanEliminated.replace("(clan)", cDeathTag));
                    ons.playSound(ons.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
                }
            }
            if (checkClan(cDeathTag)) {
                for (Player ons : Bukkit.getOnlinePlayers()) {
                    ons.sendMessage(VuccaneGladiador.getInstance().ClanEliminated.replace("(clan)", cDeathTag));
                    ons.playSound(ons.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
                }
            }
            if (hasTwoClan()) {
                String cKillerTag = null;
                for (Participantes participantes2 : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                    if(!VuccaneGladiador.getInstance().espectador.contains(participantes2.getPlayer())){
                        ClanPlayer cClan = clans.getPlayer(participantes2.getPlayer());
                        cKillerTag = cClan.getClanTag();
                        break;
                    }
                }
                VuccaneGladiador.getInstance().acontecendo = false;

                for (Participantes playuer : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                    ClanPlayer cp = clans.getPlayer(playuer.getPlayer());
                    if(cp.getClanTag().equals(cKillerTag)){
                        Finalistas finalistas = new Finalistas(playuer.getPlayer(), playuer.getKill());
                        VuccaneGladiador.getInstance().getManagerFinalistas().saveFinalistas(finalistas);
                    }
                }
                Clan winner = clans.getClan(cKillerTag);

                String pgetName1, pgetName2, pgetName3,pgetKill1, pgetKill2, pgetKill3;
                String[] split = VuccaneGladiador.getInstance().getTopKills().getRank(1).split(" ");
                pgetName1 = split[0];
                pgetKill1 = split[2];

                split = VuccaneGladiador.getInstance().getTopKills().getRank(2).split(" ");
                pgetName2 = split[0];
                pgetKill2 = split[2];

                split = VuccaneGladiador.getInstance().getTopKills().getRank(3).split(" ");
                pgetName3 = split[0];
                pgetKill3 = split[2];

                String peneira = VuccaneGladiador.getInstance().getConfig().getStringList("Mensagens.clanWinner").stream().map(l -> l.replace("&", "§")
                        .replace("(clanWinner)", winner.getName())).collect(Collectors.toList()).toString();

                String menWinner = peneira.replace("{matador1}", pgetName1)
                        .replace("{matador2}", pgetName2)
                        .replace("{matador3}", pgetName3)
                        .replace("{kill1}", pgetKill1)
                        .replace("{kill2}", pgetKill2)
                        .replace("{kill3}", pgetKill3)
                        .replace(",", "\n")
                        .replace("[", "")
                        .replace("]", "")
                        .replace("(dinheiro)", NumberFormat.getCurrencyInstance().format(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getInt("dinheiro")));
                for (Player on : Bukkit.getOnlinePlayers()) {
                    on.sendMessage(menWinner);
                }
                VuccaneGladiador.getInstance().start = false;
                if (VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getBoolean("premio")) {
                    Player ownerClan = Bukkit.getPlayerExact(winner.getLeader());
                    VuccaneGladiador.getInstance().ganhador.put(ownerClan, winner.getTag());
                    if (ownerClan != null) {
                        ownerClan.sendMessage("§aVocê pode resgatar o premio para o clan na aba /gladiador");
                    }
                }
                Player ownerClan = Bukkit.getPlayerExact(winner.getLeader());
                String topKill = VuccaneGladiador.getInstance().getTopKills().getRank(1);

                VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().set("Gladiador", ownerClan.getName());
                VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().set("Matador", topKill);
                VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();

                if(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getInt("dinheiro") != 0)
                    setCoins(winner.getTag(),VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getInt("dinheiro"));

                setData(winner.getLeader(), topKill);

                Location spawn = VuccaneGladiador.unserialize(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("locSpawn"));
                for (Player player : VuccaneGladiador.getInstance().espectador) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.teleport(spawn);
                    player.sendMessage("§aVocê foi teleportado para o spawn!");
                }
                VuccaneGladiador.getInstance().espectador.clear();
                for (Participantes participantes1 : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                    participantes1.getPlayer().teleport(spawn);
                    participantes1.getPlayer().sendMessage("§aVocê foi teleportado para o spawn!");
                }
                VuccaneGladiador.getInstance().getManagerParticipante().getParticipante().clear();

                if(VuccaneGladiador.getInstance().bukkitTaskHashMap.containsKey("Start")){
                    Bukkit.getScheduler().cancelTask(VuccaneGladiador.getInstance().bukkitTaskHashMap.get("Start"));
                    VuccaneGladiador.getInstance().bukkitTaskHashMap.clear();
                }
                WorldBorder borda = VuccaneGladiador.getInstance().loc.getWorld().getWorldBorder();
                borda.reset();
            }
        }
    }
    public boolean checkClan(String ClanTag) {
        yClansAPI clans = yClansAPI.yclansapi;
        ClanPlayer cCheck;
        int contagem = 0;
        for (Participantes player : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
            if(!VuccaneGladiador.getInstance().espectador.contains(player.getPlayer())){
                cCheck = clans.getPlayer(player.getPlayer());
                String tag = cCheck.getClanTag();
                if (ClanTag.equalsIgnoreCase(tag))
                    contagem++;
            }
        }

        if (contagem > 0) {
            return false;
        } else {
            return true;
        }

    }

    public boolean hasTwoClan() {
        yClansAPI clans = yClansAPI.yclansapi;
        String cCheck1 = null;

        for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
            if(!VuccaneGladiador.getInstance().espectador.contains(participantes.getPlayer())){
                cCheck1 = clans.getPlayer(participantes.getPlayer()).getClanTag();
                break;
            }

        }
        ClanPlayer cCheck2;
        for (Participantes player : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
            if(!VuccaneGladiador.getInstance().espectador.contains(player.getPlayer())){
                cCheck2 = clans.getPlayer(player.getPlayer());
                String tag = cCheck2.getClanTag();
                if (!tag.equalsIgnoreCase(cCheck1)) {
                    return false;
                }
            }
        }
        return true;

    }

    public void setData(String lider, String topKill) {
        FileConfiguration config = VuccaneGladiador.getInstance().getManagerArquivos().get("Data").getConfig();
        FileConfiguration config2 = VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig();

        yClansAPI clans = yClansAPI.yclansapi;
        ClanPlayer pClan = clans.getPlayer(lider);
        String tagClan = pClan.getClanTag();
        String nameClan = pClan.getClan().getName();

        Player owner = Bukkit.getPlayerExact(lider);
        String[] split = topKill.split(" ");
        String peneira = split[0];

        VuccaneGladiador.getInstance().hashTags.clear();
        List<String> tagsMatGlad = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        if(owner.getName().equals(peneira)){
            tagsMatGlad.add("Matador");
            tagsMatGlad.add("Gladiador");
            VuccaneGladiador.getInstance().hashTags.put(owner.getName(), tagsMatGlad);
        }else{
            tagsMatGlad.add("Gladiador");
            VuccaneGladiador.getInstance().hashTags.put(owner.getName(), tagsMatGlad);
            tags.add("Matador");
            VuccaneGladiador.getInstance().hashTags.put(peneira, tags);
        }
        config2.set("Gladiado", owner.getName());
        config2.set("Matador", peneira);

        if (!config.contains(tagClan)) {
            config.createSection(tagClan);
            config.getConfigurationSection(tagClan).set("Nome:", nameClan);
            config.getConfigurationSection(tagClan).set("Lider:", lider);
            config.getConfigurationSection(tagClan).set("Ganhadas:", 1);
            config.getConfigurationSection(tagClan).set("TopKill:", topKill);
            VuccaneGladiador.getInstance().getManagerArquivos().get("Data").saveConfig();
        } else {
            int antes = config.getConfigurationSection(tagClan).getInt("Ganhadas:");
            config.getConfigurationSection(tagClan).set("Lider:", lider);
            config.getConfigurationSection(tagClan).set("Ganhadas:", antes + 1);
            config.getConfigurationSection(tagClan).set("TopKill:", topKill);
            VuccaneGladiador.getInstance().getManagerArquivos().get("Data").saveConfig();
        }
    }
    public void setCoins(String ClanTag, int valoradd){
        yClansAPI clans = yClansAPI.yclansapi;
        Double antes = clans.getClan(ClanTag).getMoney();
        clans.getClan(ClanTag).setMoney(antes + valoradd);
    }
}