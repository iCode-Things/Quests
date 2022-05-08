package me.dan.quests.listener;

import me.dan.pluginapi.message.Placeholder;
import me.dan.quests.config.Messages;
import me.dan.quests.event.QuestCompleteEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class QuestCompleteListener implements Listener {

    @EventHandler
    public void onQuestComplete(QuestCompleteEvent e) {
        Player player = Bukkit.getPlayer(e.getQuestUser().getUuid());
        Messages.QUEST_COMPLETE.send(player, new Placeholder("{quest}", e.getQuest().getName()));
    }

}
