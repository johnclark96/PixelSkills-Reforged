package com.lypaka.pixelskills.Config.Getters;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class LevelLockedRewardGetters {

    public static void setLLStatus (String skill, boolean status) {

        int folder = GeneralGetters.getConfigFromSkill(skill);
        ConfigManager.getConfigNode(folder, 0, "Toggle", "Level-Locked-Rewards-Enabled").setValue(status);
        ConfigManager.save();

    }

    public static boolean llRewardsEnabled (int folder) {

        return ConfigManager.getConfigNode(folder, 0, "Toggle", "Level-Locked-Rewards-Enabled").getBoolean();

    }

    public static boolean doDuplicateRewards (int folder) {

        return ConfigManager.getConfigNode(folder, 4, "Duplicate-Rewards").getBoolean();

    }

    public static List<Integer> getLevels (int folder) throws ObjectMappingException {

        return ConfigManager.getConfigNode(folder, 4, "Levels-Triggered-On").getList(TypeToken.of(Integer.class));

    }

    public static Map<String, String> getRewardMap (int folder, int lvl, int option) throws ObjectMappingException {

        return ConfigManager.getConfigNode(folder, 4, "Level-" + lvl, "Option-" + option).getValue(new TypeToken<Map<String, String>>() {});

    }

}
