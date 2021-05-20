package com.lypaka.pixelskills.Utils.CustomEvents;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.api.entity.living.player.Player;

@Cancelable
public class SkillExperienceEvent extends Event {

    private Player player;
    private double expGained;
    private String skill;

    public SkillExperienceEvent (Player player, double expGained, String skill) {

        this.player = player;
        this.expGained = expGained;
        this.skill = skill;

    }

    public Player getPlayer() {

        return this.player;

    }

    public double getExpGained() {

        return this.expGained;

    }

    public String getSkill() {

        return this.skill;

    }

}
