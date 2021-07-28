package eu.mccluster.anotherpokestop.config.presets;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class PresetTrainer extends Config {

    @Order(1)
    public String trainer = "DefaultTrainerConfig";

    @Order(2)
    public int rarity = 1;

    @Override
    public File getFile() {
        return null;
    }
}
