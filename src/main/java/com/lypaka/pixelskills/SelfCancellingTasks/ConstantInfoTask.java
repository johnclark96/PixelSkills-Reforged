package com.lypaka.pixelskills.SelfCancellingTasks;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.FancyFeatures.FancyFeaturesGetters;
import com.lypaka.pixelskills.FancyFeatures.FancyFeaturesHandler;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.chat.ChatTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ConstantInfoTask implements Consumer<Task> {

    @Override
    public void accept (Task task) {

        List<Player> players = new ArrayList<>();
        players.addAll(Sponge.getServer().getOnlinePlayers());
        boolean running = false;

        if (players.size() == 0) {

            task.cancel();

        } else {

            for (Player player : players) {

                if (FancyFeaturesHandler.showingInfoMap.containsKey(player)) {

                    if (FancyFeaturesHandler.showingInfoMap.get(player)) {

                        running = true;
                        break;

                    }

                }

            }

            if (running) {

                Map<Player, String> map = FancyFeaturesHandler.constantInfoMap;
                for (Map.Entry<Player, String> entry : map.entrySet()) {

                    int conf = GeneralGetters.getConfigFromSkill(entry.getValue());
                    entry.getKey().sendMessage(ChatTypes.ACTION_BAR, FancyFeaturesGetters.getActionBarText(entry.getKey(), conf, "EXP"));

                }

            } else {

                task.cancel();

            }

        }

    }

}