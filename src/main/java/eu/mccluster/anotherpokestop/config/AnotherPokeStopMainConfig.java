package eu.mccluster.anotherpokestop.config;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import eu.mccluster.dependency.configmanager.api.annotations.Skip;

import java.io.File;

public class AnotherPokeStopMainConfig extends Config {

    @Skip
    File _configFile;

    public AnotherPokeStopMainConfig(File file) {
        _configFile = file;
    }

    @Order(1)
    public AnotherPokeStopConfig config = new AnotherPokeStopConfig();

    @Override
    public File getFile() {
        return _configFile;
    }

}
