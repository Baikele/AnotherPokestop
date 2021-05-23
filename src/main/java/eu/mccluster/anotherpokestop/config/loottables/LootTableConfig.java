package eu.mccluster.anotherpokestop.config.loottables;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LootTableConfig extends Config {

    @Order(1)
    public List<LootTableData> lootData = new ArrayList<>();

    public LootTableConfig() {

        lootData.add(new LootTableData());
    }

    @Override
    public File getFile() {
        return null;
    }
}
