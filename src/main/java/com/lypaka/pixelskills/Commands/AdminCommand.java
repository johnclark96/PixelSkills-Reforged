package com.lypaka.pixelskills.Commands;

import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.*;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.lypaka.pixelskills.Utils.FancyText;
import com.lypaka.pixelskills.Utils.MessageGetters;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

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
                    if (PerkGetters.getChanceValue(GeneralGetters.getConfigFromSkill(skill)).equalsIgnoreCase("integer")) {

                        int value = Integer.parseInt(context.getOne("chance").get().toString());
                        AccountsHandler.setNextPerkChanceManual(GeneralGetters.getConfigFromSkill(skill), player, value);
                        sender.sendMessage(Text.of(TextColors.GREEN, "Successfully set " + player.getName() + "'s chance value to " + value + "!"));

                    } else {

                        double value = Double.parseDouble(context.getOne("skill").get().toString());
                        AccountsHandler.setNextPerkChanceManual(GeneralGetters.getConfigFromSkill(skill), player, value);
                        sender.sendMessage(Text.of(TextColors.GREEN, "Successfully set " + player.getName() + "'s chance value to " + value + "!"));

                    }

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
                    AccountsHandler.setLevel(skill, player, level);
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

                        if (ExperienceHandler.didLevelUp(skill, player)) {

                            ExperienceHandler.levelUp(skill, player);

                        }

                    } catch (ObjectMappingException e) {

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
                    AccountsHandler.setLevel(skill, player, level);
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
                .permission("pixelskills.command.admin")
                .executor((sender, context) -> {return CommandResult.success();}).build();

    }

    public static String getPrettySkillName (String name) {

        switch (name) {

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

            case "scanner":
                return "Scanner";
                
            case "trader":
                return "Trader";

        }
        
        throw new IllegalArgumentException("Skill " + name + " does not exist!");

    }

}
