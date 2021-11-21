package eu.pixelunited.anotherpokestop.config.presets;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class PresetConfig {


    @Comment("Defines the Range new created Pokestops change to a cube")
    public int cubeRange = 20;

    @Comment("Size of the Pokestop. The default value is 2.0")
    public float pokestopSize = 2.0F;

    @Comment("Loottable for this preset")
    public String loottable = "DefaultLootConfig";

    @Comment("Trainer config for this preset")
    public List<PresetTrainer> trainerList = new ArrayList<>();

    @Comment("Min 0 | Max 255")
    public int red = 63;

    @Comment("Min 0 | Max 255")
    public int green = 0;

    @Comment("Min 0 | Max 255")
    public int blue = 255;

    public PresetConfig() {
        trainerList.add(new PresetTrainer());
    }

}
