package me.dan.quests.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestProgress {

    private final String quest;
    private double amount;
    private boolean complete;

}
