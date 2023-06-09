package me.flugel.vuccanegladiador.gui;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import me.flugel.vuccanegladiador.api.Item;
import me.flugel.vuccanegladiador.api.SkullCreator;
import me.flugel.vuccanegladiador.objetos.Arquivo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import yclans.api.yClansAPI;
import yclans.models.ClanPlayer;

import java.text.NumberFormat;
import java.util.ArrayList;

public class GladiadorGui {

    VuccaneGladiador main = VuccaneGladiador.getInstance();

    public void invPlayer(Player player){
        Inventory inv = Bukkit.createInventory(null, 3*9, "§5          Evento   Gladiador");
        Arquivo arquivo = main.getManagerArquivos().get("Configuracao");
        FileConfiguration config = arquivo.getConfig();
        yClansAPI clans = yClansAPI.yclansapi;
        ClanPlayer pClan = clans.getPlayer(player);

        String base64Gladiador = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzM2ZThhZTA1NTgwOTRkM2U1OGI1MTM0ZWUxODY3NTk4OTI0ZjVmMTI5ODI5ZmMzODk2Y2YxZWU0NDM5NjEzNyJ9fX0=";
        Item item = new Item(SkullCreator.itemFromBase64(base64Gladiador));
        item.setDisplayName("&7Evento gladiador");

        String base64Confirm = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQwNjNiYTViMTZiNzAzMGEyMGNlNmYwZWE5NmRjZDI0YjA2NDgzNmY1NzA0NTZjZGJmYzllODYxYTc1ODVhNSJ9fX0=";
        Item ItemConfirm = new Item(SkullCreator.itemFromBase64(base64Confirm));
        ItemConfirm.setDisplayName("§aEntrar no evento");

        String base64Assistir = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmEzZmNlNTAzNmY3YWQ0ZjExMTExY2UzMThmOGYxYWVlODU5ZWY0OWRlMTI5M2YxMTYyY2EyZTJkZWEyODFkYiJ9fX0=";
        Item ItemAssit = new Item(SkullCreator.itemFromBase64(base64Assistir));
        ItemAssit.setDisplayName("§eAssistir evento! §6" + config.getString("espectar").toUpperCase());

        String base64Resgate = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWI4ZmUxYzQ0YWNiZWViOTE4ZDM4YmM0MmQ1NTBiZWRkNWMzZGQwNDk4ODlmZDllZWVhMTE2MGFiOGI2YSJ9fX0=";
        Item itemResgate = new Item(SkullCreator.itemFromBase64(base64Resgate));
        itemResgate.setDisplayName("§aResgatar recompensa do evento!");
        itemResgate.setLore("", "§aClique para pegar a rescompensa!", "");

        String base64Fechar = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIwZWYwNmRkNjA0OTk3NjZhYzhjZTE1ZDJiZWE0MWQyODEzZmU1NTcxODg2NGI1MmRjNDFjYmFhZTFlYTkxMyJ9fX0=";
        Item itemFechar = new Item(SkullCreator.itemFromBase64(base64Fechar));
        itemFechar.setDisplayName("§cFechar pagina");

        inv.setItem(4,item.build());
        inv.setItem(11, ItemConfirm.build());
        inv.setItem(15, itemFechar.build());
        inv.setItem(26, ItemAssit.build());
        if(config.getBoolean("premio")){
            if(main.ganhador.containsValue(pClan.getClanTag())){
                inv.setItem(18, itemResgate.build());
            }
        }

        player.openInventory(inv);
    }

