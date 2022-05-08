package me.dan.quests.quest;

import lombok.Builder;
import lombok.Getter;
import me.dan.pluginapi.configuration.Serializable;
import me.dan.pluginapi.file.YamlFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class QuestList extends Serializable {

    private List<Quest> questList;

    @Override
    public Map<String, Object> serialize() {
        int i = 1;
        Map<String, Object> map = new HashMap<>();
        for (Quest quest : questList) {
            Map<String, Object> serialized = quest.serialize();
            for (String key : serialized.keySet()) {
                map.put(i + "." + key, serialized.get(key));
            }
            i++;
        }
        return map;
    }

    public static QuestList deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        List<Quest> quests = new ArrayList<>();
        for (String key : c.getConfigurationSection(path).getKeys(false)) {
            quests.add(Quest.deserialize(yamlFile, path + "." + key));
        }
        return QuestList.builder().questList(quests).build();
    }
}
