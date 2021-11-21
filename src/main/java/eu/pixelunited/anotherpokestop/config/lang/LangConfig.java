package eu.pixelunited.anotherpokestop.config.lang;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class LangConfig {

    public LangConfig() {

    }


    @Comment("Message when a player lacks of the claim permission")
    public String noClaimPermission = "You don't have permission to claim Pokestops!";

    @Comment("Message the player gets after defeating a grunt")
    public String winText = "The Grunt you defeated dropped some items he stole from this location!";

    @Comment("Lang settings for the drop menu")
    public LangDropMenu langDropMenu = new LangDropMenu();

    @Comment("Lang settings for everything related with cooldowns")
    public LangCooldown langCooldown = new LangCooldown();

    @Comment("Lang settings for dialogues")
    public LangDialogue langDialogue = new LangDialogue();

    @Comment("Lang settings for the 18 pokemon types")
    public LangTypes langTypes = new LangTypes();

}
