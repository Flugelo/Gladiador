package me.flugel.vuccanegladiador;

import me.flugel.vuccanegladiador.api.TopKills;
import me.flugel.vuccanegladiador.comandos.Gladiador;
import me.flugel.vuccanegladiador.gui.GladiadorGui;
import me.flugel.vuccanegladiador.listeners.*;
import me.flugel.vuccanegladiador.objetos.Arquivo;
import me.flugel.vuccanegladiador.objetos.ManagerArquivos;
import me.flugel.vuccanegladiador.objetos.ManagerFinalistas;
import me.flugel.vuccanegladiador.objetos.ManagerParticipante;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class VuccaneGladiador extends JavaPlugin {

    public static VuccaneGladiador instance;

    public GladiadorGui gladiadorGui;

    private ManagerArquivos managerArquivos;

    private ManagerParticipante managerParticipante;
    private ManagerFinalistas managerFinalistas;

    private TopKills topKills;

    public int tempo = getConfig().getInt("Config.duracao_do_evento");

    public HashMap<Player, String> ganhador;
    public HashMap<String, Player> setTag = new HashMap<>();
    public HashMap<String, String> setKit = new HashMap<>();
    public HashMap<String, String> setInv = new HashMap<>();

    private List<String> texto = getConfig().getStringList("Config.mensagem_de_divulgacao");

    public List<String> divu;
    public List<Player> finalistas;

    public HashMap<String, List<String>> hashTags;
    public HashMap<String, Integer> bukkitTaskHashMap;

    public String deathMensage;
    public String ClanEliminated;
    public String StringtagGlad;
    public String StringtagMat;
    public List<Player> espectador;
    public String bordaFechando;
    public boolean sounds;
    public boolean killmensage;
    public boolean kit;
    public boolean premio;
    public boolean espectar;
    public boolean pvp;

    public boolean start;
    public boolean acontecendo;

    public Location loc;

    @Override
    public void onEnable() {

        instance = this;
        saveDefaultConfig();
        gladiadorGui = new GladiadorGui();


        try {
            managerArquivos = new ManagerArquivos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        newArquivos();

        getCommand("gladiador").setExecutor(new Gladiador());
        getCommand("sair").setExecutor(new Gladiador());

        Bukkit.getPluginManager().registerEvents(new Guis(), this);
        Bukkit.getPluginManager().registerEvents(new Morte(), this);
        Bukkit.getPluginManager().registerEvents(new SaveItens(), this);
        Bukkit.getPluginManager().registerEvents(new NoTeleportEspec(), this);
        Bukkit.getPluginManager().registerEvents(new NoUserCommand(), this);
        Bukkit.getPluginManager().registerEvents(new SetGladTag(), this);
        Bukkit.getPluginManager().registerEvents(new Chat(), this);
        Bukkit.getPluginManager().registerEvents(new ActivePvP(), this);
        Bukkit.getPluginManager().registerEvents(new ExitServer(), this);
        Bukkit.getPluginManager().registerEvents(new SaveInvKit(), this);
        Bukkit.getConsoleSender().sendMessage("§a-=-=-=-=-=-=-=-=-=-=-=-");
        Bukkit.getConsoleSender().sendMessage("§aVUCCANE GLADIATOR ON");
        Bukkit.getConsoleSender().sendMessage("§a-=-=-=-=-=-=-=-=-=-=-=-");


        managerParticipante = new ManagerParticipante();
        managerFinalistas = new ManagerFinalistas();

        divu = new ArrayList<>();
        topKills = new TopKills();

        divu = texto.stream().map(l -> l.replace("&", "§")).collect(Collectors.toList());
        StringtagGlad = getManagerArquivos().get("Configuracao").getConfig().getString("tagGlad").replace("&","§");
        StringtagMat = getManagerArquivos().get("Configuracao").getConfig().getString("tagMatador").replace("&","§");
        start = false;
        acontecendo = false;
        pvp = true;
        espectador = new ArrayList<>();
        ganhador = new HashMap<>();
        bukkitTaskHashMap = new HashMap<>();
        finalistas = new ArrayList<>();

        deathMensage = getConfig().getString("Mensagens.PlayerDeath").replace("&", "§");
        ClanEliminated = getConfig().getString("Mensagens.ClanEliminated").replace("&", "§");
        sounds = getManagerArquivos().get("Configuracao").getConfig().getBoolean("Sounds");
        killmensage = getManagerArquivos().get("Configuracao").getConfig().getBoolean("killmensage");
        kit = getManagerArquivos().get("Configuracao").getConfig().getBoolean("Kit");
        premio = getManagerArquivos().get("Configuracao").getConfig().getBoolean("premio");
        espectar = getManagerArquivos().get("Configuracao").getConfig().getBoolean("espectar");
        bordaFechando = getConfig().getString("Mensagens.fechando_borda").replace("&", "§");

        hashTags = new HashMap<>();
        pegarGanhadores();
        pegarKits();

    }


    public static VuccaneGladiador getInstance() {
        return instance;
    }

    public GladiadorGui getGladiadorGui() {
        return gladiadorGui;
    }

    public ManagerArquivos getManagerArquivos() {
        return managerArquivos;
    }


    public void newArquivos() {
        Arquivo arquivo = managerArquivos.get("Configuracao");
        Arquivo data = managerArquivos.get("Data");
        if (arquivo == null) {
            arquivo = new Arquivo("Configuracao");
            FileConfiguration file = arquivo.getConfig();
            file.set("espectar", true);
            file.set("killmensage", true);
            file.set("Kit", false);
            file.set("Sounds", true);
            file.set("premio", true);
            file.set("tagGlad", "&5Gladiador");
            file.set("tagMatador", "&cMatador");
            file.set("tamanho", 0);
            file.set("locArena", null);
            file.set("locCentro", null);
            file.set("locLobby", null);
            file.set("itensPremios", "null");
            file.set("dinheiro", 0);
            arquivo.saveConfig();
        }
        if (data == null) {
            data = new Arquivo("Data");
            data.saveConfig();
        }

    }

    public void pegarGanhadores() {

        try {
            String glad = getManagerArquivos().get("Configuracao").getConfig().getString("Gladiador");
            String[] split = getManagerArquivos().get("Configuracao").getConfig().getString("Matador").split(" ");
            String mat = split[0];
            List<String> tagsMatGlad = new ArrayList<>();
            List<String> tags = new ArrayList<>();
            if(glad.equals(mat)){
                tagsMatGlad.add("Matador");
                tagsMatGlad.add("Gladiador");
                this.hashTags.put(glad, tagsMatGlad);
            }else{
                tagsMatGlad.add("Gladiador");
                this.hashTags.put(glad, tagsMatGlad);
                tags.add("Matador");
                this.hashTags.put(mat, tags);
                System.out.println(hashTags.get(glad));
                System.out.println(hashTags.get(mat));
            }

        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§cNão a ganhadores do gladiadores no momento!");
        }

    }
    public void pegarKits(){
        try {
            String base64Inv = getManagerArquivos().get("Configuracao").getConfig().getString("kitInv");
            String base64Armor = getManagerArquivos().get("Configuracao").getConfig().getString("KitArmor");

            setKit.put("Armor", base64Armor);
            setInv.put("Inv", base64Inv);
        }catch (Exception e){

        }
    }

    public static String serialize(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
    }

    public static Location unserialize(String location) {
        String[] parts = location.split(":");

        org.bukkit.World w = Bukkit.getServer().getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);
        return new Location((org.bukkit.World) w, x, y, z, yaw, pitch);
    }


    public ManagerParticipante getManagerParticipante() {
        return managerParticipante;
    }

    public List<Player> getEspectador() {
        return espectador;
    }

    public String setBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("ERRO ao salvar o inventario!", e);
        }
    }

    public ItemStack[] getBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("ERRO ao retornar o inventario", e);
        }
    }

    public TopKills getTopKills() {
        return topKills;
    }

    public ManagerFinalistas getManagerFinalistas() {
        return managerFinalistas;
    }


}
