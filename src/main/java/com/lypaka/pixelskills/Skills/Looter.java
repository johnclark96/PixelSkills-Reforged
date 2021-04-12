package com.lypaka.pixelskills.Skills;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.PokeLootClaimedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;

public class Looter {

    @SubscribeEvent
    public void onPokeLootOpen (PokeLootClaimedEvent event) throws IOException, ObjectMappingException {
        
        if (GeneralGetters.isSkillEnabled(12)) {
            
            if (GeneralGetters.isTaskEnabled(12, "Opening-Poke-Loot-Chests")) {
                
                Player player = (Player) event.player;
                
                if (GeneralGetters.getSkillPerm(12).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(12))) {
                    
                    ExperienceHandler.didTask(12, "Opening-Poke-Loot-Chests", player);
                    
                }
            }
        }
    }
}
