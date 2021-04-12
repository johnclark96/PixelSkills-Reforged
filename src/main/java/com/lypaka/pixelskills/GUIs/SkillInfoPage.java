package com.lypaka.pixelskills.GUIs;

import com.codehusky.huskyui.StateContainer;
import com.codehusky.huskyui.states.Page;
import com.codehusky.huskyui.states.action.ActionType;
import com.codehusky.huskyui.states.action.runnable.RunnableAction;
import com.codehusky.huskyui.states.element.ActionableElement;
import com.codehusky.huskyui.states.element.Element;
import com.google.common.collect.Lists;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class SkillInfoPage {

    private static NBTTagCompound tag = new NBTTagCompound();

    public static void openSkillInfoPage (Player player) {

        StateContainer container = new StateContainer();
        Page.PageBuilder main = Page.builder()
                .setAutoPaging(false)
                .setInventoryDimension(InventoryDimension.of(9, 5))
                .setTitle(Text.of(TextColors.YELLOW, "Skills Menu"))
                .setEmptyStack(EmptyPage.empty());

        for (int i = 0; i < 45; i++) {
            main.putElement(i, new Element(BorderPage.empty()));
        }

        int[] slots = new int[]{0, 2, 4, 6, 8, 18, 20, 22, 24, 26, 36, 38, 40, 42, 44};
        String[] skills = new String[]{"Archaeologist", "Artificer", "Barterer", "Botanist", "Breeder", "Caregiver", "Collector",
                            "Conqueror", "Darwinist", "Fisherman", "Gladiator", "Harvester", "Looter", "Scanner", "Trader"};

        String[] icons = new String[]{"pixelmon:fossil", "pixelmon:anvil", "pixelmon:red_vending_machine", "pixelmon:red_apricorn", "pixelmon:ranch", "pixelmon:potion",
                            "pixelmon:poke_ball", "pixelmon:charizardite_x", "pixelmon:water_stone", "pixelmon:super_rod", "pixelmon:choice_band", "pixelmon:crystal_pickaxe",
                            "pixelmon:poke_chest", "pixelmon:wide_lens", "pixelmon:trade_machine"};

        net.minecraft.item.ItemStack is;
        tag.setBoolean("HideTooltip", true);
        for (int i = 0; i < 15; i++) {

            is = new net.minecraft.item.ItemStack(Item.getByNameOrId(icons[i]));
            is.setTagCompound(tag);
            main.putElement(slots[i], new ActionableElement(
                    new RunnableAction(container, ActionType.CLOSE, "", c -> {player.closeInventory();}),
                    ItemStack.builder()
                            .fromItemStack((ItemStack) (Object) is)
                            .add(Keys.DISPLAY_NAME, Text.of(TextColors.GREEN, skills[i]))
                            .add(Keys.ITEM_LORE, Lists.newArrayList(
                                    Text.of(TextColors.YELLOW, "Level: " + AccountsHandler.getLevel(skills[i], player)),
                                    Text.of(TextColors.YELLOW, "EXP: " + AccountsHandler.getCurrentEXP(skills[i], player)),
                                    Text.of(TextColors.YELLOW, "Level-Up: " + AccountsHandler.getEXPToNextLvl(skills[i], player) + " EXP"),
                                    Text.of(""),
                                    Text.of(TextColors.AQUA, "Use ",TextColors.YELLOW, "\"/skills info skill " + skills[i] + "\"",TextColors.YELLOW, " to find out more about this skill!")
                            ))
                            .build()
            ));

        }

        container.setInitialState(main.build("main"));
        container.launchFor(player);

    }

}
