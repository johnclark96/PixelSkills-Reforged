package com.lypaka.pixelskills.PerkHandlers;

import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Config.Getters.PerkGetters;
import com.lypaka.pixelskills.PixelSkills;
import com.lypaka.pixelskills.Skills.Barterer;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import com.lypaka.pixelskills.Utils.FancyText;
import com.lypaka.pixelskills.Utils.MessageGetters;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class BartererPerks {

    private static EventContext eventContext = EventContext.builder().add(EventContextKeys.PLUGIN, PixelSkills.getContainer()).build();

    public static boolean doesPerkTrigger (int folder, Player player, String task) {

        // Check if perks are even enabled before we do anything else
        String skill = GeneralGetters.getSkillFromConfigNumber(folder);
        int level = AccountsHandler.getLevel(skill, player);

        if (PerkGetters.perksEnabled(folder)) {

            // Check if perks are even enabled before we do anything else

            if (PerkGetters.getPerkMode(folder).equalsIgnoreCase("default")) {

                // Check player's level to make sure they are on a level that perks can trigger for them

                if (AccountsHandler.getLevel(skill, player) == PerkGetters.getActivationLevel(folder)) {

                    // Run a RNG to determine if perk triggers

                    Random random = new Random();
                    int rng = random.nextInt(100);
                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());

                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }


                } else if (AccountsHandler.getLevel(GeneralGetters.getSkillFromConfigNumber(folder), player) == AccountsHandler.getNextPerkLevel(GeneralGetters.getSkillFromConfigNumber(folder), player)) {

                    // Run a RNG to determine if perk triggers

                    Random random = new Random();
                    int rng = random.nextInt(100);

                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());

                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                } else if (level == AccountsHandler.getLastPerkLevel(player, skill, task)) {

                    Random random = new Random();
                    int rng = random.nextInt(100);

                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);


                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);

                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                } else if (AccountsHandler.getLevel(skill, player) == GeneralGetters.getMaxLevel(folder)) {

                    // Run a RNG to determine if perk triggers

                    Random random = new Random();
                    int rng = random.nextInt(100);
                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                }

            } else {

                // Check player's level to make sure they are on a level that perks can trigger for them

                if (AccountsHandler.getLevel(GeneralGetters.getSkillFromConfigNumber(folder), player) == PerkGetters.getActivationLevel(folder)) {

                    // Run a RNG to determine if perk triggers
                    // Use default chance on activation level because player's specific chance value has not been set yet

                    Random random = new Random();
                    int rng = random.nextInt(100);
                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());

                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                } else if (AccountsHandler.getLevel(GeneralGetters.getSkillFromConfigNumber(folder), player) == AccountsHandler.getNextPerkLevel(GeneralGetters.getSkillFromConfigNumber(folder), player)) {

                    // Run a RNG to determine if perk triggers

                    Random random = new Random();
                    int rng = random.nextInt(100);
                    if (rng <= AccountsHandler.getPerkChance(GeneralGetters.getSkillFromConfigNumber(folder), player)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());

                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                } else if (level == AccountsHandler.getLastPerkLevel(player, skill, task)) {

                    Random random = new Random();
                    int rng = random.nextInt(100);

                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);


                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);

                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                } else if (AccountsHandler.getLevel(skill, player) == GeneralGetters.getMaxLevel(folder)) {

                    // Run a RNG to determine if perk triggers

                    Random random = new Random();
                    int rng = random.nextInt(100);
                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                }

            }

        }

        return false;

    }

    public static boolean doesPerkTrigger (int folder, Player player, int level, String task) {

        // Check if perks are even enabled before we do anything else
        String skill = GeneralGetters.getSkillFromConfigNumber(folder);

        if (PerkGetters.perksEnabled(folder)) {

            // Checks perk chance mode to determine which chance value to use

            if (PerkGetters.getPerkMode(folder).equalsIgnoreCase("default")) {

                // Check player's level to make sure they are on a level that perks can trigger for them

                if (level == PerkGetters.getActivationLevel(folder)) {

                    // Run a RNG to determine if perk triggers

                    Random random = new Random();
                    int rng = random.nextInt(100);
                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());

                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                } else if (level == AccountsHandler.getNextPerkLevel(GeneralGetters.getSkillFromConfigNumber(folder), player)) {

                    // Run a RNG to determine if perk triggers

                    Random random = new Random();
                    int rng = random.nextInt(100);
                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());

                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                } else if (level == AccountsHandler.getLastPerkLevel(player, skill, task)) {

                    Random random = new Random();
                    int rng = random.nextInt(100);

                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);


                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);

                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                } else if (AccountsHandler.getLevel(skill, player) == GeneralGetters.getMaxLevel(folder)) {

                    // Run a RNG to determine if perk triggers

                    Random random = new Random();
                    int rng = random.nextInt(100);
                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                }

            } else {

                // Check player's level to make sure they are on a level that perks can trigger for them

                if (level == PerkGetters.getActivationLevel(folder)) {

                    // Run a RNG to determine if perk triggers
                    // Use default chance on activation level because player's specific chance value has not been set yet

                    Random random = new Random();
                    int rng = random.nextInt(100);
                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());

                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                } else if (level == AccountsHandler.getNextPerkLevel(GeneralGetters.getSkillFromConfigNumber(folder), player)) {

                    // Run a RNG to determine if perk triggers

                    Random random = new Random();
                    int rng = random.nextInt(100);
                    if (rng <= AccountsHandler.getPerkChance(GeneralGetters.getSkillFromConfigNumber(folder), player)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());

                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                AccountsHandler.setLastPerkLevel(player, skill, task, level);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                } else if (level == AccountsHandler.getLastPerkLevel(player, skill, task)) {

                    Random random = new Random();
                    int rng = random.nextInt(100);

                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            // Checks if the player has not triggered the perk for this task yet

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);


                            }

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                AccountsHandler.setNextPerkLevel(folder, player);
                                AccountsHandler.setNextPerkChance(folder, player);
                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);

                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                } else if (AccountsHandler.getLevel(skill, player) == GeneralGetters.getMaxLevel(folder)) {

                    // Run a RNG to determine if perk triggers

                    Random random = new Random();
                    int rng = random.nextInt(100);
                    if (rng <= PerkGetters.getDefaultPerkChance(folder)) {

                        // Checks if multiple perks can trigger on this level

                        if (PerkGetters.multiplePerksCanTrigger(folder)) {

                            return true;

                        } else {

                            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").getBoolean()) {

                                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, task, "Triggered").setValue(true);
                                ConfigManager.savePlayer(player.getUniqueId());
                                return true;

                            } else {

                                return false;

                            }

                        }

                    }

                }

            }

        }

        return false;

    }

    public static void runPerkCode (int folder, String task, Player player) throws ObjectMappingException {

        String perkTriggerMode = PerkGetters.getPerkRewardMode(folder);

        switch (perkTriggerMode) {

            case "default":
                givePerk(task, player);
                break;

            case "custom":
                giveCustomPerk(player);
                break;

        }



    }


    private static void givePerk (String task, Player player) throws ObjectMappingException {

        String amountString = PerkGetters.getPerkModifiers(2, task).get("Amount");
        int amount;
        if (amountString.equalsIgnoreCase("%price%")) {

            amount = Barterer.getPrice();

        } else {

            amount = Integer.parseInt(amountString);

        }
        String[] modifier = PerkGetters.getPerkModifiers(2, task).get("Modifier").split(" ");
        String function = modifier[0];
        double num;
        double result = 0;

        if (modifier[1].equalsIgnoreCase("%player-level%")) {

            num = AccountsHandler.getLevel("Barterer", player);

        } else {

            num = Double.parseDouble(modifier[1]);

        }

        switch (function) {

            case "add":
                result = num + amount;
                break;

            case "multiply":
                result = num * amount;
                break;

        }

        switch (task) {

            case "Buying-From-Shopkeepers":

                String item = Barterer.getItem();
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + item + " " + (int) result);
                player.sendMessage(FancyText.getFancyText(MessageGetters.getPerkMessage(GeneralGetters.getConfigFromSkill("Barterer"), task)
                    .replace("%number%", String.valueOf((int) result))
                ));
                break;

            case "Selling-To-Shopkeepers":

                Optional<EconomyService> econ = Sponge.getServiceManager().provide(EconomyService.class);

                if (econ.isPresent()) {

                    Optional<UniqueAccount> a = econ.get().getOrCreateAccount(player.getUniqueId());
                    if (a.isPresent()) {

                        Currency defaultCur = econ.get().getDefaultCurrency();
                        a.get().deposit(defaultCur, BigDecimal.valueOf(result), Cause.of(eventContext, PixelSkills.getContainer()));
                        player.sendMessage(FancyText.getFancyText(MessageGetters.getPerkMessage(GeneralGetters.getConfigFromSkill("Barterer"), task)
                                .replace("%money%", String.valueOf(result))
                        ));
                        break;

                    }

                }

        }



    }

    private static void giveCustomPerk (Player player) throws ObjectMappingException {

        int options = PerkGetters.getCustomPerkAmount(2);
        Map<String, String> map;
        int perkNum = 0;

        if (options > 1) {

            Random random = new Random();
            int rng = random.nextInt(options) + 1;
            map = PerkGetters.getCustomPerkMap(2, rng);
            perkNum = rng;

        } else {

            map = PerkGetters.getCustomPerkMap(2, 1);
            perkNum = 1;

        }

        int amount = Integer.parseInt(map.get("Amount"));
        String[] modifier = map.get("Modifier").split(" ");
        String prizeType = map.get("Prize-Type").toLowerCase();
        String prize = map.get("Prize");
        String function = modifier[0];
        double modNum;
        double result = 0;

        if (modifier[1].equalsIgnoreCase("%player-level%")) {

            modNum = AccountsHandler.getLevel("Barterer", player);

        } else {

            modNum = Double.parseDouble(modifier[1]);

        }

        switch (function) {

            case "add":
                result = amount + modNum;
                break;

            case "multiply":
                result = amount * modNum;
                break;

        }

        switch (prizeType) {

            case "item":
                if (prize.contains(", ")) {

                    String[] prizes = prize.split(", ");

                    for (String prz : prizes) {

                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + prz + " " + (int) result);
                        player.sendMessage(FancyText.getFancyText(MessageGetters.getCustomPerkMessage(2, perkNum)
                                .replace("%number%", String.valueOf(result))
                                .replace("%prize%", prz)
                        ));

                    }

                } else {

                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + prize + " " + (int) result);
                    player.sendMessage(FancyText.getFancyText(MessageGetters.getCustomPerkMessage(2, perkNum)
                            .replace("%number%", String.valueOf(result))
                            .replace("%prize%", prize)
                    ));

                }
                break;

            case "pokemon":

                if (prize.contains(", ")) {

                    String[] prizes = prize.split(", ");

                    for (String prz : prizes) {

                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + player.getName() + " " + prz);
                        player.sendMessage(FancyText.getFancyText(MessageGetters.getCustomPerkMessage(2, perkNum)
                                .replace("%prize%", prz)
                        ));

                    }

                } else {

                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + player.getName() + " " + prize);
                    player.sendMessage(FancyText.getFancyText(MessageGetters.getCustomPerkMessage(2, perkNum)
                            .replace("%prize%", prize)
                    ));

                }
                break;

            case "command":

                if (prize.contains(", ")) {

                    String[] prizes = prize.split(", ");

                    for (String prz : prizes) {

                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), prz.replace("%player%", player.getName()));

                    }

                } else {

                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), prize.replace("%player%", player.getName()));

                }

                player.sendMessage(FancyText.getFancyText(MessageGetters.getCustomPerkMessage(2, perkNum)));
                break;

        }

    }

}
