package eu.pixelunited.anotherpokestop.config.trainerConfig;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class TrainerBattleRulesConfig {

    public int levelCap = 100;

    public String battleType = "Single";

    public int numPokemon = 6;

    public int turnTime = 60;

    public int teamSelectTime = 120;

    public boolean teamPreview = true;

    public boolean fullheal = true;

    public boolean raiseToCap = false;

    @Comment("pokemon for SpeciesClause, speedpass for SpeedPass, sky for SkyBattle, item for ItemClause, sleep for SleepCause, bag for BagClause, forfeit for ForfeitClause, inverse for InverseBattle" +
            "batonpass for BatonPass, drizzleswim, drought, endlessbattle, evasionability, evasion, legendary, mega, moody, ohko, sandstream, shadowtag, smashpass, snowwarning, souldew, swagger, weatherspeed")
    public List<String> clauses = new ArrayList<>();

    public List<String> bannedPokemon = new ArrayList<>();

    public List<String> bannedAbilities = new ArrayList<>();

    public List<String> bannedItems = new ArrayList<>();

    public TrainerBattleRulesConfig() {
        clauses.add("pokemon");
        bannedPokemon.add("bibarel");
        bannedAbilities.add("Levitate");
        bannedItems.add("weakness_policy");
    }

}
