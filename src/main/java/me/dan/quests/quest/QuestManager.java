package me.dan.quests.quest;

import me.dan.pluginapi.manager.Manager;
import me.dan.quests.config.Config;

import java.util.ArrayList;
import java.util.List;

public class QuestManager extends Manager<String, Quest> {

    public QuestManager() {
        init();
    }

    private void init() {
        for (Quest quest : Config.QUESTS.getQuestList().getQuestList()) {
            insert(quest.getId(), quest);
        }
    }

    public List<Quest> getByType(QuestType questType) {
        List<Quest> questList = new ArrayList<>();
        for (Quest quest : getAll()) {
            if (quest.getQuestType().equals(questType)) {
                questList.add(quest);
            }
        }
        return questList;
    }

}
