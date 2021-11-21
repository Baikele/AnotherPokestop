package eu.pixelunited.anotherpokestop.objects;


import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.File;

@ConfigSerializable
public class LoottableStorage {

    private String loottable;

    public LoottableStorage(String loottable) {
        this.loottable = loottable;
    }

    public LoottableStorage() {

    }

    public String getLoottable() { return this.loottable; }

}
