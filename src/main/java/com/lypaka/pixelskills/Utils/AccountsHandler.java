package com.lypaka.pixelskills.Utils;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Config.Getters.PerkGetters;
import com.lypaka.pixelskills.Config.Getters.RewardGetters;
import com.lypaka.pixelskills.PixelSkills;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class AccountsHandler {


    public static boolean areMessagesEnabled (Player player, String skill) throws ObjectMappingException {

        Map<String, Boolean> toggleMap = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "EXP-Messages").getValue(new TypeToken<Map<String, Boolean>>() {});
        System.out.println(skill);
        System.out.println(toggleMap);
        return toggleMap.get(skill);

    }

    public static int getLevel (String skill, Player player) {

        return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Level").getInt();

    }

    public static void setLevel (String skill, Player player, int level) {

        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Level").setValue(level);

    }

    public static double getCurrentEXP (String skill, Player player) {

        return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").getDouble();

    }

    public static boolean hasAccountGenerated (Player player) {

        if (ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Account-Generated").isVirtual()) {

            return false;

        } else {

            return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Account-Generated").getBoolean();

        }

    }

    public static void setNextRewardLevel (int conf, Player player) {

        String skill = GeneralGetters.getSkillFromConfigNumber(conf);
        int lvlInt = RewardGetters.getRewardInterval(conf);
        int newRewardLevel = lvlInt + getLevel(skill, player);
        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Next-Reward-Level").setValue(newRewardLevel);

    }

    public static int getNextRewardLevel (int conf, Player player) {
        
        String skill = GeneralGetters.getSkillFromConfigNumber(conf);
        return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Next-Reward-Level").getInt();
    }

    public static void setNextPerkLevel (int conf, Player player) {
        
        String skill = GeneralGetters.getSkillFromConfigNumber(conf);
        int lvlInt = PerkGetters.getLevelInterval(conf);
        int newRewardLevel = lvlInt + getLevel(skill, player);
        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Next-Perk-Level").setValue(newRewardLevel);

    }

    public static void setNextPerkLevelManual (int conf, Player player, int level) {

        String skill = GeneralGetters.getSkillFromConfigNumber(conf);
        int lvlInt = PerkGetters.getLevelInterval(conf);
        int lvl = lvlInt + level;
        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Next-Perk-Level").setValue(lvl);

    }

    public static int getNextPerkLevel (String skill, Player player) {

        return Optional.of(ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Next-Perk-Level").getInt()).orElse(0);

    }

    public static int getPerkChance (String skill, Player player) {

        return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Perk-Chance").getInt();

    }

    public static double getRewardChance (String skill, Player player) {

        return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Reward-Chance").getDouble();

    }

    public static void setNextPerkChanceManual (int conf, Player player, int value) {

        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", GeneralGetters.getSkillFromConfigNumber(conf), "Perk-Chance").setValue(value);
        ConfigManager.savePlayer(player.getUniqueId());

    }

    public static void setNextPerkChanceManual (int conf, Player player, double value) {

        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", GeneralGetters.getSkillFromConfigNumber(conf), "Perk-Chance").setValue(value);
        ConfigManager.savePlayer(player.getUniqueId());

    }

    public static void setNextPerkChance (int conf, Player player) {

        if (PerkGetters.getPerkMode(conf).equalsIgnoreCase("player")) {

            if (PerkGetters.chanceIncreases(conf)) {

                if (PerkGetters.getChanceValue(conf).equalsIgnoreCase("integer")) {

                    double baseChance = PerkGetters.getDefaultPerkChance(conf);
                    String[] modifier = PerkGetters.getModifier(conf).split(" ");
                    int mod;
                    if (modifier[1].equalsIgnoreCase("%player-level%")) {

                        mod = getLevel(GeneralGetters.getSkillFromConfigNumber(conf), player);

                    } else {

                        mod = Integer.parseInt(modifier[1]);

                    }
                    String function = modifier[0];
                    int result = 0;
                    switch (function) {

                        case "add":
                            result = (int) (baseChance + mod);
                            break;

                        case "multiply":
                            result = (int) (baseChance * mod);
                            break;

                    }

                    ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", GeneralGetters.getSkillFromConfigNumber(conf), "Perk-Chance").setValue(result);

                } else {

                    double baseChance = PerkGetters.getDefaultPerkChance(conf);
                    String[] modifier = PerkGetters.getModifier(conf).split(" ");
                    double mod;
                    if (modifier[1].equalsIgnoreCase("%player-level%")) {

                        mod = getLevel(GeneralGetters.getSkillFromConfigNumber(conf), player);

                    } else {

                        mod = Integer.parseInt(modifier[1]);

                    }
                    String function = modifier[0];
                    double result = 0;
                    switch (function) {

                        case "add":
                            result = baseChance + mod;
                            break;

                        case "multiply":
                            result = baseChance * mod;
                            break;

                    }

                    ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", GeneralGetters.getSkillFromConfigNumber(conf), "Perk-Chance").setValue(result);

                }

            } else {

                PixelSkills.INSTANCE.logger.error("PixelSkills has detected player mode, but chance does not increase!");

            }

        }

    }

    public static void setNextRewardChance (int conf, Player player) throws IOException {

        if (RewardGetters.getRewardMode(conf).equalsIgnoreCase("player")) {

            if (RewardGetters.chanceIncreases(conf)) {

                double baseChance = RewardGetters.getDefaultRewardChance(conf);
                String[] modifier = RewardGetters.getModifier(conf).split(" ");
                int mod;
                if (modifier[1].equalsIgnoreCase("%player-level%")) {

                    mod = getLevel(GeneralGetters.getSkillFromConfigNumber(conf), player);

                } else {

                    mod = Integer.parseInt(modifier[1]);

                }
                String function = modifier[0];
                double result = 0;
                switch (function) {

                    case "add":
                        result = baseChance + mod;
                        break;

                    case "multiply":
                        result = baseChance * mod;
                        break;

                }

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", GeneralGetters.getSkillFromConfigNumber(conf), "Reward-Chance").setValue(result);

            } else {

                PixelSkills.INSTANCE.logger.error("PixelSkills has detected player mode, but chance does not increase!");

            }

        }

    }

    public static void setEXP (String skill, double exp, Player player) {

        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP-To-Next-Level").setValue(exp);

    }

    public static double getEXPToNextLvl (String skill, Player player) {

        return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP-To-Next-Level").getDouble();

    }

    public static void setCraftedQuantity (Player player, double quantity){

        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Artificer", "Quantity").setValue(quantity);

    }

    public static void clearCraftedQuantity (Player player) {

        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Artificer", "Quantity").setValue(0);

    }

    public static double getCraftedQuantity (Player player) {

        return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Artificer", "Quantity").getDouble();

    }

    public static int getCatchrateModifier (Player player, String pokemon) throws ObjectMappingException {

        Map<String, Integer> map = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Catchrate-Modifiers").getValue(new TypeToken<Map<String, Integer>>() {});
        return map.get(pokemon);

    }

    public static void setCatchrateModifier (Player player, String pokemon, int rate) throws ObjectMappingException {

        Map<String, Integer> map = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Catchrate-Modifiers").getValue(new TypeToken<Map<String, Integer>>() {});
        map.put(pokemon, rate);
        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Catchrate-Modifiers").setValue(map);

    }

    public static int getLastPerkLevel (Player player, String skill, String task) {

        return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Last-Triggered-Level").getInt();

    }

    public static void setLastPerkLevel (Player player, String skill, String task, int level) {

        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Last-Triggered-Level").setValue(level);

    }

    public static boolean showConstantInfo (Player player) {

        return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Constantly-Display-Skill-Info").getBoolean();

    }

}
