package com.lypaka.pixelskills.Utils.Listeners;

import com.lypaka.pixelskills.Skills.Conqueror;
import com.lypaka.pixelskills.Skills.Gladiator;
import com.lypaka.pixelskills.Utils.PerkHandler;
import com.pixelmonmod.pixelmon.api.events.ExperienceGainEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PokemonEXPGainListener {

    @SubscribeEvent
    public void onEXPGain (ExperienceGainEvent event) {

        EntityPlayerMP player = event.pokemon.getPlayerOwner();
        Pokemon pokemon = event.pokemon.getPokemon();

        if (Conqueror.fPlayer != null) {

            if (player == Conqueror.fPlayer) {

                if (pokemon == Conqueror.pokemon) {

                    if (Conqueror.perkTriggered) {

                        event.setExperience(PerkHandler.modifiedEXP);
                        Conqueror.perkTriggered = false;
                        PerkHandler.modifiedEXP = 0;

                    }

                }

            }

        } else if (Gladiator.owner != null) {

            if (Gladiator.owner == player) {

                if (Gladiator.perkTriggered) {

                    event.setExperience(PerkHandler.modifiedEXP);
                    Gladiator.perkTriggered = false;

                }

            }

        }

    }

}
