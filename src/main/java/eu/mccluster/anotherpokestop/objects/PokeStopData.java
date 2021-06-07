package eu.mccluster.anotherpokestop.objects;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import net.minecraft.world.World;
import org.spongepowered.api.Sponge;

import java.io.File;
import java.util.UUID;

public class PokeStopData extends Config {

    @Order(1)
    private String pokeStopUniqueId;
    @Order(2)
    private RGBStorage color = new RGBStorage();
    @Order(3)
    private String world;
    @Order(4)
    private double posX;
    @Order(5)
    private double posY;
    @Order(6)
    private double posZ;
    @Order(7)
    private LoottableStorage loottable = new LoottableStorage();

    public PokeStopData() {

    }

    public PokeStopData(UUID pokeStopUniqueId, RGBStorage color, World world, double posX, double posY, double posZ, LoottableStorage loottable) {
        this.pokeStopUniqueId = pokeStopUniqueId.toString();
        this.color = color;
        this.world = world.getWorldInfo().getWorldName();
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.loottable = loottable;
    }

    public UUID getPokeStopUniqueId() { return UUID.fromString(this.pokeStopUniqueId); }

    public RGBStorage getColor() { return this.color; }

    public World getWorld() {
        return (World) Sponge.getServer().getWorld(this.world).orElseThrow(null);
    }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public double getPosZ() {
        return this.posZ;
    }

    public LoottableStorage getLoottable() { return this.loottable; }


    @Override
    public File getFile() {
        return null;
    }
}
