package com.lypaka.pixelskills.Skills;

import com.pixelmonmod.pixelmon.api.events.moveskills.UseMoveSkillEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Arcanist {

    @SubscribeEvent
    public void onMoveSkill (UseMoveSkillEvent event) {

        System.out.println(event.moveSkill.id);
        System.out.println(event.pixelmon);
        System.out.println(event.handler);
        System.out.println(event.pixelmon.getName());
        System.out.println(event.moveSkill.name);

    }

}
