package me.dan.quests.config;

import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.quests.quest.Quest;
import me.dan.quests.quest.QuestList;
import me.dan.quests.quest.QuestType;

import java.util.Arrays;

public enum Config implements Configuration{

    SAVE_INTERVAL("save-interval", 300),
    MONGO_HOST("mongo.host", "localhost"),
    MONGO_PORT("mongo.port", 27017),
    MONGO_DATABASE("mongo.database", "quest-database"),
    QUESTS("quests", QuestList.builder()
            .questList(Arrays.asList(
                    Quest.builder()
                            .id("cow-kills")
                            .questType(QuestType.ENTITY_KILL)
                            .amount(10)
                            .target("COW")
                            .name("Kill 10 Cows")
                            .build(),
                    Quest.builder()
                            .id("walk-500")
                            .name("Walk 500 Blocks")
                            .amount(500)
                            .questType(QuestType.DISTANCE_TRAVEL)
                            .build(),
                    Quest.builder()
                            .id("catch-30-fish")
                            .name("Catch 30 Fish")
                            .questType(QuestType.FISH_CATCH)
                            .amount(30)
                            .build(),
                    Quest.builder()
                            .id("break-50")
                            .name("Break 50 Blocks")
                            .questType(QuestType.BLOCK_BREAK)
                            .amount(50)
                            .build(),
                    Quest.builder()
                            .id("place-50")
                            .name("Place 50 Blocks")
                            .questType(QuestType.BLOCK_PLACE)
                            .amount(50)
                            .build()
            )).build());

    @Getter
    private final String path;

    @Getter
    @Setter
    private Object value;

    Config(String path, Object value) {
        this.path = path;
        this.value = value;
    }

    public QuestList getQuestList() {
        return (QuestList) value;
    }

    public String getString() {
        return (String) value;
    }

    public int getInt() {
        return (int) value;
    }

}
