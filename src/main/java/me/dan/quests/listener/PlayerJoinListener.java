package me.dan.quests.listener;

import me.dan.quests.Quests;
import me.dan.quests.user.QuestUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Quests.getInstance().getQuestUserManager().insert(e.getPlayer().getUniqueId(), new QuestUser(e.getPlayer().getUniqueId()));
    }

}
