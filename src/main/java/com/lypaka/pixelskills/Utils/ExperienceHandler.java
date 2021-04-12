package com.lypaka.pixelskills.Utils;

import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.EXPGetters;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Config.Getters.PerkGetters;
import com.lypaka.pixelskills.FancyFeatures.FancyFeaturesGetters;
import com.lypaka.pixelskills.FancyFeatures.FancyFeaturesHandler;
import com.lypaka.pixelskills.Skills.Artificer;
import com.lypaka.pixelskills.Skills.Barterer;
import com.lypaka.pixelskills.Skills.Gladiator;
import com.lypaka.pixelskills.Utils.Listeners.JoinListener;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

public class ExperienceHandler {

    private static double newEXP;
    public static int startingLvl = 0;
    public static int endingLvl = 0;

    public static void didTask (int conf, String task, Player player) throws IOException, ObjectMappingException {

        String skill = GeneralGetters.getSkillFromConfigNumber(conf);

        if (AccountsHandler.getLevel(skill, player) < GeneralGetters.getMaxLevel(conf)) {

            // Gets the EXP from the config and adds it to the player
            addEXP(conf, task, player);

            // Checks if the player has enough EXP to level up, then executes level up code
            startingLvl = AccountsHandler.getLevel(skill, player);
            if (didLevelUp(skill, player)) {

                levelUp(skill, player);
                LevelLockedRewardsHandler.triggerLevelLockedRewards(conf, player);
                RewardsHandler.triggerRewards(conf, player);

                if (PerkGetters.taskPerkEnabled(conf, task)) {

                    PerkHandler.checkForMissedPerks(conf, task, player);

                }


            }

        }

        // Runs perk code
        if (PerkGetters.taskPerkEnabled(conf, task)) {

            PerkHandler.runPerkCode(conf, task, player);

        }

        ConfigManager.savePlayer(player.getUniqueId());

    }

    public static void didArtificerTask (Player player, String task) throws IOException, ObjectMappingException {

        if (AccountsHandler.getLevel("Artificer", player) < GeneralGetters.getMaxLevel(1)) {

            if (!task.equals("Using-Pixelmon-Anvil")) {

                addArtificerEXP(player, Double.parseDouble(String.valueOf(AccountsHandler.getCraftedQuantity(player))));

            } else {

                addArtificerEXP(player, Artificer.getPixelAnvilEXP());

            }

            // Run "next perk level" code in case "multiple perks per level" = false
            String skill = "Artificer";
            int conf = GeneralGetters.getConfigFromSkill(skill);
            if (!PerkGetters.multiplePerksCanTrigger(conf)) {

                int level = AccountsHandler.getLevel(skill, player);
                int actLevel = PerkGetters.getActivationLevel(conf);
                int nxtLevel = AccountsHandler.getNextPerkLevel(skill, player);
                if (level == actLevel || level == nxtLevel) {

                    AccountsHandler.setNextPerkLevel(conf, player);

                }

            }

            startingLvl = AccountsHandler.getLevel("Artificer", player);
            if (didLevelUp("Artificer", player)) {

                levelUp("Artificer", player);
                LevelLockedRewardsHandler.triggerLevelLockedRewards(conf, player);
                RewardsHandler.triggerRewards(conf, player);

                if (PerkGetters.taskPerkEnabled(GeneralGetters.getConfigFromSkill("Artificer"), task)) {

                    PerkHandler.checkForMissedPerks(1, task, player);

                }


            }

        }

        // Runs perk code
        if (PerkGetters.taskPerkEnabled(GeneralGetters.getConfigFromSkill("Artificer"), task)) {

            PerkHandler.runPerkCode(GeneralGetters.getConfigFromSkill("Artificer"), task, player);

        }

        ConfigManager.savePlayer(player.getUniqueId());

    }

