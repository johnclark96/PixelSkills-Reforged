package com.lypaka.pixelskills.Utils;

import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Config.Getters.LevelLockedRewardGetters;
import com.lypaka.pixelskills.Config.Getters.RewardGetters;
import net.minecraft.item.Item;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RewardsHandler {

    private static ItemStack stack;

    public static void triggerRewards (int folder, Player player) throws ObjectMappingException, IOException {

        // Check if rewards are enabled for the provided skill
        String skill = GeneralGetters.getSkillFromConfigNumber(folder);
        if (RewardGetters.rewardsEnabled(folder)) {

            // Check if player's current level is a reward level

            int level = AccountsHandler.getLevel(skill, player);

            // Checks Level Locked Rewards to execute appropriately
            if (!LevelLockedRewardGetters.doDuplicateRewards(folder)) {

                List<Integer> list = LevelLockedRewardGetters.getLevels(folder);
                for (int lvl : list) {

                    if (lvl == level) {

                        // Sets the Next-Reward-Level so the Rewards module doesn't break
                        AccountsHandler.setNextRewardLevel(folder, player);
                        return;
                    }

                }

            }

            if (RewardGetters.getActivationLevel(folder) == level) {

                // Run chance code
                double rng = new Random().nextDouble();

                // Player is on Reward activation level, so "next reward level" is not set yet, so use default chance
                double chance = RewardGetters.getDefaultRewardChance(folder);

                if (rng <= chance) {

                    // Give reward
                    int options = RewardGetters.getOptions(folder);
                    Map<Integer, Double> triggerChanceMap = new HashMap<>();

                    for (int i = 1; i <= options; i++) {

                        triggerChanceMap.put(i, ConfigManager.getConfigNode(folder, 3, "Reward-Options", "Reward-" + i, "Chance").getDouble());

                    }

                    double triggerChanceSum = triggerChanceMap.values().stream().mapToDouble(c -> c).sum();
                    double chanceSelected = triggerChanceSum * new Random().nextDouble();
                    int reward = 0;

                    for (Map.Entry<Integer, Double> entry : triggerChanceMap.entrySet()) {

                        if (Double.compare(chanceSelected, entry.getValue()) <= 0) {

                            reward = entry.getKey();
                            break;

                        } else {

                            chanceSelected -= entry.getValue();

                        }

                    }

                    giveReward(folder, player, reward);

                }

                String mode = RewardGetters.getRewardMode(folder);
                if (mode.equals("player")) {

                    AccountsHandler.setNextRewardChance(folder, player);

                }

                AccountsHandler.setNextRewardLevel(folder, player);


            } else if (AccountsHandler.getNextRewardLevel(folder, player) == level) {

                // Run chance code
                double rng = new Random().nextDouble();
                double chance = 0;
                String mode = RewardGetters.getRewardMode(folder);

                switch (mode) {

                    case "default":
                        chance = RewardGetters.getDefaultRewardChance(folder);
                        break;

                    case "player":
                        chance = AccountsHandler.getRewardChance(skill, player);
                        break;

                }

                if (rng <= chance) {

                    // Give reward
                    int options = RewardGetters.getOptions(folder);
                    Map<Integer, Double> triggerChanceMap = new HashMap<>();

                    for (int i = 1; i <= options; i++) {

                        triggerChanceMap.put(i, ConfigManager.getConfigNode(folder, 3, "Reward-Options", "Reward-" + i, "Chance").getDouble());

                    }

                    double triggerChanceSum = triggerChanceMap.values().stream().mapToDouble(c -> c).sum();
                    double chanceSelected = triggerChanceSum * new Random().nextDouble();
                    int reward = 0;

                    for (Map.Entry<Integer, Double> entry : triggerChanceMap.entrySet()) {

                        if (Double.compare(chanceSelected, entry.getValue()) <= 0) {

                            reward = entry.getKey();
                            break;

                        } else {

                            chanceSelected -= entry.getValue();

                        }

                    }

                    giveReward(folder, player, reward);

                }

                if (mode.equals("player")) {

                    AccountsHandler.setNextRewardChance(folder, player);

                }

                AccountsHandler.setNextRewardLevel(folder, player);

            }

        }

    }


    private static void giveReward (int folder, Player player, int rewardNum) throws ObjectMappingException {

        String skill = GeneralGetters.getSkillFromConfigNumber(folder);
        Map<String, String> map = RewardGetters.getRewardMap(folder, rewardNum);
        int amount = Integer.parseInt(map.get("Amount"));
        String modifierString = map.get("Modifier");
        double modifier = ModifierHandler.eval(modifierString.replace("%player-level%", String.valueOf(AccountsHandler.getLevel(skill, player))));
        String prizeType = map.get("Prize-Type").toLowerCase();
        String prize = map.get("Prize");
        String function = map.get("Function");
        double result = 0;

        switch (function) {

            case "add":
                result = amount + modifier;
                break;

            case "multiply":
                result = amount * modifier;
                break;

        }

        switch (prizeType) {

            case "item":
                if (prize.contains(", ")) {

                    String[] prizes = prize.split(", ");

                    for (String prz : prizes) {

                        //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + prz + " " + (int) result);
                        stack = ItemStack.builder()
                                .fromItemStack((ItemStack) (Object) (new net.minecraft.item.ItemStack(Item.getByNameOrId(prz))))
                                .quantity((int) result)
                                .build();
                        player.getInventory().offer(stack);
                        player.sendMessage(FancyText.getFancyText(MessageHandlers.getRewardMessage(folder, rewardNum)
                                .replace("%number%", String.valueOf((int) result))
                                .replace("%prize%", prz)
                        ));

                    }

                } else {

                    //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + prize + " " + (int) result);
                    stack = ItemStack.builder()
                            .fromItemStack((ItemStack) (Object) (new net.minecraft.item.ItemStack(Item.getByNameOrId(prize))))
                            .quantity((int) result)
                            .build();
                    player.getInventory().offer(stack);
                    player.sendMessage(FancyText.getFancyText(MessageHandlers.getRewardMessage(folder, rewardNum)
                            .replace("%number%", String.valueOf((int) result))
                            .replace("%prize%", prize)
                    ));

                }
                break;

            case "pokemon":

                if (prize.contains(", ")) {

                    String[] prizes = prize.split(", ");

                    for (String prz : prizes) {

                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + player.getName() + " " + prz);
                        player.sendMessage(FancyText.getFancyText(MessageHandlers.getRewardMessage(folder, rewardNum)
                                .replace("%prize%", prz)
                        ));

                    }

                } else {

                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + player.getName() + " " + prize);
                    player.sendMessage(FancyText.getFancyText(MessageHandlers.getRewardMessage(folder, rewardNum)
                            .replace("%prize%", prize)
                    ));

                }
                break;

            case "command":

                if (prize.contains(", ")) {

                    String[] prizes = prize.split(", ");

                    for (String prz : prizes) {

                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), prz.replace("%player%", player.getName()));

                    }

                } else {

                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), prize.replace("%player%", player.getName()));

                }

                player.sendMessage(FancyText.getFancyText(MessageHandlers.getRewardMessage(folder, rewardNum)));
                break;

        }

    }

}
