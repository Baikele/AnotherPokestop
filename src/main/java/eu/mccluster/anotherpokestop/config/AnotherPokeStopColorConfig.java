package eu.mccluster.anotherpokestop.config;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class AnotherPokeStopColorConfig extends Config {

    @Order(1)
    @Comment("Min 0 | Max 255")
    public int red = 63;

    @Order(2)
    @Comment("Min 0 | Max 255")
    public int green = 0;

    @Order(3)
    @Comment("Min 0 | Max 255")
    public int blue = 255;

    @Override
    public File getFile() {
        return null;
    }
}

