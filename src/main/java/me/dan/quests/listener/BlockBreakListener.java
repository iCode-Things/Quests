package me.dan.quests.listener;

import me.dan.quests.Quests;
import me.dan.quests.quest.Quest;
import me.dan.quests.quest.QuestType;
import me.dan.quests.user.QuestUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        for (Quest quest : Quests.getInstance().getQuestManager().getByType(QuestType.BLOCK_BREAK)) {
            if (quest.getTarget() != null && !quest.getTarget().equalsIgnoreCase(e.getBlock().getType().name())) {
                return;
            }

            QuestUser questUser = Quests.getInstance().getQuestUserManager().get(e.getPlayer().getUniqueId());
            questUser.incrementProgress(quest);
        }
    }

}
