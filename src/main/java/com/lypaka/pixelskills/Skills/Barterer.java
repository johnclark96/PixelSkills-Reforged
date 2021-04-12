package com.lypaka.pixelskills.Skills;

import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.ShopkeeperEvent;
import com.pixelmonmod.pixelmon.entities.npcs.NPCShopkeeper;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopItemWithVariation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

import java.io.IOException;
import java.util.List;

public class Barterer {

    private static int finalMod;
    private static String item;
    private static int quantity;
    private static int price;

    @SubscribeEvent
    public void onShopkeeper (ShopkeeperEvent.Purchase event) throws IOException, ObjectMappingException {

        //Buying from Shopkeepers task

        if (GeneralGetters.isSkillEnabled(2)) {

            if (GeneralGetters.isTaskEnabled(2, "Buying-From-Shopkeepers")) {

                EntityPlayerMP forgePlayer = event.getEntityPlayer();
                Player player = (Player) forgePlayer;

                if (GeneralGetters.getSkillPerm(2).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(2))) {

                    int req = ConfigManager.getConfigNode(2, 2, "Perk-Setting-Modifiers", "Task-Modifiers", "Buying-From-Shopkeepers", "Price-Requirement").getInt();
                    int spent = 0;

                    NPCShopkeeper shopKeep = (NPCShopkeeper) event.getNpc();
                    List<ShopItemWithVariation> list = shopKeep.getSellList(event.getEntityPlayer());
                    ItemStack stack = (ItemStack) (Object) (event.getItem());

                    for (ShopItemWithVariation item : list) {

                        if (item.getBaseShopItem().id.equalsIgnoreCase(stack.getType().getId())) {

                            spent = event.getItem().getCount() * item.getBuyCost();
                            break;

                        }

                    }

                    if (spent >= req) {

                        ExperienceHandler.didTask(2, "Buying-From-Shopkeepers", player);
                        ItemStack itemStack = (ItemStack) (Object) event.getItem();
                        setItem(itemStack.getType().getId());

                    }

                }

            }

        }

    }

    @SubscribeEvent
    public void onShopkeeperSell (ShopkeeperEvent.Sell event) throws IOException, ObjectMappingException {

        if (GeneralGetters.isSkillEnabled(2)) {

            if (GeneralGetters.isTaskEnabled(2, "Selling-To-Shopkeepers")) {

                EntityPlayerMP forgePlayer = event.getPlayer();
                Player player = (Player) forgePlayer;

                if (GeneralGetters.getSkillPerm(2).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(2))) {

                    int req = ConfigManager.getConfigNode(2, 2, "Perk-Setting-Modifiers", "Task-Modifiers", "Selling-To-Shopkeepers", "Price-Requirement").getInt();
                    int gained = 0;
                    NPCShopkeeper shopKeep = (NPCShopkeeper) event.getNpc();
                    List<ShopItemWithVariation> list = shopKeep.getSellList(event.getEntityPlayer());
                    ItemStack stack = (ItemStack) (Object) (event.getItem());

                    for (ShopItemWithVariation item : list) {

                        if (item.getBaseShopItem().id.equalsIgnoreCase(stack.getType().getId())) {

                            setPrice(item.getSellCost());
                            gained = event.getItem().getCount() * item.getSellCost();
                            break;

                        }

                    }

                    if (gained >= req) {

                        ExperienceHandler.didTask(2, "Selling-To-Shopkeepers", player);
                        setQuantity(event.getItem().getCount());
                        ItemStack itemStack = (ItemStack) (Object) event.getItem();
                        setItem(itemStack.getType().getId());

                    }

                }

            }

        }
        
    }

    private static void setItem (String thing) {
        item = thing;
    }

    public static String getItem() {

        return item;

    }

    private static void setPrice (int money) {

        price = money;

    }

    public static int getPrice() {

        return price;

    }

    private static void setQuantity (int value) {
        quantity = value;
    }

    public static int getQuantity() {
        return quantity;
    }
}
