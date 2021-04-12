package com.lypaka.pixelskills.Utils.Listeners;

import com.lypaka.pixelskills.Skills.Caregiver;
import com.lypaka.pixelskills.Utils.CustomEvents.UseHealingItemEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UseItemEventListener {

    @SubscribeEvent
    public void onHeal (UseHealingItemEvent event) {

        EntityPixelmon pokemon = event.getPokemon();
        event.setHeal(Caregiver.getModifiedAmount());
        pokemon.heal(event.getHeal());

    }

}
