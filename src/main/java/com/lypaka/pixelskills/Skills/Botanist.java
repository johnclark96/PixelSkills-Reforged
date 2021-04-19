package com.lypaka.pixelskills.Skills;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.ApricornEvent;
import com.pixelmonmod.pixelmon.api.events.BerryEvent;
import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityApricornTree;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;

public class Botanist {

    private static String item;
    private static TileEntityApricornTree plant;

    @SubscribeEvent
    public void onApricornPick (ApricornEvent.PickApricorn event) throws IOException, ObjectMappingException {

        if (event.isCanceled()) {

            return;

        }
        
        if (GeneralGetters.isSkillEnabled(3)) {
            
            if (GeneralGetters.isTaskEnabled(3, "Picking-Apricorns")) {
                
                Player player = (Player) event.player;
                
                if (GeneralGetters.getSkillPerm(3).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(3))) {

                    String type = event.apricorn.toString().toLowerCase();
                    setItem("pixelmon:" + type + "_apricorn");
                    ExperienceHandler.didTask(3, "Picking-Apricorns", player);

                }
                
            }
            
        }
        
    }

    @SubscribeEvent
    public void onApricornPlant (ApricornEvent.ApricornPlanted event) throws IOException, ObjectMappingException {

        if (event.isCanceled()) {

            return;

        }
        
        if (GeneralGetters.isSkillEnabled(3)) {
            
            if (GeneralGetters.isTaskEnabled(3, "Planting-Apricorns")) {
                
                Player player = (Player) event.player;
                
                if (GeneralGetters.getSkillPerm(3).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(3))) {

                    String type = event.apricorn.toString().toLowerCase();
                    setItem("pixelmon:" + type + "_apricorn");
                    ExperienceHandler.didTask(3, "Planting-Apricorns", player);

                }
                
            }
            
        }
        
    }

    @SubscribeEvent
    public void onBerryPick (BerryEvent.PickBerry event) throws IOException, ObjectMappingException {

        if (event.isCanceled()) {

            return;

        }
        
        if (GeneralGetters.isSkillEnabled(3)) {
            
            if (GeneralGetters.isTaskEnabled(3, "Picking-Berries")) {
                
                Player player = (Player) event.player;
                
                if (GeneralGetters.getSkillPerm(3).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(3))) {

                    String type = event.berry.toString().toLowerCase();
                    setItem("pixelmon:" + type + "_berry");
                    ExperienceHandler.didTask(3, "Picking-Berries", player);

                }
                
            }
            
        }
        
    }

    @SubscribeEvent
    public void onBerryPlant (BerryEvent.BerryPlanted event) throws IOException, ObjectMappingException {

        if (event.isCanceled()) {

            return;

        }
        
        if (GeneralGetters.isSkillEnabled(3)) {
            
            if (GeneralGetters.isTaskEnabled(3, "Planting-Berries")) {
                
                Player player = (Player) event.player;
                
                if (GeneralGetters.getSkillPerm(3).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(3))) {

                    String type = event.berry.toString().toLowerCase();
                    setItem("pixelmon:" + type + "_berry");
                    ExperienceHandler.didTask(3, "Planting-Berries", player);

                }
                
            }
            
        }
        
    }

    @SubscribeEvent
    public void onApricornWater (ApricornEvent.ApricornWatered event) throws IOException, ObjectMappingException {

        if (event.isCanceled()) {

            return;

        }
        
        if (GeneralGetters.isSkillEnabled(3)) {
            
            if (GeneralGetters.isTaskEnabled(3, "Watering-Apricorns")) {
                
                Player player = (Player) event.player;
                
                if (GeneralGetters.getSkillPerm(3).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(3))) {

                    setPlant(event.tree);
                    ExperienceHandler.didTask(3, "Watering-Apricorns", player);

                }
                
            }
            
        }
        
    }
    

    private static void setItem (String thing) {
        item = thing;
    }

    private static void setPlant (TileEntityApricornTree tree) {
        plant = tree;
    }

    public static String getItem() {

        return item;

    }

    public static TileEntityApricornTree getTree() {

        return plant;

    }

}
