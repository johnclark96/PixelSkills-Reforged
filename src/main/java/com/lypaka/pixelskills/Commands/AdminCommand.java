package com.lypaka.pixelskills.Commands;

import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.*;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.lypaka.pixelskills.Utils.FancyText;
import com.lypaka.pixelskills.Utils.MessageHandlers;
import com.lypaka.pixelskills.Utils.SkillCandy.CandyItem;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;

public class AdminCommand {

    public static CommandSpec getAdminCommands() {

        CommandSpec setPerkChance = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.string(Text.of("skill")),
                        GenericArguments.string(Text.of("chance"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) context.getOne("player").get();
                    String skill = getPrettySkillName(context.getOne("skill").get().toString().toLowerCase());
                    double value = Double.parseDouble(context.getOne("chance").get().toString());
                    AccountsHandler.setNextPerkChanceManual(GeneralGetters.getConfigFromSkill(skill), player, value);
                    sender.sendMessage(Text.of(TextColors.GREEN, "Successfully set " + player.getName() + "'s chance value to " + value + "!"));

                    return CommandResult.success();

                })
                .build();

        CommandSpec checkLevel = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.string(Text.of("skill"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) context.getOne("player").get();
                    String skill = getPrettySkillName(context.getOne("skill").get().toString().toLowerCase());
                    int level = AccountsHandler.getLevel(skill, player);
                    sender.sendMessage(Text.of(TextColors.YELLOW, player.getName() + "'s level in " + skill + " is " + level + "."));

                    return CommandResult.success();

                })
                .build();

        CommandSpec setLevel = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.string(Text.of("skill")),
                        GenericArguments.integer(Text.of("level"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) context.getOne("player").get();
                    String skill = getPrettySkillName(context.getOne("skill").get().toString().toLowerCase());
                    int level = (int) context.getOne("level").get();
                    int currentLevel = AccountsHandler.getLevel(skill, player);
                    int difference = level - currentLevel;
                    for (int i = 0; i < difference; i++) {

                        try {

                            ExperienceHandler.levelUp(skill, player, true, false);

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                    }
                    //AccountsHandler.setLevel(skill, player, level);
                    ConfigManager.savePlayer(player.getUniqueId());
                    sender.sendMessage(Text.of(TextColors.GREEN, "Successfully set " + player.getName() + "'s level in " + skill + " to " + level + "."));

                    return CommandResult.success();

                })
                .build();

        CommandSpec checkEXP = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.string(Text.of("skill"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) context.getOne("player").get();
                    String skill = getPrettySkillName(context.getOne("skill").get().toString().toLowerCase());
                    double exp = AccountsHandler.getCurrentEXP(skill, player);
                    sender.sendMessage(Text.of(TextColors.YELLOW, player.getName() + "'s EXP in " + skill + " is " + exp + "."));

                    return CommandResult.success();

                })
                .build();

        CommandSpec setEXP = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.string(Text.of("skill")),
                        GenericArguments.doubleNum(Text.of("exp"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) context.getOne("player").get();
                    String skill = getPrettySkillName(context.getOne("skill").get().toString().toLowerCase());
                    double exp = (double) context.getOne("exp").get();
                    AccountsHandler.setEXP(skill, exp, player);
                    ConfigManager.savePlayer(player.getUniqueId());
                    sender.sendMessage(Text.of(TextColors.GREEN, "Successfully set " + player.getName() + "'s EXP in " + skill + " to " + exp + "."));

                    return CommandResult.success();

                })
                .build();

        CommandSpec addEXP = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.string(Text.of("skill")),
                        GenericArguments.doubleNum(Text.of("exp"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) context.getOne("player").get();
                    String skill = getPrettySkillName(context.getOne("skill").get().toString().toLowerCase());
                    double exp = (double) context.getOne("exp").get();
                    double currentEXP = AccountsHandler.getCurrentEXP(skill, player);
                    double newEXP = exp + currentEXP;
                    AccountsHandler.setEXP(skill, ExperienceHandler.getPrettyDouble(newEXP), player);
                    ConfigManager.savePlayer(player.getUniqueId());

                    try {

                        int conf = GeneralGetters.getConfigFromSkill(skill);

                        if (AccountsHandler.areMessagesEnabled(player, skill)) {

                            if (exp != 1) {

                                player.sendMessage(FancyText.getFancyText(MessageHandlers.getEXPGained(conf)
                                        .replace("%exp%", String.valueOf(exp))
                                        .replace("%skill%", skill)
                                        .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                                ));

                            } else {

                                player.sendMessage(FancyText.getFancyText(MessageHandlers.getEXPGained(conf)
                                        .replace("points", "point")
                                        .replace("%exp%", String.valueOf(exp))
                                        .replace("%skill%", skill)
                                        .replace("%next-level%", String.valueOf(AccountsHandler.getEXPToNextLvl(skill, player)))
                                ));
                            }

                        }

                        if (ExperienceHandler.didLevelUp(skill, player)) {

                            ExperienceHandler.levelUp(skill, player, false, false);

                        }

                    } catch (ObjectMappingException | IOException e) {

                        e.printStackTrace();

                    }

                    sender.sendMessage(Text.of(TextColors.GREEN, "Successfully set " + player.getName() + "'s EXP in " + skill + " to " + exp + "."));

                    return CommandResult.success();

                })
                .build();

        CommandSpec levelUp = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.string(Text.of("skill"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) context.getOne("player").get();
                    String skill = getPrettySkillName(context.getOne("skill").get().toString().toLowerCase());
                    int level = AccountsHandler.getLevel(skill, player) + 1;
                    //AccountsHandler.setLevel(skill, player, level);
                    try {

                        ExperienceHandler.levelUp(skill, player, true, false);

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }
                    ConfigManager.savePlayer(player.getUniqueId());
                    sender.sendMessage(Text.of(TextColors.GREEN, "Successfully leveled up " + player.getName() + "'s level in " + skill + " to " + level + "."));

                    return CommandResult.success();

                })
                .build();

        CommandSpec reset = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.string(Text.of("skill"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) context.getOne("player").get();
                    String skill = getPrettySkillName(context.getOne("skill").get().toString().toLowerCase());
                    AccountsHandler.setLevel(skill, player, 1);
                    AccountsHandler.setEXP(skill, 0.0, player);
                    ConfigManager.savePlayer(player.getUniqueId());
                    sender.sendMessage(Text.of(TextColors.GREEN, "Successfully reset " + player.getName() + "'s " + skill + "."));

                    return CommandResult.success();

                })
                .build();

        CommandSpec reload = CommandSpec.builder()
                .executor((sender, context) -> {

                    ConfigManager.load();
                    sender.sendMessage(Text.of(TextColors.GREEN, "Successfully reloaded PixelSkills config!"));

                    return CommandResult.success();

                })
                .build();

        CommandSpec toggle = CommandSpec.builder()
                .arguments(
                        GenericArguments.string(Text.of("module")),
                        GenericArguments.string(Text.of("object")),
                        GenericArguments.bool(Text.of("value"))
                )
                .executor((sender, context) -> {

                    // Modules: perks, rewards, level-locked-rewards, tasks, skills
                    String module = context.getOne("module").get().toString().toLowerCase();
                    String object = context.getOne("object").get().toString().toLowerCase();
                    boolean value = Boolean.parseBoolean(context.getOne("value").get().toString());

                    switch (module) {

                        case "perks":
                            for (String s : TaskGetters.getAllTasks()) {

                                if (s.equalsIgnoreCase(object)) {

                                    PerkGetters.setPerkStatus(s, value);
                                    break;

                                }

                            }
                            break;

                        case "rewards":
                            RewardGetters.setRewardStatus(getPrettySkillName(object), value);
                            break;

                        case "level-locked-rewards":
                        case "llr":
                            LevelLockedRewardGetters.setLLStatus(getPrettySkillName(object), value);
                            break;

                        case "tasks":
                            for (String s : TaskGetters.getAllTasks()) {

                                if (s.equalsIgnoreCase(object)) {

                                    TaskGetters.setTaskStatus(s, value);
                                    break;

                                }

                            }
                            break;

                        case "skills":
                            GeneralGetters.setSkillStatus(getPrettySkillName(object), value);
                            break;

                    }

                    return CommandResult.success();

                })
                .build();

        CommandSpec msgToggle = CommandSpec.builder()
                .arguments(
                        GenericArguments.string(Text.of("skill")),
                        GenericArguments.string(Text.of("type")),
                        GenericArguments.bool(Text.of("value"))
                )
                .executor((sender, context) -> {

                    String skill = getPrettySkillName(context.getOne("skill").get().toString());
                    String type = context.getOne("type").get().toString();
                    boolean value = Boolean.parseBoolean(context.getOne("value").get().toString());
                    int folder = GeneralGetters.getConfigFromSkill(skill);

                    if (type.equalsIgnoreCase("exp")) {

                        ConfigManager.getConfigNode(folder, 0, "Messages", "Enable", "EXP").setValue(value);

                    } else {

                        ConfigManager.getConfigNode(folder, 0, "Messages", "Enable", "Level-Up").setValue(value);

                    }

                    ConfigManager.saveSkill(folder, 0);
                    sender.sendMessage(Text.of(TextColors.GREEN, "Successfully toggled the " + type + " messages for the " + skill + " skill!"));

                    return CommandResult.success();

                })
                .build();

        CommandSpec giveCandy = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player")),
                        GenericArguments.optional(GenericArguments.integer(Text.of("amount")))
                )
                .executor((sender, context) -> {

                    Player player = (Player) context.getOne("player").get();
                    int amount;
                    if (context.getOne("amount").isPresent()) {

                        amount = (int) context.getOne("amount").get();

                    } else {

                        amount = 1;

                    }

                    player.getInventory().offer(CandyItem.getSkillCandy(amount));
                    sender.sendMessage(Text.of(TextColors.GREEN, "Successfully gave " + player.getName() + " " + amount + " Skill Candy!"));
                    player.sendMessage(Text.of(TextColors.AQUA, "You've received " + amount + " Skill Candy!"));

                    return CommandResult.success();

                })
                .build();

        CommandSpec setUnbreakable = CommandSpec.builder()
                .arguments(
                        GenericArguments.player(Text.of("player"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) context.getOne("player").get();
                    if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {

                        ItemStack tool = player.getItemInHand(HandTypes.MAIN_HAND).get();
                        if (tool.supports(Keys.ITEM_DURABILITY)) {

                            tool.offer(Keys.UNBREAKABLE, true);
                            player.sendMessage(Text.of("Your tool is now unbreakable!"));

                        } else {

                            sender.sendMessage(Text.of("Player's current tool does not support durability!"));

                        }

                    } else {

                        sender.sendMessage(Text.of("Could not detect a tool in the player's hand!"));

                    }

                    return CommandResult.success();

                })
                .build();

        return CommandSpec.builder()
                .child(checkLevel, "checklevel", "checklvl")
                .child(levelUp, "lvlup", "levelup")
                .child(checkEXP, "checkexp")
                .child(setEXP, "setexp")
                .child(addEXP, "addexp")
                .child(setLevel, "setlvl", "setlevel")
                .child(setPerkChance, "setperkchance")
                .child(reset, "reset")
                .child(reload, "reload")
                .child(toggle, "toggle")
                .child(msgToggle, "msgtoggle")
                .child(giveCandy, "give")
                .child(setUnbreakable, "setunbreakable")
                .permission("pixelskills.command.admin")
                .executor((sender, context) -> {return CommandResult.success();}).build();

    }

    public static String getPrettySkillName (String name) {

        switch (name.toLowerCase()) {

            case "archaeologist":
                return "Archaeologist";

            case "artificer":
                return "Artificer";

            case "barterer":
                return "Barterer";

            case "botanist":
                return "Botanist";

            case "breeder":
                return "Breeder";

            case "caregiver":
                return "Caregiver";

            case "collector":
                return "Collector";

            case "conqueror":
                return "Conqueror";

            case "darwinist":
                return "Darwinist";

            case "fisherman":
                return "Fisherman";
                
            case "gladiator":
                return "Gladiator";
                
            case "harvester":
                return "Harvester";
                
            case "looter":
                return "Looter";

            case "photographer":
                return "Photographer";

            case "teacher":
                return "Teacher";
                
            case "trader":
                return "Trader";

        }
        
        throw new IllegalArgumentException("Skill " + name + " does not exist!");

    }

}
