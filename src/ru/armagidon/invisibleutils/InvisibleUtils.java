package ru.armagidon.invisibleutils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.stream.Collectors;

public class InvisibleUtils extends JavaPlugin
{
    private static Team hidden;
    private static Scoreboard hide_name_tag;

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();
        saveDefaultConfig();
        ScoreboardManager manager = getServer().getScoreboardManager();
        if(manager!=null) {
            hide_name_tag = manager.getMainScoreboard();
        }

        List<String> teams = hide_name_tag.getTeams().stream().map(Team::getName).collect(Collectors.toList());
        if(!teams.contains("hidden")){
            hidden = hide_name_tag.registerNewTeam("hidden");
        } else {
            hidden = hide_name_tag.getTeam("hidden");
        }
        if(hidden!=null) {
            hidden.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            hidden.setCanSeeFriendlyInvisibles(false);
            if (Bukkit.getOnlinePlayers().size() > 0) {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.setScoreboard(hide_name_tag);
                    hidden.addEntry(p.getName());
                });
            }
        }
        getServer().getPluginManager().registerEvents(new InvisibleHandler(this),this);
    }

    @Override
    public void onDisable() {
        List<String> teams = hide_name_tag.getTeams().stream().map(Team::getName).collect(Collectors.toList());
        if(teams.contains("hidden")) {
            Bukkit.getOnlinePlayers().forEach(p -> {
                hidden.removeEntry(p.getName());
            });
            hidden.unregister();
        }
    }

    public static Team getHidden() {
        return hidden;
    }

    public static Scoreboard getHideNameTag() {
        return hide_name_tag;
    }
}
