package com.lypaka.pixelskills.Skills;

import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.CameraEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;

public class Photographer {

    public static EntityPixelmon photographedMon;

    @SubscribeEvent
    public void onCamera (CameraEvent.TakePhoto event) throws IOException, ObjectMappingException {

        if (GeneralGetters.isSkillEnabled(13)) {

            if (GeneralGetters.isTaskEnabled(13, "Photographing-Pokemon")) {

                Player player = (Player) event.player;
                EntityPixelmon pokemon = event.pixelmon;
                if (pokemon.hasOwner()) {

                    if (ConfigManager.getConfigNode(13, 2, "Perk-Setting-Modifiers", "Settings", "Photographing-Player-Pokemon-Gives-EXP").getBoolean()) {

                        photographedMon = pokemon;
                        ExperienceHandler.didTask(13, "Photographing-Pokemon", player);

                    }

                } else {

                    photographedMon = pokemon;
                    ExperienceHandler.didTask(13, "Photographing-Pokemon", player);

                }

                if (ConfigManager.getConfigNode(13, 2, "Perk-Settings-Modifiers", "Settings", "Despawn-Photographed-Pokemon").getBoolean()) {

                    pokemon.setDead();


                }

            }

        }

    }

    public static boolean applyModifiers() {

        return ConfigManager.getConfigNode(13, 2, "Perk-Setting-Modifiers", "Settings", "Apply-Catchrate-Modifiers").getBoolean();

    }

}
