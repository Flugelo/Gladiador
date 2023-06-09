package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import me.flugel.vuccanegladiador.objetos.Arquivo;
import me.flugel.vuccanegladiador.objetos.Participantes;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import yclans.api.yClansAPI;
import yclans.models.ClanPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Guis implements Listener {

    Arquivo arquivo = VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao");
    FileConfiguration config = arquivo.getConfig();

    @EventHandler
    public void aoclicar(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase("§5          Evento   Gladiador")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    Player p = (Player) e.getWhoClicked();
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Configurações")) {
                        VuccaneGladiador.getInstance().getGladiadorGui().invConfig(p);
                        p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Check")) {
                        VuccaneGladiador.getInstance().getGladiadorGui().invCheck(p);
                        p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().contains("kit")){
                        VuccaneGladiador.getInstance().getGladiadorGui().opcoes(p);
                        p.updateInventory();
                        p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Entrar")) {
                        yClansAPI clans = yClansAPI.yclansapi;
                        ClanPlayer pClan = clans.getPlayer(p);
                        if (pClan.hasClan()) {
                            if (VuccaneGladiador.getInstance().start) {
                                if (VuccaneGladiador.getInstance().acontecendo == false) {
                                    if(VuccaneGladiador.getInstance().kit == false){
                                        if (VuccaneGladiador.getInstance().getManagerParticipante().getByPlayer(p) == null) {
                                            p.closeInventory();
                                            p.teleport(VuccaneGladiador.unserialize(config.getString("locLobby")));
                                            Participantes participantes = new Participantes(p, 0);
                                            VuccaneGladiador.getInstance().getManagerParticipante().saveParticipante(participantes);
                                        } else {
                                            p.sendMessage("§cVocê já esta participando do evento!");
                                            p.closeInventory();
                                            return;
                                        }
                                    }else{
                                        if(isEmpty(p)){
                                            p.sendMessage("§cInv limpo");
                                            if(isEmptyArmor(p)){
                                                p.sendMessage("§carmor limpo");
                                                try{
                                                    p.sendMessage("§csetando armor");
                                                    p.teleport(VuccaneGladiador.unserialize(config.getString("locLobby")));
                                                    Participantes participantes = new Participantes(p, 0);
                                                    VuccaneGladiador.getInstance().getManagerParticipante().saveParticipante(participantes);
                                                    p.getInventory().setContents(VuccaneGladiador.getInstance().getBase64(VuccaneGladiador.getInstance().setInv.get("Inv")));
                                                    p.getInventory().setArmorContents(VuccaneGladiador.getInstance().getBase64(VuccaneGladiador.getInstance().setKit.get("Armor")));
                                                    p.closeInventory();
                                                    p.sendMessage("§aKit setado com sucesso!");
                                                }catch (Exception ex1){
                                                }
                                            }else{
                                                p.sendMessage("§cRetire a armadura para poder entrar no evento GLADIADOR.");
                                            }
                                        }else{
                                            p.sendMessage("§cEvento gladiador esta em configuração KIT SETADO. Para poder participar limpe seu inventario");
                                            return;
                                        }
                                    }
                                } else {
                                    if (VuccaneGladiador.getInstance().espectar) {
                                        p.setGameMode(GameMode.SPECTATOR);
                                        p.sendMessage("§aEvento ja foi fechado, você foi enviado como espectador!");
                                        VuccaneGladiador.getInstance().espectador.add(p);
                                        p.teleport(VuccaneGladiador.unserialize(config.getString("locCentro")));
                                        p.closeInventory();
                                    }
                                    p.sendMessage("§cO Evento ja esta acontecendo, modo espectador esta desativado");
                                    p.closeInventory();
                                    return;
                                }
                            } else {
                                p.sendMessage("§cO Evento no momento não esta ocorrendo!");
                                return;
                            }
                        } else {
                            p.sendMessage("§cVocê não esta em clan para participar");
                            return;
                        }
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Setar")) {
                        VuccaneGladiador.getInstance().getGladiadorGui().invSetPremio(p);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Resgatar")) {
                        if (VuccaneGladiador.getInstance().ganhador.containsKey(p)) {
                            if (isEmpty(p)) {
                                try {
                                    p.getInventory().addItem(VuccaneGladiador.getInstance().getBase64(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("itensPremios")));
                                    p.closeInventory();
                                    p.sendMessage("§aPremio resgatado com sucesso!");
                                    p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 1, 1);
                                    VuccaneGladiador.getInstance().ganhador.clear();
                                }catch (Exception exception){

                                }
                            } else {
                                p.sendMessage("§cLimpe seu inventario para poder resgatar sua recompensa!");
                                return;
                            }
                        } else {
                            p.sendMessage("§cSo o dono do clan pode resgatar a recompensa do evento!");
                            return;
                        }
                    }
                }
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§5     Configuração do evento")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    Player p = (Player) e.getWhoClicked();
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Espectador")) {
                        if (config.getBoolean("espectar")) {
                            config.set("espectar", false);
                        } else {
                            config.set("espectar", true);
                        }
                        arquivo.saveConfig();
                        VuccaneGladiador.getInstance().getGladiadorGui().invConfig(p);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Mensagens")) {
                        if (config.getBoolean("killmensage")) {
                            config.set("killmensage", false);
                        } else {
                            config.set("killmensage", true);
                        }
                        arquivo.saveConfig();
                        VuccaneGladiador.getInstance().getGladiadorGui().invConfig(p);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Kits")) {
                        if (config.getBoolean("Kit")) {
                            config.set("Kit", false);
                            VuccaneGladiador.getInstance().setKit.clear();
                            VuccaneGladiador.getInstance().setInv.clear();
                        } else {
                            config.set("Kit", true);
                            if(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().contains("kitInv")){
                                String base64Inv = VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("kitInv");
                                System.out.println(base64Inv);
                                VuccaneGladiador.getInstance().setInv.put("Inv",base64Inv);
                            }
                            if(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().contains("KitArmor")){
                                String base64Armor = VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("KitArmor");
                                System.out.println(base64Armor);
                                VuccaneGladiador.getInstance().setKit.put("Armor", base64Armor);
                            }

                        }
                        arquivo.saveConfig();
                        VuccaneGladiador.getInstance().getGladiadorGui().invConfig(p);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Som")) {
                        if (config.getBoolean("Sounds")) {
                            config.set("Sounds", false);
                        } else {
                            config.set("Sounds", true);
                        }
                        arquivo.saveConfig();
                        VuccaneGladiador.getInstance().getGladiadorGui().invConfig(p);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Premiação")) {
                        if (config.getBoolean("premio")) {
                            config.set("premio", false);
                        } else {
                            config.set("premio", true);
                        }
                        arquivo.saveConfig();
                        VuccaneGladiador.getInstance().getGladiadorGui().invConfig(p);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("gladiador:")) {
                        p.closeInventory();
                        p.sendMessage("§aDigite no chat como você quer a tag GLADIADOR. Você tem 30 segundos para seta. Se quiser cancelar digite §eCANCELAR");
                        setTagGlad(p);
                        VuccaneGladiador.getInstance().setTag.put("gladiador", p);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("matador")) {
                        p.closeInventory();
                        p.sendMessage("§aDigite no chat como você quer a tag MATADOR. Você tem 30 segundos para seta. Se quiser cancelar digite §eCANCELAR");
                        setTagGlad(p);
                        VuccaneGladiador.getInstance().setTag.put("matador", p);
                    }

                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("arena")) {
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().set("locArena", VuccaneGladiador.serialize(p.getLocation()));
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.sendMessage("§aLocalização da §2arena §asetada com §2SUCESSO");
                        p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1, 1);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("lobby")) {
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().set("locLobby", VuccaneGladiador.serialize(p.getLocation()));
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.sendMessage("§aLocalização do §2lobby §asetada com §2SUCESSO");
                        p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1, 1);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("centro")) {
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().set("locCentro", VuccaneGladiador.serialize(p.getLocation()));
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.sendMessage("§aLocalização do §2centro da borda §asetada com §2SUCESSO");
                        p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1, 1);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Tamanho")) {
                        VuccaneGladiador.getInstance().getGladiadorGui().setTamanho(p);
                        p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
                    }

                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Proxima")) {
                        VuccaneGladiador.getInstance().getGladiadorGui().invCofig2(p);
                        p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
                    }

                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Spawn")) {
                        config.set("locSpawn", VuccaneGladiador.serialize(p.getLocation()));
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.sendMessage("§aLocalização do §2spawn §asetada com §2SUCESSO");
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Premio")) {
                        VuccaneGladiador.getInstance().getGladiadorGui().setCoin(p);
                        p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1, 1);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Voltar")) {
                        VuccaneGladiador.getInstance().getGladiadorGui().invAdmin(p);
                        p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 1, 1);

                    }
                }
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§5         Iniciação / Check")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    Player p = (Player) e.getWhoClicked();
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("INICIAR")) {
                        p.sendMessage("§aVerificando as configurações...");
                        if (checkConfig()) {
                            p.sendMessage("§eLista das configurações do evento:");
                            p.sendMessage("§e");
                            p.sendMessage("§eEspectadores: §6" + config.getString("espectar").toLowerCase(Locale.ROOT));
                            p.sendMessage("§eSons de kill: §6" + config.getString("killmensage").toLowerCase(Locale.ROOT));
                            p.sendMessage("§eKit: §6" + config.getString("Kit").toLowerCase(Locale.ROOT));
                            p.sendMessage("§ePremios em itens: §6" + config.getString("premio").toLowerCase(Locale.ROOT));
                            p.sendMessage("§eTag do Gladiador: §6" + config.getString("tagGlad").replace("&", "§"));
                            p.sendMessage("§eTag do Matador: §6" + config.getString("tagMatador").replace("&", "§"));
                            p.sendMessage("§eTamanho da borda: §6" + config.getInt("tamanho"));

                            new Contador().runTaskTimer(VuccaneGladiador.getInstance(), 0, 20);
                            VuccaneGladiador.getInstance().start = true;
                            VuccaneGladiador.getInstance().pvp = false;
                        } else {
                            p.sendMessage("§cErro ao iniciar o evento, algo esta faltando na configuração, cheque novamente as configuraçôes");

                        }
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Voltar")) {
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().invAdmin(p);
                        p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 1, 1);
                        p.updateInventory();
                    }
                }
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§5         Tamanho da borda")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    Player p = (Player) e.getWhoClicked();
                    Arquivo arquivo = VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao");
                    FileConfiguration config = arquivo.getConfig();
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("-10")) {
                        int antes = config.getInt("tamanho");
                        int resultado = antes - 9;
                        if (resultado < 1) {
                            p.sendMessage("§cO tamanho da borda esta pequena de mais para diminuir");
                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                            return;
                        }
                        config.set("tamanho", antes - 9);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setTamanho(p);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("-5")) {
                        int antes = config.getInt("tamanho");
                        int resultado = antes - 5;
                        if (resultado < 1) {
                            p.sendMessage("§cO tamanho da borda esta pequena de mais para diminuir");
                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                            return;
                        }
                        config.set("tamanho", antes - 5);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setTamanho(p);

                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("-1")) {
                        int antes = config.getInt("tamanho");
                        int resultado = antes - 1;
                        if (resultado < 1) {
                            p.sendMessage("§cO tamanho da borda esta pequena de mais para diminuir");
                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                            return;
                        }
                        config.set("tamanho", antes - 1);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setTamanho(p);

                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+1")) {
                        int antes = config.getInt("tamanho");
                        config.set("tamanho", antes + 1);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setTamanho(p);

                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+5")) {
                        int antes = config.getInt("tamanho");
                        config.set("tamanho", antes + 5);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setTamanho(p);

                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+10")) {
                        int antes = config.getInt("tamanho");
                        config.set("tamanho", antes + 9);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setTamanho(p);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Voltar")) {
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().invAdmin(p);
                        p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 1, 1);
                        p.updateInventory();
                    }
                }
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§2              Dinheiro")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    Player p = (Player) e.getWhoClicked();
                    Arquivo arquivo = VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao");
                    FileConfiguration config = arquivo.getConfig();
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("-100000")) {
                        int antes = config.getInt("dinheiro");
                        int resultado = antes - 10000;
                        if (resultado < 0) {
                            p.sendMessage("§cA quantidade de coin's não pode ficar negativa");
                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                            return;
                        }
                        config.set("dinheiro", antes - 90000);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setCoin(p);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("-50000")) {
                        int antes = config.getInt("dinheiro");
                        int resultado = antes - 50000;
                        if (resultado < 0) {
                            p.sendMessage("§cA quantidade de coin's não pode ficar negativa");
                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                            return;
                        }
                        config.set("dinheiro", antes - 50000);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setCoin(p);

                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("-10000")) {
                        int antes = config.getInt("dinheiro");
                        int resultado = antes - 10000;
                        if (resultado < 0) {
                            p.sendMessage("§cA quantidade de coin's não pode ficar negativa");
                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                            return;
                        }
                        config.set("dinheiro", antes - 10000);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setCoin(p);

                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+10000")) {
                        int antes = config.getInt("dinheiro");
                        config.set("dinheiro", antes + 10000);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setCoin(p);

                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+50000")) {
                        int antes = config.getInt("dinheiro");
                        config.set("dinheiro", antes + 50000);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setCoin(p);

                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+100000")) {
                        int antes = config.getInt("dinheiro");
                        config.set("dinheiro", antes + 90000);
                        VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").saveConfig();
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().setCoin(p);
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Voltar")) {
                        p.closeInventory();
                        VuccaneGladiador.getInstance().getGladiadorGui().invAdmin(p);
                        p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 1, 1);
                        p.updateInventory();
                    }
                }
            }
        }
        if(e.getInventory().getTitle().equalsIgnoreCase("§2   Setar itens")){
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSetar o inventario do jogador")){
                        VuccaneGladiador.getInstance().getGladiadorGui().invSetInventario((Player) e.getWhoClicked());
                    }
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSetar a armadura do jogador")){
                        VuccaneGladiador.getInstance().getGladiadorGui().invSetKit((Player) e.getWhoClicked());
                    }
                }
            }
        }
    }


    public boolean checkConfig() {
        Arquivo arquivo = VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao");
        FileConfiguration config = arquivo.getConfig();
        if (!config.contains("locArena"))
            return false;

        if (!config.contains("locCentro"))
            return false;

        if (!config.contains("locLobby"))
            return false;

        if (!config.contains("locSpawn"))
            return false;

        if (config.getInt("dinheiro") == 0)
            return false;

        return true;
    }

    public void setTagGlad(Player player) {
        new BukkitRunnable() {
            int contador = 30;

            @Override
            public void run() {
                if (contador > 0) {
                    if (VuccaneGladiador.getInstance().setTag.containsValue(player)) {
                        contador--;
                    } else {
                        this.cancel();
                    }
                } else {
                    this.cancel();
                    player.sendMessage("§cTempo esgotado para setar a tag!");
                }
            }
        }.runTaskTimer(VuccaneGladiador.getInstance(), 0, 20);
    }
    public Boolean isEmpty(Player p){
        for(ItemStack item : p.getInventory().getContents()){
            if (item != null && item.getType() != Material.AIR)
                return false;
        }
        return true;
    }
    public Boolean isEmptyArmor(Player p){
        for(ItemStack item : p.getInventory().getArmorContents()){
            if (item != null && item.getType() != Material.AIR)
                return false;
        }
        return true;
    }

}
