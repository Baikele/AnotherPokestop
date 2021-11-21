package eu.pixelunited.anotherpokestop.config.lureModule;


import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class LureModuleConfig {

    public String particleType = "heart";

    @Comment("Only use for redstone dust")
    public ParticleColor colors = new ParticleColor();

    public int activationCostWeak = 5000;

    public int activationCostStrong = 10000;

    public int spawnTicksWeak = 3;

    public int spawnTicksStrong = 6;

    @Comment("Times in second a spawnTick happens. spawnTick * spawnInterval = Overall time")
    public int spawnInterval = 10;

    @Comment("Chance in percent something spawns on a spawn tick")
    public int spawnChance = 40;

    public int spawnQuantity = 2;

    public List<LurePokeSettings> pokeList = new ArrayList<>();

    public LureModuleConfig() {
        pokeList.add(new LurePokeSettings());
    }

}
