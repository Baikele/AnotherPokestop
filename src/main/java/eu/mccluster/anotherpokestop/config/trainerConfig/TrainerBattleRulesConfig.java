package eu.mccluster.anotherpokestop.config.trainerConfig;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TrainerBattleRulesConfig extends Config {

    @Order(1)
    public int levelCap = 100;

    @Order(2)
    public String battleType = "Single";

    @Order(3)
    public int numPokemon = 6;

    @Order(4)
    public int turnTime = 60;

    @Order(5)
    public int teamSelectTime = 120;

    @Order(6)
    public boolean teamPreview = true;

    @Order(7)
    public boolean fullheal = true;

    @Order(8)
    @Comment("pokemon for SpeciesClause, speedpass for SpeedPass, sky for SkyBattle, item for ItemClause, sleep for SleepCause, bag for BagClause, forfeit for ForfeitClause, inverse for InverseBattle" +
            "batonpass for BatonPass, drizzleswim, drought, endlessbattle, evasionability, evasion, legendary, mega, moody, ohko, sandstream, shadowtag, smashpass, snowwarning, souldew, swagger, weatherspeed")
    public List<String> clauses = new ArrayList<>();

    //Pokemon, Abilities, Moves, Items
    @Order(9)
    public List<String> bannedPokemon = new ArrayList<>();

    @Order(10)
    public List<String> bannedAbilities = new ArrayList<>();

    @Order(11)
    public List<String> bannedItems = new ArrayList<>();

    public TrainerBattleRulesConfig() {
        clauses.add("pokemon");
        bannedPokemon.add("bibarel");
        bannedAbilities.add("Levitate");
        bannedItems.add("weakness_policy");
    }


    @Override
    public File getFile() {
        return null;
    }
}
