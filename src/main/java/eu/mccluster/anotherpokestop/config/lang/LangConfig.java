package eu.mccluster.anotherpokestop.config.lang;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Comments;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import eu.mccluster.dependency.configmanager.api.annotations.Skip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LangConfig extends Config {

    @Skip
    File _langFile;

    @Order(1)
    @Comment("Message when a player lacks of the claim permission")
    public String noClaimPermission = "You don't have permission to claim Pokestops!";

    @Order(2)
    @Comment("Message the player gets after defeating a grunt")
    public String winText = "The Grunt you defeated dropped some items he stole from this location!";

    @Order(3)
    @Comment("Lang settings for the drop menu")
    public LangDropMenu langDropMenu = new LangDropMenu();

    @Order(4)
    @Comment("Lang settings for everything related with cooldowns")
    public LangCooldown langCooldown = new LangCooldown();

    @Order(5)
    @Comment("Lang settings for dialogues")
    public LangDialogue langDialogue = new LangDialogue();

    @Order(6)
    @Comment("Lang settings for the 18 pokemon types")
    public LangTypes langTypes = new LangTypes();


    public LangConfig(File file) {
        _langFile = file;
    }


    @Override
    public File getFile() {
        return _langFile;
    }
}
