package eu.mccluster.anotherpokestop.objects;

import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.battles.rules.clauses.*;
import com.pixelmonmod.pixelmon.entities.pixelmon.abilities.AbilityBase;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleType;
import com.pixelmonmod.pixelmon.enums.heldItems.EnumHeldItems;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerBattleRulesConfig;
import eu.mccluster.anotherpokestop.utils.Utils;

import java.util.*;

/**
 * @author Flashback083 on 5/16/19
 * @author NateCraft on 8/31/19 - Edited
 * https://github.com/Flashback083/PixelmonCookbook/blob/master/src/main/java/com/pixelmonmod/cookbook/examplemod/battle/Rules.java
 * **/
public class Rules extends BattleRules {

    private BattleRules rules;
    private List<BattleClause> clauses;

    public Rules(TrainerBattleRulesConfig config)
    {
        this.rules = new BattleRules();
        this.clauses = new ArrayList<>();
        this.levelCap = config.levelCap;

        //Set BattleType
        switch(config.battleType.toLowerCase())
        {
            case "single": super.battleType = EnumBattleType.Single; break;
            case "double": super.battleType = EnumBattleType.Double; break;
            case "triple": super.battleType = EnumBattleType.Triple; break;
            case "Rotation": super.battleType = EnumBattleType.Rotation; break;
        }

        this.numPokemon = config.numPokemon;
        this.turnTime = config.turnTime;
        this.teamSelectTime = config.teamSelectTime;
        this.teamPreview = config.teamPreview;
        this.getRules().teamPreview = config.teamPreview;
        this.getRules().fullHeal = config.fullheal;
        this.getRules().raiseToCap = config.raiseToCap;

        //Insert default battle clauses
        config.clauses.forEach(this::addClause);

        //Ban pokemon
        config.bannedPokemon.forEach(pkm -> addClause(new PokemonClause("no" + Utils.capitalizeFirstLetter(pkm), EnumSpecies.getFromNameAnyCase(pkm))));

        //Ban Ability
        config.bannedAbilities.forEach(ability -> {
            Optional<AbilityBase> optionalAbility = AbilityBase.getAbility(ability);

            optionalAbility.ifPresent(abilityBase -> {
                @SuppressWarnings("unchecked")
                AbilityClause clause = new AbilityClause("no" + Utils.capitalizeFirstLetter(ability), abilityBase.getClass());
                addClause(clause);
            });
        });

        //Ban items
        Arrays.stream(EnumHeldItems.values())
                .filter(heldItem -> config.bannedItems.contains(heldItem.name().toLowerCase(Locale.ROOT)))
                .forEach(heldItem -> addClause(new ItemPreventClause("no" + Utils.capitalizeFirstLetter(heldItem.name()), heldItem)));

    }

    /**
     * Method to add a new battleClause
     * @param id id of clause
     */
    public void addClause(String id)
    {
        clauses.add(BattleClauseRegistry.getClauseRegistry().getClause(id));
        rules.setNewClauses(clauses);
    }

    public void addClause(BattleClause battleClause)
    {
        clauses.add(battleClause);
        rules.setNewClauses(clauses);
    }

    /**
     *
     * @return complete BattleRules
     */
    public BattleRules getRules()
    {
        return rules;
    }
}