    public void invAdmin(Player player){

        Inventory inv = Bukkit.createInventory(null, 3*9, "§5          Evento   Gladiador");

        Arquivo arquivo = main.getManagerArquivos().get("Configuracao");
        FileConfiguration config = arquivo.getConfig();

        String base64ADM = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjcxNTdjZmZiMDYwNjNiMzUyZGM2ODQ3OGY0NzZlN2QyMDJjM2JhNmU3Y2JmMjk3MjQxYmU4MTY4MTA3NGJmIn19fQ==";
        Item itemAdm = new Item(SkullCreator.itemFromBase64(base64ADM));
        itemAdm.setDisplayName("§eAcesso administrativo do evento");

        String base64Info = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTgyNTQxOWU0MjlhZmMwNDBjOWU2OGIxMDUyM2I5MTdkN2I4MDg3ZDYzZTc2NDhiMTA4MDdkYThiNzY4ZWUifX19";
        Item itemInfo = new Item(SkullCreator.itemFromBase64(base64Info));
        itemInfo.setDisplayName("§eInformações do evento!");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§eInformações do evento Gladiador");
        lore.add("§f° §6Acontecendo: §e" + VuccaneGladiador.getInstance().acontecendo);
        lore.add("§f° §6Espectador: §e" + config.getString("espectar").toUpperCase());
        lore.add("§f° §6Kit: §e" + config.getString("Kit").toUpperCase());
        lore.add("§f° §6Premiação em itens: §e" + config.getString("premio"));
        lore.add("§f° §6Som de morte: §e" + config.getString("Sounds").toUpperCase());
        itemInfo.setLore(lore);

        String base64Config = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmJkNDE0MzA5NjJmMWQ2MjE3MDgzMDE1ZTViOWQwZWY3Y2UzODQ2OTY0NzJjYmY3OGRhNDUxODUxNmNiZmYxZSJ9fX0=";
        Item itemConfig = new Item(SkullCreator.itemFromBase64(base64Config));
        itemConfig.setDisplayName("§eConfigurações do evento");

        String base64Start = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY5MTk2YjMzMGM2Yjg5NjJmMjNhZDU2MjdmYjZlY2NlNDcyZWFmNWM5ZDQ0Zjc5MWY2NzA5YzdkMGY0ZGVjZSJ9fX0=";
        Item itemStart = new Item(SkullCreator.itemFromBase64(base64Start));
        itemStart.setDisplayName("§aCheck / iniciação");

        String base64Kit = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjIxMjhmNDhkOTk3MTg2NTYzZmJjNWI0N2E4OGMwZDBhYWM5MmZhMmMyODVjZDFmYWU0MjBjMzRmYThmMjAxMCJ9fX0=";
        Item itemKit = new Item(SkullCreator.itemFromBase64(base64Kit));
        itemKit.setDisplayName("§eSetar o kit para os jogadores");

        String base64setPremio = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDhjMWUxYzYyZGM2OTVlYjkwZmExOTJkYTZhY2E0OWFiNGY5ZGZmYjZhZGI1ZDI2MjllYmZjOWIyNzg4ZmEyIn19fQ==";
        Item itemSetpremio = new Item(SkullCreator.itemFromBase64(base64setPremio));
        itemSetpremio.setDisplayName("§aSetar item para premiação");

        if(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getBoolean("Kit"))
            inv.setItem(0, itemKit.build());

        inv.setItem(4, itemAdm.build());
        inv.setItem(11, itemInfo.build());
        inv.setItem(13, itemConfig.build());
        inv.setItem(15, itemStart.build());

        if(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getBoolean("premio"))
            inv.setItem(18, itemSetpremio.build());

        player.openInventory(inv);
    }
    public void invSetPremio(Player player)  {
        try {
            Inventory inv = Bukkit.createInventory(null, 4*9, "§5         Setar os premios");
            Arquivo arquivo = main.getManagerArquivos().get("Configuracao");
            FileConfiguration config = arquivo.getConfig();

            if(!config.getString("itensPremios").equalsIgnoreCase("null")){
                inv.setContents(VuccaneGladiador.getInstance().getBase64(config.getString("itensPremios")));
            }
            player.openInventory(inv);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void invConfig(Player player){
        Inventory inv = Bukkit.createInventory(null, 3*9, "§5     Configuração do evento");
        Arquivo arquivo = main.getManagerArquivos().get("Configuracao");
        FileConfiguration config = arquivo.getConfig();

        String base64Kill = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWViZmQyMzk2Y2JhYmRiNDJjMzQ4YmNmNDE1OTljODdhNTA2YTcxZWY2MDk0OGM0OTZmOTVjNmNiNjMxNDEifX19";
        Item itemAdm = new Item(SkullCreator.itemFromBase64(base64Kill));
        itemAdm.setDisplayName("§eEspectador §6" + config.getString("espectar").toUpperCase());

        String base64MenK = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzk3YzUyMTA5ZTVkMTgwNTI0ZWRmZmIzNjQ2ZGQ1NTdiNDFlMThkOTA3NjVkNGQ1NjI4YzM4YzUyMjQ5YzQ2NSJ9fX0=";
        Item itemInfo = new Item(SkullCreator.itemFromBase64(base64MenK));
        itemInfo.setDisplayName("§eMensagens de morte: §6" + config.getString("killmensage").toUpperCase());

        String base64Kit = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjIxMjhmNDhkOTk3MTg2NTYzZmJjNWI0N2E4OGMwZDBhYWM5MmZhMmMyODVjZDFmYWU0MjBjMzRmYThmMjAxMCJ9fX0=";
        Item itemConfig = new Item(SkullCreator.itemFromBase64(base64Kit));
        itemConfig.setDisplayName("§eKits para os jogadores: §6" + config.getString("Kit").toUpperCase());

        String base64Sound = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQzOTFiNWZkMzU5YmU1YjNhOTYwYmU1NTIzN2Y2NjM1ZWE1YmQzZDc3ODNiZjYwZGQ2NTVmMjExMGJiOTMifX19";
        Item itemSound = new Item(SkullCreator.itemFromBase64(base64Sound));
        itemSound.setDisplayName("§eSom de morte: §6" + config.getString("Sounds").toUpperCase());

        String base64Present = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjczYjg3NjliNDYxZTg2NWNiODVkMGUwNWEzNDhjYzQxMmVjNzAxYjhmY2E5OWRkNWQ0NjRjOWUyN2Y5YjQ0MCJ9fX0=";
        Item itemPresent = new Item(SkullCreator.itemFromBase64(base64Present));
        itemPresent.setDisplayName("§ePremiação: §6" + config.getString("premio").toUpperCase());

        String base64TagGlad = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzM2ZThhZTA1NTgwOTRkM2U1OGI1MTM0ZWUxODY3NTk4OTI0ZjVmMTI5ODI5ZmMzODk2Y2YxZWU0NDM5NjEzNyJ9fX0=";
        Item itemTagGlad = new Item(SkullCreator.itemFromBase64(base64TagGlad));
        itemTagGlad.setDisplayName("§eTag gladiador: " + config.getString("tagGlad").replace("&", "§"));

        String base64TagMat = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTJlYzk1MmZjMzdkYTAzZDUyYjhjM2UxOTYxM2E4NGQyOTcyMzY5ODVlMThlNzlhM2E1MmRiYTc1MCJ9fX0=";
        Item itemTagMat = new Item(SkullCreator.itemFromBase64(base64TagMat));
        itemTagMat.setDisplayName("§eTag matador: " + config.getString("tagMatador").replace("&", "§"));

        String base64Voltar = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQ5ZDI3MWM1ZGY4NGY4YTNjOGFhNWQxNTQyN2Y2MjgzOTM0MWRhYjUyYzYxOWE1OTg3ZDM4ZmJlMThlNDY0In19fQ==";
        Item itemVoltar = new Item(SkullCreator.itemFromBase64(base64Voltar));
        itemVoltar.setDisplayName("§cVoltar");

        String base64Seguinte = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmI0YzFkYmQ4NTQ0Y2ZhZWQ5NjNkZjRkMTM5YmM4YmFjNGI3NmFjNGJiYjMwYWI4NmY3NmZiYzMxYWI5YTcwIn19fQ==";
        Item itemNext = new Item(SkullCreator.itemFromBase64(base64Seguinte));
        itemNext.setDisplayName("§2Proxima pagina");

        inv.setItem(10, itemAdm.build());
        inv.setItem(11, itemInfo.build());
        inv.setItem(12, itemConfig.build());
        inv.setItem(13, itemSound.build());
        inv.setItem(14, itemPresent.build());
        inv.setItem(15, itemTagGlad.build());
        inv.setItem(16, itemTagMat.build());
        inv.setItem(18, itemVoltar.build());
        inv.setItem(26,itemNext.build());

        player.openInventory(inv);
    }

    public void invCofig2(Player player){
        Inventory inv = Bukkit.createInventory(null, 3*9, "§5     Configuração do evento");
        Arquivo arquivo = main.getManagerArquivos().get("Configuracao");
        FileConfiguration config = arquivo.getConfig();

        String base64Pos1 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODNhNjg1ODAxMmIyNWUxZTg2YWRiZjhjZjA2YmE1ZDJkOTI0YjRjMjNmY2Q0ZDVlNTEzY2ZhODEyNDI2MjNiOSJ9fX0=";
        Item itemPos1 = new Item(SkullCreator.itemFromBase64(base64Pos1));
        itemPos1.setDisplayName("§aLocalização da arena");
        itemPos1.setLore("", "§2§lCLIQUE-ME §aPara setar a localização", "");

        String base64Lobby = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjg1NDA2MGFhNTc3NmI3MzY2OGM4OTg2NTkwOWQxMmQwNjIyNDgzZTYwMGI2NDZmOTBjMTg2YzY1Yjc1ZmY0NSJ9fX0=";
        Item itemLobby = new Item(SkullCreator.itemFromBase64(base64Lobby));
        itemLobby.setDisplayName("§aLocalização do lobby");
        itemLobby.setLore("", "§2§lCLIQUE-ME §aPara setar a localização", "");

        String base64Centro = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBiZDFlYTZiMjY0YjdhMWNlYmY0N2ZiZWRlMTM1NThjN2QxZTFiMTY4NjA4ZTY1YjY1MmE5MTQ3Y2Y3ZjEyNSJ9fX0=";
        Item itemCentro = new Item(SkullCreator.itemFromBase64(base64Centro));
        itemCentro.setDisplayName("§aLocalização do centro da borda");
        itemCentro.setLore("", "§2§lCLIQUE-ME §aPara setar a localização", "");

        String base64Tamanha = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTVhNzQzOTM4MjIxNTFkM2U4MWY4Nzk0ZmY4NGJjMWI5OGM1N2VlOWE4YTgxZTI5ODMxZjczZWRhMmIzNmEifX19";
        Item itemTamanho = new Item(SkullCreator.itemFromBase64(base64Tamanha));
        itemTamanho.setDisplayName("§aTamanho da borda do evento");
        itemTamanho.setLore("", "§eTamanho da borda setada: §6" + config.getInt("tamanho"), "", "§2§lCLIQUE-ME §aPara setar o tamanho");

        String base64Spawn = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM4Y2YzZjhlNTRhZmMzYjNmOTFkMjBhNDlmMzI0ZGNhMTQ4NjAwN2ZlNTQ1Mzk5MDU1NTI0YzE3OTQxZjRkYyJ9fX0=";
        Item itemSpawn = new Item(SkullCreator.itemFromBase64(base64Spawn));
        itemSpawn.setDisplayName("§eSpawn do servidor");
        itemSpawn.setLore("", "§2§lCLIQUE-ME §aPara setar o spawn");

        String base64Coin = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM4MWM1MjlkNTJlMDNjZDc0YzNiZjM4YmI2YmEzZmRlMTMzN2FlOWJmNTAzMzJmYWE4ODllMGEyOGU4MDgxZiJ9fX0=";
        Item itemCoin = new Item(SkullCreator.itemFromBase64(base64Coin));
        itemCoin.setDisplayName("§ePremio em dinheiro: §6" + NumberFormat.getCurrencyInstance().format(config.getInt("dinheiro")));
        itemSpawn.setLore("", "§2§lCLIQUE-ME §aPara setar a quantia em dinheiro");

        String base64Voltar = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQ5ZDI3MWM1ZGY4NGY4YTNjOGFhNWQxNTQyN2Y2MjgzOTM0MWRhYjUyYzYxOWE1OTg3ZDM4ZmJlMThlNDY0In19fQ==";
        Item itemVoltar = new Item(SkullCreator.itemFromBase64(base64Voltar));
        itemVoltar.setDisplayName("§cVoltar");

        inv.setItem(10, itemPos1.build());
        inv.setItem(12, itemCentro.build());
        inv.setItem(13,itemCoin.build());
        inv.setItem(14, itemLobby.build());
        inv.setItem(16, itemTamanho.build());
        inv.setItem(18, itemVoltar.build());
        inv.setItem(26, itemSpawn.build());
        player.openInventory(inv);
    }


    public void invCheck(Player player){
        Inventory inv = Bukkit.createInventory(null, 3*9, "§5         Iniciação / Check");

        Arquivo arquivo = main.getManagerArquivos().get("Configuracao");
        FileConfiguration config = arquivo.getConfig();

        double x= 0,x1= 0,x2 = 0;
        double y = 0,y1 = 0,y2 = 0;
        double z = 0,z1 = 0,z2 = 0;

        if(config.contains("locLobby")){
            Location locLobby = VuccaneGladiador.unserialize(config.getString("locLobby"));
             x = (int) locLobby.getX();
             y = (int) locLobby.getY();
             z = (int) locLobby.getZ();
        }
        if(config.contains("locArena")){
            Location locArena = VuccaneGladiador.unserialize(config.getString("locArena"));
             x1 = (int) locArena.getX();
             y1 = (int) locArena.getY();
             z1 = (int) locArena.getZ();
        }
        if(config.contains("locCentro")){
            Location locCentro = VuccaneGladiador.unserialize(config.getString("locCentro"));
             x2 = (int) locCentro.getX();
             y2 = (int) locCentro.getY();
             z2 = (int) locCentro.getZ();
        }

        String base64Pointlobby = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RlMzNjOTVmZWMxYjhkOTg4MjUwZjVmNWIzYTI0ODU3NDI0MzlmYWVhYTc1ZWQ1MDZlYTAxZDc1ZTE3ZjIxIn19fQ==";
        Item itemPointLobby = new Item(SkullCreator.itemFromBase64(base64Pointlobby));
        itemPointLobby.setDisplayName("§aChecagem do evento");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§eLobby: §6X: " + x + " Y: " + y + " Z: " + z);
        lore.add("§eArena: §6X: " + x1 + " Y: " + y1 + " Z: " + z1);
        lore.add("§eCentro da borda: §6X: " + x2 + " Y: " + y2 + " Z: " + z2);
        lore.add("§eTamanho da borda: §6" + config.getInt("tamanho"));
        lore.add("§eEspectar: §6" + config.getString("espectar").toUpperCase());
        lore.add("§eMensagens de morte §6" + config.getString("killmensage").toUpperCase());
        lore.add("§eKit: §6" + config.getString("Kit").toUpperCase());
        lore.add("§eSom de mortes: §6" + config.getString("Sounds").toUpperCase());
        lore.add("§ePremio: §6" + config.getString("premio").toUpperCase());
        lore.add("");
        itemPointLobby.setLore(lore);

        String base64Start = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxMmNhNDYzMmRlZjVmZmFmMmViMGQ5ZDdjYzdiNTVhNTBjNGUzOTIwZDkwMzcyYWFiMTQwNzgxZjVkZmJjNCJ9fX0=";
        Item itemStart = new Item(SkullCreator.itemFromBase64(base64Start));
        itemStart.setDisplayName("§2INICIAR EVENTO!");
        itemStart.setLore("","§c§lINFORMAÇÃO IMPORTANTE", "", "§eAntes de iniciar o evento, confirme as configurações na (§aChecagem de evento§e)" , "");

        String base64Voltar = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQ5ZDI3MWM1ZGY4NGY4YTNjOGFhNWQxNTQyN2Y2MjgzOTM0MWRhYjUyYzYxOWE1OTg3ZDM4ZmJlMThlNDY0In19fQ==";
        Item itemVoltar = new Item(SkullCreator.itemFromBase64(base64Voltar));
        itemVoltar.setDisplayName("§cVoltar");

        inv.setItem(12, itemPointLobby.build());
        inv.setItem(14, itemStart.build());
        inv.setItem(18, itemVoltar.build());

        player.openInventory(inv);
    }

    public void setTamanho(Player player){
        Inventory inv = Bukkit.createInventory(null, 3*9, "§5         Tamanho da borda");

        Arquivo arquivo = main.getManagerArquivos().get("Configuracao");
        FileConfiguration config = arquivo.getConfig();

        String base64menos10 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU5ODdmNDNmZjU3ZDRkYWJhYTJkMmNlYjlmMDFmYzZlZTQ2ZGIxNjJhNWUxMmRmZGJiNTdmZDQ2ODEzMmI4In19fQ==";
        Item itemMenos10 = new Item(SkullCreator.itemFromBase64(base64menos10));
        itemMenos10.setDisplayName("§c§l-10");

        String base64Menos5 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYzZjU2NWE4ODkyOGVlNWE5ZDY4NDNkOTgyZDc4ZWFlNmI0MWQ5MDc3ZjJhMWU1MjZhZjg2N2Q3OGZiIn19fQ==";
        Item itemMenos5 = new Item(SkullCreator.itemFromBase64(base64Menos5));
        itemMenos5.setDisplayName("§c§l-5");

        String base64menos1 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQyNDU0ZTRjNjdiMzIzZDViZTk1M2I1YjNkNTQxNzRhYTI3MTQ2MDM3NGVlMjg0MTBjNWFlYWUyYzExZjUifX19";
        Item itemMenos1 = new Item(SkullCreator.itemFromBase64(base64menos1));
        itemMenos1.setDisplayName("§c§l-1");

        String base64mais10 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJmNjhjNzMxMDg3NTI2N2VlYzJhYzVhYTZjZGIzYTkxZjIyZDU5OThhMzgwYTRjZWM5MmFhZmFmNmQ3MCJ9fX0=";
        Item itemmais10 = new Item(SkullCreator.itemFromBase64(base64mais10));
        itemmais10.setDisplayName("§2§l+10");

        String base64mais5 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJjMTQyYWY1OWYyOWViMzVhYjI5YzZhNDVlMzM2MzVkY2ZjMmE5NTZkYmQ0ZDJlNTU3MmIwZDM4ODkxYjM1NCJ9fX0=";
        Item itemmais5 = new Item(SkullCreator.itemFromBase64(base64mais5));
        itemmais5.setDisplayName("§2§l+5");

        String base64mais1 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODg5OTE2OTc0Njk2NTNjOWFmODM1MmZkZjE4ZDBjYzljNjc3NjNjZmU2NjE3NWMxNTU2YWVkMzMyNDZjNyJ9fX0=";
        Item itemmais1 = new Item(SkullCreator.itemFromBase64(base64mais1));
        itemmais1.setDisplayName("§2§l+1");

        String base64Tamanha = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTVhNzQzOTM4MjIxNTFkM2U4MWY4Nzk0ZmY4NGJjMWI5OGM1N2VlOWE4YTgxZTI5ODMxZjczZWRhMmIzNmEifX19";
        Item itemTamanho = new Item(SkullCreator.itemFromBase64(base64Tamanha));
        itemTamanho.setDisplayName("§aTamanho da borda do evento : §e" + config.getInt("tamanho"));

        String base64Voltar = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQ5ZDI3MWM1ZGY4NGY4YTNjOGFhNWQxNTQyN2Y2MjgzOTM0MWRhYjUyYzYxOWE1OTg3ZDM4ZmJlMThlNDY0In19fQ==";
        Item itemVoltar = new Item(SkullCreator.itemFromBase64(base64Voltar));
        itemVoltar.setDisplayName("§cVoltar");

        inv.setItem(10, itemMenos10.build());
        inv.setItem(11, itemMenos5.build());
        inv.setItem(12, itemMenos1.build());
        inv.setItem(13, itemTamanho.build());
        inv.setItem(14, itemmais1.build());
        inv.setItem(15, itemmais5.build());
        inv.setItem(16, itemmais10.build());
        inv.setItem(18, itemVoltar.build());


        player.openInventory(inv);
    }
    public void setCoin(Player player){
        Inventory inv = Bukkit.createInventory(null, 3*9, "§2              Dinheiro");

        Arquivo arquivo = main.getManagerArquivos().get("Configuracao");
        FileConfiguration config = arquivo.getConfig();

        String base64menos10 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU5ODdmNDNmZjU3ZDRkYWJhYTJkMmNlYjlmMDFmYzZlZTQ2ZGIxNjJhNWUxMmRmZGJiNTdmZDQ2ODEzMmI4In19fQ==";
        Item itemMenos10 = new Item(SkullCreator.itemFromBase64(base64menos10));
        itemMenos10.setDisplayName("§c§l-100000");

        String base64Menos5 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYzZjU2NWE4ODkyOGVlNWE5ZDY4NDNkOTgyZDc4ZWFlNmI0MWQ5MDc3ZjJhMWU1MjZhZjg2N2Q3OGZiIn19fQ==";
        Item itemMenos5 = new Item(SkullCreator.itemFromBase64(base64Menos5));
        itemMenos5.setDisplayName("§c§l-50000");

        String base64menos1 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQyNDU0ZTRjNjdiMzIzZDViZTk1M2I1YjNkNTQxNzRhYTI3MTQ2MDM3NGVlMjg0MTBjNWFlYWUyYzExZjUifX19";
        Item itemMenos1 = new Item(SkullCreator.itemFromBase64(base64menos1));
        itemMenos1.setDisplayName("§c§l-10000");

        String base64mais10 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJmNjhjNzMxMDg3NTI2N2VlYzJhYzVhYTZjZGIzYTkxZjIyZDU5OThhMzgwYTRjZWM5MmFhZmFmNmQ3MCJ9fX0=";
        Item itemmais10 = new Item(SkullCreator.itemFromBase64(base64mais10));
        itemmais10.setDisplayName("§2§l+100000");

        String base64mais5 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJjMTQyYWY1OWYyOWViMzVhYjI5YzZhNDVlMzM2MzVkY2ZjMmE5NTZkYmQ0ZDJlNTU3MmIwZDM4ODkxYjM1NCJ9fX0=";
        Item itemmais5 = new Item(SkullCreator.itemFromBase64(base64mais5));
        itemmais5.setDisplayName("§2§l+50000");

        String base64mais1 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODg5OTE2OTc0Njk2NTNjOWFmODM1MmZkZjE4ZDBjYzljNjc3NjNjZmU2NjE3NWMxNTU2YWVkMzMyNDZjNyJ9fX0=";
        Item itemmais1 = new Item(SkullCreator.itemFromBase64(base64mais1));
        itemmais1.setDisplayName("§2§l+10000");

        String base64Tamanha = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM4MWM1MjlkNTJlMDNjZDc0YzNiZjM4YmI2YmEzZmRlMTMzN2FlOWJmNTAzMzJmYWE4ODllMGEyOGU4MDgxZiJ9fX0=";
        Item itemTamanho = new Item(SkullCreator.itemFromBase64(base64Tamanha));
        itemTamanho.setDisplayName("§aQuantidade no momento: : §e" + NumberFormat.getCurrencyInstance().format(config.getInt("dinheiro")));

        String base64Voltar = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQ5ZDI3MWM1ZGY4NGY4YTNjOGFhNWQxNTQyN2Y2MjgzOTM0MWRhYjUyYzYxOWE1OTg3ZDM4ZmJlMThlNDY0In19fQ==";
        Item itemVoltar = new Item(SkullCreator.itemFromBase64(base64Voltar));
        itemVoltar.setDisplayName("§cVoltar");

        inv.setItem(10, itemMenos10.build());
        inv.setItem(11, itemMenos5.build());
        inv.setItem(12, itemMenos1.build());
        inv.setItem(13, itemTamanho.build());
        inv.setItem(14, itemmais1.build());
        inv.setItem(15, itemmais5.build());
        inv.setItem(16, itemmais10.build());
        inv.setItem(18, itemVoltar.build());


        player.openInventory(inv);
    }

    public void invSetInventario(Player player){
        try{
            Inventory inv = Bukkit.createInventory(null, 4*9, "§a Setar inventario do jogador");

            if(VuccaneGladiador.getInstance().setInv.containsKey("Inv"))
                inv.setContents(VuccaneGladiador.getInstance().getBase64(VuccaneGladiador.getInstance().setInv.get("Inv")));

            player.openInventory(inv);
        }catch (Exception e){

        }
    }

    public void invSetKit(Player player){
        try{
            Inventory inv = Bukkit.createInventory(null, 9, "§a Setar armadura do jogador");

            if(VuccaneGladiador.getInstance().setKit.containsKey("Armor"))
                inv.setContents(VuccaneGladiador.getInstance().getBase64(VuccaneGladiador.getInstance().setKit.get("Armor")));

            player.openInventory(inv);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void opcoes(Player player){
        Inventory inv = Bukkit.createInventory(null, 9, "§2   Setar itens");

        Item sword = new Item(Material.DIAMOND_SWORD);
        sword.setDisplayName("§eSetar o inventario do jogador");

        Item peitoral = new Item(Material.DIAMOND_CHESTPLATE);
        peitoral.setDisplayName("§eSetar a armadura do jogador");

        inv.setItem(2, sword.build());
        inv.setItem(6, peitoral.build());

        player.openInventory(inv);

    }
}
