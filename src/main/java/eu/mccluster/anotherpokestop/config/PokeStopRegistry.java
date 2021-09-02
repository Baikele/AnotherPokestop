package eu.mccluster.anotherpokestop.config;

import eu.mccluster.anotherpokestop.objects.PokeStopData;
import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import eu.mccluster.dependency.configmanager.api.annotations.Skip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PokeStopRegistry extends Config {
    @Skip
    File _registryFile;

    public PokeStopRegistry(File file) {
        _registryFile = file;
    }

    @Order(1)
    @Comment("Don't change this by hand on any case or else your Pokestops will be broke!")
    public List<PokeStopData> registryList = new ArrayList<>();

    @Order(2)
    @Comment("Loot Multiplier")
    public double lootMultiplier = 1.0;

    @Override
    public File getFile() {
        return _registryFile;
    }

    public void setMultiplier(double multiplier) {
        lootMultiplier = multiplier;
    }
}
