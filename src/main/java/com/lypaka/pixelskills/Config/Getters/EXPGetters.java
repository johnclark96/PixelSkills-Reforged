package com.lypaka.pixelskills.Config.Getters;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;
import java.util.Map;

public class EXPGetters {

    public static String getEXPMode (int folder) {

        return ConfigManager.getConfigNode(folder, 1, "Base-Settings", "EXP-Mode").getString();

    }

    public static double getBaseEXP (int folder) {

        return ConfigManager.getConfigNode(folder, 1, "Calculation", "Base-Amount-For-Level-2").getDouble();

    }

    public static int getIncrementAmount (int folder) {

        return ConfigManager.getConfigNode(folder, 1, "Calculation", "Increase-Increment", "Amount").getInt();

    }

    public static String getModifier (int folder) {

        return ConfigManager.getConfigNode(folder, 1, "Calculation", "Increase-Increment", "Modifier").getString();

    }

    public static Map<String, Double> getEXPMap (int folder) throws ObjectMappingException {

        return ConfigManager.getConfigNode(folder, 1, "Manual-EXP-Mapping").getValue(new TypeToken<Map<String, Double>>() {});

    }

    public static Double getManualEXPAmount (int folder, int level) throws ObjectMappingException {

        Map<String, Double> map = getEXPMap(folder);
        return map.get("Level-" + level);

    }

    public static Map<String, Double> getModifiers (int folder, String task) throws ObjectMappingException {

        return ConfigManager.getConfigNode(folder, 0, "Tasks", task, "Modifiers").getValue(new TypeToken<Map<String, Double>>() {});

    }

    public static double getEXPFromTask (int folder, String task, Player player) throws ObjectMappingException {

        Map<String, Double> modifiers = getModifiers(folder, task);
        double mod = modifiers.get("default");

        for (Map.Entry<String, Double> entry : modifiers.entrySet()) {

            if (player.hasPermission(entry.getKey())) {

                mod = entry.getValue();
                break;

            }

        }

        double baseEXP = ConfigManager.getConfigNode(folder, 0, "Tasks", task, "EXP-Gained-Per-Task").getDouble();


        double exp = baseEXP * mod;
        return ExperienceHandler.getPrettyDouble(exp);

    }

    public static void setEXPToNextLevel (int folder, Player player) throws ObjectMappingException {

        String expMode = getEXPMode(folder);
        int lvl = AccountsHandler.getLevel(GeneralGetters.getSkillFromConfigNumber(folder), player);
        double base = getBaseEXP(folder);
        int amount = getIncrementAmount(folder);
        double newEXP = 0;
        Map<String, Double> map = getEXPMap(folder);

        switch (expMode) {

            case "calculated":

                String[] modifier = getModifier(folder).split(" ");
                int mod = 0;

                if (modifier[1].equalsIgnoreCase("%player-level%")) {

                    mod = lvl;

                } else {

                    mod = Integer.parseInt(modifier[1]);

                }

                String function = modifier[0];

                switch (function) {

                    case "add":
                        newEXP = amount + mod;
                        break;

                    case "multiply":
                        newEXP = amount * mod;
                        break;

                }

                AccountsHandler.setEXP(GeneralGetters.getSkillFromConfigNumber(folder), newEXP, player);
                break;

            case "fixed":

                AccountsHandler.setEXP(GeneralGetters.getSkillFromConfigNumber(folder), map.get("Level-" + lvl), player);
                break;

        }

    }

    public static List<String> getCollectorBlackList (String task) throws ObjectMappingException {

        return ConfigManager.getConfigNode(6, 2, "Perk-Setting-Modifiers", "Pokemon-Blacklist", task).getList(TypeToken.of(String.class));

    }

    public static boolean doBartererByQuantity() {

        return ConfigManager.getConfigNode(2, 2, "Perk-Setting-Modifiers", "Modify-EXP").getBoolean();

    }

}
