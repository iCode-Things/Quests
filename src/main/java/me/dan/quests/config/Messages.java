package me.dan.quests.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.message.Message;

import java.util.Arrays;
import java.util.List;

public enum Messages implements Configuration, Message {

    PREFIX("prefix", "&8[&bQuests&8] &3"),
    QUEST_MESSAGE("quest-message", Arrays.asList(
            "{prefix}Your Quests",
            "{quests}"
    )),
    QUEST_FORMAT("quest-format", "&8> &3{quest} &7&o({progress}/{max}) &bStatus: {status}"),
    STATUS_COMPLETE("status.complete", "&aComplete"),
    STATUS_INCOMPLETE("status.incomplete", "&4Incomplete"),
    QUEST_COMPLETE("quest-complete", "{prefix}You have completed the Quest: {quest}!");

    @Getter
    private final String path;

    @Getter
    @Setter
    private Object value;

    Messages(String path, Object value) {
        this.path = path;
        this.value = value;
    }

    @Override
    public String getPrefix() {
        return PREFIX.getString();
    }

    @Override
    public List<String> getStringList() {
        return Configuration.super.getStringList();
    }

    @Override
    public String getString() {
        return Configuration.super.getString();
    }
}
