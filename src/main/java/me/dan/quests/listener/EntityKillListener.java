package me.dan.quests.listener;

import me.dan.quests.Quests;
import me.dan.quests.quest.Quest;
import me.dan.quests.quest.QuestType;
import me.dan.quests.user.QuestUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityKillListener implements Listener {

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) {
            return;
        }

        for (Quest quest : Quests.getInstance().getQuestManager().getByType(QuestType.ENTITY_KILL)) {
            if (quest.getTarget() != null && !quest.getTarget().equalsIgnoreCase(e.getEntity().getType().name())) {
                return;
            }

            QuestUser questUser = Quests.getInstance().getQuestUserManager().get(e.getEntity().getKiller().getUniqueId());
            questUser.incrementProgress(quest);
        }

    }

}
