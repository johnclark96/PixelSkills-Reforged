package com.lypaka.pixelskills.FancyFeatures;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.PixelSkills;
import com.lypaka.pixelskills.SelfCancellingTasks.ConstantInfoTask;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.chat.ChatTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FancyFeaturesHandler {

    private static Task task;
    public static HashMap<UUID, ServerBossBar> bars = new HashMap<>();
    public static Map<Player, String> constantInfoMap = new HashMap<>();
    public static Map<Player, Boolean> showingInfoMap = new HashMap<>();

    public static ServerBossBar displayBar (Player player, String skill) {

        int conf = GeneralGetters.getConfigFromSkill(skill);
        double exp = AccountsHandler.getCurrentEXP(skill, player);
        float percent = (float) (exp / AccountsHandler.getEXPToNextLvl(skill, player));

        ServerBossBar bar = ServerBossBar.builder()
                .percent(Math.min(percent, 1))
                .overlay(BossBarOverlays.PROGRESS)
                .name(FancyFeaturesGetters.getBossBarText(conf))
                .color(FancyFeaturesGetters.getColor(conf))
                .build();

        if (bars.containsKey(player.getUniqueId())) {

            if (bars.get(player.getUniqueId()).isVisible()) {

                bars.get(player.getUniqueId()).removePlayer(player);
                bars.get(player.getUniqueId()).setVisible(false);
                bars.get(player.getUniqueId()).setPercent(Math.min(percent, 1));
                bars.get(player.getUniqueId()).setName(FancyFeaturesGetters.getBossBarText(conf));
                bars.get(player.getUniqueId()).setColor(FancyFeaturesGetters.getColor(conf));
                bars.get(player.getUniqueId()).addPlayer(player);
                bars.get(player.getUniqueId()).setVisible(true);

            }

        } else {

            bar.addPlayer(player);
            bars.put(player.getUniqueId(), bar);
            bars.get(player.getUniqueId()).setVisible(true);

            task = Task.builder()
                    .delayTicks(FancyFeaturesGetters.getTimer(conf))
                    .execute(t -> {

                        bars.get(player.getUniqueId()).removePlayer(player);
                        bars.get(player.getUniqueId()).setVisible(false);
                        bars.remove(player.getUniqueId());

                    })
                    .submit(PixelSkills.INSTANCE);

        }

        return bar;

    }

    public static void spawnParticles (Player player, int conf, String type) {

        player.spawnParticles(FancyFeaturesGetters.getEffects(conf, type), player.getPosition(), FancyFeaturesGetters.getEffAmount(conf, type));

    }

    public static void playSound (Player player, int conf, String type) {

        player.playSound(FancyFeaturesGetters.getSound(conf, type), player.getPosition(), FancyFeaturesGetters.getVolume(conf, type));

    }

    public static void sendTitle (Player player, int conf, String type) {

        player.sendTitle(FancyFeaturesGetters.getTitle(player, conf, type));

    }

    public static void sendActionBar (Player player, int conf, String type) {

        player.sendMessage(ChatTypes.ACTION_BAR, FancyFeaturesGetters.getActionBarText(player, conf, type));

        if (AccountsHandler.showConstantInfo(player)) {

            String skill = GeneralGetters.getSkillFromConfigNumber(conf);

            if (!constantInfoMap.containsKey(player)) {

                constantInfoMap.put(player, skill);
                showingInfoMap.put(player, true);
                Sponge.getScheduler().createTaskBuilder()
                        .intervalTicks(20)
                        .execute(new ConstantInfoTask())
                        .submit(PixelSkills.INSTANCE);

            } else {

                if (!constantInfoMap.get(player).equalsIgnoreCase(skill)) {

                    constantInfoMap.put(player, skill);

                }

            }

        } else {

            showingInfoMap.entrySet().removeIf(entry -> entry.getKey() == player);
            constantInfoMap.entrySet().removeIf(entry -> entry.getKey() == player);

        }

    }

}
