package eu.pixelunited.anotherpokestop.config.lureModule;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class LurePokeSettings {

    public String pokemon = "Haunter";

    public int rarity = 10;

    public int shinyChance = 1;

    public String customArgs = "";

}
