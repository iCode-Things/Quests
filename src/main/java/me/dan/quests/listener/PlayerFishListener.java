package me.dan.quests.listener;

import me.dan.quests.Quests;
import me.dan.quests.quest.Quest;
import me.dan.quests.quest.QuestType;
import me.dan.quests.user.QuestUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Objects;

public class PlayerFishListener implements Listener {

    @EventHandler
    public void onBlockBreak(PlayerFishEvent e) {
        if (e.getCaught() == null) {
            return;
        }
        for (Quest quest : Quests.getInstance().getQuestManager().getByType(QuestType.FISH_CATCH)) {
            if (quest.getTarget() != null && !quest.getTarget().equalsIgnoreCase(e.getCaught().getType().name())) {
                return;
            }

            QuestUser questUser = Quests.getInstance().getQuestUserManager().get(e.getPlayer().getUniqueId());
            questUser.incrementProgress(quest);
        }
    }

}
