package me.dan.quests.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.dan.quests.quest.Quest;
import me.dan.quests.user.QuestUser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class QuestCompleteEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final QuestUser questUser;
    private final Quest quest;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
