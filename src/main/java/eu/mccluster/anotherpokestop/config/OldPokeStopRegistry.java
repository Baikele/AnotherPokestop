package eu.mccluster.anotherpokestop.config;

import eu.mccluster.anotherpokestop.objects.OldPokeStopData;
import eu.mccluster.anotherpokestop.objects.PokeStopData;
import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import eu.mccluster.dependency.configmanager.api.annotations.Skip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OldPokeStopRegistry extends Config {
    @Skip
    File _registryFile;

    public OldPokeStopRegistry(File file) {
        _registryFile = file;
    }

    @Order(1)
    @Comment("Don't change this by hand on any case or else your Pokestops will be broke!")
    public List<OldPokeStopData> registryList = new ArrayList<>();

    @Override
    public File getFile() {
        return _registryFile;
    }
}
