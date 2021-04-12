package com.lypaka.pixelskills.Config.Getters;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.HashMap;
import java.util.Map;

public class PerkGetters {

    public static void setPerkStatus (String task, boolean status) {

        String skill = TaskGetters.getSkillFromTask(task);
        int folder = GeneralGetters.getConfigFromSkill(skill);

        ConfigManager.getConfigNode(folder, 1, "Tasks", task, "Perk-Enabled").setValue(status);
        ConfigManager.save();

    }

    public static boolean taskPerkEnabled (int folder, String task) {

        return ConfigManager.getConfigNode(folder, 0, "Tasks", task, "Perk-Enabled").getBoolean();

    }

    public static boolean perksEnabled (int folder) {

        return ConfigManager.getConfigNode(folder, 0, "Toggle", "Perks-Enabled").getBoolean();

    }

    public static String getPerkMode (int folder) {

        return ConfigManager.getConfigNode(folder, 2, "General-Settings", "Chance-Mode").getString();

    }

    public static double getDefaultPerkChance (int folder) {

        String value = getChanceValue(folder);
        if (value.equalsIgnoreCase("integer")) {

            return ConfigManager.getConfigNode(folder, 2, "General-Settings", "Chance-To-Trigger").getInt();

        } else {

            return ConfigManager.getConfigNode(folder, 2, "General-Settings", "Chance-To-Trigger").getDouble();

        }

    }

    public static int getActivationLevel (int folder) {

        return ConfigManager.getConfigNode(folder, 2, "General-Settings", "Level-Activation").getInt();

    }

    public static int getLevelInterval (int folder) {

        return ConfigManager.getConfigNode(folder, 2, "General-Settings", "Level-Interval").getInt();

    }

    public static boolean multiplePerksCanTrigger (int folder) {

        return ConfigManager.getConfigNode(folder, 2, "Perk-Chance-Settings", "Can-Perks-Trigger-Multiple-Times-Per-Level").getBoolean();

    }

    public static boolean chanceIncreases (int folder) {

        return ConfigManager.getConfigNode(folder, 2, "Perk-Chance-Settings", "Chance-Gets-Higher-As-Level-Gets-Higher").getBoolean();

    }

    public static String getModifier (int folder) {

        return ConfigManager.getConfigNode(folder, 2, "Perk-Chance-Settings", "Modifier").getString();

    }

    public static String getPerkRewardMode (int folder) {

        return ConfigManager.getConfigNode(folder, 2, "Perk-Settings", "Activate-Perk-Reward-Mode").getString();

    }

    public static int getCustomPerkAmount (int folder) {

        return ConfigManager.getConfigNode(folder, 2, "Perk-Settings", "Custom-Perks", "Amount").getInt();

    }

    public static Map<String, String> getCustomPerkMap (int folder, int perkNum) throws ObjectMappingException {

        return ConfigManager.getConfigNode(folder, 2, "Perk-Settings", "Custom-Perks", "Perk-" + perkNum).getValue(new TypeToken<Map<String, String>>() {});

    }

    public static Map<String, String> getPerkModifiers (int folder, String task) throws ObjectMappingException {

        Map<String, String> map = new HashMap<>();
        switch (folder) {

            case 0:
            case 3:
            case 4:
            case 6:
            case 7:
            case 10:
                map = ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", task).getValue(new TypeToken<Map<String, String>>() {});
                break;

            case 11:
            case 2:
                map = ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", task).getValue(new TypeToken<Map<String, String>>() {});
                break;

            case 1:
                map = ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifier", "Modifier").getValue(new TypeToken<Map<String, String>>() {});
                break;

            case 12:
                map = ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier").getValue(new TypeToken<Map<String, String>>() {});
                break;

            case 5:
            case 8:
            case 9:
            case 14:
                map = ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers").getValue(new TypeToken<Map<String, String>>() {});
                break;

            case 13:
                map = ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catch-Rate-Modifier").getValue(new TypeToken<Map<String, String>>() {});
                break;

        }

        return map;

    }

    public static String getChanceValue (int folder) {

        return ConfigManager.getConfigNode(folder, 2, "General-Settings", "Chance-Value").getString();

    }

}