    public static void didHarvesterTask (Player player, String task, double exp) throws IOException, ObjectMappingException {

        if (AccountsHandler.getLevel("Harvester", player) < GeneralGetters.getMaxLevel(11)) {

            addHarvesterEXP(player, exp);

            // Run "next perk level" code in case "multiple perks per level" = false
            String skill = "Harvester";
            int conf = GeneralGetters.getConfigFromSkill(skill);
            if (!PerkGetters.multiplePerksCanTrigger(conf)) {

                int level = AccountsHandler.getLevel(skill, player);
                int actLevel = PerkGetters.getActivationLevel(conf);
                int nxtLevel = AccountsHandler.getNextPerkLevel(skill, player);
                if (level == actLevel || level == nxtLevel) {

                    AccountsHandler.setNextPerkLevel(conf, player);

                }

            }

            // Checks if the player has enough EXP to level up, then executes level up code

            startingLvl = AccountsHandler.getLevel("Harvester", player);
            if (didLevelUp("Harvester", player)) {

                levelUp("Harvester", player);
                LevelLockedRewardsHandler.triggerLevelLockedRewards(conf, player);
                RewardsHandler.triggerRewards(conf, player);

                if (PerkGetters.taskPerkEnabled(GeneralGetters.getConfigFromSkill("Harvester"), task)) {

                    PerkHandler.checkForMissedPerks(11, task, player);

                }

            }

        }

        // Runs perk code
        if (PerkGetters.taskPerkEnabled(GeneralGetters.getConfigFromSkill("Harvester"), task)) {

            PerkHandler.runPerkCode(GeneralGetters.getConfigFromSkill("Harvester"), task, player);

        }

        ConfigManager.savePlayer(player.getUniqueId());

    }

