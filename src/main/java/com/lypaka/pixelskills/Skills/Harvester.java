package com.lypaka.pixelskills.Skills;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.EXPGetters;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Harvester {

    private static double base;

    @Listener
    public void onMine (ChangeBlockEvent.Break event, @Root Player player) throws IOException, ObjectMappingException {

        if (!Sponge.getServer().getPlayer(player.getName()).isPresent()) return;

        if (player.gameMode().get().equals(GameModes.CREATIVE)) return;

        Optional<Player> cause = event.getCause().first(Player.class);

        if (cause.isPresent()) {

            for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {

                Optional<Location<World>> optLoc = transaction.getOriginal().getLocation();

                if (optLoc.isPresent()) {

                    BlockSnapshot snapshot = transaction.getOriginal();

                    if (!snapshot.getCreator().isPresent() && !snapshot.getState().getType().getName().contains("pixelmon:berrytree")) {

                        if (snapshot.getState().getType().getName().contains("fossil") && !snapshot.getState().getType().getName().contains("machine") && !snapshot.getState().getType().getName().contains("cleaner")) {

                            // Archaeologist's Mining Fossils task
                            if (GeneralGetters.isSkillEnabled(0)) {

                                if (GeneralGetters.isTaskEnabled(0, "Mining-Fossils")) {

                                    if (GeneralGetters.getSkillPerm(0).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(0))) {

                                        if (event.isCancelled()) {

                                            return;

                                        }

                                        try {

                                            ExperienceHandler.didTask(0, "Mining-Fossils", player);

                                        } catch (IOException | ObjectMappingException ioException) {

                                            ioException.printStackTrace();

                                        }

                                    }

                                }

                            }

                        } else {

                            // Harvester Skill
                            if (GeneralGetters.isSkillEnabled(11)) {

                                String blockName = snapshot.getState().getType().getName();

                                if (blockName.contains("log") || blockName.contains("logs") || blockName.contains("planks") || blockName.contains("wood")) {

                                    if (GeneralGetters.isTaskEnabled(11, "Chopping-Wood")) {

                                        if (GeneralGetters.getSkillPerm(11).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(11))) {

                                            if (!getBlackList().contains(blockName)) {

                                                if (getWhiteList().containsKey(blockName)) {

                                                    for (Map.Entry<String, Double> entry : getWhiteList().entrySet()) {

                                                        if (entry.getKey().equalsIgnoreCase(blockName)) {

                                                            base = entry.getValue();
                                                            break;

                                                        }

                                                    }


                                                } else {

                                                    base = EXPGetters.getEXPFromTask(11, "Chopping-Wood", player);

                                                }

                                                if (event.isCancelled()) {

                                                    return;

                                                }

                                                ExperienceHandler.didHarvesterTask(player, "Chopping-Wood", base);

                                            }

                                        }

                                    }

                                } else {

                                    if (GeneralGetters.isTaskEnabled(11, "Mining-Blocks")) {

                                        if (GeneralGetters.getSkillPerm(11).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(11))) {

                                            if (!getBlackList().contains(blockName)) {

                                                if (getWhiteList().containsKey(blockName)) {

                                                    for (Map.Entry<String, Double> entry : getWhiteList().entrySet()) {

                                                        if (entry.getKey().equalsIgnoreCase(blockName)) {

                                                            base = entry.getValue();
                                                            break;

                                                        }

                                                    }


                                                } else {

                                                    base = EXPGetters.getEXPFromTask(11, "Mining-Blocks", player);

                                                }

                                                if (event.isCancelled()) {

                                                    return;

                                                }

                                                ExperienceHandler.didHarvesterTask(player, "Mining-Blocks", ExperienceHandler.getPrettyDouble(base));

                                            }

                                        }

                                    }

                                }

                            }

                        }

                    }

                }

            }

        }

    }

    private static List<String> getBlackList() throws ObjectMappingException {

        return ConfigManager.getConfigNode(11, 2, "Perk-Setting-Modifiers", "Lists", "Blocks-Blacklist").getList(TypeToken.of(String.class));

    }

    private static Map<String, Double> getWhiteList() throws ObjectMappingException {

        return ConfigManager.getConfigNode(11, 2, "Perk-Setting-Modifiers", "Lists", "Blocks-Whitelist").getValue(new TypeToken<Map<String, Double>>() {});

    }


}
