package eu.mccluster.anotherpokestop.config.lureModule;

import eu.mccluster.anotherpokestop.config.loottables.LootTableConfig;
import eu.mccluster.dependency.configmanager.api.Config;
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
    public int activationCost = 10000;

    @Order(3)
    public int spawnTicks = 6;

    @Order(4)
    public int spawnInterval = 10;

    @Order(5)
    public int spawnChance = 40;

    @Order(6)
    public int spawnQuantity = 2;

    @Order(7)
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
