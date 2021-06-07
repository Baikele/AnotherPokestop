package eu.mccluster.anotherpokestop.config.loottables;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LootTableConfig extends Config {

    @Order(1)
    @Comment("Default LootTableConfig for Pokestop")
    public List<LootTableData> lootData = new ArrayList<>();
    public List<RocketTableData> rocketData = new ArrayList<>();

    public LootTableConfig() {

        lootData.add(new LootTableData());
        rocketData.add(new RocketTableData());
    }


    @Override
    public File getFile() {
        return null;
    }
}
