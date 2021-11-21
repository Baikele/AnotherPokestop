package eu.pixelunited.anotherpokestop.config.loottables;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;


@ConfigSerializable
public class LootTableData {

    public String loot = "pixelmon:poke_ball";

    public String displayItem = "pixelmon:poke_ball";

    public String rewardTitle = "§71x Pokeball";

    public int lootAmount = 1;

    public int lootRarity = 5;

}
