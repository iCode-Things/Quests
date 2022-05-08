package me.dan.quests.user;

import me.dan.pluginapi.manager.Manager;
import me.dan.quests.Quests;
import me.dan.quests.config.Config;
import me.dan.quests.quest.Quest;
import org.bukkit.Bukkit;

import java.util.*;

public class QuestUserManager extends Manager<UUID, QuestUser> {

    private final List<QuestUser> saveQueue;

    public QuestUserManager() {
        this.saveQueue = new ArrayList<>();
        long saveInterval = Config.SAVE_INTERVAL.getInt() * 20L;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Quests.getInstance(), () -> Bukkit.getScheduler().runTaskAsynchronously(Quests.getInstance(), this::runSaveTask), saveInterval, saveInterval);
    }

    public void runSaveTask() {
        for (QuestUser questUser : saveQueue) {
            questUser.save();
        }
        saveQueue.clear();
    }

    public void removeFromQueue(QuestUser questUser) {
        saveQueue.remove(questUser);
    }

    @Override
    public QuestUser get(UUID uuid) {
        QuestUser questUser = super.get(uuid);
        saveQueue.add(questUser);
        return questUser;
    }

}
