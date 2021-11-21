package eu.pixelunited.anotherpokestop.config.mainConfig;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class AnotherPokeStopConfig {

    @Comment("Enables / Disables getting all the loot on clicking the close button inside the drop menu")
    public boolean claimRewardsOnClose = true;

    @Comment("Enable / Disable the Team Rocket Encounter.")
    public boolean rocketEvent = false;

    @Comment("Chance in percent that a player gets the Team Rocket encounter")
    public int rocketChance = 5;

    @Comment("Blacklist for Trainers. All world in that list will deactivate Trainer Encounters from Pokestops")
    public List<String> blackListTrainer = new ArrayList<>();

    @Comment("Enable / Disable lure modules for Pokestops")
    public boolean lureModules = false;

    @Comment("Blacklist. All worlds in that list will not be able to activate lure modules")
    public List<String> blacklistLures = new ArrayList<>();

    @Comment("Spawn range for lure modules")
    public int spawnRadius = 8;

    @Comment("Enable / Disable particles indicating a active lure module")
    public boolean particles = true;

    @Comment("Amount of particles per rotation. Scale this wih caution")
    public int numberOfParticles = 20;

    @Comment("Time in seconds how long an animation cycle lasts")
    public int timePerRotation = 3;

    @Comment("Don't touch this setting!!!")
    public String version = "1.x.x";

}
