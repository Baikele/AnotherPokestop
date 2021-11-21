package eu.pixelunited.anotherpokestop.config.loottables;


import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class LootTableConfig{

    @Comment("Amount of ItemStacks the player receives when collects a Pokestop")
    public int lootSize = 3;

    @Comment("Amount of ItemStacks the player receives when he wins a Team Rocket Encounter")
    public int rocketLootSize = 6;

    @Comment("Cooldown to collect the Pokestop again. Add a m, h or d to the number to change the cooldown to Minutes, Hours or Days")
    public String cooldown = "24h";

    @Comment("Default Loottable for Pokestop")
    public List<LootTableData> lootData = new ArrayList<>();

    @Comment("Default Loottable for Rocket Encounters")
    public List<LootTableData> rocketData = new ArrayList<>();

    public LootTableConfig() {

        lootData.add(new LootTableData());
        rocketData.add(new LootTableData());
    }

}
