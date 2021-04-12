package com.lypaka.pixelskills.Skills;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.CustomEvents.UseHealingItemEvent;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;

import java.io.IOException;

public class Caregiver {

    public static float finalMod;
    public static float healAmount;
    private static EntityPixelmon pixelmon;
    public static boolean perkTriggered;

    /**
     *
     * TO-DO:
     * Rewrite this skill when Pixelmon adds an event for using items on Pokemon outside of battle
     * The way that I have to handle it by coding it in Sponge is just...icky.
     *
     */

    @Listener
    public void onRightClick (InteractEntityEvent.Secondary.MainHand event, @Root Player player) throws IOException, ObjectMappingException {
        
        if (event.getTargetEntity() instanceof EntityPixelmon) {
            
            EntityPixelmon pokemon = (EntityPixelmon) event.getTargetEntity();

            if (!pokemon.hasOwner()) return;

            if (pokemon.getOwnerId() != player.getUniqueId()) return;
            
            if (pokemon.getHealth() < pokemon.getMaxHealth()) {
                
                if (isHealingItem(getItemFromString(player.getItemInHand(HandTypes.MAIN_HAND).get().createSnapshot().getType().toString()))) {
                    
                    if (GeneralGetters.isSkillEnabled(5)) {
                        
                        if (GeneralGetters.isTaskEnabled(5, "Using-Healing-Items")) {
                            
                            if (GeneralGetters.getSkillPerm(5).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(5))) {

                                ExperienceHandler.didTask(5, "Using-Healing-Items", player);
                                event.setCancelled(true);
                                
                                if (perkTriggered) {
                                    
                                    //Don't judge me.
                                    float heal = getHPAmtFromItem(getItemFromString(player.getItemInHand(HandTypes.MAIN_HAND).get().createSnapshot().getType().toString()));
                                    EntityPlayerMP fPlayer = (EntityPlayerMP) player;
                                    MinecraftForge.EVENT_BUS.post(new UseHealingItemEvent(fPlayer, pokemon, heal));
                                    player.getItemInHand(HandTypes.MAIN_HAND).get().setQuantity(player.getItemInHand(HandTypes.MAIN_HAND).get().getQuantity() - 1);
                                    Caregiver.perkTriggered = false;
                                    
                                } else {
                                    
                                    pokemon.heal(getHPAmtFromItem(getItemFromString(player.getItemInHand(HandTypes.MAIN_HAND).get().createSnapshot().getType().toString())));
                                    player.getItemInHand(HandTypes.MAIN_HAND).get().setQuantity(player.getItemInHand(HandTypes.MAIN_HAND).get().getQuantity() - 1);
                                    
                                }
                                
                            }
                            
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
    }


    public static float getModifiedAmount() {

        return Caregiver.finalMod;

    }

    private static void setPokemon (EntityPixelmon pokemon) {
        Caregiver.pixelmon = pokemon;
    }

    public static EntityPixelmon getPixelmon() {
        return Caregiver.pixelmon;
    }

    private static int getHPAmtFromItem (String item) {

        switch (item) {

            case "berry_juice":
            case "potion":
            case "sweet_heart":
                return 20;

            case "fresh_water":
                return 30;

            case "energy_powder":
            case "soda_pop":
                return 50;

            case "super_potion":
                return 60;

            case "lemonade":
                return 70;

            case "moo_moo_milk":
                return 100;

            case "hyper_potion":
                return 120;

            case "energy_root":
                return 200;

            case "full_restore":
                return 999;

        }

        return 0;
    }

    private static String getItemFromString (String string) {
        String[] separator = string.split("=");
        return separator[1].replace("}", "");
    }

    private boolean isHealingItem (String string) {
        return string.equals("potion") || string.equals("super_potion") || string.equals("hyper_potion") || string.contains("berry_juice") ||
                string.contains("sweet_heart") || string.contains("fresh_water") || string.contains("energy_powder") || string.contains("soda_pop") ||
                string.contains("lemonade") || string.contains("moo_moo_milk") || string.contains("energy_root");
    }

}
