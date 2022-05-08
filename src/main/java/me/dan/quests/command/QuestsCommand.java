package me.dan.quests.command;

import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import me.dan.pluginapi.math.NumberUtil;
import me.dan.pluginapi.message.Placeholder;
import me.dan.pluginapi.util.Text;
import me.dan.quests.Quests;
import me.dan.quests.config.Messages;
import me.dan.quests.quest.Quest;
import me.dan.quests.user.QuestProgress;
import me.dan.quests.user.QuestUser;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.math.BigDecimal;

public class QuestsCommand extends AbstractCommand {

    public QuestsCommand() {
        super("quests");
        setRequiresPlayer(true);
    }

    @Override
    public void perform(CommandContext commandContext) {
        Player player = (Player) commandContext.getCommandSender();
        QuestUser questUser = Quests.getInstance().getQuestUserManager().get(player.getUniqueId());
        for (String message : Messages.QUEST_MESSAGE.getStringList()) {
            message = Text.c(Placeholder.apply(message, new Placeholder("{prefix}", Messages.PREFIX.getString())));
            if (message.contains("{quests}")) {
                for (Quest quest : Quests.getInstance().getQuestManager().getAll()) {
                    QuestProgress questProgress = questUser.getQuestProgress(quest);
                    Messages.QUEST_FORMAT.send(player, new Placeholder("{quest}", quest.getName()),
                            new Placeholder("{progress}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(questProgress.getAmount()))),
                            new Placeholder("{max}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(quest.getAmount()))),
                            new Placeholder("{status}", questProgress.isComplete() ? Messages.STATUS_COMPLETE.getString() : Messages.STATUS_INCOMPLETE.getString()));
                }
            } else {
                player.sendMessage(message);
            }
        }
    }
}
