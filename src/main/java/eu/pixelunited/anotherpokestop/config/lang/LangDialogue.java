package eu.pixelunited.anotherpokestop.config.lang;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class LangDialogue {

    @Comment("Dialogue the Player get when he encounters Team Rocket")
    public String dialogueText = "You encountered a Team Rocket Grunt";

    @Comment("Button text to start a fight with the Team Rocket Grunt")
    public String choiceYes = "Fight the Grunt";

    @Comment("Button text to evade the fight")
    public String choiceNo = "Evade the Grunt";

    @Comment("Text when a Lure Module can be activated. Use %money% and %lure% to display the activation cost and the lure type")
    public String activateLureText = "Do you want to activate a %type% §rLure? This will cost %money% and your lure.";

    @Comment("Button to activate Lure")
    public String startLureModule = "Activate module";

    @Comment("Text when a Lure is already active")
    public String lureActive = "A lure module of the type %lure% §ris already active. Try it again later";

    @Comment("Button to close the dialogue")
    public String closeMenu = "Goodbye";

    @Comment("Message if you dont have enough money to activate a module. Use %money% to display the activation cost")
    public String notEnoughMoney = "You dont have enough money! You need %money% to activate the lure module.";

}
