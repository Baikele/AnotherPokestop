package eu.mccluster.anotherpokestop.objects;


import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class RGBStorage extends Config {

    @Order(1)
    private int r;
    @Order(2)
    private int g;
    @Order(3)
    private int b;

    public RGBStorage(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public RGBStorage() {}

    public int getG() {
        return this.g;
    }

    public int getB() {
        return this.b;
    }
    public int getR() {
        return this.r;
    }

    @Override
    public File getFile() {
        return null;
    }
}


