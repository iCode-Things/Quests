package me.dan.quests.listener;

import me.dan.quests.Quests;
import me.dan.quests.user.QuestUser;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        QuestUser questUser = Quests.getInstance().getQuestUserManager().get(e.getPlayer().getUniqueId());
        Bukkit.getScheduler().runTaskAsynchronously(Quests.getInstance(), questUser::save);
        Quests.getInstance().getQuestUserManager().removeFromQueue(questUser);
        Quests.getInstance().getQuestUserManager().remove(e.getPlayer().getUniqueId());
    }

}
