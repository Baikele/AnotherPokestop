package eu.mccluster.anotherpokestop.config.loottables;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LootTableConfig extends Config {

    @Order(1)
    @Comment("Amount of ItemStacks the player receives when collects a Pokestop")
    public int lootSize = 3;

    @Order(2)
    @Comment("Amount of ItemStacks the player receives when he wins a Team Rocket Encounter")
    public int rocketLootSize = 6;

    @Order(2)
    @Comment("Cooldown to collect the Pokestop again. Add a m, h or d to the number to change the cooldown to Minutes, Hours or Days")
    public String cooldown = "24h";

    @Order(3)
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
