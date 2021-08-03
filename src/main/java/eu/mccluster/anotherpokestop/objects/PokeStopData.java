package eu.mccluster.anotherpokestop.objects;

import eu.mccluster.anotherpokestop.config.presets.PresetTrainer;
import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PokeStopData extends Config {

    @Order(1)
    private String pokeStopUniqueId;
    @Order(2)
    private int version;
    @Order(3)
    private RGBStorage color = new RGBStorage();
    @Order(4)
    private RGBStorage cooldownColor = new RGBStorage();
    @Order(5)
    private String world;
    @Order(6)
    private double posX;
    @Order(7)
    private double posY;
    @Order(8)
    private double posZ;
    @Order(9)
    private String loottable;
    @Order(10)
    private List<PresetTrainer> trainer = new ArrayList<>();

    public PokeStopData() {

    }

    public PokeStopData(UUID pokeStopUniqueId, int version, RGBStorage color, RGBStorage cooldownColor, String world, double posX, double posY, double posZ, String loottable, List<PresetTrainer> trainer) {
        this.pokeStopUniqueId = pokeStopUniqueId.toString();
        this.version = version;
        this.color = color;
        this.cooldownColor = cooldownColor;
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

    public RGBStorage getCooldownColor() { return this.cooldownColor; }

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


    @Override
    public File getFile() {
        return null;
    }
}
