package com.lypaka.pixelskills.GUIs;

import com.codehusky.huskyui.StateContainer;
import com.codehusky.huskyui.states.Page;
import com.codehusky.huskyui.states.action.ActionType;
import com.codehusky.huskyui.states.action.runnable.RunnableAction;
import com.codehusky.huskyui.states.element.ActionableElement;
import com.codehusky.huskyui.states.element.Element;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Commands.AdminCommand;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import com.lypaka.pixelskills.Utils.FancyText;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;

public class SkillInfoPage {

    public static void openSkillInfoPage (Player player) throws ObjectMappingException {

        StateContainer container = new StateContainer();
        int rows = ConfigManager.getBaseNode(0, "GUI", "Settings", "Rows").getInt();
        String title = ConfigManager.getBaseNode(0, "GUI", "Settings", "Title").getString();
        Page main = Page.builder()
                .setTitle(FancyText.getFancyText(title))
                .setInventoryDimension(InventoryDimension.of(9, rows))
                .setInventoryArchetype(InventoryArchetypes.CHEST)
                .setAutoPaging(false)
                .build("main");

        int slots = rows * 9;
        for (int i = 0; i < slots; i++) {

            main.getElements().put(i, new Element(getIcon(i, player)));

        }

        container.setInitialState(main);
        container.launchFor(player);

    }

    private static ItemStack getIcon (int slot, Player player) throws ObjectMappingException {

        String id = ConfigManager.getBaseNode(0, "GUI", "Slot-" + slot, "ID").getString();
        net.minecraft.item.ItemStack item = new net.minecraft.item.ItemStack(Item.getByNameOrId(id));

        if (!ConfigManager.getBaseNode(0, "GUI", "Slot-" + slot, "UnsafeData").isVirtual()) {

            int unsafe = ConfigManager.getBaseNode(0, "GUI", "Slot-" + slot, "UnsafeData").getInt();
            item.setItemDamage(unsafe);

        }

        ItemStack itemStack = ItemStack.builder()
                .fromItemStack((ItemStack) (Object) (item))
                .build();

        if (!ConfigManager.getBaseNode(0, "GUI", "Slot-" + slot, "Lore").isVirtual()) {

            List<String> loreString = ConfigManager.getBaseNode(0, "GUI", "Slot-" + slot, "Lore").getList(TypeToken.of(String.class));
            List<Text> lore = new ArrayList<>();
            for (String s : loreString) {

                String value;
                if (s.contains("%")) {

                    value = replacePlaceholder(s, player);

                } else {

                    value = s;

                }
                lore.add(FancyText.getFancyText(value));

            }

            itemStack.offer(Keys.ITEM_LORE, lore);

        }



        if (!ConfigManager.getBaseNode(0, "GUI", "Slot-" + slot, "Title").isVirtual()) {

            Text title = FancyText.getFancyText(ConfigManager.getBaseNode(0, "GUI", "Slot-" + slot, "Title").getString());
            itemStack.offer(Keys.DISPLAY_NAME, title);

        }

        return itemStack;

    }

    private static String replacePlaceholder (String placeholder, Player player) {

        String[] split = placeholder.replace("%", "").split("-");
        String skill = split[2];
        String value = split[1];

        String p = "none";

        switch (value) {

            case "level":
                p = placeholder
                        .replace("%player-level-" + skill + "%", String.valueOf(AccountsHandler.getLevel(AdminCommand.getPrettySkillName(skill), player)));
                break;

            case "exp":
                p = placeholder
                        .replace("%player-exp-" + skill + "%", String.valueOf(AccountsHandler.getCurrentEXP(AdminCommand.getPrettySkillName(skill), player)));
                break;

            case "up":
                p =  placeholder
                        .replace("%player-up-" + skill + "%", String.valueOf(AccountsHandler.getEXPToNextLvl(AdminCommand.getPrettySkillName(skill), player)));
                break;

        }

        return p;

    }

}
