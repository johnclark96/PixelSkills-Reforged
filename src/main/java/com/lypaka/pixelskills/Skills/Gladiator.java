package com.lypaka.pixelskills.Skills;

import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.PerkHandlers.GladiatorPerks;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.BeatTrainerEvent;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBase;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.links.PokemonLink;
import com.pixelmonmod.pixelmon.enums.EnumBossMode;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;
import java.util.List;

public class Gladiator {

    public static boolean perkTriggered;
    public static int originalMoney;
    public static int originalEXP;
    public static EntityPlayerMP owner;
    public static int pokelvl;

    @SubscribeEvent
    public void onTrainerDefeat (BeatTrainerEvent event) throws IOException, ObjectMappingException {
        
        if (GeneralGetters.isSkillEnabled(10)) {
            
            if (GeneralGetters.isTaskEnabled(10, "Defeating-NPC-Trainers")) {
                
                Player player = (Player) event.player;

                List<String> list = ConfigManager.getConfigNode(10, 2, "Perk-Setting-Modifiers", "NPC-Blacklist").getList(TypeToken.of(String.class));
                String location = player.getWorld().getName() + "," + event.trainer.getPosition().getX() + "," + event.trainer.getPosition().getY() + "," + event.trainer.getPosition().getZ();
                for (String loc : list) {

                    if (loc.equalsIgnoreCase(location)) {

                        return;

                    }

                }
                
                if (GeneralGetters.getSkillPerm(10).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(10))) {

                    originalMoney = event.trainer.getWinMoney();
                    ExperienceHandler.didTask(10, "Defeating-NPC-Trainers", player);

                    if (perkTriggered) {

                        event.trainer.winMoney = GladiatorPerks.modifiedMoney;
                        perkTriggered = false;

                    }
                    
                }
                
            }
            
        }
        
    }

    @SubscribeEvent
    public void onPlayerDefeat (BattleEndEvent event) {
        
        if (event.bc.participants.get(0) instanceof PlayerParticipant && event.bc.participants.get(1) instanceof PlayerParticipant) {
            
            Player player1 = (Player) event.bc.participants.get(0).getEntity();
            Player player2 = (Player) event.bc.participants.get(1).getEntity();
            
            if (GeneralGetters.isSkillEnabled(10)) {
                
                if (GeneralGetters.isTaskEnabled(10, "Defeating-Players")) {
                    
                    // Huge thanks goes out to a Java Wizard by the name of BlackSpirit of Pixelmon Generations for the following Java magic
                    ImmutableMap<BattleParticipant, BattleResults> map = event.results;
                    
                    map.forEach((key, value) -> {
                        
                        if (value == BattleResults.VICTORY) {
                            
                            if (key instanceof PlayerParticipant) {
                                
                                // Player 1 won the battle, so we need to check if they have the permission to gain EXP from this task
                                
                                if (((PlayerParticipant) key).player.getUniqueID().equals(player1.getUniqueId())) {
                                    
                                    if (GeneralGetters.getSkillPerm(10).equals("none") || player1.hasPermission(GeneralGetters.getSkillPerm(11))) {
                                        
                                        try {
                                            
                                            ExperienceHandler.didTask(10, "Defeating-Players", player1);
                                            
                                        } catch (IOException | ObjectMappingException e) {
                                            
                                            e.printStackTrace();
                                            
                                        }
                                        
                                    }
                                    
                                // Likewise, player 2 won the battle so they would get the skill EXP
                                    
                                } else if (((PlayerParticipant) key).player.getUniqueID().equals(player2.getUniqueId())) {
                                    
                                    if (GeneralGetters.getSkillPerm(10).equals("none") || player2.hasPermission(GeneralGetters.getSkillPerm(10))) {
                                        
                                        try {
                                            
                                            ExperienceHandler.didTask(10, "Defeating-Players", player2);
                                            
                                        } catch (IOException | ObjectMappingException e) {
                                            
                                            e.printStackTrace();
                                            
                                        }
                                        
                                    }
                                    
                                }
                                
                            }
                            
                        }
                        
                    });
                    
                }
                
            }
            
        }
        
    }

    @SubscribeEvent
    public void onWildPokeBeat (BeatWildPixelmonEvent event) throws IOException, ObjectMappingException {

        EntityPixelmon pixelmon = event.wpp.controlledPokemon.get(0).entity;
        PixelmonWrapper w = event.wpp.controlledPokemon.get(0);
        Player player = (Player) event.player;

        String task;
        if (w.canMegaEvolve() && w.isMega) {

            task = "Kill-Mega-Bosses";


        } else if (!w.canMegaEvolve() && pixelmon.getBossMode() != EnumBossMode.NotBoss) {

            task = "Kill-Normal-Bosses";

        } else {

            task = "Defeating-Wild-Pokemon";

        }

        switch (task) {

            case "Kill-Mega-Bosses":
            case "Kill-Normal-Bosses":

                if (GeneralGetters.isSkillEnabled(7)) {

                    if (GeneralGetters.isTaskEnabled(7, task)) {

                        Gladiator.pokelvl = event.wpp.controlledPokemon.get(0).pokemon.getLevel();

                        if (GeneralGetters.getSkillPerm(7).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(7))) {

                            owner = (EntityPlayerMP) player;
                            ExperienceHandler.didTask(7, task, player);

                        }

                    }

                }
                break;

            case "Defeating-Wild-Pokemon":

                if (GeneralGetters.isSkillEnabled(10)) {

                    if (GeneralGetters.isTaskEnabled(10, "Defeating-Wild-Pokemon")) {

                        if (GeneralGetters.getSkillPerm(10).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(10))) {

                            Conqueror.pokemon = event.wpp.controlledPokemon.get(0).pokemon;
                            Conqueror.fPlayer = event.player;
                            ExperienceHandler.didTask(10, "Defeating-Wild-Pokemon", player);


                        }

                    }

                }
                break;

        }
        
    }

    // I cannot believe I have to do this.

    private static boolean canHaveMega (Pokemon pokemon) {
        
        return pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Venusaur") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Charizard") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Blastoise") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Alakazam") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Gengar") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Kangaskhan") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Pinsir") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Gyarados") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Aerodactyl") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Mewtwo") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Ampharos") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Scizor") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Heracross") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Houndoom") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Tyranitar") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Blaziken") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Gardevoir") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Mawile") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Aggron") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Medicham") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Manectric") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Banette") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Absol") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Latias") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Latios") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Garchomp") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Lucario") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Abomasnow") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Beedrill") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Pidgeot") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Slowbro") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Steelix") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Sceptile") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Swampert") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Sableye") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Sharpedo") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Camerupt") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Altaria") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Glalie") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Salamence") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Metagross") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Rayquaza") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Lopunny") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Gallade") ||
                pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Audino") || pokemon.getBaseStats().pixelmonName.equalsIgnoreCase("Diancie");

    }
    
}
