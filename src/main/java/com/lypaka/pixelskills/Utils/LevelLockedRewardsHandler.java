package com.lypaka.pixelskills.Utils;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Config.Getters.LevelLockedRewardGetters;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LevelLockedRewardsHandler {

    public static void triggerLevelLockedRewards (int folder, Player player) throws ObjectMappingException {

        String skill = GeneralGetters.getSkillFromConfigNumber(folder);

        if (LevelLockedRewardGetters.llRewardsEnabled(folder)) {

            List<Integer> list = LevelLockedRewardGetters.getLevels(folder);
            int level = AccountsHandler.getLevel(skill, player);

            for (int lvl : list) {

                if (lvl == level) {

                    giveReward(folder, player, lvl);
                    break;

                }

            }

        }

    }

    private static void giveReward (int folder, Player player, int rewardNum) throws ObjectMappingException {

        String skill = GeneralGetters.getSkillFromConfigNumber(folder);
        Map<String, Map<String, String>> optionsMap = ConfigManager.getConfigNode(folder, 4, "Level-" + AccountsHandler.getLevel(skill, player)).getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        int option = 1;
        Map<String, Double> chances = new HashMap<>();
        Map<String, String> innerMap;

        for (Map.Entry<String, Map<String, String>> entry : optionsMap.entrySet()) {

            String opt = entry.getKey();
            innerMap = entry.getValue();
            //chances.add(Double.parseDouble(innerMap.get("Chance")));
            chances.put(opt, Double.parseDouble(innerMap.get("Chance")));

        }

        double chanceSum = chances.values().stream().mapToDouble(c -> c).sum();
        double rngChance = chanceSum * new Random().nextDouble();

        for (Map.Entry<String, Double> entry : chances.entrySet()) {

            if (Double.compare(rngChance, entry.getValue()) <= 0) {

                String[] o = entry.getKey().split("-");
                option = Integer.parseInt(o[1]);
                break;

            } else {

                rngChance -= entry.getValue();

            }

        }

        Map<String, String> map = LevelLockedRewardGetters.getRewardMap(folder, rewardNum, option);
        int amount = Integer.parseInt(map.get("Amount"));
        String[] modifier = map.get("Modifier").split(" ");
        String prizeType = map.get("Prize-Type").toLowerCase();
        String prize = map.get("Prize");
        String function = modifier[0];
        double modNum;
        double result = 0;



        if (modifier[1].equalsIgnoreCase("%player-level%")) {

            modNum = AccountsHandler.getLevel(skill, player);

        } else {

            modNum = Double.parseDouble(modifier[1]);

        }

        switch (function) {

            case "add":
                result = amount + modNum;
                break;

            case "multiply":
                result = amount * modNum;
                break;

        }

        switch (prizeType) {

            case "item":
                if (prize.contains(", ")) {

                    String[] prizes = prize.split(", ");

                    for (String prz : prizes) {

                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + prz + " " + (int) result);
                        player.sendMessage(FancyText.getFancyText(MessageGetters.getLLRewardMessage(folder, rewardNum, option)
                                .replace("%number%", String.valueOf(result))
                                .replace("%prize%", prz)
                        ));

                    }

                } else {

                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + prize + " " + (int) result);
                    player.sendMessage(FancyText.getFancyText(MessageGetters.getLLRewardMessage(folder, rewardNum, option)
                            .replace("%number%", String.valueOf(result))
                            .replace("%prize%", prize)
                    ));

                }
                break;

            case "pokemon":

                if (prize.contains(", ")) {

                    String[] prizes = prize.split(", ");

                    for (String prz : prizes) {

                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + player.getName() + " " + prz);
                        player.sendMessage(FancyText.getFancyText(MessageGetters.getLLRewardMessage(folder, rewardNum, option)
                                .replace("%prize%", prz)
                        ));

                    }

                } else {

                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + player.getName() + " " + prize);
                    player.sendMessage(FancyText.getFancyText(MessageGetters.getLLRewardMessage(folder, rewardNum, option)
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

                player.sendMessage(FancyText.getFancyText(MessageGetters.getLLRewardMessage(folder, rewardNum, option)));
                break;

        }

    }

}
