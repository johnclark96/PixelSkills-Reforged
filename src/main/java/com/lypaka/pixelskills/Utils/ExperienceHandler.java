package com.lypaka.pixelskills.Utils;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelboosters.Config.ConfigGetters;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.EXPGetters;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Config.Getters.PerkGetters;
import com.lypaka.pixelskills.FancyFeatures.FancyFeaturesGetters;
import com.lypaka.pixelskills.FancyFeatures.FancyFeaturesHandler;
import com.lypaka.pixelskills.PixelSkills;
import com.lypaka.pixelskills.Skills.Artificer;
import com.lypaka.pixelskills.Skills.Barterer;
import com.lypaka.pixelskills.Skills.Gladiator;
import com.lypaka.pixelskills.Utils.CustomEvents.SkillExperienceEvent;
import com.lypaka.pixelskills.Utils.CustomEvents.SkillLevelUpEvent;
import com.lypaka.pixelskills.Utils.Listeners.JoinListener;
import net.minecraftforge.common.MinecraftForge;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
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

                levelUp(skill, player, false);
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

                levelUp("Artificer", player, false);
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

                levelUp("Harvester", player, false);
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

            double newEXP;
            if (PixelSkills.isPixelBoostersLoaded) {

                List<String> skillBlacklist = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(14, "Skills-Blacklist").getList(TypeToken.of(String.class));
                if (ConfigGetters.isPersonalBoosterActive(player, "Skills") || ConfigGetters.isServerBoosterActive("Skills")) {

                    if (skillBlacklist.isEmpty() || !skillBlacklist.contains(skill)) {

                        String[] mod = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(14, "Personal").getString().split(" ");
                        double modDouble = Double.parseDouble(mod[1]);
                        String function = mod[0];
                        if (function.equalsIgnoreCase("add")) {

                            useEXP = useEXP + modDouble;

                        } else {

                            useEXP = useEXP * modDouble;

                        }

                    }

                }

            }

            newEXP = ExperienceHandler.getPrettyDouble(useEXP);
            giveEXP(player, getPrettyDouble(newEXP), skill);


        } else if (task.equalsIgnoreCase("Defeating-Wild-Pokemon")) {

            double gainedEXP;

            if (GeneralGetters.doGladiatorEXPScaling()) {

                int playLvl = AccountsHandler.getLevel("Gladiator", player);
                int pokeLvl = Gladiator.pokelvl;

                double modEXP = exp * (pokeLvl / playLvl) * 2;
                if (PixelSkills.isPixelBoostersLoaded) {

                    List<String> skillBlacklist = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(14, "Skills-Blacklist").getList(TypeToken.of(String.class));
                    if (ConfigGetters.isPersonalBoosterActive(player, "Skills") || ConfigGetters.isServerBoosterActive("Skills")) {

                        if (skillBlacklist.isEmpty() || !skillBlacklist.contains(skill)) {

                            String[] mod = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(14, "Personal").getString().split(" ");
                            double modDouble = Double.parseDouble(mod[1]);
                            String function = mod[0];
                            if (function.equalsIgnoreCase("add")) {

                                modEXP = modEXP + modDouble;

                            } else {

                                modEXP = modEXP * modDouble;

                            }

                        }

                    }

                }
                gainedEXP = ExperienceHandler.getPrettyDouble(modEXP);

            } else {

                if (PixelSkills.isPixelBoostersLoaded) {

                    List<String> skillBlacklist = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(14, "Skills-Blacklist").getList(TypeToken.of(String.class));
                    if (ConfigGetters.isPersonalBoosterActive(player, "Skills") || ConfigGetters.isServerBoosterActive("Skills")) {

                        if (skillBlacklist.isEmpty() || !skillBlacklist.contains(skill)) {

                            String[] mod = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(14, "Personal").getString().split(" ");
                            double modDouble = Double.parseDouble(mod[1]);
                            String function = mod[0];
                            if (function.equalsIgnoreCase("add")) {

                                exp = exp + modDouble;

                            } else {

                                exp = exp * modDouble;

                            }

                        }

                    }

                }

                gainedEXP = ExperienceHandler.getPrettyDouble(exp);

            }

            giveEXP(player, getPrettyDouble(gainedEXP), skill);

        } else {

            if (PixelSkills.isPixelBoostersLoaded) {

                List<String> skillBlacklist = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(14, "Skills-Blacklist").getList(TypeToken.of(String.class));
                if (ConfigGetters.isPersonalBoosterActive(player, "Skills") || ConfigGetters.isServerBoosterActive("Skills")) {

                    if (skillBlacklist.isEmpty() || !skillBlacklist.contains(skill)) {

                        String[] mod = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(14, "Personal").getString().split(" ");
                        double modDouble = Double.parseDouble(mod[1]);
                        String function = mod[0];
                        if (function.equalsIgnoreCase("add")) {

                            exp = exp + modDouble;

                        } else {

                            exp = exp * modDouble;

                        }

                    }

                }

            }

            double newEXP = ExperienceHandler.getPrettyDouble(exp);
            giveEXP(player, newEXP, skill);

        }

    }

    // EXP adding method from Artificer skill
    // Blame Sponge for the lack of a proper getQuantity method that made coding this skill so damn complicated

    private static void addArtificerEXP (Player player, double exp) throws ObjectMappingException {

        if (PixelSkills.isPixelBoostersLoaded) {

            List<String> skillBlacklist = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(14, "Skills-Blacklist").getList(TypeToken.of(String.class));
            if (ConfigGetters.isPersonalBoosterActive(player, "Skills") || ConfigGetters.isServerBoosterActive("Skills")) {

                if (skillBlacklist.isEmpty() || !skillBlacklist.contains("Artificer")) {

                    String[] mod = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(14, "Personal").getString().split(" ");
                    double modDouble = Double.parseDouble(mod[1]);
                    String function = mod[0];
                    if (function.equalsIgnoreCase("add")) {

                        exp = exp + modDouble;

                    } else {

                        exp = exp * modDouble;

                    }

                }

            }

        }
        double newEXP = ExperienceHandler.getPrettyDouble(exp);
        giveEXP(player, getPrettyDouble(newEXP), "Artificer");

    }

    private static void addHarvesterEXP (Player player, double exp) throws ObjectMappingException {

        if (PixelSkills.isPixelBoostersLoaded) {

            List<String> skillBlacklist = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(13, "Skills-Blacklist").getList(TypeToken.of(String.class));
            if (ConfigGetters.isPersonalBoosterActive(player, "Skills") || ConfigGetters.isServerBoosterActive("Skills")) {

                if (skillBlacklist.isEmpty() || !skillBlacklist.contains("Harvester")) {

                    String[] mod = com.lypaka.pixelboosters.Config.ConfigManager.getBoosterNode(13, "Modifier").getString().split(" ");
                    double modDouble = Double.parseDouble(mod[1]);
                    String function = mod[0];
                    if (function.equalsIgnoreCase("add")) {

                        exp = exp + modDouble;

                    } else {

                        exp = exp * modDouble;

                    }

                }

            }

        }

        double newEXP = ExperienceHandler.getPrettyDouble(exp);
        giveEXP(player, getPrettyDouble(newEXP), "Harvester");

    }

    public static boolean didLevelUp (String skill, Player player) throws ObjectMappingException {

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

    public static void levelUp (String skill, Player player, boolean forced) throws ObjectMappingException, IOException {

        String expMode = EXPGetters.getEXPMode(GeneralGetters.getConfigFromSkill(skill));
        int currentLvl = AccountsHandler.getLevel(skill, player);
        int newLevel = currentLvl + 1;
        double currentEXP = AccountsHandler.getCurrentEXP(skill, player);
        int conf = GeneralGetters.getConfigFromSkill(skill);

        SkillLevelUpEvent event = new SkillLevelUpEvent(player, newLevel, skill);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) {

            if (forced) {

                // This will only ever get called if the player is using a Skill Candy, so its safe to just get the optional here.
                player.getItemInHand(HandTypes.MAIN_HAND).get().setQuantity(player.getItemInHand(HandTypes.MAIN_HAND).get().getQuantity() - 1);
                player.sendMessage(Text.of(TextColors.AQUA, "You used 1 Skill Candy to level up your " + skill + " skill!"));
                LevelLockedRewardsHandler.triggerLevelLockedRewards(conf, player);
                RewardsHandler.triggerRewards(conf, player);
                player.closeInventory();

            }

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

            if (GeneralGetters.areMessagesEnabled(skill, "Level-Up")) {

                player.sendMessage(FancyText.getFancyText(MessageHandlers.getLvlUp(conf)
                        .replace("%lvl%", String.valueOf(currentLvl + 1))
                        .replace("%skill%", skill)
                        .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                ));

            }

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

            // Checks for a double level up, which is unlikely but dependent upon how server owners configure their config files
            if (didLevelUp(skill, player)) {

                if (AccountsHandler.getLevel(skill, player) < GeneralGetters.getMaxLevel(conf)) {

                    levelUp(skill, player, false);

                }

            }

        }

    }

    public static double getPrettyDouble (double value) {

        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(value);
        return Double.parseDouble(formatted);

    }

    private static void giveEXP (Player player, double exp, String skill) throws ObjectMappingException {

        SkillExperienceEvent event = new SkillExperienceEvent(player, exp, skill);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) {

            double setEXP = ExperienceHandler.getPrettyDouble(exp) + ExperienceHandler.getPrettyDouble(AccountsHandler.getCurrentEXP(skill, player));
            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(ExperienceHandler.getPrettyDouble(setEXP));
            MessageHandlers.sendEXPMessages(player, exp, skill);
            if (skill.equalsIgnoreCase("Artificer")) {

                AccountsHandler.clearCraftedQuantity(player);

            }

        }

    }

}
