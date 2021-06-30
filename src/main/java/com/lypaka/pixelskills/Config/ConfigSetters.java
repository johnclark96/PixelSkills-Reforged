package com.lypaka.pixelskills.Config;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigSetters {

    public static void setTasksAndAccessPerm (int folder) {

        String skill = "";

        List<String> list = new ArrayList<>();

        if (ConfigManager.getConfigNode(folder, 0, "Messages", "Enable").isVirtual()) {

            ConfigManager.getConfigNode(folder, 0, "Messages", "Enable", "EXP").setValue(true);
            ConfigManager.getConfigNode(folder, 0, "Messages", "Enable", "Level-Up").setValue(true);

        }

        if (ConfigManager.getConfigNode(folder, 5, "Node-Generated").isVirtual()) {

            ConfigManager.getConfigNode(folder, 5, "Skill-Description").setValue(list);
            ConfigManager.getConfigNode(folder, 5, "Task-Descriptions", "Task-Name").setValue(list);
            ConfigManager.getConfigNode(folder, 5, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
            ConfigManager.getConfigNode(folder, 5, "Node-Generated").setValue(true);

            ConfigManager.saveSkill(folder, 5);

        }

        Map<String, Double> modifiersMap = new HashMap();
        modifiersMap.put("default", 1.0);

        switch (folder) {
            
            case 0:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Mining-Fossils", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Mining-Fossils", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Mining-Fossils", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Mining-Fossils", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Reviving-Fossils", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Reviving-Fossils", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Reviving-Fossils", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Reviving-Fossils", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);
                    
                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 1:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Brewing-Potions", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Brewing-Potions", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Brewing-Potions", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Brewing-Potions", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Crafting-Items", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Crafting-Items", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Crafting-Items", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Crafting-Items", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Anvil", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Anvil", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Anvil", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Anvil", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Pixelmon-Anvil", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Pixelmon-Anvil", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Pixelmon-Anvil", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Pixelmon-Anvil", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 2:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Buying-From-Shopkeepers", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Buying-From-Shopkeepers", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Buying-From-Shopkeepers", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Buying-From-Shopkeepers", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Selling-To-Shopkeepers", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Selling-To-Shopkeepers", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Selling-To-Shopkeepers", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Selling-To-Shopkeepers", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 3:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Picking-Apricorns", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Picking-Apricorns", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Picking-Apricorns", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Picking-Apricorns", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Picking-Berries", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Picking-Berries", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Picking-Berries", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Picking-Berries", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Planting-Apricorns", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Planting-Apricorns", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Planting-Apricorns", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Planting-Apricorns", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Planting-Berries", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Planting-Berries", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Planting-Berries", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Planting-Berries", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Watering-Apricorns", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Watering-Apricorns", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Watering-Apricorns", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Watering-Apricorns", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 4:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Hatching-Eggs", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Hatching-Eggs", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Hatching-Eggs", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Hatching-Eggs", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Making-Eggs", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Making-Eggs", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Making-Eggs", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Making-Eggs", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 5:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Healing-Items", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Healing-Items", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Healing-Items", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Using-Healing-Items", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 6:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Legendary-Pokemon", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Legendary-Pokemon", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Legendary-Pokemon", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Legendary-Pokemon", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Normal-Pokemon", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Normal-Pokemon", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Normal-Pokemon", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Normal-Pokemon", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Shiny-Pokemon", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Shiny-Pokemon", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Shiny-Pokemon", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Shiny-Pokemon", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 7:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Kill-Mega-Bosses", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Kill-Mega-Bosses", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Kill-Mega-Bosses", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Kill-Mega-Bosses", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Kill-Normal-Bosses", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Kill-Normal-Bosses", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Kill-Normal-Bosses", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Kill-Normal-Bosses", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 8:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Evolving-Pokemon", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Evolving-Pokemon", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Evolving-Pokemon", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Evolving-Pokemon", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 9:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Fish", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Fish", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Fish", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Catching-Fish", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 10:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-NPC-Trainers", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-NPC-Trainers", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-NPC-Trainers", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-NPC-Trainers", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-Players", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-Players", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-Players", "Perk-Enabled").setValue(false);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-Players", "Task-Enabled").setValue(false);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-Wild-Pokemon", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-Wild-Pokemon", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-Wild-Pokemon", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Defeating-Wild-Pokemon", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "EXP-Scaling").setComment("Set this to true to enable scaling the amount of EXP gained from killing Pokemon based on levels");
                    ConfigManager.getConfigNode(folder, 0, "EXP-Scaling").setValue(false);
                    ConfigManager.getConfigNode(folder, 0, "Scaling", "Equation").setComment("At this current time, this equation is not configurable! This is just here to show you how it does the math. Touching this will break it!");
                    ConfigManager.getConfigNode(folder, 0, "Scaling", "Equation").setValue("(%pokemon-level% / %player-level%) * 2");
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 11:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Chopping-Wood", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Chopping-Wood", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Chopping-Wood", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Chopping-Wood", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Mining-Blocks", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Mining-Blocks", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Mining-Blocks", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Mining-Blocks", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 12:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Opening-Poke-Loot-Chests", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Opening-Poke-Loot-Chests", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Opening-Poke-Loot-Chests", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Opening-Poke-Loot-Chests", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 13:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Photographing-Pokemon", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Photographing-Pokemon", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Photographing-Pokemon", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Photographing-Pokemon", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 14:
                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Learning-Moves", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Learning-Moves", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Learning-Moves", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Learning-Moves", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;

            case 15:

                if (ConfigManager.getConfigNode(folder, 0, "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Trading-With-NPCs", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Trading-With-NPCs", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Trading-With-NPCs", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Trading-With-NPCs", "Task-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Trading-With-Players", "EXP-Gained-Per-Task").setValue(1);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Trading-With-Players", "Perk-Enabled").setValue(true);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Trading-With-Players", "Modifiers").setValue(modifiersMap);
                    ConfigManager.getConfigNode(folder, 0, "Tasks", "Trading-With-Players", "Task-Enabled").setValue(true);
                    skill = GeneralGetters.getSkillFromConfigNumber(folder).toLowerCase();
                    ConfigManager.getConfigNode(folder, 0, "Access-Permission").setValue("pixelskills." + skill + ".unlocked");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 0, "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 0, "Update-Player-Accounts-To-New-Automated-System").setValue(false);

                }
                ConfigManager.saveSkill(folder, 0);
                break;
        }

        list.add("exampleblock:item_id");
        Map<String, Double> map = new HashMap<>();
        map.put("exampleblock:item_id", 1.1);
        List<String> pokemonList = new ArrayList<>();
        pokemonList.add("pokemonName");

        switch (folder) {

            case 0:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Mining-Fossils", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Mining-Fossils", "Function").setValue("add");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Mining-Fossils", "Modifier").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Mining-Fossils", "Send-Message").setValue("You found %number% more fossils grouped together with that one!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Reviving-Fossils", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Reviving-Fossils", "Function").setValue("add");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Reviving-Fossils", "Modifier").setValue("1.1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Reviving-Fossils", "Modify-Stats").setValue("hp, atk, def, satk, sdef, spd");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Reviving-Fossils", "Send-Message").setValue("That looks like a strong Pokemon!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Reviving-Fossils", "Amount").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Reviving-Fossils", "Amount").setValue("1");

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 1:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Item-Lists", "Anvil-Blacklist").setValue(list);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Item-Lists", "Anvil-Whitelist").setValue(map);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Item-Lists", "Brewing-Blacklist").setValue(list);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Item-Lists", "Brewing-Whitelist").setValue(map);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Item-Lists", "Crafting-Blacklist").setValue(list);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Item-Lists", "Crafting-Whitelist").setValue(map);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Item-Lists", "Pixelmon-Anvil-Blacklist").setValue(list);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Item-Lists", "Pixelmon-Anvil-Whitelist").setValue(map);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifier", "Modifier", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifier", "Modifier", "Function").setValue("add");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifier", "Modifier", "Modifier").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifier", "Modifier", "Send-Message").setValue("Making this item has been quite the learning experience!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 2:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    List<String> itemList = new ArrayList<>();
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Item-Blacklist").setValue(itemList);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modify-EXP").setValue(true);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Buying-From-Shopkeepers", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Buying-From-Shopkeepers", "Function").setValue("add");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Buying-From-Shopkeepers", "Modifier").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Buying-From-Shopkeepers", "Send-Message").setValue("You got a BOGO deal! You were given %number% extra items!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Selling-To-Shopkeepers", "Amount").setValue("%price%");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Selling-To-Shopkeepers", "Function").setValue("add");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Selling-To-Shopkeepers", "Modifier").setValue("1.1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Selling-To-Shopkeepers", "Send-Message").setValue("The shopkeeper mistakenly gave you %money% extra money!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Task-Modifiers", "Buying-From-Shopkeepers", "Price-Requirement").setValue(0);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Task-Modifiers", "Buying-From-Shopkeepers", "Price-Requirement").setComment("The amount of money players must spend to get EXP");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Task-Modifiers", "Selling-To-Shopkeepers", "Price-Requirement").setValue(0);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Task-Modifiers", "Selling-To-Shopkeepers", "Price-Requirement").setComment("The amount of money players must get to get EXP");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 3:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Picking-Apricorns", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Picking-Apricorns", "Function").setValue("add");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Picking-Apricorns", "Modifier").setValue("add 2");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Picking-Apricorns", "Send-Message").setValue("There were %number% extra Apricorns hidden behind that one!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Picking-Berries", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Picking-Berries", "Function").setValue("add");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Picking-Berries", "Modifier").setValue("add 3");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Picking-Berries", "Send-Message").setValue("There were %number% extra Berries hidden behind that one!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Planting-Apricorns", "Amount").setValue("2");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Planting-Apricorns", "Function").setValue("add");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Planting-Apricorns", "Modifier").setValue("add %player-level%");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Planting-Apricorns", "Send-Message").setValue("There were %number% extra Apricorns budding off of that one!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Planting-Berries", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Planting-Berries", "Function").setValue("add");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Planting-Berries", "Modifier").setValue("add 1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Planting-Berries", "Send-Message").setValue("There were %number% extra Berries budding off of that one!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Watering-Apricorns", "Growth-Mode").setValue("max");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Watering-Apricorns", "Send-Message").setValue("What was in that water!? The tree grew instantly!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 4:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Hatching-Eggs", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Hatching-Eggs", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Hatching-Eggs", "Modifier").setValue("%player-level%");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Hatching-Eggs", "Modify-Stats").setValue("hp, atk, def, satk, sdef, spd");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Hatching-Eggs", "Send-Message").setValue("Your guts tells you this will be a good Pokemon!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Making-Eggs", "Set-To-Hidden-Ability").setComment("Note: if the Pokemon does not have a HA, it will do nothing.");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Making-Eggs", "Set-To-Hidden-Ability").setValue(true);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Making-Eggs", "Send-Message").setValue("This egg has a rare power inside it!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Hatching-Eggs", "Amount").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Hatching-Eggs", "Amount").setValue("1");

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 5:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier").setValue("%player-level%");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Send-Message").setValue("Your Pokemon is feeling much better now!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 6:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Legendary-Pokemon", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Legendary-Pokemon", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Legendary-Pokemon", "Modifier").setValue("1.1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Legendary-Pokemon", "Modify-Stats").setValue("hp, atk, def, satk, sdef, spd");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Legendary-Pokemon", "Send-Message").setValue("That looks like a very strong Pokemon!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Normal-Pokemon", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Normal-Pokemon", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Normal-Pokemon", "Modifier").setValue("1.1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Normal-Pokemon", "Modify-Stats").setValue("hp, atk, def, satk, sdef, spd");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Normal-Pokemon", "Send-Message").setValue("That looks like a very strong Pokemon!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Shiny-Pokemon", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Shiny-Pokemon", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Shiny-Pokemon", "Modifier").setValue("1.1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Shiny-Pokemon", "Modify-Stats").setValue("hp, atk, def, satk, sdef, spd");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Shiny-Pokemon", "Send-Message").setValue("That looks like a very strong Pokemon!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Pokemon-Blacklist", "Catching-Legendary-Pokemon").setValue(pokemonList);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Pokemon-Blacklist", "Catching-Normal-Pokemon").setValue(pokemonList);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Pokemon-Blacklist", "Catching-Shiny-Pokemon").setValue(pokemonList);
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Legendary-Pokemon", "Amount").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Legendary-Pokemon", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Normal-Pokemon", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catching-Shiny-Pokemon", "Amount").setValue("1");

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 7:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Kill-Mega-Bosses", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Kill-Mega-Bosses", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Kill-Mega-Bosses", "Modifier").setValue("1.5");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Kill-Mega-Bosses", "Send-Message").setValue("Your Pokemon grew a little stronger from the harsh battle!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Kill-Normal-Bosses", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Kill-Normal-Bosses", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Kill-Normal-Bosses", "Modifier").setValue("1.1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Kill-Normal-Bosses", "Send-Message").setValue("Your Pokemon grew a little stronger from the harsh battle!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Kill-Mega-Bosses", "Amount").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Kill-Mega-Bosses", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Kill-Normal-Bosses", "Amount").setValue("1");

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 8:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier").setValue("1.1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modify-Stats").setValue("hp, atk, def, satk, sdef, spd");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Send-Message").setValue("That Pokemon gained some more muscle from the evolution!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Amount").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Amount").setValue("1");

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 9:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier").setValue("1.2");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modify-Stats").setValue("hp, atk, def, satk, sdef, spd");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Send-Message").setValue("That is one powerful looking fish!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Amount").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Amount").setValue(1);

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 10:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Defeating-NPC-Trainers", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Defeating-NPC-Trainers", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Defeating-NPC-Trainers", "Modifier").setValue("1.8");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Defeating-NPC-Trainers", "Send-Message").setValue("The Trainer was so impressed with your Pokemon they gave you extra money!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Defeating-Wild-Pokemon", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Defeating-Wild-Pokemon", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Defeating-Wild-Pokemon", "Modifier").setValue("1.1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Defeating-Wild-Pokemon", "Send-Message").setValue("Your Pokemon grew a little more than it normally does from that battle!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "NPC-Blacklist").setValue(list);
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Defeating-NPC-Trainers", "Amount").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Defeating-NPC-Trainers", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Defeating-Wild-Pokemon", "Amount").setValue("1");

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 11:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Lists", "Blocks-Blacklist").setValue(list);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Lists", "Blocks-Whitelist").setValue(map);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Chopping-Wood", "Amount").setValue("2");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Chopping-Wood", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Chopping-Wood", "Modifier").setValue("2");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Chopping-Wood", "Send-Message").setValue("Your mastery of using this tool extended its life!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Mining-Blocks", "Amount").setValue("2");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Mining-Blocks", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Mining-Blocks", "Modifier").setValue("%player-level%");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Perk-Modifiers", "Mining-Blocks", "Send-Message").setValue("Your mastery of using this tool extended its life!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 12:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    list.add("ExecuteConsoleCommand-give %player% minecraft:air 1");
                    list.add("ExecuteConsoleCommand-give %player% pixelmon:poke_ball %num%");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Item-List").setValue(list);
                    list.remove("ExecuteConsoleCommand-give %player% minecraft:air 1");
                    list.remove("ExecuteConsoleCommand-give %player% pixelmon:poke_ball %num%");
