package eu.pixelunited.anotherpokestop.objects;

import eu.pixelunited.anotherpokestop.config.presets.PresetTrainer;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ConfigSerializable
public class PokeStopData {

    private String pokeStopUniqueId;

    private int version = 2;

    private RGBStorage color = new RGBStorage();

    private String world = "world";

    private double posX = 1;

    private double posY = 1;

    private double posZ = 1;

    private String loottable = "default";

    private List<PresetTrainer> trainer = new ArrayList<>();

    public PokeStopData() {

    }

    public PokeStopData(UUID pokeStopUniqueId, int version, RGBStorage color, String world, double posX, double posY, double posZ, String loottable, List<PresetTrainer> trainer) {
        this.pokeStopUniqueId = pokeStopUniqueId.toString();
        this.version = version;
        this.color = color;
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.loottable = loottable;
        this.trainer = trainer;
    }

    public UUID getPokeStopUniqueId() { return UUID.fromString(this.pokeStopUniqueId); }

    public Integer getVersion() { return this.version; }

    public RGBStorage getColor() { return this.color; }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public double getPosZ() {
        return this.posZ;
    }

    public String getWorld() { return this.world; }

    public String getLoottable() { return this.loottable; }

    public List<PresetTrainer> getTrainer() { return this.trainer; }

}
