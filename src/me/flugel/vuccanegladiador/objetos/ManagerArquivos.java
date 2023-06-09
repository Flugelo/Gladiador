package me.flugel.vuccanegladiador.objetos;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import org.bukkit.Bukkit;


public class ManagerArquivos {

	private List<Arquivo> arquivos;
	
	public ManagerArquivos() throws IOException {
		arquivos = new ArrayList<>();
		File file = new File(VuccaneGladiador.getInstance().getDataFolder()+"/Arquivos");
		if (!file.exists()) {
			file.mkdir();
			Bukkit.getConsoleSender().sendMessage(VuccaneGladiador.getInstance() + " §aA Pasta Arquivos foi criada com sucesso!");
		}
		if (file.listFiles().length>0) {
			for(File arquivo : file.listFiles()) {
				Bukkit.getConsoleSender().sendMessage("§c" + arquivo.getName());
				Arquivo fdp = new Arquivo(arquivo.getName().replace(".yml", ""));
				arquivos.add(fdp);
			}
		}
	}
	
	public Arquivo get(String nome) {
		return this.arquivos.stream().filter(a -> a.getNome().equals(nome)).findFirst().orElse(null);
	}
	
	public List<Arquivo> getArquivos(){return arquivos;}
	
}
