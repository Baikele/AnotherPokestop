package eu.mccluster.anotherpokestop.config.lureModule;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class ParticleColor extends Config {

    @Order(1)
    public int red = 0;
    @Order(2)
    public int green = 0;
    @Order(3)
    public int blue = 255;

    @Override
    public File getFile() {
        return null;
    }
}
