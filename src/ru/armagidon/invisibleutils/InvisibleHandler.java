package ru.armagidon.invisibleutils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

public class InvisibleHandler implements Listener
{

    private final Plugin plugin;

    public InvisibleHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        InvisibleUtils.getHidden().addEntry(e.getPlayer().getName());
        e.getPlayer().setScoreboard(InvisibleUtils.getHideNameTag());
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent e){
        Entity right = e.getRightClicked();
        if(right.getType().equals(EntityType.PLAYER)){
            e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("title", "title").
                            replace("{name}",e.getRightClicked().getName()))));
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        InvisibleUtils.getHidden().addEntry(e.getPlayer().getName());
        e.getPlayer().setScoreboard(InvisibleUtils.getHideNameTag());
    }
}
