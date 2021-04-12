package com.lypaka.pixelskills.Skills;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.FishingEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;

public class Fisherman {

    public static EntityPixelmon pokemon;

    @SubscribeEvent
    public void onFish (FishingEvent.Reel event) throws IOException, ObjectMappingException {

        if (GeneralGetters.isSkillEnabled(9)) {

            if (GeneralGetters.isTaskEnabled(9, "Catching-Fish")) {

                Player player = (Player) event.player;

                if (GeneralGetters.getSkillPerm(9).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(9))) {

                    if (event.optEntity.isPresent()) {

                        if (event.optEntity.get() instanceof EntityPixelmon) {

                            pokemon = (EntityPixelmon) event.optEntity.get();
                            ExperienceHandler.didTask(9, "Catching-Fish", player);

                        }

                    }

                }

            }

        }

    }

}
