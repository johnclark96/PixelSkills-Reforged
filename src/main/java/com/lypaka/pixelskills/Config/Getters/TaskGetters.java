package com.lypaka.pixelskills.Config.Getters;

import com.lypaka.pixelskills.Config.ConfigManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskGetters {

    public static void setTaskStatus (String task, boolean status) {

        String skill = getSkillFromTask(task);
        int folder = GeneralGetters.getConfigFromSkill(skill);

        ConfigManager.getConfigNode(folder, 1, "Tasks", task, "Task-Enabled").setValue(status);
        ConfigManager.save();

    }

    public static String getSkillFromTask (String task) {

        switch (task) {

            case "Mining-Fossils":
            case "Reviving-Fossils":
                return "Archaeologist";

            case "Brewing-Potions":
            case "Crafting-Items":
            case "Using-Anvil":
            case "Using-Pixelmon-Anvil":
                return "Artificer";

            case "Buying-From-Shopkeepers":
            case "Selling-To-Shopkeepers":
                return "Barterer";

            case "Picking-Apricorns":
            case "Picking-Berries":
            case "Planting-Apricorns":
            case "Planting-Berries":
            case "Watering-Apricorns":
                return "Botanist";

            case "Hatching-Eggs":
            case "Making-Eggs":
                return "Breeder";

            case "Using-Healing-Items":
                return "Caregiver";

            case "Catching-Legendary-Pokemon":
            case "Catching-Normal-Pokemon":
            case "Catching-Shiny-Pokemon":
                return "Collector";

            case "Kill-Mega-Bosses":
            case "Kill-Normal-Bosses":
                return "Conqueror";

            case "Evolving-Pokemon":
                return "Darwinist";

            case "Catching-Fish":
                return "Fisherman";

            case "Defeating-NPC-Trainers":
            case "Defeating-Players":
            case "Defeating-Wild-Pokemon":
                return "Gladiator";

            case "Chopping-Wood":
            case "Mining-Blocks":
                return "Harvester";

            case "Opening-Poke-Loot-Chests":
                return "Looter";

            case "Scanning-Pokemon":
                return "Scanner";

            case "Trading-With-NPCs":
            case "Trading-With-Players":
                return "Trader";

        }

        return null;

    }

    public static List<String> getAllTasks() {

        List<String> tasks = new ArrayList<>();
        String[] taskArray;

        taskArray = new String[]{"Mining-Fossils", "Reviving-Fossils",
                "Brewing-Potions", "Crafting-Items", "Using-Anvil", "Using-Pixelmon-Anvil",
                "Buying-From-Shopkeepers", "Selling-To-Shopkeepers",
                "Picking-Apricorns", "Picking-Berries", "Planting-Apricorns", "Planting-Berries", "Watering-Apricorns",
                "Hatching-Eggs", "Making-Eggs",
                "Using-Healing-Items",
                "Catching-Legendary-Pokemon", "Catching-Normal-Pokemon", "Catching-Shiny-Pokemon",
                "Kill-Mega-Bosses", "Kill-Normal-Bosses",
                "Evolving-Pokemon",
                "Catching-Fish",
                "Defeating-NPC-Trainers", "Defeating-Players", "Defeating-Wild-Pokemon",
                "Chopping-Wood", "Mining-Blocks",
                "Opening-Poke-Loot-Chests",
                "Scanning-Pokemon",
                "Trading-With-NPCs", "Trading-With-Players",
        };

        Collections.addAll(tasks, taskArray);

        return tasks;

    }

    public static List<String> getTasks (String skill) {

        List<String> tasks = new ArrayList<>();
        String[] taskArray;

        switch (skill) {

            case "Archaeologist":

                taskArray = new String[]{"Mining-Fossils", "Reviving-Fossils"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(0, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Artificer":

                taskArray = new String[]{"Brewing-Potions", "Crafting-Items", "Using-Anvil", "Using-Pixelmon-Anvil"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(1, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Barterer":

                taskArray = new String[]{"Buying-From-Shopkeepers", "Buying-From-Vending-Machines", "Selling-To-Shopkeepers"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(2, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Botanist":

                taskArray = new String[]{"Picking-Apricorns", "Picking-Berries", "Planting-Apricorns", "Planting-Berries", "Watering-Apricorns"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(3, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Breeder":

                taskArray = new String[]{"Hatching-Eggs", "Making-Eggs"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(4, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Caregiver":

                taskArray = new String[]{"Using-Healing-Items"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(5, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Collector":

                taskArray = new String[]{"Catching-Legendary-Pokemon", "Catching-Normal-Pokemon", "Catching-Shiny-Pokemon"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(6, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Conqueror":

                taskArray = new String[]{"Kill-Mega-Bosses", "Kill-Normal-Bosses", "Kill-Totem-Pokemon"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(7, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Darwinist":

                taskArray = new String[]{"Evolving-Pokemon"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(8, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Fisherman":

                taskArray = new String[]{"Catching-Fish"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(9, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Gladiator":

                taskArray = new String[]{"Defeating-NPC-Trainers", "Defeating-Players", "Defeating-Wild-Pokemon"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(10, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Harvester":

                taskArray = new String[]{"Chopping-Wood", "Mining-Blocks"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(11, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Looter":

                taskArray = new String[]{"Opening-Poke-Loot-Chests"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(12, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Scanner":

                taskArray = new String[]{"Scanning-Pokemon"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(13, task)) {

                        tasks.add(task);

                    }

                }

                break;

            case "Trader":

                taskArray = new String[]{"Trading-With-NPCs", "Trading-With-Players"};

                for (String task : taskArray) {

                    if (GeneralGetters.isTaskEnabled(14, task)) {

                        tasks.add(task);

                    }

                }

                break;

        }

        return tasks;

    }

}
