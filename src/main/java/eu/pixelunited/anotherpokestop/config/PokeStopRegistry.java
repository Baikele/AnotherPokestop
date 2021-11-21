package eu.pixelunited.anotherpokestop.config;

import eu.pixelunited.anotherpokestop.objects.PokeStopData;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class PokeStopRegistry {

    @Comment("Don't change this by hand on any case or else your Pokestops will be broke!")
    public List<PokeStopData> registryList = new ArrayList<>();

    @Comment("Loot Multiplier")
    public double lootMultiplier = 1.0;
}
