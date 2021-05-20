package com.lypaka.pixelskills.Utils;

import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Config.Getters.LevelLockedRewardGetters;
import com.lypaka.pixelskills.Config.Getters.PerkGetters;
import com.lypaka.pixelskills.Config.Getters.RewardGetters;
import com.lypaka.pixelskills.FancyFeatures.FancyFeaturesGetters;
import com.lypaka.pixelskills.FancyFeatures.FancyFeaturesHandler;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Map;

public class MessageHandlers {

    public static String getEXPGained (int folder) {

        return ConfigManager.getConfigNode(folder, 0, "Messages", "EXP-Gained").getString();

    }

    public static String getLvlUp (int folder) {

        return ConfigManager.getConfigNode(folder, 0, "Messages", "Level-Up").getString();

    }

    public static String getPerkMessage (int folder, String task) {

        switch (folder) {

            case 0:
            case 3:
            case 4:
            case 6:
            case 7:
            case 10:
                return ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", task, "Send-Message").getString();

            case 1:
                return ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifier", "Modifier", "Send-Message").getString();

            case 2:
            case 11:
                return ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", task, "Send-Message").getString();

            case 5:
            case 8:
            case 9:
            case 14:
                return ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Send-Message").getString();

            case 13:
                return ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catch-Rate-Modifier", "Send-Message").getString();

            case 12:
                return ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifiers", "Send-Message").getString();


        }

        return "";

    }

    public static String getCustomPerkMessage (int folder, int perkNum) throws ObjectMappingException {

        Map<String, String> map = PerkGetters.getCustomPerkMap(folder, perkNum);

        return map.get("Send-Message");

    }

    public static String getRewardMessage (int folder, int rewardNum) throws ObjectMappingException {

        Map<String, String> map = RewardGetters.getRewardMap(folder, rewardNum);

        return map.get("Send-Message");

    }

    public static String getLLRewardMessage (int folder, int num, int option) throws ObjectMappingException {

        Map<String, String> map = LevelLockedRewardGetters.getRewardMap(folder, num, option);

        return map.get("Send-Message");

    }

    public static void sendEXPMessages (Player player, double exp, String skill) throws ObjectMappingException {

        String value;
        int conf = GeneralGetters.getConfigFromSkill(skill);
        if (exp != 1) {

            value = "points";

        } else {

            value = "point";

        }

        if (GeneralGetters.areMessagesEnabled(skill, "EXP")) {

            if (AccountsHandler.areMessagesEnabled(player, skill)) {

                player.sendMessage(FancyText.getFancyText(MessageHandlers.getEXPGained(11)
                        .replace("points", value)
                        .replace("%exp%", String.valueOf(exp))
                        .replace("%skill%", skill)
                        .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                ));

            }

        }

        if (FancyFeaturesGetters.showActionBar(conf, "EXP")) {

            FancyFeaturesHandler.sendActionBar(player, conf, "EXP");

        }
        if (FancyFeaturesGetters.showBossBar(conf)) {

            FancyFeaturesHandler.displayBar(player, skill);

        }
        if (FancyFeaturesGetters.sendTitleMessages(conf, "EXP")) {

            FancyFeaturesHandler.sendTitle(player, conf, "EXP");

        }
        if (FancyFeaturesGetters.doEffects(conf, "EXP")) {

            FancyFeaturesHandler.spawnParticles(player, conf, "EXP");

        }
        if (FancyFeaturesGetters.playSounds(conf, "EXP")) {

            FancyFeaturesHandler.playSound(player, conf, "EXP");

        }

    }

}
