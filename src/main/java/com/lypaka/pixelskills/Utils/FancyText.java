package com.lypaka.pixelskills.Utils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class FancyText {

    public static Text getFancyText (String value) {

        return TextSerializers.FORMATTING_CODE.deserialize(value);

    }

}
