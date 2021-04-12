package com.lypaka.pixelskills.Skills;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.EXPGetters;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.PixelSkills;
import com.lypaka.pixelskills.Utils.ExperienceHandler;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;
import java.util.Map;

public class Collector {

    private static EntityPixelmon pokeToModify;
    private static String task;

    @SubscribeEvent
    public void onCatchAttempt (CaptureEvent.StartCapture event) {

        if (Scanner.applyModifiers()) {

            Player player = (Player) event.player;
            String pokemon = event.getPokemon().getPokemonName();

            try {

                Map<String, Integer> map = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Catchrate-Modifiers").getValue(new TypeToken<Map<String, Integer>>() {});
                int newRate = map.get(pokemon);
                event.setCatchRate(newRate);

            } catch (ObjectMappingException e) {

                e.printStackTrace();

            }

        }

    }

    @SubscribeEvent
    public void onCatch (CaptureEvent.SuccessfulCapture event) throws IOException, ObjectMappingException {

        if (GeneralGetters.isSkillEnabled(6)) {

            if (GeneralGetters.isTaskEnabled(6, getTaskFromPokemon(event.getPokemon()))) {

                Player player = (Player) event.player;

                if (GeneralGetters.getSkillPerm(6).equals("none") || player.hasPermission(GeneralGetters.getSkillPerm(6))) {

                    setPokeToModify(event.getPokemon());
                    setTask(getTaskFromPokemon(event.getPokemon()));
                    if (!EXPGetters.getCollectorBlackList(getTaskFromPokemon(event.getPokemon())).contains(event.getPokemon().getPokemonName())) {

                        ExperienceHandler.didTask(6, getTaskFromPokemon(event.getPokemon()), player);

                    }

                }

            }

        }

    }

    private static String getTaskFromPokemon (EntityPixelmon pokemon) {
        if (pokemon.getPokemonData().isShiny() && !isLegendary(pokemon.getPokemonName())) {
            return "Catching-Shiny-Pokemon";
        } else if (!pokemon.getPokemonData().isShiny() && !isLegendary(pokemon.getPokemonName())) {
            return "Catching-Normal-Pokemon";
        } else if (!pokemon.getPokemonData().isShiny() && isLegendary(pokemon.getPokemonName())) {
            return "Catching-Legendary-Pokemon";
        } else if (pokemon.getPokemonData().isShiny() && isLegendary(pokemon.getPokemonName())) {
            int rng = PixelSkills.getRandom().nextInt(2) + 1;
            if (rng == 1) {
                return "Catching-Shiny-Pokemon";
            } else {
                return "Catching-Legendary-Pokemon";
            }
        }
        return "null";
    }

    public static boolean isLegendary (String pokemon) {
        return pokemon.contains("Mewtwo") || pokemon.contains("Mew") || pokemon.contains("HoOh") || pokemon.contains("Ho-Oh") || pokemon.contains("Lugia") ||
                pokemon.contains("Entei") || pokemon.contains("Suicune") || pokemon.contains("Raikou") || pokemon.contains("Celebi") || pokemon.contains("Latios") ||
                pokemon.contains("Latias") || pokemon.contains("Groudon") || pokemon.contains("Kyogre") || pokemon.contains("Rayquaza") ||
                pokemon.contains("Jirachi") || pokemon.contains("Deoxys") || pokemon.contains("Heatran") || pokemon.contains("Moltres") || pokemon.contains("Zapdos") ||
                pokemon.contains("Articuno") || pokemon.contains("Regirock") || pokemon.contains("Regice") || pokemon.contains("Registeel") || pokemon.contains("Azelf") ||
                pokemon.contains("Uxie") || pokemon.contains("Mesprit") || pokemon.contains("Dialga") || pokemon.contains("Palkia") || pokemon.contains("Giratina") ||
                pokemon.contains("Cresselia") || pokemon.contains("Regigigas") || pokemon.contains("Darkrai") || pokemon.contains("Shaymin") || pokemon.contains("Arceus") ||
                pokemon.contains("Manaphy") || pokemon.contains("Victini") || pokemon.contains("Keldeo") || pokemon.contains("Terrakion") || pokemon.contains("Virizion") ||
                pokemon.contains("Cobalion") || pokemon.contains("Thundurus") || pokemon.contains("Tornadus") || pokemon.contains("Landorus") || pokemon.contains("Reshiram") ||
                pokemon.contains("Kyurem") || pokemon.contains("Zekrom") || pokemon.contains("Xerneas") || pokemon.contains("Yveltal") || pokemon.contains("Zygarde") ||
                pokemon.contains("TypeNull") || pokemon.contains("Type:Null") || pokemon.contains("Type Null") || pokemon.contains("Silvally") || pokemon.contains("TapuKoko") || pokemon.contains("Tapu Koko") ||
                pokemon.contains("TapuFini") || pokemon.contains("Tapu Fini") || pokemon.contains("TapuLele") || pokemon.contains("Tapu Lele") || pokemon.contains("TapuBulu") ||
                pokemon.contains("Tapu Bulu") || pokemon.contains("Cosmog") || pokemon.contains("Cosmoem") || pokemon.contains("Solgaleo") || pokemon.contains("Lunala") ||
                pokemon.contains("Necrozma") || pokemon.contains("Meloetta") || pokemon.contains("Genesect") || pokemon.contains("Diancie") || pokemon.contains("Hoopa") ||
                pokemon.contains("Volcanion") || pokemon.contains("Magearna") || pokemon.contains("Marshadow") || pokemon.contains("Zeraora") || pokemon.contains("Zacian") ||
                pokemon.contains("Zamazenta") || pokemon.contains("Eternatus") || pokemon.contains("Meltan") || pokemon.contains("Melmetal") || pokemon.contains("Zarude") || pokemon.contains("Calyrex") ||
                pokemon.contains("Glastrier") || pokemon.contains("Spectrier") || pokemon.contains("Regieleki") || pokemon.contains("Regidrago");
    }

    private static void setPokeToModify (EntityPixelmon pokemon) {
        pokeToModify = pokemon;
    }

    public static EntityPixelmon getPokeToModify() {
        return pokeToModify;
    }

    private static void setTask (String action) {
        task = action;
    }

    public static String getTask() {
        return task;
    }

}
