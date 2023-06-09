package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import me.flugel.vuccanegladiador.objetos.Finalistas;
import me.flugel.vuccanegladiador.objetos.Participantes;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitScheduler;
import yclans.api.yClansAPI;
import yclans.models.Clan;
import yclans.models.ClanPlayer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Morte implements Listener {
    @EventHandler
    public void aoMorrer(PlayerDeathEvent e) {
        if (VuccaneGladiador.getInstance().start) {
            if (VuccaneGladiador.getInstance().killmensage) {
                if (e.getEntity().getKiller() instanceof Player) {
                    if (VuccaneGladiador.getInstance().getManagerParticipante().getByPlayer(e.getEntity().getPlayer()) != null) {
                        if (checkKiller(e.getEntity().getKiller())) {
                            if (checkDeath(e.getEntity().getPlayer())) {
                                yClansAPI clans = yClansAPI.yclansapi;
                                ClanPlayer cKiller = clans.getPlayer(e.getEntity().getKiller());
                                ClanPlayer cDeath = clans.getPlayer(e.getEntity().getPlayer());

                                String cKillerTag = cKiller.getClanTag();
                                String cDeathTag = cDeath.getClanTag();

                                e.setDeathMessage(VuccaneGladiador.getInstance().deathMensage
                                        .replace("&", "§")
                                        .replace("(DeathPlayer)", e.getEntity().getPlayer().getName())
                                        .replace("(PlayerClanDeath)", cDeathTag)
                                        .replace("(playerKiller)", e.getEntity().getKiller().getName())
                                        .replace("(PlayerClanKiller)", cKillerTag));


                                Participantes matador = getParticipante(e.getEntity().getKiller());
                                matador.setKill(matador.getKill() + 1);

                                VuccaneGladiador.getInstance().espectador.add(e.getEntity().getPlayer());
                                e.getEntity().getPlayer().setGameMode(GameMode.SPECTATOR);

                                if (VuccaneGladiador.getInstance().sounds)
                                    e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());

                                if (checkClan(cDeathTag)) {
                                    for (Player ons : Bukkit.getOnlinePlayers()) {
                                        ons.sendMessage(VuccaneGladiador.getInstance().ClanEliminated.replace("(clan)", cDeathTag));
                                        ons.playSound(ons.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
                                    }
                                }
                                if (hasTwoClan()) {
                                    for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                                        if(!VuccaneGladiador.getInstance().espectador.contains(participantes.getPlayer())){
                                            ClanPlayer cClan = clans.getPlayer(participantes.getPlayer());
                                            cKillerTag = cClan.getClanTag();
                                            break;
                                        }
                                    }
                                    VuccaneGladiador.getInstance().acontecendo = false;
                                    Clan winner = clans.getClan(cKillerTag);

                                    for (Participantes playuer : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                                        ClanPlayer cp = clans.getPlayer(playuer.getPlayer());
                                        if(cp.getClanTag().equals(cKillerTag)){
                                            Finalistas finalistas = new Finalistas(playuer.getPlayer(), playuer.getKill());
                                            VuccaneGladiador.getInstance().getManagerFinalistas().saveFinalistas(finalistas);
                                        }
                                    }

                                    String pgetName1, pgetName2, pgetName3, pgetKill1, pgetKill2, pgetKill3;

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
                                    for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                                        participantes.getPlayer().teleport(spawn);
                                        participantes.getPlayer().sendMessage("§aVocê foi teleportado para o spawn!");
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
                    }
                } else {
                    if (VuccaneGladiador.getInstance().getManagerParticipante().getByPlayer(e.getEntity().getPlayer()) != null) {
                        yClansAPI clans = yClansAPI.yclansapi;
                        ClanPlayer cDeath = clans.getPlayer(e.getEntity().getPlayer());
                        String cDeathTag = cDeath.getClanTag();

                        VuccaneGladiador.getInstance().espectador.add(e.getEntity().getPlayer());
                        e.getEntity().getPlayer().setGameMode(GameMode.SPECTATOR);
                        e.setDeathMessage("§4" + e.getEntity().getPlayer().getName() + " §cmorreu por causas naturais");

                        if (VuccaneGladiador.getInstance().sounds)
                            e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());

                        if (checkClan(cDeathTag)) {
                            for (Player ons : Bukkit.getOnlinePlayers()) {
                                ons.sendMessage(VuccaneGladiador.getInstance().ClanEliminated.replace("(clan)", cDeathTag));
                                ons.playSound(ons.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
                            }
                        }
                        if (hasTwoClan()) {
                            String cKillerTag = null;
                            for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                                if(!VuccaneGladiador.getInstance().espectador.contains(participantes)){
                                    ClanPlayer cClan = clans.getPlayer(participantes.getPlayer());
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
                            for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                                participantes.getPlayer().teleport(spawn);
                                participantes.getPlayer().sendMessage("§aVocê foi teleportado para o spawn!");
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
            } else {
                if (VuccaneGladiador.getInstance().getManagerParticipante().getByPlayer(e.getEntity().getPlayer()) != null) {
                    yClansAPI clans = yClansAPI.yclansapi;
                    ClanPlayer cDeath = clans.getPlayer(e.getEntity().getPlayer());
                    String cDeathTag = cDeath.getClanTag();

                    VuccaneGladiador.getInstance().espectador.add(e.getEntity().getPlayer());
                    e.getEntity().getPlayer().setGameMode(GameMode.SPECTATOR);

                    if (VuccaneGladiador.getInstance().sounds)
                        e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());

                    if (checkClan(cDeathTag)) {
                        for (Player ons : Bukkit.getOnlinePlayers()) {
                            ons.sendMessage(VuccaneGladiador.getInstance().ClanEliminated.replace("(clan)", cDeathTag));
                            ons.playSound(ons.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
                        }
                    }
                    if (hasTwoClan()) {
                        String cKillerTag = null;
                        for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                            if(!VuccaneGladiador.getInstance().espectador.contains(participantes)){
                                ClanPlayer cClan = clans.getPlayer(participantes.getPlayer());
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
                        for (Participantes participantes : VuccaneGladiador.getInstance().getManagerParticipante().getParticipante()) {
                            participantes.getPlayer().teleport(spawn);
                            participantes.getPlayer().sendMessage("§aVocê foi teleportado para o spawn!");
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
        }
    }

    public boolean checkKiller(Player killer) {
        yClansAPI clans = yClansAPI.yclansapi;
        ClanPlayer cKiller = clans.getPlayer(killer);
        if (cKiller.hasClan())
            return true;

        return false;
    }

    public boolean checkDeath(Player death) {
        yClansAPI clans = yClansAPI.yclansapi;
        ClanPlayer cKiller = clans.getPlayer(death);
        if (cKiller.hasClan())
            return true;

        return false;
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

    public Participantes getParticipante(Player player) {
        return VuccaneGladiador.getInstance().getManagerParticipante().getByPlayer(player);
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

        config2.set("Gladiador", owner.getName());
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
