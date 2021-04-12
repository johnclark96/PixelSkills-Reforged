package com.lypaka.pixelskills.Skills;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.EvolveEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;

public class Darwinist {

    public static EntityPixelmon pokemon;

    @SubscribeEvent
    public void onEvolve (EvolveEvent.PostEvolve event) throws IOException, ObjectMappingException {

        if (GeneralGetters.isSkillEnabled(8)) {

            if (GeneralGetters.isTaskEnabled(8, "Evolving-Pokemon")) {

                Player player = (Player) event.player;

                if (GeneralGetters.getSkillPerm(8).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(8))) {

                    pokemon = event.pokemon;
                    ExperienceHandler.didTask(8, "Evolving-Pokemon", player);

                }

            }

        }

    }

}
