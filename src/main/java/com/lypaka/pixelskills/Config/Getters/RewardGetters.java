package com.lypaka.pixelskills.Config.Getters;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Map;

public class RewardGetters {

    public static void setRewardStatus (String skill, boolean status) {

        int folder = GeneralGetters.getConfigFromSkill(skill);
        ConfigManager.getConfigNode(folder, 0, "Toggle", "Rewards", "Enabled").setValue(status);
        ConfigManager.save();

    }

    public static boolean rewardsEnabled (int folder) {

        return ConfigManager.getConfigNode(folder, 0, "Toggle", "Rewards-Enabled").getBoolean();

    }

    public static String getRewardMode (int folder) {

        return ConfigManager.getConfigNode(folder, 3, "General-Settings", "Chance-Mode").getString();

    }

    public static double getDefaultRewardChance (int folder) {

        return ConfigManager.getConfigNode(folder, 3, "General-Settings", "Chance-To-Trigger").getDouble();

    }

    public static int getActivationLevel (int folder) {

        return ConfigManager.getConfigNode(folder, 3, "General-Settings", "Level-Activation").getInt();

    }

    public static int getRewardInterval (int folder) {

        return ConfigManager.getConfigNode(folder, 3, "General-Settings", "Level-Interval").getInt();

    }

    public static boolean chanceIncreases (int folder) {

        return ConfigManager.getConfigNode(folder, 3, "Reward-Chance", "Chance-Gets-Higher-As-Level-Gets-Higher").getBoolean();

    }

    public static String getModifier (int folder) {

        return ConfigManager.getConfigNode(folder, 3, "Reward-Chance", "Modifier").getString();

    }

    public static int getOptions (int folder) {

        return ConfigManager.getConfigNode(folder, 3, "Reward-Options", "Amount").getInt();

    }

    public static Map<String, String> getRewardMap (int folder, int rewardNum) throws ObjectMappingException {

        return ConfigManager.getConfigNode(folder, 3, "Reward-Options", "Reward-" + rewardNum).getValue(new TypeToken<Map<String, String>>() {});

    }

}
