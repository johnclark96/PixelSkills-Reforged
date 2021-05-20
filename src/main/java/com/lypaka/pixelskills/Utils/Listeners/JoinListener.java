package com.lypaka.pixelskills.Utils.Listeners;

import com.google.common.reflect.TypeToken;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.EXPGetters;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.PixelSkills;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import net.minecraft.world.World;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JoinListener {

    @Listener
    public void onJoin (ClientConnectionEvent.Join event, @Root Player player) throws ObjectMappingException {

        ConfigManager.loadPlayer(player.getUniqueId());

        if (ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "EXP-Messages").isVirtual()) {

            generateMessageToggleMap(player);

        }

        if (ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers").isVirtual()) {

            String[] skills = new String[]{"Archaeologist", "Artificer", "Barterer", "Botanist", "Breeder", "Caregiver", "Collector", "Conqueror", "Darwinist", "Fisherman", "Gladiator", "Harvester", "Looter", "Photographer", "Teacher", "Trader"};
            for (String skill : skills) {

                generatePerkTriggers(player, skill);

            }

        }

        if (ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Constantly-Display-Skill-Info").isVirtual()) {

            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Constantly-Display-Skill-Info").setValue(false);

        }

        if (ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account").isVirtual()) {

            generateAccountSettings(player);

        }

        checkForMissingSkills(player);

        if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner").isVirtual()) {

            double exp = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner", "EXP").getDouble();
            int level = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner", "Level").getInt();
            double expToNext = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner", "EXP-To-Next-Level").getDouble();
            double perkChance = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner", "Perk-Chance").getDouble();
            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Photographer", "EXP").setValue(exp);
            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Photographer", "Level").setValue(level);
            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Photographer", "EXP-To-Next-Level").setValue(expToNext);
            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Photographer", "Perk-Chance").setValue(perkChance);
            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner", "Next-Reward-Level").isVirtual()) {

                int rl = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner", "Next-Reward-Level").getInt();
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Photographer", "Next-Reward-Level").setValue(rl);

            }
            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner", "Next-Perk-Level").isVirtual()) {

                int rl = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner", "Next-Perk-Level").getInt();
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Photographer", "Next-Perk-Level").setValue(rl);

            }
            if (!ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner", "Reward-Chance").isVirtual()) {

                double rc = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner", "Next-Perk-Level").getInt();
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Photographer", "Next-Perk-Level").setValue(rc);

            }

            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "EXP-Messages", "Scanner").setValue(null);
            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", "Scanner").setValue(null);

            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", "Scanner").setValue(null);

        }

        ConfigManager.savePlayer(player.getUniqueId());

    }

    public static void generateCatchrateNodes (Player player) throws IOException {

        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Catchrate-Modifiers").setValue(Stream.of(EnumSpecies.values()).collect(Collectors.toMap(a -> a.getPokemonName(), a -> PokemonSpec.from(a.name).create((World) Sponge.getServer().getWorlds().iterator().next()).getBaseStats().catchRate)));
        ConfigManager.savePlayer(player.getUniqueId());

    }

    public static void generateMessageToggleMap (Player player) {

        Map<String, Boolean> toggleMap = new HashMap<>();
        String[] skills = new String[]{"Archaeologist", "Artificer", "Barterer", "Botanist", "Breeder", "Caregiver", "Collector", "Conqueror", "Darwinist", "Fisherman", "Gladiator", "Harvester", "Looter", "Photographer", "Teacher", "Trader"};
        for (String skill : skills) {

            toggleMap.put(skill, true);

        }

        ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "EXP-Messages").setValue(toggleMap);
        ConfigManager.savePlayer(player.getUniqueId());

    }

    public static void generatePerkTriggers (Player player, String skill) {

        switch (skill) {

            case "Archaeologist":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Mining-Fossils", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Mining-Fossils", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Reviving-Fossils", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Reviving-Fossils", "Triggered").setValue(false);
                break;

            case "Artificer":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Brewing-Potions", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Brewing-Potions", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Crafting-Items", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Crafting-Items", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Using-Anvil", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Using-Anvil", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Using-Pixelmon-Anvil", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Using-Pixelmon-Anvil", "Triggered").setValue(false);
                break;

            case "Barterer":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Buying-From-Shopkeepers", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Buying-From-Shopkeepers", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Selling-To-Shopkeepers", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Selling-To-Shopkeepers", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Buying-From-Vending-Machines", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Buying-From-Vending-Machines", "Triggered").setValue(false);
                break;

            case "Botanist":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Picking-Apricorns", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Picking-Apricorns", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Picking-Berries", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Picking-Berries", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Planting-Apricorns", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Planting-Apricorns", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Planting-Berries", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Planting-Berries", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Watering-Apricorns", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Watering-Apricorns", "Triggered").setValue(false);
                break;

            case "Breeder":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Hatching-Eggs", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Hatching-Eggs", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Making-Eggs", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Making-Eggs", "Triggered").setValue(false);
                break;

            case "Caregiver":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Using-Healing-Items", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Using-Healing-Items", "Triggered").setValue(false);
                break;

            case "Collector":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Catching-Legendary-Pokemon", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Catching-Legendary-Pokemon", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Catching-Normal-Pokemon", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Catching-Normal-Pokemon", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Catching-Shiny-Pokemon", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Catching-Shiny-Pokemon", "Triggered").setValue(false);
                break;

            case "Conqueror":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Kill-Mega-Bosses", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Kill-Mega-Bosses", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Kill-Normal-Bosses", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Kill-Normal-Bosses", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Kill-Totem-Pokemon", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Kill-Totem-Pokemon", "Triggered").setValue(false);
                break;

            case "Darwinist":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Evolving-Pokemon", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Evolving-Pokemon", "Triggered").setValue(false);
                break;

            case "Fisherman":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Catching-Fish", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Catching-Fish", "Triggered").setValue(false);
                break;

            case "Gladiator":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Defeating-NPC-Trainers", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Defeating-NPC-Trainers", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Defeating-Wild-Pokemon", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Defeating-Wild-Pokemon", "Triggered").setValue(false);
                break;

            case "Harvester":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Chopping-Wood", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Chopping-Wood", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Mining-Blocks", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Mining-Blocks", "Triggered").setValue(false);
                break;

            case "Looter":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Opening-Poke-Loot-Chests", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Opening-Poke-Loot-Chests", "Triggered").setValue(false);
                break;

            case "Photographer":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Photographing-Pokemon", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Photographing-Pokemon", "Triggered").setValue(false);
                break;

            case "Teacher":
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Learning-Moves", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Learning-Moves", "Triggered").setValue(false);
                break;

            case "Trader":

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Trading-With-NPCs", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Trading-With-NPCs", "Triggered").setValue(false);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Trading-With-Players", "Last-Triggered-Level").setValue(0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Trading-With-Players", "Triggered").setValue(false);
                break;

        }

    }

    public static void generateAccountSettings (Player player) throws ObjectMappingException {

        int level = 1;
        double perkChance = 0.0;
        String[] skills = new String[]{"Archaeologist", "Artificer", "Barterer", "Botanist", "Breeder", "Caregiver", "Collector", "Conqueror", "Darwinist", "Fisherman", "Gladiator", "Harvester", "Looter", "Photographer", "Teacher", "Trader"};
        if (!AccountsHandler.hasAccountGenerated(player)) {

            for (String skill : skills) {

                int conf = GeneralGetters.getConfigFromSkill(skill);

                if (EXPGetters.getEXPMode(conf).equalsIgnoreCase("calculated")) {

                    ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP-To-Next-Level").setValue(EXPGetters.getBaseEXP(conf));

                } else {

                    ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP-To-Next-Level").setValue(EXPGetters.getManualEXPAmount(conf, 2));

                }

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(0.0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Level").setValue(level);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Perk-Chance").setValue(perkChance);

            }

            ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Account-Generated").setValue(true);

        }

    }

    public static void checkForMissingSkills (Player player) throws ObjectMappingException {

        String[] skills = new String[]{"Archaeologist", "Artificer", "Barterer", "Botanist", "Breeder", "Caregiver", "Collector", "Conqueror", "Darwinist", "Fisherman", "Gladiator", "Harvester", "Looter", "Photographer", "Teacher", "Trader"};
        int level = 1;
        double perkChance = 0.0;
        for (String skill : skills) {

            if (ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill).isVirtual()) {

                int conf = GeneralGetters.getConfigFromSkill(skill);

                if (EXPGetters.getEXPMode(conf).equalsIgnoreCase("calculated")) {

                    ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP-To-Next-Level").setValue(EXPGetters.getBaseEXP(conf));

                } else {

                    ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP-To-Next-Level").setValue(EXPGetters.getManualEXPAmount(conf, 2));

                }

                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "EXP").setValue(0.0);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Level").setValue(level);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account", skill, "Perk-Chance").setValue(perkChance);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Account-Generated").setValue(true);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "Perk-Triggers", skill, "Learning-Moves", "Last-Triggered-Level").setValue(0);
                generatePerkTriggers(player, skill);
                Map<String, Boolean> toggleMap = ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "EXP-Messages").getValue(new TypeToken<Map<String, Boolean>>() {});
                toggleMap.put(skill, true);
                ConfigManager.getPlayerConfigNode(player.getUniqueId(), "Account-Settings", "EXP-Messages").setValue(toggleMap);

            }

        }

    }

}
