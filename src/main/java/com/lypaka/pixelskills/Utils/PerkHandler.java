package com.lypaka.pixelskills.Utils;

import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Config.Getters.PerkGetters;
import com.lypaka.pixelskills.PerkHandlers.*;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;
import java.util.stream.IntStream;

public class PerkHandler {

    public static int pseudoLevelFirst = 0;
    public static int pseudoLevelNext = 0;

    public static void runPerkCode (int folder, String task, Player player) throws IOException, ObjectMappingException {

        switch (folder) {

            case 0:

                if (ArchaeologistPerks.doesPerkTrigger(folder, player, task)) {

                    ArchaeologistPerks.runPerkCode(folder, task, player);

                }

                break;

            case 1:

                if (ArtificerPerks.doesPerkTrigger(folder, player, task)) {

                    ArtificerPerks.runPerkCode(folder, task, player);

                }

                break;

            case 2:

                if (BartererPerks.doesPerkTrigger(folder, player, task)) {

                    BartererPerks.runPerkCode(folder, task, player);

                }

                break;

            case 3:

                if (BotanistPerks.doesPerkTrigger(folder, player, task)) {

                    BotanistPerks.runPerkCode(folder, task, player);

                }

                break;

            case 4:

                if (BreederPerks.doesPerkTrigger(folder, player, task)) {

                    BreederPerks.runPerkCode(folder, task, player);

                }

                break;

            case 5:

                if (CaregiverPerks.doesPerkTrigger(folder, player, task)) {

                    CaregiverPerks.runPerkCode(folder, task, player);

                }

                break;

            case 6:

                if (CollectorPerks.doesPerkTrigger(folder, player, task)) {

                    CollectorPerks.runPerkCode(folder, task, player);

                }

                break;

            case 7:

                if (ConquerorPerks.doesPerkTrigger(folder, player, task)) {

                    ConquerorPerks.runPerkCode(folder, task, player);

                }

                break;

            case 8:

                if (DarwinistPerks.doesPerkTrigger(folder, player, task)) {

                    DarwinistPerks.runPerkCode(folder, task, player);

                }

                break;

            case 9:

                if (FishermanPerks.doesPerkTrigger(folder, player, task)) {

                    FishermanPerks.runPerkCode(folder, task, player);

                }

                break;

            case 10:

                if (GladiatorPerks.doesPerkTrigger(folder, player, task)) {

                    GladiatorPerks.runPerkCode(folder, task, player);

                }

                break;

            case 11:

                if (HarvesterPerks.doesPerkTrigger(folder, player, task)) {

                    HarvesterPerks.runPerkCode(folder, task, player);

                }

                break;

            case 12:

                if (LooterPerks.doesPerkTrigger(folder, player, task)) {

                    LooterPerks.runPerkCode(folder, task, player);

                }

                break;

            case 13:

                if (PhotographerPerks.doesPerkTrigger(folder, player, task)) {

                    PhotographerPerks.runPerkCode(folder, task, player);

                }

                break;

            case 14:

                if (TeacherPerks.doesPerkTrigger(folder, player, task)) {

                    TeacherPerks.runPerkCode(folder, task, player);

                }

                break;

            case 15:

                if (TraderPerks.doesPerkTrigger(folder, player, task)) {

                    TraderPerks.runPerkCode(folder, task, player);

                }

                break;

        }

    }

