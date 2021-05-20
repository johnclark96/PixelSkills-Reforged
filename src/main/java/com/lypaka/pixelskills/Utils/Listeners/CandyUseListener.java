package com.lypaka.pixelskills.Utils.Listeners;

import com.lypaka.pixelskills.GUIs.CandyPage;
import com.pixelmonmod.pixelmon.api.events.RareCandyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;

public class CandyUseListener {

    @SubscribeEvent
    public void onRareCandy (RareCandyEvent event) {

        Player player = (Player) event.player;
        if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {

            if (player.getItemInHand(HandTypes.MAIN_HAND).get().get(Keys.DISPLAY_NAME).isPresent()) {

                String name = player.getItemInHand(HandTypes.MAIN_HAND).get().get(Keys.DISPLAY_NAME).get().toString();
                if (name.contains("Skill Candy")) {

                    event.setCanceled(true);

                }

            }

        }

    }

    @Listener
    public void onUse (InteractItemEvent.Secondary.MainHand event, @Root Player player) {

        if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {

            if (player.getItemInHand(HandTypes.MAIN_HAND).get().get(Keys.DISPLAY_NAME).isPresent()) {

                String name = player.getItemInHand(HandTypes.MAIN_HAND).get().get(Keys.DISPLAY_NAME).get().toString();
                if (name.contains("Skill Candy")) {

                    CandyPage.openCandyMenu(player);

                }

            }

        }

    }

}