    private static void addEXP (int conf, String task, Player player) throws ObjectMappingException {

        String skill = GeneralGetters.getSkillFromConfigNumber(conf);
        double exp = EXPGetters.getEXPFromTask(conf, task, player);
        double currentEXP = AccountsHandler.getCurrentEXP(skill, player);

        if (task.equals("Selling-To-Shopkeepers") && ConfigManager.getConfigNode(2, 2, "Perk-Setting-Modifiers", "Modify-EXP").getBoolean()) {

            double modEXP = exp * Barterer.getQuantity();
            double useEXP;
            if (EXPGetters.doBartererByQuantity()) {

                useEXP = ExperienceHandler.getPrettyDouble(modEXP);

            } else {

                useEXP = ExperienceHandler.getPrettyDouble(exp);

            }
            double newEXP = ExperienceHandler.getPrettyDouble(exp) + ExperienceHandler.getPrettyDouble(currentEXP);

            if (AccountsHandler.areMessagesEnabled(player, skill)) {

                if (exp != 1) {

                    player.sendMessage(FancyText.getFancyText(MessageGetters.getEXPGained(conf)
                            .replace("%exp%", String.valueOf(useEXP))
                            .replace("%skill%", skill)
                            .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                    ));

                } else {

                    player.sendMessage(FancyText.getFancyText(MessageGetters.getEXPGained(conf)
                            .replace("points", "point")
                            .replace("%exp%", String.valueOf(useEXP))
                            .replace("%skill%", skill)
                            .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                    ));
                }

            }

            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(ExperienceHandler.getPrettyDouble(newEXP));
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

        } else if (task.equalsIgnoreCase("Defeating-Wild-Pokemon")) {

            double gainedEXP;

            if (GeneralGetters.doGladiatorEXPScaling()) {

                int playLvl = AccountsHandler.getLevel("Gladiator", player);
                int pokeLvl = Gladiator.pokelvl;

                double modEXP = exp * (pokeLvl / playLvl) * 2;
                gainedEXP = ExperienceHandler.getPrettyDouble(exp) + ExperienceHandler.getPrettyDouble(currentEXP);

                if (AccountsHandler.areMessagesEnabled(player, "Gladiator")) {

                    if (exp != 1) {

                        player.sendMessage(FancyText.getFancyText(MessageGetters.getEXPGained(conf)
                                .replace("%exp%", String.valueOf(modEXP))
                                .replace("%skill%", skill)
                                .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                        ));

                    } else {

                        player.sendMessage(FancyText.getFancyText(MessageGetters.getEXPGained(conf)
                                .replace("points", "point")
                                .replace("%exp%", String.valueOf(modEXP))
                                .replace("%skill%", skill)
                                .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                        ));
                    }

                }

            } else {

                 gainedEXP = ExperienceHandler.getPrettyDouble(exp) + ExperienceHandler.getPrettyDouble(currentEXP);

                if (AccountsHandler.areMessagesEnabled(player, "Gladiator")) {

                    if (exp != 1) {

                        player.sendMessage(FancyText.getFancyText(MessageGetters.getEXPGained(conf)
                                .replace("%exp%", String.valueOf(exp))
                                .replace("%skill%", skill)
                                .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                        ));

                    } else {

                        player.sendMessage(FancyText.getFancyText(MessageGetters.getEXPGained(conf)
                                .replace("points", "point")
                                .replace("%exp%", String.valueOf(exp))
                                .replace("%skill%", skill)
                                .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                        ));
                    }

                }

            }

            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(ExperienceHandler.getPrettyDouble(gainedEXP));
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


        } else {

            double newEXP = ExperienceHandler.getPrettyDouble(exp) + ExperienceHandler.getPrettyDouble(currentEXP);

            if (AccountsHandler.areMessagesEnabled(player, skill)) {

                if (exp != 1) {

                    player.sendMessage(FancyText.getFancyText(MessageGetters.getEXPGained(conf)
                            .replace("%exp%", String.valueOf(exp))
                            .replace("%skill%", skill)
                            .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                    ));

                } else {

                    player.sendMessage(FancyText.getFancyText(MessageGetters.getEXPGained(conf)
                            .replace("points", "point")
                            .replace("%exp%", String.valueOf(exp))
                            .replace("%skill%", skill)
                            .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                    ));
                }

            }

            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(newEXP);
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

    // EXP adding method from Artificer skill
    // Blame Sponge for the lack of a proper getQuantity method that made coding this skill so damn complicated

    private static void addArtificerEXP (Player player, double exp) throws ObjectMappingException {

        int conf = GeneralGetters.getConfigFromSkill("Artificer");
        String skill = "Artificer";
        double currentEXP = AccountsHandler.getCurrentEXP("Artificer", player);
        double newEXP = ExperienceHandler.getPrettyDouble(exp) + ExperienceHandler.getPrettyDouble(currentEXP);
        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Artificer", "EXP").setValue(ExperienceHandler.getPrettyDouble(newEXP));
        String value;
        if (newEXP != 1) {

            value = "points";

        } else {

            value = "point";

        }

        if (AccountsHandler.areMessagesEnabled(player, "Artificer")) {

            player.sendMessage(FancyText.getFancyText(MessageGetters.getEXPGained(1)
                    .replace("points", value)
                    .replace("%exp%", String.valueOf(exp))
                    .replace("%skill%", "Artificer")
                    .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
            ));

        }

        AccountsHandler.clearCraftedQuantity(player);
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

    private static void addHarvesterEXP (Player player, double exp) throws ObjectMappingException {

        int conf = GeneralGetters.getConfigFromSkill("Harvester");
        String skill = "Harvester";
        double currentEXP = ExperienceHandler.getPrettyDouble(AccountsHandler.getCurrentEXP("Harvester", player));
        double newEXP = ExperienceHandler.getPrettyDouble(exp) + ExperienceHandler.getPrettyDouble(currentEXP);
        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Harvester", "EXP").setValue(ExperienceHandler.getPrettyDouble(newEXP));
        String value;
        if (newEXP != 1) {

            value = "points";

        } else {

            value = "point";

        }

        if (AccountsHandler.areMessagesEnabled(player, "Harvester")) {

            player.sendMessage(FancyText.getFancyText(MessageGetters.getEXPGained(11)
                    .replace("points", value)
                    .replace("%exp%", String.valueOf(exp))
                    .replace("%skill%", "Harvester")
                    .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
            ));

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

    public static boolean didLevelUp(String skill, Player player) throws ObjectMappingException {
        
        String expMode = EXPGetters.getEXPMode(GeneralGetters.getConfigFromSkill(skill));

        if (expMode.equals("calculated")) {
            if (ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Level").getInt() == 1) {
                
                return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").getDouble() >= EXPGetters.getBaseEXP(GeneralGetters.getConfigFromSkill(skill));
                
            } else {
                
                return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").getDouble() >= ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP-To-Next-Level").getDouble();
                
            }
            
        } else if (expMode.equals("fixed")) {
            
            int lvl = AccountsHandler.getLevel(skill, player);
            int checkLvl = lvl + 1;
            Map<String, Double> expMap = EXPGetters.getEXPMap(GeneralGetters.getConfigFromSkill(skill));
            return ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").getDouble() >= expMap.get("Level-" + checkLvl);
            
        }

        return false;

    }

    public static void levelUp(String skill, Player player) throws ObjectMappingException {
        
        String expMode = EXPGetters.getEXPMode(GeneralGetters.getConfigFromSkill(skill));
        int currentLvl = AccountsHandler.getLevel(skill, player);
        double currentEXP = AccountsHandler.getCurrentEXP(skill, player);
        int conf = GeneralGetters.getConfigFromSkill(skill);

        // Increase player level by 1 and reset EXP
        if (expMode.equals("calculated")) {
            
            if (currentEXP > AccountsHandler.getEXPToNextLvl(skill, player)) {
                
                if (currentLvl == 1) {
                    
                    newEXP = currentEXP - EXPGetters.getBaseEXP(GeneralGetters.getConfigFromSkill(skill));
                    
                } else {
                    
                    newEXP = currentEXP - AccountsHandler.getEXPToNextLvl(skill, player);
                    
                }

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(newEXP);
                
            } else if (currentEXP == AccountsHandler.getEXPToNextLvl(skill, player)) {

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(0);
                
            }
            
        } else if (expMode.equals("fixed")) {
            
            // Add 1 from player level here to get level of EXP for level player would be leveling up to

            if (AccountsHandler.getLevel(skill, player) == 1) {

                if (currentEXP > EXPGetters.getManualEXPAmount(GeneralGetters.getConfigFromSkill(skill), 2)) {

                    double newEXP = currentEXP - EXPGetters.getManualEXPAmount(GeneralGetters.getConfigFromSkill(skill), 2);
                    ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(newEXP);

                } else if (currentEXP == EXPGetters.getManualEXPAmount(GeneralGetters.getConfigFromSkill(skill), 2)) {

                    ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(0);

                }
                
            } else {

                if (currentEXP >= AccountsHandler.getEXPToNextLvl(skill, player)) {

                    int checkLevel = AccountsHandler.getLevel(skill, player) + 1;
                    double newEXP = currentEXP - EXPGetters.getManualEXPAmount(GeneralGetters.getConfigFromSkill(skill), checkLevel);
                    ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(newEXP);

                } else if (currentEXP == AccountsHandler.getEXPToNextLvl(skill, player)) {

                    ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(0);

                }

            }
            
        }

        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Level").setValue(currentLvl + 1);

        EXPGetters.setEXPToNextLevel(GeneralGetters.getConfigFromSkill(skill), player);

        player.sendMessage(FancyText.getFancyText(MessageGetters.getLvlUp(conf)
                .replace("%lvl%", String.valueOf(currentLvl + 1))
                .replace("%skill%", skill)
                .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
        ));
        if (FancyFeaturesGetters.showActionBar(conf, "Level-Up")) {

            FancyFeaturesHandler.sendActionBar(player, conf, "Level-Up");

        }
        if (FancyFeaturesGetters.sendTitleMessages(conf, "Level-Up")) {

            FancyFeaturesHandler.sendTitle(player, conf, "Level-Up");

        }
        if (FancyFeaturesGetters.doEffects(conf, "Level-Up")) {

            FancyFeaturesHandler.spawnParticles(player, conf, "Level-Up");

        }
        if (FancyFeaturesGetters.playSounds(conf, "Level-Up")) {

            FancyFeaturesHandler.playSound(player, conf, "Level-Up");

        }

        // Resets the "triggered" boolean value for Perk triggers on level up back to "false".
        JoinListener.generatePerkTriggers(player, skill);

        endingLvl = currentLvl + 1;
        if (AccountsHandler.getNextPerkLevel(skill, player) < endingLvl) {

            AccountsHandler.setNextPerkLevel(conf, player);

        }

        // Checks for a double level up, which is unlikely but dependent upon how server owners configure their config files
        if (didLevelUp(skill, player)) {

            if (AccountsHandler.getLevel(skill, player) < GeneralGetters.getMaxLevel(conf)) {

                levelUp(skill, player);

            }

        }

    }

    public static double getPrettyDouble (double value) {

        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(value);
        return Double.parseDouble(formatted);

    }

}
