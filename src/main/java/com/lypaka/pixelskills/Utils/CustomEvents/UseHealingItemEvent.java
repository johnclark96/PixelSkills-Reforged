package com.lypaka.pixelskills.Utils.CustomEvents;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.Event;

public class UseHealingItemEvent extends Event {

    private final EntityPlayerMP player;
    private final EntityPixelmon pokemon;
    private float heal;

    public UseHealingItemEvent(EntityPlayerMP player, EntityPixelmon pokemon, float heal) {

        this.player = player;
        this.pokemon = pokemon;
        this.heal = heal;

    }

    public EntityPlayerMP getPlayer() {

        return this.player;

    }

    public EntityPixelmon getPokemon() {

        return this.pokemon;

    }

    public float getHeal() {

        return this.heal;

    }

    public void setHeal (float amount) {

        this.heal = amount;

    }

}
