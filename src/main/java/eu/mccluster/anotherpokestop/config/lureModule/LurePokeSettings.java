package eu.mccluster.anotherpokestop.config.lureModule;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class LurePokeSettings extends Config {

    @Order(1)
    public String pokemon = "Haunter";

    @Order(2)
    public int rarity = 10;

    @Order(3)
    public int shinyChance = 1;

    @Order(4)
    public String customArgs = "";

    @Override
    public File getFile() {
        return null;
    }
}
