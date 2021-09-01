package eu.mccluster.anotherpokestop.config.mainConfig;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import eu.mccluster.dependency.configmanager.api.annotations.Skip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    @Comment("Blacklist for Trainers. All world in that list will deactivate Trainer Encounters from Pokestops")
    public List<String> blackListTrainer = new ArrayList<>();

    @Order(5)
    @Comment("Enable / Disable lure modules for Pokestops")
    public boolean lureModules = false;

    @Order(6)
    @Comment("Blacklist. All worlds in that list will not be able to activate lure modules")
    public List<String> blacklistLures = new ArrayList<>();

    @Order(7)
    @Comment("Spawn range for lure modules")
    public int spawnRadius = 8;

    @Order(8)
    @Comment("Enable / Disable particles indicating a active lure module")
    public boolean particles = true;

    @Order(9)
    @Comment("Amount of particles per rotation. Scale this wih caution")
    public int numberOfParticles = 20;

    @Order(10)
    @Comment("Time in seconds how long an animation cycle lasts")
    public int timePerRotation = 3;

    @Order(11)
    @Comment("Don't touch this setting!!!")
    public String version = "1.x.x";

    @Override
    public File getFile() {
        return _configFile;
    }

    public void setVersion() {
        version = "2.0.0";
    }
}
