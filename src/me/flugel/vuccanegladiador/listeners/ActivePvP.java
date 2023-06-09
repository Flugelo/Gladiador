package me.flugel.vuccanegladiador.listeners;

import me.flugel.vuccanegladiador.VuccaneGladiador;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import yclans.api.yClansAPI;
import yclans.models.ClanPlayer;

public class ActivePvP implements Listener {
    yClansAPI clans = yClansAPI.yclansapi;
    @EventHandler
    public void onTestEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player){
            try {
                if(event.getDamager().getLocation().getWorld().equals(aWorld())){
                    if (event.getEntity() instanceof Player) {

                        Player entityDamager = (Player) event.getDamager();
                        Player entity = (Player) event.getEntity();

                        ClanPlayer damager = yClansAPI.yclansapi.getPlayer(entityDamager);
                        ClanPlayer entidade = yClansAPI.yclansapi.getPlayer(entity);

                        if(damager.getClanTag().equals(entidade.getClanTag())){
                            event.setCancelled(true); return;
                        }


                        if(VuccaneGladiador.getInstance().pvp){
                            event.setCancelled(false);

                        }else{
                            event.setCancelled(true);
                            ((Player) event.getDamager()).getPlayer().sendMessage("§4§lPVP §6Não esta ativado no momento!");
                        }
                    }
                }
            }catch (Exception e){

            }
        }
    }
    public World aWorld(){
        if(VuccaneGladiador.getInstance().loc == null){
            VuccaneGladiador.getInstance().loc = VuccaneGladiador.unserialize(VuccaneGladiador.getInstance().getManagerArquivos().get("Configuracao").getConfig().getString("locCentro"));
            return VuccaneGladiador.getInstance().loc.getWorld();
        }
        return VuccaneGladiador.getInstance().loc.getWorld();
    }
}
