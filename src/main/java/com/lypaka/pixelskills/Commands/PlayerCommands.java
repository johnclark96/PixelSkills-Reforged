package com.lypaka.pixelskills.Commands;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Config.Getters.TaskGetters;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.comm.packetHandlers.npc.SetNPCData;
import com.pixelmonmod.pixelmon.entities.npcs.NPCChatting;
import com.pixelmonmod.pixelmon.enums.EnumGuiScreen;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerCommands {

    public static String[] skills = new String[]{"Archaeologist", "Artificer", "Barterer", "Botanist", "Breeder", "Caregiver", "Collector", "Conqueror", "Darwinist", "Fisherman",
                                    "Gladiator", "Harvester", "Looter", "Scanner", "Trader"};

    private static CommandSpec getSkillInfoCommand() {

        return CommandSpec.builder()
                .arguments(
                        GenericArguments.string(Text.of("skill-name"))
                )
                .executor((sender, context) -> {

                    String skill = context.getOne("skill-name").get().toString();
                    Player player = (Player) sender;

                    if (isSkill(skill)) {

                        String skillFixed = getCamelCase(skill);
                        int folder = GeneralGetters.getConfigFromSkill(skillFixed);

                        try {

                            if (!ConfigManager.getConfigNode(folder, 5, "Skill-Description").getList(TypeToken.of(String.class)).isEmpty()) {

                                ArrayList<String> desc = new ArrayList<>(ConfigManager.getConfigNode(folder, 5, "Skill-Description").getList(TypeToken.of(String.class)));
                                NPCChatting npc = new NPCChatting((World) player.getWorld());
                                String name = "";
                                npc.setName(name);
                                EntityPlayerMP fP = (EntityPlayerMP) player;
                                Pixelmon.network.sendTo(new SetNPCData(name, desc), fP);
                                fP.openGui(Pixelmon.instance, EnumGuiScreen.NPCChat.getIndex(), fP.world, npc.getId(), 0, 0);

                            } else {

                                player.sendMessage(Text.of(TextColors.RED, "There is no description for this skill yet!"));

                            }

                        } catch (ObjectMappingException e) {

                            e.printStackTrace();

                        }

                    } else {

                        player.sendMessage(Text.of(TextColors.RED, "Skill name " + skill + " does not exist!"));

                    }

                    return CommandResult.success();

                })
                .build();

    }

    private static CommandSpec getTasksEnabled() {

        return CommandSpec.builder()
                .arguments(
                        GenericArguments.string(Text.of("skill"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) sender;
                    String skill = AdminCommand.getPrettySkillName(context.getOne("skill").get().toString().toLowerCase());

                    if (isSkill(skill)) {

                        String skillFixed = getCamelCase(skill);
                        List<String> tasks = TaskGetters.getTasks(skillFixed);

                        if (tasks.size() > 0) {

                            player.sendMessage(Text.of(TextColors.YELLOW, "The " + skillFixed + " skill has the following tasks: "));

                            for (String taskString : tasks) {

                                player.sendMessage(Text.of(TextColors.YELLOW, taskString));

                            }

                            player.sendMessage(Text.of(TextColors.YELLOW, "To see info about that task, use \"/pixelskills info task <task> <skill>\"."));

                        } else {

                            player.sendMessage(Text.of(TextColors.RED, "The " + skillFixed + " skill doesn't have any tasks enabled!"));

                        }

                    } else {

                        player.sendMessage(Text.of(TextColors.RED, "Skill name " + skill + " does not exist!"));

                    }

                    return CommandResult.success();

                })
                .build();

    }

    private static CommandSpec getTaskInfo() {

        return CommandSpec.builder()
                .arguments(
                        GenericArguments.string(Text.of("task")),
                        GenericArguments.string(Text.of("skill"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) sender;
                    String skill = AdminCommand.getPrettySkillName(context.getOne("skill").get().toString().toLowerCase());
                    String taskArg = context.getOne("task").get().toString();
                    String taskFixed = null;

                    if (isSkill(taskArg)) {

                        String skillFixed = getCamelCase(skill);
                        List<String> tasks = TaskGetters.getTasks(skillFixed);

                        for (String taskString : tasks) {

                            if (taskString.equalsIgnoreCase(taskArg)) {

                                taskFixed = taskString;
                                break;

                            }

                        }

                        if (taskFixed != null) {

                            int folder = GeneralGetters.getConfigFromSkill(skillFixed);

                            try {

                                if (!ConfigManager.getConfigNode(folder, 5, "Task-Descriptions", taskFixed).getList(TypeToken.of(String.class)).isEmpty()) {

                                    ArrayList<String> desc = new ArrayList<String>(ConfigManager.getConfigNode(folder, 5, "Task-Descriptions", taskFixed).getList(TypeToken.of(String.class)));
                                    NPCChatting npc = new NPCChatting((World) player.getWorld());
                                    String name = "";
                                    npc.setName(name);
                                    EntityPlayerMP fP = (EntityPlayerMP) player;
                                    Pixelmon.network.sendTo(new SetNPCData(name, desc), fP);
                                    fP.openGui(Pixelmon.instance, EnumGuiScreen.NPCChat.getIndex(), fP.world, npc.getId(), 0, 0);

                                }

                            } catch (ObjectMappingException e) {

                                e.printStackTrace();

                            }

                        }

                    }

                    return CommandResult.success();

                })
                .build();

    }

    public static CommandSpec getMsgToggle() {

        return CommandSpec.builder()
                .arguments(
                        GenericArguments.string(Text.of("skill")),
                        GenericArguments.bool(Text.of("true|false"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) sender;
                    String skill;
                    if (context.getOne("skill").get().toString().equalsIgnoreCase("all")) {

                        skill = "all";

                    } else if (context.getOne("skill").get().toString().equalsIgnoreCase("constant")) {

                        skill = "constant";

                    } else {

                        skill = AdminCommand.getPrettySkillName(context.getOne("skill").get().toString().toLowerCase());

                    }

                    boolean value = (boolean) context.getOne("true|false").get();

                    try {

                        Map<String, Boolean> toggleMap = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "EXP-Messages").getValue(new TypeToken<Map<String, Boolean>>() {});

                        if (skill.equalsIgnoreCase("all")) {

                            String[] skills = new String[]{"Archaeologist", "Artificer", "Barterer", "Botanist", "Breeder", "Caregiver", "Collector", "Conqueror", "Darwinist", "Fisherman", "Gladiator", "Harvester", "Looter", "Scanner", "Trader"};
                            for (String s : skills) {

                                toggleMap.put(s, value);

                            }

                            if (value) {

                                player.sendMessage(Text.of(TextColors.GREEN, "Successfully enabled EXP messages for all skills!"));

                            } else {

                                player.sendMessage(Text.of(TextColors.GREEN, "Successfully disabled EXP messages for all skills!"));

                            }

                        } else if (skill.equalsIgnoreCase("constant")) {

                            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Constantly-Display-Skill-Info").setValue(value);
                            player.sendMessage(Text.of(TextColors.GREEN, "Set the constant viewing mode to " + value + "!"));

                        } else {

                            toggleMap.put(skill, value);

                            if (value) {

                                player.sendMessage(Text.of(TextColors.GREEN, "Successfully enabled EXP messages for the " + skill + " skill!"));

                            } else {

                                player.sendMessage(Text.of(TextColors.GREEN, "Successfully disabled EXP messages for the " + skill + " skill!"));

                            }

                        }

                        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "EXP-Messages").setValue(toggleMap);
                        ConfigManager.savePlayer(player.getUniqueId());

                    } catch (ObjectMappingException e) {

                        e.printStackTrace();

                    }



                    return CommandResult.success();

                })
                .build();

    }

    public static CommandSpec getCheckLevel() {

        return CommandSpec.builder()
                .arguments(
                        GenericArguments.string(Text.of("skill"))
                )
                .executor((sender, context) -> {

                    Player player = (Player) sender;
                    String skill = AdminCommand.getPrettySkillName(context.getOne("skill").get().toString());
                    int lvl = AccountsHandler.getLevel(skill, player);
                    player.sendMessage(Text.of(TextColors.AQUA, "Your current level in ", TextColors.YELLOW, skill, TextColors.AQUA, " is ", TextColors.YELLOW, String.valueOf(lvl)));

                    return CommandResult.success();

                })
                .build();

    }

    public static CommandSpec getInfoSpec() {

        return CommandSpec.builder()
                .child(getTasksEnabled(), "listtasks")
                .child(getSkillInfoCommand(), "skill")
                .child(getTaskInfo(), "task")
                .executor((sender, context) -> {return CommandResult.success();}).build();

    }

    public static boolean isSkill (String string) {

        for (String skill : skills) {

            if (string.equalsIgnoreCase(skill)) {

                return true;

            }

        }

        return false;

    }

    public static String getCamelCase (String string) {

        for (String skill : skills) {

            if (string.equalsIgnoreCase(skill)) {

                return skill;

            }

        }

        return "";

    }

}