    public static void runPerkCode (int folder, String task, Player player, int level) throws IOException, ObjectMappingException {

        switch (folder) {

            case 0:

                if (ArchaeologistPerks.doesPerkTrigger(folder, player, level, task)) {

                    ArchaeologistPerks.runPerkCode(folder, task, player);

                }

                break;

            case 1:

                if (ArtificerPerks.doesPerkTrigger(folder, player, level, task)) {

                    ArtificerPerks.runPerkCode(folder, task, player);

                }

                break;

            case 2:

                if (BartererPerks.doesPerkTrigger(folder, player, level, task)) {

                    BartererPerks.runPerkCode(folder, task, player);

                }

                break;

            case 3:

                if (BotanistPerks.doesPerkTrigger(folder, player, level, task)) {

                    BotanistPerks.runPerkCode(folder, task, player);

                }

                break;

            case 4:

                if (BreederPerks.doesPerkTrigger(folder, player, level, task)) {

                    BreederPerks.runPerkCode(folder, task, player);

                }

                break;

            case 5:

                if (CaregiverPerks.doesPerkTrigger(folder, player, level, task)) {

                    CaregiverPerks.runPerkCode(folder, task, player);

                }

                break;

            case 6:

                if (CollectorPerks.doesPerkTrigger(folder, player, level, task)) {

                    CollectorPerks.runPerkCode(folder, task, player);

                }

                break;

            case 7:

                if (ConquerorPerks.doesPerkTrigger(folder, player, level, task)) {

                    ConquerorPerks.runPerkCode(folder, task, player);

                }

                break;

            case 8:

                if (DarwinistPerks.doesPerkTrigger(folder, player, level, task)) {

                    DarwinistPerks.runPerkCode(folder, task, player);

                }

                break;

            case 9:

                if (FishermanPerks.doesPerkTrigger(folder, player, level, task)) {

                    FishermanPerks.runPerkCode(folder, task, player);

                }

                break;

            case 10:

                if (GladiatorPerks.doesPerkTrigger(folder, player, level, task)) {

                    GladiatorPerks.runPerkCode(folder, task, player);

                }

                break;

            case 11:

                if (HarvesterPerks.doesPerkTrigger(folder, player, level, task)) {

                    HarvesterPerks.runPerkCode(folder, task, player);

                }

                break;

            case 12:

                if (LooterPerks.doesPerkTrigger(folder, player, level, task)) {

                    LooterPerks.runPerkCode(folder, task, player);

                }

                break;

            case 14:

                if (TeacherPerks.doesPerkTrigger(folder, player, level, task)) {

                    TeacherPerks.runPerkCode(folder, task, player);

                }

                break;

            case 15:

                if (TraderPerks.doesPerkTrigger(folder, player, level, task)) {

                    TraderPerks.runPerkCode(folder, task, player);

                }

                break;

        }

    }

    public static void checkForMissedPerks (int folder, String task, Player player) throws IOException, ObjectMappingException {

        int lvl = AccountsHandler.getLevel(GeneralGetters.getSkillFromConfigNumber(folder), player);
        if (lvl == 1) return;
        int[] levels = IntStream.range(ExperienceHandler.startingLvl, ExperienceHandler.endingLvl + 1).toArray();
        for (int level : levels) {

            if (PerkGetters.getActivationLevel(folder) == level) {

                runPerkCode(folder, task, player, level);
                AccountsHandler.setNextPerkLevelManual(folder, player, level);

            } else if (level == getPseudoFirstSkillLevel(folder)) {

                runPerkCode(folder, task, player, level);
                AccountsHandler.setNextPerkLevelManual(folder, player, level);

            } else if (level == getPseudoLevelNext(folder)) {

                runPerkCode(folder, task, player, level);
                AccountsHandler.setNextPerkLevelManual(folder, player, level);

            }

        }

    }

    private static int getPseudoFirstSkillLevel (int folder) {

        int[] levels = IntStream.range(ExperienceHandler.startingLvl, ExperienceHandler.endingLvl + 1).toArray();
        int activationLevel = PerkGetters.getActivationLevel(folder);
        int interval = PerkGetters.getLevelInterval(folder);

        for (int level : levels) {

            if (level == activationLevel) {

                pseudoLevelFirst = level + interval;
                break;

            }

        }

        return pseudoLevelFirst;

    }

    private static int getPseudoLevelNext (int folder) {

        int[] levels = IntStream.range(ExperienceHandler.startingLvl, ExperienceHandler.endingLvl + 1).toArray();
        int interval = PerkGetters.getLevelInterval(folder);

        for (int level : levels) {

            if (level == pseudoLevelFirst) {

                pseudoLevelNext = level + interval;
                break;

            }

        }

        return pseudoLevelNext;

    }

}
