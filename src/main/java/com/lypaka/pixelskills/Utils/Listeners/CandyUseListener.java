package com.lypaka.pixelskills.Utils.Listeners;

import com.lypaka.pixelskills.GUIs.CandyPage;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.inventory.ItemStack;

public class CandyUseListener {

    @Listener
    public void onRightClickOnPokemon (InteractEntityEvent.Secondary.MainHand event, @Root Player player) {

        if (event.getTargetEntity() instanceof EntityPixelmon) {

            if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {

                ItemStack item = player.getItemInHand(HandTypes.MAIN_HAND).get();
                if (item.getType().getId().equalsIgnoreCase("pixelmon:rare_candy")) {

                    if (item.get(Keys.DISPLAY_NAME).isPresent()) {

                        String name = player.getItemInHand(HandTypes.MAIN_HAND).get().get(Keys.DISPLAY_NAME).get().toString();
                        if (name.contains("Skill Candy")) {

                            event.setCancelled(true);

                        }

                    }

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
