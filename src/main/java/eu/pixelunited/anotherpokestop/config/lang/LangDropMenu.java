package eu.pixelunited.anotherpokestop.config.lang;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class LangDropMenu {

    @Comment("Headline of the DropMenu")
    public String header = "Pokestop-Drops";

    @Comment("Close Button of the DropMenu")
    public String buttonText = "Close";

}
