package com.lypaka.pixelskills;

import com.google.inject.Inject;
import com.lypaka.pixelskills.Commands.AdminCommand;
import com.lypaka.pixelskills.Commands.PlayerCommands;
import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.GUIs.SkillInfoPage;
import com.lypaka.pixelskills.Skills.*;
import com.lypaka.pixelskills.Utils.Listeners.CandyUseListener;
import com.lypaka.pixelskills.Utils.Listeners.JoinListener;
import com.lypaka.pixelskills.Utils.Listeners.PokemonEXPGainListener;
import com.lypaka.pixelskills.Utils.Listeners.UseItemEventListener;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;

@Plugin(
        id = "pixelskills",
        name = "PixelSkills",
        version = "2.0.0-Reforged",
        dependencies = {@Dependency(id = "pixelmon")}
)
public class PixelSkills {

    @Inject
    @ConfigDir(sharedRoot = false)
    public Path configDir;

    @Inject
    private PluginContainer container;

    @Inject
    public Logger logger;

    public static PixelSkills INSTANCE;
    private static final Random random = new Random();
    public static boolean isPixelBoostersLoaded = false;

    public static String[] skills = new String[]{"Archaeologist", "Artificer", "Barterer", "Botanist", "Breeder", "Caregiver", "Collector", "Conqueror", "Darwinist", "Fisherman", "Gladiator", "Harvester", "Looter", "Photographer", "Teacher", "Trader"};


    @Listener
    public void onPreInit (GamePreInitializationEvent event) throws IOException {

        logger.info("PixelSkills is starting....!");
        INSTANCE = this;
        container = Sponge.getPluginManager().getPlugin("pixelskills").get();
        ConfigManager.setup(configDir);
        registerSkillEvents();
        registerUtilEvents();

        // Loads the plugin's commands
        registerCommands();

        if (Sponge.getPluginManager().getPlugin("pixelboosters").isPresent()) {

            isPixelBoostersLoaded = true;
            logger.info("Detected PixelBoosters, integrating...");

        }
    }



    @Listener
    public void onReload (GameReloadEvent event) {
        ConfigManager.load();
    }


    private void registerCommands() {

        CommandSpec main = CommandSpec.builder()
                .child(AdminCommand.getAdminCommands(), "admin")
                .child(PlayerCommands.getInfoSpec(), "info")
                .child(PlayerCommands.getMsgToggle(), "toggle")
                .child(PlayerCommands.getCheckLevel(), "lvl")
                .executor((sender, context) -> {

                    // Let's do an experiment
                    Player player = (Player) sender;
                    try {
                        SkillInfoPage.openSkillInfoPage(player);
                    } catch (ObjectMappingException e) {
                        e.printStackTrace();
                    }

                    return CommandResult.success();

                })
                .build();

        Sponge.getCommandManager().register(this, main, "pixelskills", "pskills", "skills");

        logger.info("Successfully loaded PixelSkills commands!");

    }


    private void registerSkillEvents() {

        Pixelmon.EVENT_BUS.register(new Archaeologist());
        Pixelmon.EVENT_BUS.register(new Barterer());
        Pixelmon.EVENT_BUS.register(new Botanist());
        Pixelmon.EVENT_BUS.register(new Breeder());
        Sponge.getEventManager().registerListeners(this, new Caregiver());
        Pixelmon.EVENT_BUS.register(new Collector());
        Sponge.getEventManager().registerListeners(this, new Artificer());
        MinecraftForge.EVENT_BUS.register(new Artificer());
        Pixelmon.EVENT_BUS.register(new Artificer());
        Pixelmon.EVENT_BUS.register(new Darwinist());
        Pixelmon.EVENT_BUS.register(new Fisherman());
        Pixelmon.EVENT_BUS.register(new Gladiator());
        Pixelmon.EVENT_BUS.register(new Looter());
        Pixelmon.EVENT_BUS.register(new Photographer());
        Pixelmon.EVENT_BUS.register(new Teacher());
        Sponge.getEventManager().registerListeners(this, new CandyUseListener());
        Sponge.getEventManager().registerListeners(this, new Harvester());
        logger.info("PixelSkills finished loading the skills!");

    }

    private void registerUtilEvents() {

        Sponge.getEventManager().registerListeners(this, new JoinListener());
        MinecraftForge.EVENT_BUS.register(new UseItemEventListener());
        Pixelmon.EVENT_BUS.register(new PokemonEXPGainListener());
        logger.info("Necessary utility classes loaded!");

    }


    public static Logger getLogger() {
        return INSTANCE.logger;
    }
    public static PluginContainer getContainer() {
        return INSTANCE.container;
    }
    public static Random getRandom() {
        return random;
    }

}
