package eu.mccluster.anotherpokestop.objects;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class LoottableStorage extends Config {

    @Order(1)
    private String loottable;

    public LoottableStorage(String loottable) {
        this.loottable = loottable;
    }

    public LoottableStorage() {

    }

    public String getLoottable() { return this.loottable; }

    @Override
    public File getFile() {
        return null;
    }
}
