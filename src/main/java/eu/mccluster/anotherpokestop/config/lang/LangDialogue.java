package eu.mccluster.anotherpokestop.config.lang;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class LangDialogue extends Config {

    @Order(1)
    @Comment("Dialogue the Player get when he encounters Team Rocket")
    public String dialogueText = "You encountered a Team Rocket Grunt";

    @Order(2)
    @Comment("Button text to start a fight with the Team Rocket Grunt")
    public String choiceYes = "Fight the Grunt";

    @Order(3)
    @Comment("Button text to evade the fight")
    public String choiceNo = "Evade the Grunt";

    @Order(4)
    @Comment("Text when a Lure Module can be activated")
    public String activateLureText = "Do you want to activate a %type% §rLure? This will cost %money% and your lure.";

    @Order(5)
    @Comment("Button to activate Lure")
    public String startLureModule = "Activate module";

    @Order(6)
    @Comment("Text when a Lure is already active")
    public String lureActive = "A lure module is already active. Try it again later";

    @Order(7)
    @Comment("Button to close the dialogue")
    public String closeMenu = "Goodbye";

    @Order(8)
    @Comment("Message if you dont have enough money to activate a module")
    public String notEnoughMoney = "You dont have enough money! You need %money% to activate the lure module.";


    @Override
    public File getFile() {
        return null;
    }
}
