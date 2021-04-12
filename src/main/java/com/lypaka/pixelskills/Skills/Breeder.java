package com.lypaka.pixelskills.Skills;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.BreedEvent;
import com.pixelmonmod.pixelmon.api.events.EggHatchEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;


public class Breeder {

    public static Pokemon pokemon;

    @SubscribeEvent
    public void onMakeEgg (BreedEvent.MakeEgg event) throws IOException, ObjectMappingException {
        
        if (GeneralGetters.isSkillEnabled(4)) {
            
            if (GeneralGetters.isTaskEnabled(4, "Making-Eggs")) {

                Player player = Sponge.getServer().getPlayer(event.owner).get();
                
                if (GeneralGetters.getSkillPerm(4).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(4))) {
                    
                    pokemon = event.getEgg();
                    ExperienceHandler.didTask(4, "Making-Eggs", player);

                }
                
            }
            
        }
        
    }

    @SubscribeEvent
    public void onHatchEggs (EggHatchEvent event) throws IOException, ObjectMappingException {
        
        if (GeneralGetters.isSkillEnabled(4)) {
            
            if (GeneralGetters.isTaskEnabled(4, "Hatching-Eggs")) {
                
                Player player = (Player) event.pokemon.getOwnerPlayer();
                
                if (GeneralGetters.getSkillPerm(4).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(4))) {

                    pokemon = event.pokemon;
                    ExperienceHandler.didTask(4, "Hatching-Eggs", player);

                }
                
            }
            
        }
        
    }

}
