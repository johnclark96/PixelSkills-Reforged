package com.lypaka.pixelskills.Utils.SkillCandy;

import com.google.common.collect.Lists;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import net.minecraft.item.Item;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;

public class CandyItem {

    public static ItemStack getSkillCandy (int quantity) {

        return ItemStack.builder()
                .fromItemStack((ItemStack) (Object) (new net.minecraft.item.ItemStack(Item.getByNameOrId("pixelmon:rare_candy"))))
                .add(Keys.DISPLAY_NAME, Text.of(TextColors.RED, "Skill Candy"))
                .add(Keys.ITEM_LORE, Lists.newArrayList(
                        Text.of(TextColors.YELLOW, "Levels up a skill of your choice by 1 level!"),
                        Text.of(""),
                        Text.of(TextColors.YELLOW, "Right click to use!")
                ))
                .quantity(quantity)
                .build();

    }

    public static void levelUpSkill (Player player, String skill) throws ObjectMappingException, IOException {

        int conf = GeneralGetters.getConfigFromSkill(skill);
        if (AccountsHandler.getLevel(skill, player) < GeneralGetters.getMaxLevel(conf)) {

            ExperienceHandler.levelUp(skill, player, true, true);
            ConfigManager.savePlayer(player.getUniqueId());

        }

    }

}
