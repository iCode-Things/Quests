package me.dan.quests;

import lombok.Getter;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.configuration.Serialization;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.plugin.CustomPlugin;
import me.dan.quests.command.QuestsCommand;
import me.dan.quests.config.Config;
import me.dan.quests.config.Messages;
import me.dan.quests.listener.*;
import me.dan.quests.mongo.MongoDatabaseImpl;
import me.dan.quests.quest.Quest;
import me.dan.quests.quest.QuestList;
import me.dan.quests.quest.QuestManager;
import me.dan.quests.user.QuestUserManager;
import org.bukkit.Bukkit;

@Getter
public final class Quests extends CustomPlugin {

    private QuestManager questManager;
    private QuestUserManager questUserManager;
    private MongoDatabaseImpl mongoDatabase;

    @Getter
    private static Quests instance;

    @Override
    public void enable() {
        instance = this;
        Serialization.register(Quest.class);
        Serialization.register(QuestList.class);
        Configuration.loadConfig(new YamlFile("config.yml", this.getDataFolder().getAbsolutePath(), null, this), Config.values());
        Configuration.loadConfig(new YamlFile("messages.yml", this.getDataFolder().getAbsolutePath(), null, this), Messages.values());
        try {
            this.mongoDatabase = new MongoDatabaseImpl();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid MongoDB Database");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.questManager = new QuestManager();
        this.questUserManager = new QuestUserManager();
        registerCommands(new QuestsCommand());
        registerEvents(new PlayerJoinListener(), new PlayerQuitListener(), new BlockPlaceListener(), new BlockBreakListener(), new PlayerFishListener(), new EntityKillListener(), new PlayerMoveListener(), new QuestCompleteListener());
    }

    @Override
    public void disable() {
        questUserManager.runSaveTask();
    }
}
