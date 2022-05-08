package me.dan.quests.listener;

import me.dan.quests.Quests;
import me.dan.quests.quest.Quest;
import me.dan.quests.quest.QuestType;
import me.dan.quests.user.QuestProgress;
import me.dan.quests.user.QuestUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getTo() == null) {
            return;
        }
        for (Quest quest : Quests.getInstance().getQuestManager().getByType(QuestType.DISTANCE_TRAVEL)) {
            if (quest.getTarget() != null && !quest.getTarget().equalsIgnoreCase(e.getPlayer().getWorld().getName())) {
                return;
            }

            QuestUser questUser = Quests.getInstance().getQuestUserManager().get(e.getPlayer().getUniqueId());
            double distance = e.getFrom().distanceSquared(e.getTo());
            questUser.incrementProgress(quest, distance);
        }
    }

}
