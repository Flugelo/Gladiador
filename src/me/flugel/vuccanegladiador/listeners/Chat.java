package me.flugel.vuccanegladiador.listeners;

import com.nickuc.chat.api.events.PublicMessageEvent;
import me.flugel.vuccanegladiador.VuccaneGladiador;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class Chat implements Listener {
    @EventHandler
    public void onPublicMessage(PublicMessageEvent e) {
        if (VuccaneGladiador.getInstance().hashTags.containsKey(e.getSender().getName())) {
            List<String> tags = VuccaneGladiador.getInstance().hashTags.get(e.getSender().getName());
            if (tags.contains("Matador") && tags.contains("Gladiador")) {
                e.setTag("Matador", VuccaneGladiador.getInstance().StringtagMat);
                e.setTag("Gladiador", VuccaneGladiador.getInstance().StringtagGlad);
                return;
            }

            if (tags.contains("Gladiador")) {
                e.setTag("Gladiador", VuccaneGladiador.getInstance().StringtagGlad);
                return;
            }

            if (tags.contains("Matador")) {
                e.setTag("Matador", VuccaneGladiador.getInstance().StringtagMat);
            }
        }
    }
}