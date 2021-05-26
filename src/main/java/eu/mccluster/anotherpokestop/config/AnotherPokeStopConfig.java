package eu.mccluster.anotherpokestop.config;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class AnotherPokeStopConfig extends Config {

    @Order(1)
    @Comment("Defines the Range new created Pokestops change to a cube")
    public int cubeRange = 20;

    @Order(2)
    @Comment("Amount of ItemStacks the player receives when collects a Pokestop")
    public int lootAmount = 3;

    @Order(3)
    @Comment("Cooldown to collect the Pokestop again in Hours")
    public int cooldown = 24;

    @Order(4)
    @Comment("Message the player receives when the Pokestop is on cooldown. Use & for Formatting Codes")
    public String cooldownText = "&6Pokestop is on cooldown";

    @Order(5)
    @Comment("Message the player receives when a Pokestop is set. Use & for Formatting Codes")
    public String setText = "Set new pokestop";

    @Order(6)
    @Comment("Message the player receives when a removes a Pokestop. Use & for Formatting Codes")
    public String removeText = "Deleted Pokestop";

    @Order(7)
    @Comment("Message when player enables the remove mode. Use & for Formatting Codes")
    public String enableRemover = "Enabled remove mode";

    @Order(8)
    @Comment("Message when player disabls the remove mode. Use & for Formatting Codes")
    public String disableRemover = "Disabled remove mode";


    @Order(9)
    public AnotherPokeStopColorConfig standardColors = new AnotherPokeStopColorConfig();

    @Order(10)
    public AnotherPokeStopDropMenuConfig menuTexts = new AnotherPokeStopDropMenuConfig();

    @Order(11)
    public RocketConfig rocketSettings = new RocketConfig();

    @Override
    public File getFile() {
        return null;
    }
}
