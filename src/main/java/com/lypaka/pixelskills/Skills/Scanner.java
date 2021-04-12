package com.lypaka.pixelskills.Skills;

import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.PixelSkills;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.LensUsedEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Scanner {

    public static EntityPixelmon scannedMon;
    private static Task task;

    @SubscribeEvent
    public void onScan (LensUsedEvent event) throws IOException, ObjectMappingException {

        if (GeneralGetters.isSkillEnabled(13)) {

            if (GeneralGetters.isTaskEnabled(13, "Scanning-Pokemon")) {

                Player player = (Player) event.player;

                if (GeneralGetters.getSkillPerm(13).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(13))) {

                    EntityPixelmon pokemon = event.pixelmon;
                    boolean damage = ConfigManager.getConfigNode(13, 2, "Perk-Setting-Modifiers", "Settings", "Damage-Lens").getBoolean();

                    if (pokemon.hasOwner()) {

                        if (ConfigManager.getConfigNode(13, 2, "Perk-Setting-Modifiers", "Settings", "Scanning-Player-Pokemon-Gives-EXP").getBoolean()) {

                            scannedMon = pokemon;
                            event.shouldItemBeDamaged = damage;
                            ExperienceHandler.didTask(13, "Scanning-Pokemon", player);

                        }

                    } else {

                        scannedMon = pokemon;
                        event.shouldItemBeDamaged = damage;
                        ExperienceHandler.didTask(13, "Scanning-Pokemon", player);

                    }

                    if (ConfigManager.getConfigNode(13, 2, "Perk-Setting-Modifiers", "Settings", "Despawn-Scanned-Pokemon").getBoolean()) {

                        int delay = ConfigManager.getConfigNode(13, 2, "Perk-Setting-Modifiers", "Settings", "Despawn-Delay").getInt();

                        task = Task.builder()
                                .delay(delay, TimeUnit.SECONDS)
                                .execute(t -> {

                                    pokemon.setDead();

                                })
                                .submit(PixelSkills.INSTANCE);


                    }

                }

            }

        }

    }

    public static boolean applyModifiers() {

        return ConfigManager.getConfigNode(13, 2, "Perk-Setting-Modifiers", "Settings", "Apply-Catchrate-Modifiers").getBoolean();

    }

}
