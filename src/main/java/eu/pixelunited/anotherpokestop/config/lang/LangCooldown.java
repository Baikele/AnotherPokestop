package eu.pixelunited.anotherpokestop.config.lang;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class LangCooldown{

    @Comment("Message the player receives when the Pokestop is on cooldown. Use %cooldownpokestop% to display the time")
    public String cooldownText = "Pokestop is available in %cooldownpkstop%";

    public String singularMinute = "minute";

    public String pluralMinute = "minutes";

    public String singularHour = "hour";

    public String pluralHour = "hours";

    public String and = "and";

}