//                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier", "Amount-Given-From-List").setValue(1);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier", "Amount").setValue(1);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier", "Function").setValue("add");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier", "Modifier").setValue("%player-level%");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier", "Send-Message").setValue("There were extra items in the chest!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                if (!ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier", "Amount-Given-From-List").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier", "Amount-Given-From-List").setValue(null);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier", "Amount").setValue(1);

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 13:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catch-Rate-Modifier", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catch-Rate-Modifier", "Function").setValue("add");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catch-Rate-Modifier", "Modifier").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catch-Rate-Modifier", "Send-Message").setValue("You've got a firm understanding of how to catch this Pokemon!");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Settings", "Apply-Catchrate-Modifier").setValue(true);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Settings", "Despawn-Photographed-Pokemon").setValue(true);
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Settings", "Photographing-Player-Pokemon-Gives-EXP").setValue(false);
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catch-Rate-Modifier", "Amount").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Catch-Rate-Modifier", "Amount").setValue("1");

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 14:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Send-Message").setValue("That move got maxed out!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                ConfigManager.saveSkill(folder, 2);
                break;

            case 15:

                if (ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Amount").setValue("1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Function").setValue("multiply");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Modifier").setValue("1.1");
                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Send-Message").setValue("This Pokemon seems very friendly!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setComment("Don't touch this: doing so could wipe your settings!");
                    ConfigManager.getConfigNode(folder, 2, "General-Settings", "Node-Generated").setValue(true);

                }
                if (ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Amount").isVirtual()) {

                    ConfigManager.getConfigNode(folder, 2, "Perk-Setting-Modifiers", "Amount").setValue("1");

                }
                ConfigManager.saveSkill(folder, 2);
                break;

        }

    }

}
