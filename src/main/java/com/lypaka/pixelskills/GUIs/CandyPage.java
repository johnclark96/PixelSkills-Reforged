package com.lypaka.pixelskills.GUIs;

import com.codehusky.huskyui.StateContainer;
import com.codehusky.huskyui.states.Page;
import com.codehusky.huskyui.states.action.ActionType;
import com.codehusky.huskyui.states.action.runnable.RunnableAction;
import com.codehusky.huskyui.states.element.ActionableElement;
import com.codehusky.huskyui.states.element.Element;
import com.google.common.collect.Lists;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.PixelSkills;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import com.lypaka.pixelskills.Utils.SkillCandy.CandyItem;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;

public class CandyPage {

    public static void openCandyMenu (Player player) {

        StateContainer container = new StateContainer();
        Page candyPage = Page.builder()
                .setAutoPaging(false)
                .setInventoryArchetype(InventoryArchetypes.CHEST)
                .setInventoryDimension(InventoryDimension.of(9, 2))
                .setTitle(Text.of(TextColors.GOLD, "Pick a skill to level up!"))
                .build("candy");

        int index = 0;
        for (String s : PixelSkills.skills) {

            if (GeneralGetters.isSkillEnabled(GeneralGetters.getConfigFromSkill(s))) {

                if (AccountsHandler.getLevel(s, player) < GeneralGetters.getMaxLevel(GeneralGetters.getConfigFromSkill(s))) {

                    candyPage.getElements().put(index, new ActionableElement(
                            new RunnableAction(container, ActionType.CLOSE, "candy", c -> {

                                try {

                                    CandyItem.levelUpSkill(player, s);

                                } catch (ObjectMappingException | IOException e) {

                                    e.printStackTrace();

                                }

                            }),
                            ItemStack.builder()
                                .itemType(ItemTypes.DIAMOND)
                                .add(Keys.DISPLAY_NAME, Text.of(TextColors.AQUA, s))
                                .add(Keys.ITEM_LORE, Lists.newArrayList(
                                        Text.of(TextColors.YELLOW, "Current level: " + AccountsHandler.getLevel(s, player)),
                                        Text.of(TextColors.YELLOW, "EXP: " + AccountsHandler.getCurrentEXP(s, player) + "/" + AccountsHandler.getEXPToNextLvl(s, player)),
                                        Text.of(TextColors.YELLOW, "Max level: " + GeneralGetters.getMaxLevel(GeneralGetters.getConfigFromSkill(s)))
                                ))
                                .build()
                    ));

                } else {

                    candyPage.getElements().put(index, new Element(
                            ItemStack.builder()
                                .itemType(ItemTypes.BARRIER)
                                .add(Keys.DISPLAY_NAME, Text.of(TextColors.RED, s))
                                .add(Keys.ITEM_LORE, Lists.newArrayList(
                                        Text.of(TextColors.YELLOW, "Already max level in this skill!")
                                ))
                                .build()
                    ));

                }

                index++;

            }

        }

        container.setInitialState(candyPage);
        container.launchFor(player);

    }

}
