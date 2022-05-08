package me.dan.quests.user;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import lombok.Getter;
import me.dan.quests.Quests;
import me.dan.quests.event.QuestCompleteEvent;
import me.dan.quests.quest.Quest;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuestUser {

    @Getter
    private final UUID uuid;

    @Getter
    private final Map<String, QuestProgress> questProgressMap;

    public QuestUser(UUID uuid) {
        this.uuid = uuid;
        this.questProgressMap = new HashMap<>();
        DBCollection dbCollection = Quests.getInstance().getMongoDatabase().getCollection();
        DBObject dbObject = new BasicDBObject("uuid", uuid.toString());
        DBObject found = dbCollection.findOne(dbObject);
        if (found != null) {
            for (String key : found.keySet()) {
                questProgressMap.put(key, Quests.getInstance().getMongoDatabase().getGson().fromJson((String) found.get(key), QuestProgress.class));
            }
        }
    }

    public void save() {
        Quests.getInstance().getMongoDatabase().saveUser(this);
    }

    public QuestProgress getQuestProgress(Quest quest) {
        if (!questProgressMap.containsKey(quest.getId().toLowerCase())) {
            questProgressMap.put(quest.getId().toLowerCase(), quest.getDefaultProgress());
        }

        return questProgressMap.get(quest.getId().toLowerCase());
    }

    public void incrementProgress(Quest quest) {
        QuestProgress questProgress = getQuestProgress(quest);
        if (questProgress.isComplete()) {
            return;
        }
        questProgress.setAmount(questProgress.getAmount() + 1);
        if (questProgress.getAmount() >= quest.getAmount()) {
            questProgress.setComplete(true);
            Bukkit.getServer().getPluginManager().callEvent(new QuestCompleteEvent(this, quest));
        }
    }

    public void incrementProgress(Quest quest, double amount) {
        QuestProgress questProgress = getQuestProgress(quest);
        questProgress.setAmount(questProgress.getAmount() + amount);
        if (questProgress.getAmount() >= quest.getAmount()) {
            questProgress.setAmount(quest.getAmount());
            questProgress.setComplete(true);
            Bukkit.getServer().getPluginManager().callEvent(new QuestCompleteEvent(this, quest));
        }
    }

}
