package eu.mccluster.anotherpokestop.config.lang;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class LangCooldown extends Config {

    @Order(1)
    @Comment("Message the player receives when the Pokestop is on cooldown. Formatting Codes supported")
    public String cooldownText = "Pokestop is available in %cooldownpkstop%";

    @Order(2)
    public String singularMinute = "minute";

    @Order(3)
    public String pluralMinute = "minutes";

    @Order(4)
    public String singularHour = "hour";

    @Order(5)
    public String pluralHour = "hours";

    @Order(6)
    public String and = "and";



    @Override
    public File getFile() {
        return null;
    }
}
