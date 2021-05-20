package com.lypaka.pixelskills.Utils.CustomEvents;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.api.entity.living.player.Player;

@Cancelable
public class SkillLevelUpEvent extends Event {

    private Player player;
    private int level;
    private String skill;

    public SkillLevelUpEvent (Player player, int level, String skill) {

        this.player = player;
        this.level = level;
        this.skill = skill;

    }

    public Player getPlayer() {

        return this.player;

    }

    public int getLevel() {

        return this.level;

    }

    public String getSkill() {

        return this.skill;

    }

}
