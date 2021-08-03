package eu.mccluster.anotherpokestop.config.lureModule;

import eu.mccluster.anotherpokestop.config.loottables.LootTableConfig;
import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import eu.mccluster.dependency.configmanager.api.annotations.Skip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LureModuleConfig extends Config {

    @Skip
    File _lureFile;

    @Order(1)
    public String particleType = "heart";

    @Order(2)
    @Comment("Only use for redstone dust")
    public ParticleColor colors = new ParticleColor();

    @Order(2)
    public int activationCostWeak = 5000;

    @Order(3)
    public int activationCostStrong = 10000;

    @Order(4)
    public int spawnTicksWeak = 3;

    @Order(5)
    public int spawnTicksStrong = 6;

    @Order(6)
    @Comment("Times in second a spawnTick happens. spawnTick * spawnInterval = Overall time")
    public int spawnInterval = 10;

    @Order(7)
    @Comment("Chance that something spawns on a spawn tick")
    public int spawnChance = 40;

    @Order(8)
    public int spawnQuantity = 2;

    @Order(9)
    public List<LurePokeSettings> pokeList = new ArrayList<>();

    public LureModuleConfig(File file) {
        _lureFile = file;
        pokeList.add(new LurePokeSettings());
    }

    @Override
    public File getFile() {
        return _lureFile;
    }
}
