package me.dan.quests.gui;

import me.dan.pluginapi.math.NumberUtil;
import me.dan.quests.Quests;
import me.dan.quests.config.Messages;
import me.dan.quests.quest.Quest;
import me.dan.quests.user.QuestProgress;
import me.dan.quests.user.QuestUser;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.menu.scheme.SchemeMapping;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class QuestProgressGui extends Gui {

    private static final MenuScheme LAYOUT = new MenuScheme().masks(
            "000000000",
            "101010101",
            "000000000"
    );

    public QuestProgressGui(Player player) {
        super(player, 27, "&7Quests");
    }

    @Override
    public void redraw() {
        fillWith(ItemStackBuilder
                .of(Material.STAINED_GLASS_PANE)
                .name(" ")
                .enchant(Enchantment.ARROW_INFINITE, 1)
                .build(() -> {
                }));
        QuestUser questUser = Quests.getInstance().getQuestUserManager().get(getPlayer().getUniqueId());
        MenuPopulator menuPopulator = LAYOUT.newPopulator(this);
        for (Quest quest : Quests.getInstance().getQuestManager().getAll()) {
            QuestProgress questProgress = questUser.getQuestProgress(quest);
            menuPopulator.acceptIfSpace(
                    ItemStackBuilder.of(Material.PAPER)
                            .name("&a" + quest.getName())
                            .lore(
                                    " ",
                                    "&3Progress: &7&o(" + NumberUtil.formatBigDecimal(BigDecimal.valueOf(questProgress.getAmount())) + "/" + NumberUtil.formatBigDecimal(BigDecimal.valueOf(questProgress.getAmount())) + ")",
                                    "&3Status: " + (questProgress.isComplete() ? Messages.STATUS_COMPLETE.getString() : Messages.STATUS_INCOMPLETE.getString())
                            )
                            .build(() -> {
                            })
            );
        }
    }
}
