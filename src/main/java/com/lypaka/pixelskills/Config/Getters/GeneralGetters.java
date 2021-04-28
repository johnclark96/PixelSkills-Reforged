package com.lypaka.pixelskills.Config.Getters;

import com.lypaka.pixelskills.Config.ConfigManager;

public class GeneralGetters {

    public static boolean areMessagesEnabled (String skill, String type) {

        int folder = getConfigFromSkill(skill);
        return ConfigManager.getConfigNode(folder, 0, "Messages", "Enable", type).getBoolean();

    }

    public static void setSkillStatus (String skill, boolean status) {

        int folder = getConfigFromSkill(skill);
        ConfigManager.getConfigNode(folder, 0, "Toggle", "Skill-Enabled").setValue(status);
        ConfigManager.save();

    }

    public static boolean isSkillEnabled (int folder) {

        return ConfigManager.getConfigNode(folder, 0, "Toggle", "Skill-Enabled").getBoolean();

    }

    public static String getSkillPerm (int folder) {

        return ConfigManager.getConfigNode(folder, 0, "Access-Permission").getString();

    }

    public static boolean isTaskEnabled (int folder, String task) {

        return ConfigManager.getConfigNode(folder, 0, "Tasks", task, "Task-Enabled").getBoolean();

    }

    public static int getMaxLevel (int folder) {

        return ConfigManager.getConfigNode(folder, 0, "Max-Level").getInt();

    }

    public static String getSkillFromConfigNumber (int folder) {

        switch (folder) {
            case 0:
                return "Archaeologist";
            case 1:
                return "Artificer";
            case 2:
                return "Barterer";
            case 3:
                return "Botanist";
            case 4:
                return "Breeder";
            case 5:
                return "Caregiver";
            case 6:
                return "Collector";
            case 7:
                return "Conqueror";
            case 8:
                return "Darwinist";
            case 9:
                return "Fisherman";
            case 10:
                return "Gladiator";
            case 11:
                return "Harvester";
            case 12:
                return "Looter";
            case 13:
                return "Scanner";
            case 14:
                return "Trader";

        }

        return null;

    }

    public static int getConfigFromSkill (String skill) {

        switch (skill) {

            case "Archaeologist":
                return 0;
            case "Artificer":
                return 1;
            case "Barterer":
                return 2;
            case "Botanist":
                return 3;
            case "Breeder":
                return 4;
            case "Caregiver":
                return 5;
            case "Collector":
                return 6;
            case "Conqueror":
                return 7;
            case "Darwinist":
                return 8;
            case "Fisherman":
                return 9;
            case "Gladiator":
                return 10;
            case "Harvester":
                return 11;
            case "Looter":
                return 12;
            case "Scanner":
                return 13;
            case "Trader":
                return 14;

        }

        return 0;

    }

    public static boolean doGladiatorEXPScaling() {

        return ConfigManager.getConfigNode(10, 0, "EXP-Scaling").getBoolean();

    }

    public static boolean updateAccounts (int conf) {

        return ConfigManager.getConfigNode(conf, 0, "Update-Player-Accounts-To-New-Automated-System").getBoolean();

    }

}
