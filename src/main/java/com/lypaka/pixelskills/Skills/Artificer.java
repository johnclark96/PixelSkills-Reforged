package com.lypaka.pixelskills.Skills;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.EXPGetters;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.AnvilEvent;
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.CraftItemEvent;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Artificer {

    //ContainerBrewingStand
    //ContainerRepair
    //ContainerMechanicalAnvil           not possible with current code as of 8.1.2

    private static double base;
    private static double applyMod;

    @SubscribeEvent
    public void onCraft (PlayerContainerEvent.Close event) throws IOException, ObjectMappingException {

        if (event.getContainer().toString().contains("net.minecraft.inventory.ContainerWorkbench") || event.getContainer().toString().contains("ContainerPlayer")) {

            if (GeneralGetters.isSkillEnabled(1)) {

                if (Sponge.getServer().getPlayer(event.getEntityPlayer().getName()).isPresent()) {

                    Player player = Sponge.getServer().getPlayer(event.getEntityPlayer().getName()).get();

                    if (GeneralGetters.getSkillPerm(1).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(1))) {

                        if (AccountsHandler.getCraftedQuantity(player) != 0) {

                            ExperienceHandler.didArtificerTask(player, "Crafting-Items");
                            ConfigManager.savePlayer(player.getUniqueId());

                        }

                    }

                }

            }

        } else if (event.getContainer().toString().contains("ContainerBrewingStand")) {

            if (GeneralGetters.isSkillEnabled(1)) {

                if (GeneralGetters.isTaskEnabled(1, "Brewing-Potions")) {

                    if (Sponge.getServer().getPlayer(event.getEntityPlayer().getName()).isPresent()) {

                        Player player = Sponge.getServer().getPlayer(event.getEntityPlayer().getName()).get();

                        if (GeneralGetters.getSkillPerm(1).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(1))) {

                            if (AccountsHandler.getCraftedQuantity(player) != 0) {

                                ExperienceHandler.didArtificerTask(player, "Brewing-Potions");
                                ConfigManager.savePlayer(player.getUniqueId());

                            }

                        }

                    }

                }

            }

        }



    }

    @SubscribeEvent
    public void onBrew (PlayerBrewedPotionEvent event) throws ObjectMappingException, IOException {

        if (GeneralGetters.isSkillEnabled(1)) {

            if (GeneralGetters.isTaskEnabled(1, "Brewing-Potions")) {

                if (Sponge.getServer().getPlayer(event.getEntityPlayer().getName()).isPresent()) {

                    Player player = Sponge.getServer().getPlayer(event.getEntityPlayer().getName()).get();

                    if (GeneralGetters.getSkillPerm(1).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(1))) {

                        if (!getBlackList("Brewing").contains(event.getStack().getDisplayName())) {

                            if (getWhitelist("Brewing").containsKey(event.getStack().getDisplayName())) {

                                for (Map.Entry<String, Double> entry : getWhitelist("Brewing").entrySet()) {

                                    if (entry.getKey().equalsIgnoreCase(event.getStack().getDisplayName())) {

                                        base = entry.getValue();
                                        break;

                                    }

                                }

                                applyMod = event.getStack().getCount() * base;
                                double result = AccountsHandler.getCraftedQuantity(player) + applyMod;
                                AccountsHandler.setCraftedQuantity(player, result);


                            } else {

                                double mod = EXPGetters.getEXPFromTask(1, "Brewing-Potions", player);
                                double applyMod = mod * event.getStack().getCount();
                                double result = AccountsHandler.getCraftedQuantity(player) + applyMod;
                                AccountsHandler.setCraftedQuantity(player, result);

                            }

                        }

                    }

                }

            }

        }

    }

    @SubscribeEvent
    public void onAnvil (AnvilRepairEvent event) throws ObjectMappingException {

        if (GeneralGetters.isSkillEnabled(1)) {

            if (GeneralGetters.isTaskEnabled(1, "Using-Anvil")) {

                if (Sponge.getServer().getPlayer(event.getEntityPlayer().getName()).isPresent()) {

                    Player player = Sponge.getServer().getPlayer(event.getEntityPlayer().getName()).get();

                    if (GeneralGetters.getSkillPerm(1).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(1))) {

                        if (!getBlackList("Anvil").contains(event.getItemInput().getDisplayName()) ||
                                !getBlackList("Anvil").contains(event.getIngredientInput().getDisplayName()) ||
                                !getBlackList("Anvil").contains(event.getItemResult().getDisplayName())) {

                            if (getWhitelist("Anvil").containsKey(event.getItemInput().getDisplayName()) ||
                                    getWhitelist("Anvil").containsKey(event.getItemResult().getDisplayName()) ||
                                    getWhitelist("Anvil").containsKey(event.getIngredientInput().getDisplayName())) {

                                for (Map.Entry<String, Double> entry : getWhitelist("Anvil").entrySet()) {

                                    if (entry.getKey().equalsIgnoreCase(event.getIngredientInput().getDisplayName()) ||
                                            entry.getKey().equalsIgnoreCase(event.getItemInput().getDisplayName()) ||
                                            entry.getKey().equalsIgnoreCase(event.getItemResult().getDisplayName())) {

                                        base = entry.getValue();
                                        break;

                                    }

                                }

                                applyMod = event.getItemResult().getCount() * base;
                                double result = AccountsHandler.getCraftedQuantity(player) + applyMod;
                                AccountsHandler.setCraftedQuantity(player, result);

                            } else {

                                double mod = EXPGetters.getEXPFromTask(1, "Brewing-Potions", player);
                                double applyMod = mod * event.getItemResult().getCount();
                                double result = AccountsHandler.getCraftedQuantity(player) + applyMod;
                                AccountsHandler.setCraftedQuantity(player, result);

                            }

                        }

                    }

                }

            }

        }

    }

    @SubscribeEvent
    public void onPixelAnvil (AnvilEvent.FinishedSmith event) throws ObjectMappingException, IOException {

        if (GeneralGetters.isSkillEnabled(1)) {

            if (GeneralGetters.isTaskEnabled(1, "Using-Pixelmon-Anvil")) {

                if (!Sponge.getServer().getPlayer(event.player.getName()).isPresent()) return;

                Player player = (Player) event.player;

                if (GeneralGetters.getSkillPerm(1).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(1))) {

                    String item = event.getItem().toString()
                            .replace("ItemPokeballLid{Name=", "")
                            .replace("}", "");

                    if (!getBlackList("Pixelmon-Anvil").contains(item)) {

                        if (getWhitelist("Pixelmon-Anvil").containsKey(item)) {

                            for (Map.Entry<String, Double> entry : getWhitelist("Pixelmon-Anvil").entrySet()) {

                                if (entry.getKey().equalsIgnoreCase(item)) {

                                    base = entry.getValue();
                                    break;

                                }

                            }

                        } else {

                            base = EXPGetters.getEXPFromTask(1, "Using-Pixelmon-Anvil", player);

                        }
                        ExperienceHandler.didArtificerTask(player, "Using-Pixelmon-Anvil");

                    }

                }

            }

        }

    }



    /**
     * check tasks, if crafted item qualifies as EXP giver, add to quantity of items applying EXP modifiers to the quantity of individual items
     * (The math logic is correct, trust me. I spent 30 minutes typing it all out)
     *
     * @param event
     * @param player
     */

    @Listener
    public void onCraft (CraftItemEvent.Craft event, @Root Player player) throws ObjectMappingException {

        if (!Sponge.getServer().getPlayer(player.getName()).isPresent()) return;

        if (GeneralGetters.isSkillEnabled(1)) {

            if (GeneralGetters.isTaskEnabled(1, "Crafting-Items")) {

                if (GeneralGetters.getSkillPerm(1).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(1))) {

                    String item = event.getCrafted().getType().getName();
                    if (!getBlackList("Crafting").contains(item)) {

                        if (getWhitelist("Crafting").containsKey(item)) {

                            for (Map.Entry<String, Double> entry : getWhitelist("Crafting").entrySet()) {

                                if (entry.getKey().equalsIgnoreCase(item)) {

                                    base = entry.getValue();
                                    break;

                                }

                            }
                            applyMod = event.getCrafted().getQuantity() * base;
                            double result = AccountsHandler.getCraftedQuantity(player) + applyMod;
                            AccountsHandler.setCraftedQuantity(player, result);


                        } else {

                            double mod = EXPGetters.getEXPFromTask(1, "Crafting-Items", player);
                            double applyMod = mod * event.getCrafted().getQuantity();
                            double result = AccountsHandler.getCraftedQuantity(player) + applyMod;
                            AccountsHandler.setCraftedQuantity(player, result);

                        }

                    }

                }

            }

        }

    }

    private static List<String> getBlackList (String task) throws ObjectMappingException {

        return ConfigManager.getConfigNode(1, 2, "Perk-Setting-Modifiers", "Item-Lists", task + "-Blacklist").getList(TypeToken.of(String.class));

    }

    private static Map<String, Double> getWhitelist (String task) throws ObjectMappingException {

        return ConfigManager.getConfigNode(1, 2, "Perk-Setting-Modifiers", "Item-Lists", task + "-Whitelist").getValue(new TypeToken<Map<String, Double>>() {});

    }

    public static double getPixelAnvilEXP() {
        return base;
    }
}
