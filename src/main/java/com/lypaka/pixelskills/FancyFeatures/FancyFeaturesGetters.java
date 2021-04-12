package com.lypaka.pixelskills.FancyFeatures;

import com.lypaka.pixelskills.Config.ConfigManager;
import com.lypaka.pixelskills.Config.Getters.GeneralGetters;
import com.lypaka.pixelskills.Utils.AccountsHandler;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.BossBarColor;
import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.text.title.Title;

public class FancyFeaturesGetters {

    public static boolean showActionBar (int conf, String type) {

        return ConfigManager.getConfigNode(conf, 6, "Action-Bar", type + "-Bar", "Enabled").getBoolean();

    }

    public static Text getActionBarText (Player player, int conf, String type) {

        String skill = GeneralGetters.getSkillFromConfigNumber(conf);
        int level = AccountsHandler.getLevel(skill, player);
        double exp = AccountsHandler.getCurrentEXP(skill, player);
        double reqEXP = AccountsHandler.getEXPToNextLvl(skill, player);

        String text = ConfigManager.getConfigNode(conf, 6, "Action-Bar", type + "-Bar", "Text").getString()
                .replace("%skill%", skill)
                .replace("%level%", String.valueOf(level))
                .replace("%current-exp%", String.valueOf(exp))
                .replace("%required-exp%", String.valueOf(reqEXP));

        return TextSerializers.FORMATTING_CODE.deserialize(text);

    }

    public static boolean showBossBar (int conf) {

        return ConfigManager.getConfigNode(conf, 6, "Boss-Bar", "Enabled").getBoolean();

    }

    public static BossBarColor getColor (int conf) {

        String color = ConfigManager.getConfigNode(conf, 6, "Boss-Bar", "Settings", "Color").getString().toUpperCase();

        switch (color) {

            case "BLUE":
                return BossBarColors.BLUE;

            case "GREEN":
                return BossBarColors.GREEN;

            case "PINK":
                return BossBarColors.PINK;

            case "PURPLE":
                return BossBarColors.PURPLE;

            case "RED":
                return BossBarColors.RED;

            case "WHITE":
                return BossBarColors.WHITE;

            case "YELLOW":
                return BossBarColors.YELLOW;

        }

        throw new IllegalArgumentException("Color specified not supported by Sponge!");

    }

    public static Text getBossBarText (int conf) {

        String skill = GeneralGetters.getSkillFromConfigNumber(conf);

        return TextSerializers.FORMATTING_CODE.deserialize(ConfigManager.getConfigNode(conf, 6, "Boss-Bar", "Settings", "Text").getString().replace("%skill%", skill));

    }

    public static int getTimer (int conf) {

        return ConfigManager.getConfigNode(conf, 6, "Boss-Bar", "Settings", "Visible-For").getInt();

    }

    public static boolean doEffects (int conf, String type) {

        return ConfigManager.getConfigNode(conf, 6, "Effects", type + "-Effects", "Enabled").getBoolean();

    }

    public static ParticleEffect getEffects (int conf, String type) {

        return ParticleEffect.builder()
                .type(getParticle(conf, type))
                .quantity(getEffAmount(conf, type))
                .build();

    }

    public static int getEffAmount (int conf, String type) {

        return ConfigManager.getConfigNode(conf, 6, "Effects", type + "-Effects", "Effect-Amount").getInt();

    }

    public static ParticleType getParticle (int conf, String type) {

        String particle = ConfigManager.getConfigNode(conf, 6, "Effects", type + "-Effects", "Effect-Type").getString().toUpperCase();

        return Sponge.getRegistry().getType(ParticleType.class, particle).get();

    }

    public static boolean playSounds (int conf, String type) {

        return ConfigManager.getConfigNode(conf, 6, "Sounds", type + "-Sounds", "Enabled").getBoolean();

    }

    public static SoundType getSound (int conf, String type) {

        String sound = ConfigManager.getConfigNode(conf, 6, "Sounds", type + "-Sounds", "Sound-Type").getString().toUpperCase();

        return Sponge.getRegistry().getType(SoundType.class, sound).get();

    }

    public static int getVolume (int conf, String type) {

        return ConfigManager.getConfigNode(conf, 6, "Sounds", type + "-Sounds", "Sound-Volume").getInt();

    }

    public static boolean sendTitleMessages (int conf, String type) {

        return ConfigManager.getConfigNode(conf, 6, "Title-Messages", type + "-Titles", "Enabled").getBoolean();

    }

    public static Text getTitleText (Player player, int conf, String type) {

        String skill = GeneralGetters.getSkillFromConfigNumber(conf);
        int lvl = AccountsHandler.getLevel(skill, player);
        double exp = AccountsHandler.getCurrentEXP(skill, player);

        String message = ConfigManager.getConfigNode(conf, 6, "Title-Messages", type + "-Titles", "Title-Text").getString()
                .replace("%exp%", String.valueOf(exp))
                .replace("%level%", String.valueOf(lvl))
                .replace("%skill%", skill);

        return TextSerializers.FORMATTING_CODE.deserialize(message);

    }

    public static Title getTitle (Player player, int conf, String type) {

        return Title.builder()
                .title(getTitleText(player, conf, type))
                .build();

    }

}
