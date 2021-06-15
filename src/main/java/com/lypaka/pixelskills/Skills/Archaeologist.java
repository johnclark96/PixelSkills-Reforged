package com.lypaka.pixelskills.Skills;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.enums.ReceiveType;
import com.pixelmonmod.pixelmon.api.events.PixelmonReceivedEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;

public class Archaeologist {
    /**
     * Archaeologist skill, Mining Fossils task handled in Miner.java
     */

    public static Pokemon pokeToModify;

    @SubscribeEvent
    public void onFossilRevive (PixelmonReceivedEvent event) throws IOException, ObjectMappingException {

        if (event.receiveType.equals(ReceiveType.Fossil)) {

            if (GeneralGetters.isSkillEnabled(0)) {

                if (GeneralGetters.isTaskEnabled(0, "Reviving-Fossils")) {

                    EntityPlayerMP forgePlayer = event.player;
                    Player player = (Player) forgePlayer;
                    if (GeneralGetters.getSkillPerm(0).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(0))) {

                        setPokeToModify(event.pokemon);
                        ExperienceHandler.didTask(0, "Reviving-Fossils", player);

                    }

                }

            }

        // Trader skill
        } else if (event.receiveType.equals(ReceiveType.Trade)) {

            if (GeneralGetters.isSkillEnabled(14)) {

                if (GeneralGetters.isTaskEnabled(14, getTaskFromPokemon(event.pokemon))) {

                    Player player = (Player) event.player;
                    if (GeneralGetters.getSkillPerm(14).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(14))) {

                        pokeToModify = event.pokemon;
                        ExperienceHandler.didTask(14, getTaskFromPokemon(event.pokemon), player);

                    }

                }

            }

        }
        
    }

    private static void setPokeToModify (Pokemon pokemon) {
        Archaeologist.pokeToModify = pokemon;
    }

    public static Pokemon getPokeToModify() {
        return Archaeologist.pokeToModify;
    }

    private static String getTaskFromPokemon (Pokemon pokemon) {

        if (pokemon.getOwnerTrainer() == null) {

            return "Trading-With-Players";

        } else {

            return "Trading-With-NPCs";

        }

    }

}
