package com.lypaka.pixelskills.Utils;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Config.Getters.PerkGetters;
import com.lypaka.pixelskills.Config.Getters.TaskGetters;
import com.lypaka.pixelskills.PixelSkills;
import com.lypaka.pixelskills.Skills.*;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityApricornTree;
import com.pixelmonmod.pixelmon.comm.EnumUpdateType;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.item.Item;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.property.item.UseLimitProperty;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class PerkHandler {

    public static int pseudoLevelFirst = 0;
    public static int pseudoLevelNext = 0;
    private static EventContext eventContext = EventContext.builder().add(EventContextKeys.PLUGIN, PixelSkills.getContainer()).build();
    public static int modifiedEXP = 0;
    public static int modifiedMoney;
    private static ItemStack stack;

    public static void runPerkCode (int folder, String task, Player player) throws IOException, ObjectMappingException {

        int level = AccountsHandler.getLevel(GeneralGetters.getSkillFromConfigNumber(folder), player);

        if (doesPerkTrigger(folder, player, task)) {

            doPerkCode(folder, task, player);

        } else if (doesPerkTrigger(folder, player, level, task)) {

            doPerkCode(folder, task, player);

        }

    }

    public static void checkForMissedPerks (int folder, String task, Player player) throws IOException, ObjectMappingException {

        int lvl = AccountsHandler.getLevel(GeneralGetters.getSkillFromConfigNumber(folder), player);
        if (lvl == 1) return;
        int[] levels = IntStream.range(ExperienceHandler.startingLvl, ExperienceHandler.endingLvl + 1).toArray();
        for (int level : levels) {

            if (PerkGetters.getActivationLevel(folder) == level) {

                runPerkCode(folder, task, player);
                AccountsHandler.setNextPerkLevelManual(folder, player, level);

            } else if (level == getPseudoFirstSkillLevel(folder)) {

                runPerkCode(folder, task, player);
                AccountsHandler.setNextPerkLevelManual(folder, player, level);

            } else if (level == getPseudoLevelNext(folder)) {

                runPerkCode(folder, task, player);
                AccountsHandler.setNextPerkLevelManual(folder, player, level);

            }

        }

    }

    private static int getPseudoFirstSkillLevel (int folder) {

        int[] levels = IntStream.range(ExperienceHandler.startingLvl, ExperienceHandler.endingLvl + 1).toArray();
        int activationLevel = PerkGetters.getActivationLevel(folder);
        int interval = PerkGetters.getLevelInterval(folder);

        for (int level : levels) {

            if (level == activationLevel) {

                pseudoLevelFirst = level + interval;
                break;

            }

        }

        return pseudoLevelFirst;

    }

    private static int getPseudoLevelNext (int folder) {

        int[] levels = IntStream.range(ExperienceHandler.startingLvl, ExperienceHandler.endingLvl + 1).toArray();
        int interval = PerkGetters.getLevelInterval(folder);

        for (int level : levels) {

            if (level == pseudoLevelFirst) {

                pseudoLevelNext = level + interval;
                break;

            }

        }

        return pseudoLevelNext;

    }

    private static boolean doesPerkTrigger (int folder, Player player, String task) {

        // Check if perks are even enabled before we do anything else
        String skill = GeneralGetters.getSkillFromConfigNumber(folder);
        int level = AccountsHandler.getLevel(skill, player);

        if (PerkGetters.perksEnabled(folder)) {

            // Check if perks are even enabled before we do anything else

            if (PerkGetters.getPerkMode(folder).equalsIgnoreCase("default")) {

                // Check player's level to make sure they are on a level that perks can trigger for them

                if (AccountsHandler.getLevel(skill, player) == PerkGetters.getActivationLevel(folder)) {

                    // Run a RNG to determine if perk triggers

                    double rng = new Random().nextDouble();
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

                    double rng = new Random().nextDouble();

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

                    double rng = new Random().nextDouble();

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

                    double rng = new Random().nextDouble();
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

                    double rng = new Random().nextDouble();
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

                    double rng = new Random().nextDouble();
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

                    double rng = new Random().nextDouble();

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

                    double rng = new Random().nextDouble();
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

    private static boolean doesPerkTrigger (int folder, Player player, int level, String task) {

        // Check if perks are even enabled before we do anything else
        String skill = GeneralGetters.getSkillFromConfigNumber(folder);

        if (PerkGetters.perksEnabled(folder)) {

            // Checks perk chance mode to determine which chance value to use

            if (PerkGetters.getPerkMode(folder).equalsIgnoreCase("default")) {

                // Check player's level to make sure they are on a level that perks can trigger for them

                if (level == PerkGetters.getActivationLevel(folder)) {

                    // Run a RNG to determine if perk triggers

                    double rng = new Random().nextDouble();
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

                    double rng = new Random().nextDouble();
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

                    double rng = new Random().nextDouble();

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

                    double rng = new Random().nextDouble();
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

                    double rng = new Random().nextDouble();
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

                    double rng = new Random().nextDouble();
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

                    double rng = new Random().nextDouble();

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

                    double rng = new Random().nextDouble();
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

    private static void doPerkCode (int folder, String task, Player player) throws ObjectMappingException {

        String perkTriggerMode = PerkGetters.getPerkRewardMode(folder);

        switch (perkTriggerMode) {

            case "default":
                givePerk(task, player);
                break;

            case "custom":
                giveCustomPerk(player, folder);
                break;

        }

    }

    private static void givePerk (String task, Player player) throws ObjectMappingException {

        int folder = GeneralGetters.getConfigFromSkill(TaskGetters.getSkillFromTask(task));
        String modifierString = PerkGetters.getPerkModifiers(folder, task).get("Modifier");
        double modifier = ModifierHandler.eval(modifierString.replace("%player-level%", String.valueOf(AccountsHandler.getLevel(TaskGetters.getSkillFromTask(task), player))));
        String function = PerkGetters.getPerkModifiers(folder, task).get("Function");
        double result = 0;

        double amount;
        if (task.equalsIgnoreCase("Watering-Apricorns") || task.equalsIgnoreCase("Making-Eggs") || task.equalsIgnoreCase("Learning-Moves") || task.equalsIgnoreCase("")) {

            amount = 0;

        } else {

            amount = Double.parseDouble(PerkGetters.getPerkModifiers(folder, task).get("Amount"));

        }

        switch (function) {

            case "add":
            case "+":
                result = modifier + amount;
                break;

            case "multiply":
            case "*":
                result = modifier * amount;
                break;

        }

        switch (task) {

            case "Mining-Fossils":
                //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " pixelmon:fossil " + (int) result);
                stack = ItemStack.builder()
                        .fromItemStack((ItemStack) (Object) (new net.minecraft.item.ItemStack(Item.getByNameOrId("pixelmon:fossil"))))
                        .quantity((int) result)
                        .build();
                player.getInventory().offer(stack);
                break;

            case "Reviving-Fossils":
                String[] ivs = PerkGetters.getPerkModifiers(folder, task).get("Modify-Stats").split(", ");
                for (String stat : ivs) {

                    IVModifier.modifyIVs(Archaeologist.getPokeToModify(), stat, modifier, function);

                }
                break;

            case "Brewing-Potions":
            case "Crafting-Items":
            case "Using-Anvil":
            case "Using-Pixelmon-Anvil":
                //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " minecraft:experience_bottle " + (int) result);
                stack = ItemStack.builder()
                        .fromItemStack((ItemStack) (Object) (new net.minecraft.item.ItemStack(Item.getByNameOrId("minecraft:experience_bottle"))))
                        .quantity((int) result)
                        .build();
                player.getInventory().offer(stack);
                break;

            case "Buying-From-Shopkeepers":

                String item = Barterer.getItem();
                //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + item + " " + (int) result);
                stack = ItemStack.builder()
                        .fromItemStack((ItemStack) (Object) (new net.minecraft.item.ItemStack(Item.getByNameOrId(item))))
                        .quantity((int) result)
                        .build();
                player.getInventory().offer(stack);
                break;

            case "Selling-To-Shopkeepers":

                Optional<EconomyService> econ = Sponge.getServiceManager().provide(EconomyService.class);

                if (econ.isPresent()) {

                    Optional<UniqueAccount> a = econ.get().getOrCreateAccount(player.getUniqueId());
                    if (a.isPresent()) {

                        Currency defaultCur = econ.get().getDefaultCurrency();
                        a.get().deposit(defaultCur, BigDecimal.valueOf(result), Cause.of(eventContext, PixelSkills.getContainer()));

                    }

                }
                break;

            case "Picking-Apricorns":
            case "Picking-Berries":
            case "Planting-Apricorns":
            case "Planting-Berries":
                String itemB = Botanist.getItem();
                //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + itemB + " " + (int) result);
                stack = ItemStack.builder()
                        .fromItemStack((ItemStack) (Object) (new net.minecraft.item.ItemStack(Item.getByNameOrId(itemB))))
                        .quantity((int) result)
                        .build();
                player.getInventory().offer(stack);
                break;

            case "Watering-Apricorns":

                String mode = PerkGetters.getPerkModifiers(3, task).get("Growth-Mode").toLowerCase();
                TileEntityApricornTree tree = Botanist.getTree();

                switch (mode) {

                    case "next":

                        tree.setStage(tree.getStage() + 1);
                        break;

                    case "max":
                        tree.setStage(6);
                        break;

                }
                break;

            case "Hatching-Eggs":
                String statsToMod = PerkGetters.getPerkModifiers(4, task).get("Modify-Stats");
                if (statsToMod.contains(", ")) {

                    String[] stats = statsToMod.split(", ");

                    for (String stat : stats) {

                        IVModifier.modifyIVs(Breeder.pokemon, stat, modifier, function);

                    }

                } else {

                    IVModifier.modifyIVs(Breeder.pokemon, statsToMod, modifier, function);

                }
                break;

            case "Making-Eggs":

                Pokemon pokemon = Breeder.pokemon;
                if (ConfigManager.getConfigNode(4, 2, "Perk-Setting-Modifiers", "Making-Eggs", "Set-To-Hidden-Ability").getBoolean()) {

                    if (pokemon.getBaseStats().abilities.length == 3) {

                        pokemon.setAbility(pokemon.getBaseStats().abilities[2]);
                        player.sendMessage(FancyText.getFancyText(MessageHandlers.getPerkMessage(GeneralGetters.getConfigFromSkill("Breeder"), task)));

                    }

                }
                break;

            case "Using-Healing-Items":
                Caregiver.finalMod = (float) (Caregiver.healAmount + result);
                Caregiver.perkTriggered = true;
                break;

            case "Catching-Legendary-Pokemon":
            case "Catching-Normal-Pokemon":
            case "Catching-Shiny-Pokemon":
                String[] stats = PerkGetters.getPerkModifiers(6, task).get("Modify-Stats").split(", ");

                for (String stat : stats) {

                    IVModifier.modifyIVs(Collector.getPokeToModify(), stat, modifier, function);

                }
                break;

            case "Kill-Mega-Bosses":
            case "Kill-Normal-Bosses":
                modifiedEXP = (int) result;
                break;

            case "Evolving-Pokemon":
                String[] statsw = PerkGetters.getPerkModifiers(8, "").get("Modify-Stats").split(", ");

                for (String stat : statsw) {

                    IVModifier.modifyIVs(Darwinist.pokemon, stat, modifier, function);

                }
                break;

            case "Catching-Fish":
                String[] statsf = PerkGetters.getPerkModifiers(9, "").get("Modify-Stats").split(", ");

                for (String stat : statsf) {

                    IVModifier.modifyIVs(Fisherman.pokemon, stat, modifier, function);

                }
                break;

            case "Defeating-NPC-Trainers":
                switch (function) {

                    case "add":
                        modifiedMoney = (int) (Gladiator.originalMoney + modifier);
                        Gladiator.perkTriggered = true;
                        break;

                    case "multiply":
                        modifiedMoney = (int) (Gladiator.originalMoney * modifier);
                        Gladiator.perkTriggered = true;
                        break;

                }
                break;

            case "Defeating-Wild-Pokemon":
                switch (function) {

                    case "add":
                        modifiedEXP = Gladiator.originalEXP + (int) modifier;
                        Gladiator.perkTriggered = true;
                        break;

                    case "multiply":
                        modifiedEXP = Gladiator.originalEXP * (int) modifier;
                        Gladiator.perkTriggered = true;
                        break;

                }
                break;

            case "Chopping-Wood":
            case "Mining-Blocks":
                if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {

                    ItemStack tool = player.getItemInHand(HandTypes.MAIN_HAND).get();
                    if (tool.supports(Keys.ITEM_DURABILITY)) {

                        int durability; //= tool.getItem().getDefaultProperty(UseLimitProperty.class).get().getValue();
                        if (tool.getItem().getDefaultProperty(UseLimitProperty.class).isPresent()) {

                            durability = tool.getItem().getDefaultProperty(UseLimitProperty.class).get().getValue();

                        } else {

                            // couldn't detect maximum durability of tool, so setting a base value of 10;
                            durability = 10;
                            PixelSkills.INSTANCE.logger.warn("Couldn't accurately detect maximum durability for tool: " + tool.getType().getId() + "!");
                            PixelSkills.INSTANCE.logger.info("Please report this message to Lypaka!");

                        }
                        int currentDur = tool.get(Keys.ITEM_DURABILITY).get();

                        switch (function) {

                            case "add":
                                result = modifier + currentDur;
                                break;

                            case "multiply":
                                result = modifier * currentDur;
                                break;

                        }

                        tool.offer(Keys.ITEM_DURABILITY, Math.min((int)result, durability));

                    }

                }
                break;

            case "Claiming-PokeStops":
            case "Claiming-Poke-Loot-Chests":
                int amountGiven = Integer.parseInt(PerkGetters.getPerkModifiers(12, "").get("Amount-Given-From-List"));
                List<String> itemList = ConfigManager.getConfigNode(12, 2, "Perk-Setting-Modifiers", "Item-List").getList(TypeToken.of(String.class));
                int size = itemList.size();

                for (int i = 0; i < amountGiven; i++) {

                    Random random = new Random();
                    int rng = random.nextInt(size);

                    if (itemList.get(i).contains("ExecuteConsoleCommand")) {

                        String[] string = itemList.get(i).split("-");
                        String cmd = string[1];
                        if (cmd.contains("%num%")) {

                            int given = (int) result;
                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), cmd
                                    .replace("%num%", String.valueOf(given))
                                    .replace("%player%", player.getName())
                            );

                        } else {

                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), cmd
                                    .replace("%player%", player.getName())
                            );

                        }

                    } else {

                        String prize = itemList.get(rng);
                        //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + prize + " " + (int) result);
                        stack = ItemStack.builder()
                                .fromItemStack((ItemStack) (Object) (new net.minecraft.item.ItemStack(Item.getByNameOrId(prize))))
                                .quantity((int) result)
                                .build();
                        player.getInventory().offer(stack);

                    }


                }
                break;

            case "Photographing-Pokemon":
                EntityPixelmon pokemon2 = Photographer.photographedMon;
                int catchrate = AccountsHandler.getCatchrateModifier(player, pokemon2.getName());
                int newRate = catchrate + (int) result;
                AccountsHandler.setCatchrateModifier(player, pokemon2.getName(), newRate);
                break;

            case "Learning-Moves":
                for (Attack a : Teacher.pokemon.getPokemonData().getMoveset().attacks) {

                    if (a == Teacher.attack) {

                        //a.ppBase = a.getAttackBase().ppMax;
                        a.pp = a.getMaxPP();
                        Teacher.pokemon.update(EnumUpdateType.Moveset);
                        break;

                    }

                }
                break;

            case "Trading-With-NPCs":
            case "Trading-With-Players":
                FriendshipModifier.modifyFriendship(Archaeologist.pokeToModify, modifier, function);
                break;

        }

        player.sendMessage(FancyText.getFancyText(MessageHandlers.getPerkMessage(GeneralGetters.getConfigFromSkill(TaskGetters.getSkillFromTask(task)), task)
                .replace("%number%", String.valueOf((int) result))
        ));

    }

    private static void giveCustomPerk (Player player, int folder) throws ObjectMappingException {

        int options = PerkGetters.getCustomPerkAmount(folder);
        Map<String, String> map;
        int perkNum = 0;

        if (options > 1) {

            Random random = new Random();
            int rng = random.nextInt(options) + 1;
            map = PerkGetters.getCustomPerkMap(folder, rng);
            perkNum = rng;

        } else {

            map = PerkGetters.getCustomPerkMap(folder, 1);
            perkNum = 1;

        }

        int amount = Integer.parseInt(map.get("Amount"));
        String modifierString = map.get("Modifier");
        double modifier = ModifierHandler.eval(modifierString.replace("%player-level%", String.valueOf(AccountsHandler.getLevel(GeneralGetters.getSkillFromConfigNumber(folder), player))));
        String prizeType = map.get("Prize-Type").toLowerCase();
        String prize = map.get("Prize");
        String function = map.get("Function");
        double result = 0;

        switch (function) {

            case "add":
                result = amount + modifier;
                break;

            case "multiply":
                result = amount * modifier;
                break;

        }

        switch (prizeType) {

            case "item":
                if (prize.contains(", ")) {

                    String[] prizes = prize.split(", ");

                    for (String prz : prizes) {

                        //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + prz + " " + (int) result);
                        stack = ItemStack.builder()
                                .fromItemStack((ItemStack) (Object) (new net.minecraft.item.ItemStack(Item.getByNameOrId(prz))))
                                .quantity((int) result)
                                .build();
                        player.getInventory().offer(stack);
                        player.sendMessage(FancyText.getFancyText(MessageHandlers.getCustomPerkMessage(folder, perkNum)
                                .replace("%number%", String.valueOf(result))
                                .replace("%prize%", prz)
                        ));

                    }

                } else {

                    //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "give " + player.getName() + " " + prize + " " + (int) result);
                    stack = ItemStack.builder()
                            .fromItemStack((ItemStack) (Object) (new net.minecraft.item.ItemStack(Item.getByNameOrId(prize))))
                            .quantity((int) result)
                            .build();
                    player.getInventory().offer(stack);
                    player.sendMessage(FancyText.getFancyText(MessageHandlers.getCustomPerkMessage(folder, perkNum)
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
                        player.sendMessage(FancyText.getFancyText(MessageHandlers.getCustomPerkMessage(folder, perkNum)
                                .replace("%prize%", prz)
                        ));

                    }

                } else {

                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "pokegive " + player.getName() + " " + prize);
                    player.sendMessage(FancyText.getFancyText(MessageHandlers.getCustomPerkMessage(folder, perkNum)
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

                player.sendMessage(FancyText.getFancyText(MessageHandlers.getCustomPerkMessage(folder, perkNum)));
                break;

        }

    }

}
