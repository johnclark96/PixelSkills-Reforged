package com.lypaka.pixelskills.Utils;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

public class FriendshipModifier {

    public static void modifyFriendship (Pokemon pokemon, double modifier, String function) {

        int currentFriendship = pokemon.getFriendship();
        double newFriendship = 0;

        switch (function) {

            case "add":
                newFriendship = Math.min((int) Math.floor(currentFriendship + modifier), 255);
                break;

            case "multiply":
                newFriendship = Math.min((int) Math.floor(currentFriendship * modifier), 255);
                break;

        }

        pokemon.setFriendship((int) newFriendship);

    }

}
