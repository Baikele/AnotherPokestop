package eu.mccluster.anotherpokestop.config.mainConfig;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import eu.mccluster.dependency.configmanager.api.annotations.Skip;

import java.io.File;

public class AnotherPokeStopConfig extends Config {

    @Skip
    File _configFile;

    public AnotherPokeStopConfig(File file) {
        _configFile = file;
    }

    @Order(1)
    @Comment("Enables / Disables getting all the loot on clicking the close button inside the drop menu")
    public boolean claimRewardsOnClose = true;

    @Order(2)
    @Comment("Enable / Disable the Team Rocket Encounter.")
    public boolean rocketEvent = false;

    @Order(3)
    @Comment("Chance in percent that a player gets the Team Rocket encounter")
    public int rocketChance = 5;

    @Order(4)
    @Comment("Enable / Disable lure modules for Pokestops")
    public boolean lureModules = false;

    @Order(5)
    @Comment("Spawn range for lure modules")
    public int spawnRadius = 8;

    @Order(6)
    @Comment("Enable / Disable particles indicating a active lure module")
    public boolean particles = true;

    @Order(7)
    @Comment("Amount of particles per rotation. Scale this wih caution")
    public int numberOfParticles = 20;

    @Order(8)
    @Comment("Time in seconds how long an animation cycle lasts")
    public int timePerRotation = 3;

    @Order(9)
    @Comment("Ignore this setting unless you have to migrate your Pokestops again!")
    public boolean migrate = true;

    @Override
    public File getFile() {
        return _configFile;
    }

    public void changeMigration() {
        migrate = false;
    }
}
