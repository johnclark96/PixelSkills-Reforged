package com.lypaka.pixelskills.Utils;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;

import java.util.Random;

public class IVModifier {

    public static void modifyIVs (Pokemon pokemon, String iv, double modifier, String mode) {

        if (iv.equalsIgnoreCase("random")) {

            Random random = new Random();
            int stat = random.nextInt(5);
            String[] stats = new String[]{"atk", "def", "satk", "sdef", "spd", "hp"};
            iv = stats[stat];

        }

        switch (iv) {

            case "atk":

                switch (mode) {

                    case "add":
                        pokemon.getStats().ivs.attack = Math.min((int) Math.floor(pokemon.getStats().ivs.attack + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getStats().ivs.attack = Math.min((int) Math.floor(pokemon.getStats().ivs.attack * modifier), 31);
                        break;

                }

                break;

            case "def":

                switch (mode) {

                    case "add":
                        pokemon.getStats().ivs.defence = Math.min((int) Math.floor(pokemon.getStats().ivs.defence + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getStats().ivs.defence = Math.min((int) Math.floor(pokemon.getStats().ivs.defence * modifier), 31);
                        break;

                }

                break;

            case "satk":

                switch (mode) {

                    case "add":
                        pokemon.getStats().ivs.specialAttack = Math.min((int) Math.floor(pokemon.getStats().ivs.specialAttack + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getStats().ivs.specialAttack = Math.min((int) Math.floor(pokemon.getStats().ivs.specialAttack * modifier), 31);
                        break;

                }

                break;

            case "sdef":

                switch (mode) {

                    case "add":
                        pokemon.getStats().ivs.specialDefence = Math.min((int) Math.floor(pokemon.getStats().ivs.specialDefence + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getStats().ivs.specialDefence = Math.min((int) Math.floor(pokemon.getStats().ivs.specialDefence * modifier), 31);
                        break;

                }

                break;

            case "spd":

                switch (mode) {

                    case "add":
                        pokemon.getStats().ivs.speed = Math.min((int) Math.floor(pokemon.getStats().ivs.speed + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getStats().ivs.speed = Math.min((int) Math.floor(pokemon.getStats().ivs.speed * modifier), 31);
                        break;

                }

                break;

            case "hp":

                switch (mode) {

                    case "add":
                        pokemon.getStats().ivs.hp = Math.min((int) Math.floor(pokemon.getStats().ivs.hp + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getStats().ivs.hp = Math.min((int) Math.floor(pokemon.getStats().ivs.hp * modifier), 31);
                        break;

                }

                break;

        }

    }

    public static void modifyIVs (EntityPixelmon pokemon, String iv, double modifier, String mode) {

        if (iv.equalsIgnoreCase("random")) {

            Random random = new Random();
            int stat = random.nextInt(5);
            String[] stats = new String[]{"atk", "def", "satk", "sdef", "spd", "hp"};
            iv = stats[stat];

        }

        switch (iv) {

            case "atk":

                switch (mode) {

                    case "add":
                        pokemon.getPokemonData().getStats().ivs.attack = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.attack + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getPokemonData().getStats().ivs.attack = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.attack * modifier), 31);
                        break;

                }

                break;

            case "def":

                switch (mode) {

                    case "add":
                        pokemon.getPokemonData().getStats().ivs.defence = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.defence + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getPokemonData().getStats().ivs.defence = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.defence * modifier), 31);
                        break;

                }

                break;

            case "satk":

                switch (mode) {

                    case "add":
                        pokemon.getPokemonData().getStats().ivs.specialAttack = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.specialAttack + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getPokemonData().getStats().ivs.specialAttack = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.specialAttack * modifier), 31);
                        break;

                }

                break;

            case "sdef":

                switch (mode) {

                    case "add":
                        pokemon.getPokemonData().getStats().ivs.specialDefence = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.specialDefence + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getPokemonData().getStats().ivs.specialDefence = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.specialDefence * modifier), 31);
                        break;

                }

                break;

            case "spd":

                switch (mode) {

                    case "add":
                        pokemon.getPokemonData().getStats().ivs.speed = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.speed + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getPokemonData().getStats().ivs.speed = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.speed * modifier), 31);
                        break;

                }

                break;

            case "hp":

                switch (mode) {

                    case "add":
                        pokemon.getPokemonData().getStats().ivs.hp = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.hp + modifier), 31);
                        break;

                    case "multiply":
                        pokemon.getPokemonData().getStats().ivs.hp = Math.min((int) Math.floor(pokemon.getPokemonData().getStats().ivs.hp * modifier), 31);
                        break;

                }

                break;

        }

        pokemon.updateStats();

    }

}
