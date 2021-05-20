package com.lypaka.pixelskills.Skills;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.pokemon.MovesetEvent;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;

public class Teacher {

    public static EntityPixelmon pokemon;
    public static Attack attack;

    @SubscribeEvent
    public void onMoveLearn (MovesetEvent.LearntMoveEvent event) throws IOException, ObjectMappingException {

        if (GeneralGetters.isSkillEnabled(14)) {

            if (GeneralGetters.isTaskEnabled(14, "Learning-Moves")) {

                EntityPlayerMP owner = (EntityPlayerMP) event.pokemon.getOwnerPlayer();
                Player player = (Player) owner;

                if (GeneralGetters.getSkillPerm(14).equalsIgnoreCase("none") || player.hasPermission(GeneralGetters.getSkillPerm(15))) {

                    if (event.replacedAttack != null) {

                        pokemon = event.pokemon.getPixelmonIfExists();
                        attack = event.learntAttack;
                        ExperienceHandler.didTask(14, "Learning-Moves", player);

                    }

                }

            }

        }

    }

}
