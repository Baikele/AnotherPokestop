package eu.mccluster.anotherpokestop.config.loottables;
import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class LootTableData extends Config {

    @Order(1)
    public String loot = "pixelmon:poke_ball";

    @Order(2)
    public String displayItem = "pixelmon:poke_ball";

    @Order(3)
    public String rewardTitle = "§71x Pokeball";

    @Order(4)
    public int lootAmount = 1;

    @Order(5)
    public int lootRarity = 5;

    @Override
    public File getFile() {
        return null;
    }
}
