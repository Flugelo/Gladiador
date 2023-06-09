package me.flugel.vuccanegladiador.objetos;

import java.io.File;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class Arquivo {

	String config = VuccaneGladiador.getInstance().getDataFolder()+"/Arquivos";
	
	private File arquivo;
	private FileConfiguration fileConfiguration;
	private String nome;
	
	public Arquivo(String nome) {
		this.nome = nome;
		arquivo = new File(config, nome+".yml");
		fileConfiguration = YamlConfiguration.loadConfiguration(arquivo);
		if (!(arquivo.exists())) {
			try {
				arquivo.createNewFile();
				Bukkit.getConsoleSender().sendMessage( " §aArquivo §f" + nome + ".yml §acriado com sucesso!");
				VuccaneGladiador.getInstance().getManagerArquivos().getArquivos().add(this);
				saveConfig();
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage( " §cOcorreu um erro ao criar o arquivo §f" + nome + ".yml§c!");
			}
		}
	}
	
	public File getArquivo() {return arquivo;}
	
	public FileConfiguration getConfig() {
		return fileConfiguration;
	}
	
	public void saveConfig() {
		try {
			fileConfiguration.save(arquivo);
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(  " §cOcorreu um erro ao salvar o arquivo §f" + nome + ".yml§c!");
		}
	}
	public void reloadConfig(){
        try{
        	fileConfiguration = YamlConfiguration.loadConfiguration(arquivo);
        	VuccaneGladiador.getInstance().reloadConfig();
        	saveConfig();
            Bukkit.getConsoleSender().sendMessage( " §aConfigura§§o do arquivo §f" + nome + ".yml §arecarregada com sucesso!");
            
        }catch (Exception e){
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage( " §cErro ao recarregar o plugin H_Rankup!");
        }
    }

	public String getNome() {
		return nome;
	}
	
	public void delete() {
		Bukkit.getConsoleSender().sendMessage(  " §ao Arquivo §f" + nome + ".yml §afoi deletado!");
		VuccaneGladiador.getInstance().getManagerArquivos().getArquivos().remove(this);
		arquivo.delete();
	}
	
}
