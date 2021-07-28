package eu.mccluster.anotherpokestop.config.presets;

import eu.mccluster.anotherpokestop.objects.RGBStorage;
import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import eu.mccluster.dependency.configmanager.api.annotations.Skip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PresetConfig extends Config {

    @Skip
    File _configFile;

    @Order(1)
    @Comment("Defines the Range new created Pokestops change to a cube")
    public int cubeRange = 20;

    @Order(2)
    @Comment("Size of the Pokestop. The default value is 2.0")
    public float pokestopSize = 2.0F;

    @Order(3)
    @Comment("Loottable for this preset")
    public String loottable = "DefaultLootConfig";

    @Order(4)
    @Comment("Trainer config for this preset")
    public List<PresetTrainer> trainerList = new ArrayList<>();

    @Order(5)
    @Comment("Min 0 | Max 255")
    public int red = 63;

    @Order(6)
    @Comment("Min 0 | Max 255")
    public int green = 0;

    @Order(7)
    @Comment("Min 0 | Max 255")
    public int blue = 255;

    @Order(8)
    @Comment("Min 0 | Max 255")
    public int cooldownRed = 0;

    @Order(9)
    @Comment("Min 0 | Max 255")
    public int cooldownGreen = 0;

    @Order(10)
    @Comment("Min 0 | Max 255")
    public int cooldownBlue = 0;

    public PresetConfig(File file) {
        trainerList.add(new PresetTrainer());
        _configFile = file;
    }


    @Override
    public File getFile() {
        return _configFile;
    }
}
