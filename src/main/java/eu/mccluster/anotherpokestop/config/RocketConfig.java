package eu.mccluster.anotherpokestop.config;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class RocketConfig extends Config {

    @Order(1)
    @Comment("Enable / Disable the Team Rocket Encounter. Ignore other rocket settings if false")
    public boolean rocketEvent = true;

    @Order(2)
    @Comment("Chance in percent that a player gets the Team Rocket encounter")
    public int rocketChance = 5;

    @Order(3)
    @Comment("Dialogue the Player get when he encounters Team Rocket")
    public String dialogueText = "You encountered a Team Rocket Grunt";

    @Order(4)
    @Comment("Button text to start a fight with the Team Rocket Grunt")
    public String choiceYes = "Fight the Grunt";

    @Order(5)
    @Comment("Button text to evade the fight")
    public String choiceNo = "Evade the Grunt";



    @Override
    public File getFile() {
        return null;
    }
}
