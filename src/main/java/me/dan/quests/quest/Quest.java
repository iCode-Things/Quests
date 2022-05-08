package me.dan.quests.quest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.dan.pluginapi.configuration.Serializable;
import me.dan.pluginapi.file.YamlFile;
import me.dan.quests.user.QuestProgress;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class Quest extends Serializable {

    private String id;
    private int amount;
    private QuestType questType;
    private String target;
    private String name;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("amount", amount);
        map.put("quest-type", questType.name());
        if (target != null) {
            map.put("target", target);
        }
        return map;
    }

    public static Quest deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        QuestBuilder questBuilder = Quest.builder();
        System.out.println(path);
        questBuilder
                .id(c.getString(path + ".id"))
                .amount(c.getInt(path + ".amount"))
                .questType(QuestType.valueOf(c.getString(path + ".quest-type").toUpperCase()))
                .name(c.getString(path + ".name"));
        if (yamlFile.getConfig().contains(path + ".target")) {
            questBuilder.target(c.getString(path + ".target"));
        }
        return questBuilder.build();
    }

    public QuestProgress getDefaultProgress() {
        return new QuestProgress(id, 0, false);
    }

}
