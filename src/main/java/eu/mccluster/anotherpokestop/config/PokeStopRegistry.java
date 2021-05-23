package eu.mccluster.anotherpokestop.config;

import eu.mccluster.anotherpokestop.objects.PokeStopData;
import eu.mccluster.dependency.configmanager.api.Config;
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
    public List<PokeStopData> registryList = new ArrayList<>();

    @Override
    public File getFile() {
        return _registryFile;
    }


}
