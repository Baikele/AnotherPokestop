package eu.mccluster.anotherpokestop.objects;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.rules.teamselection.TeamSelectionList;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.*;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleAIMode;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerBaseConfig;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerPokemonConfig;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerTeamConfig;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class TrainerObject {

    @Getter
    private NPCTrainer _trainer;
    @Getter
    private EntityPlayerMP _player;
    @Getter
    @Setter
    private BattleControllerBase _battleController;
    @Getter
    private TrainerBaseConfig _config;

    public TrainerObject(EntityPlayerMP player, TrainerBaseConfig trainerBaseConfig, String lootTable) {
        if(trainerBaseConfig == null) {
            return;
        }
        this._player = player;
        AnotherPokeStop.getCurrentBattles().put(player, this);
        AnotherPokeStop.getPokestopLoot().put(player, lootTable);
        this._trainer = new NPCTrainer(this._player.world);
        _config = trainerBaseConfig;
        setTrainerInfo(trainerBaseConfig);
    }

    private void setTrainerInfo(TrainerBaseConfig config) {
        _trainer.setBossMode(EnumBossMode.valueOf(_config.bossMode));
        _trainer.setLevel(config.trainerLevel);
        _trainer.setName(config.name);
        _trainer.setBattleAIMode(EnumBattleAIMode.valueOf(config.battleAI));

        //Dynamax OR Mega
        if(config.oldGen) {
            _trainer.setOldGenMode(EnumOldGenMode.Mega);
            _trainer.setMegaItem(EnumMegaItemsUnlocked.Mega);
        } else if(config.canDynamax) {
            _trainer.setOldGenMode(EnumOldGenMode.Dynamax);
            _trainer.setMegaItem(EnumMegaItemsUnlocked.Dynamax);
        }

        TrainerTeamConfig teamConfig = config.trainerTeams.get(ThreadLocalRandom.current().nextInt(0, config.trainerTeams.size()));

        //TRAINER MODIFICATIONS ABOVE THIS LINE!
        ArrayList<Pokemon> pokemons = createPokemonFromConfig(teamConfig.trainerParty);
        _trainer.loadPokemon(pokemons);
        Optional<PlayerPartyStorage> optStorage = Optional.ofNullable(Pixelmon.storageManager.getParty(_player.getUniqueID()));
        if(!optStorage.isPresent()) {
            return;
        }

        //BATTLE START
        Rules rules = new Rules(config.battleRules);
        for(int i = 0; i < pokemons.size(); i++) {
            if(pokemons.get(i) != null) {
                _trainer.getPokemonStorage().set(i, pokemons.get(i));
            }
        }

        TeamSelectionList.addTeamSelection(rules.getRules(), true, optStorage.get(), _trainer.getPokemonStorage());
    }

    private ArrayList<Pokemon> createPokemonFromConfig(List<TrainerPokemonConfig> config) {
        ArrayList<Pokemon> team = new ArrayList<>();
        config.forEach(pkmConfig -> {
            team.add(createPokemon(pkmConfig));
        });
        return team;
    }

    private Pokemon createPokemon(TrainerPokemonConfig config) {
        EnumSpecies species = EnumSpecies.getFromNameAnyCase(config.name);
        Pokemon pkm = Pixelmon.pokemonFactory.create(species);
        //Setting IVs
        pkm.getIVs().setStat(StatsType.HP, config.ivs.hp);
        pkm.getIVs().setStat(StatsType.Attack, config.ivs.atk);
        pkm.getIVs().setStat(StatsType.Defence, config.ivs.def);
        pkm.getIVs().setStat(StatsType.SpecialAttack, config.ivs.spAtk);
        pkm.getIVs().setStat(StatsType.SpecialDefence, config.ivs.spDef);
        pkm.getIVs().setStat(StatsType.Speed, config.ivs.init);
        //Setting EVs
        pkm.getEVs().setStat(StatsType.HP, config.evs.hp);
        pkm.getEVs().setStat(StatsType.Attack, config.evs.atk);
        pkm.getEVs().setStat(StatsType.Defence, config.evs.def);
        pkm.getEVs().setStat(StatsType.SpecialAttack, config.evs.spAtk);
        pkm.getEVs().setStat(StatsType.SpecialDefence, config.evs.spDef);
        pkm.getEVs().setStat(StatsType.Speed, config.evs.init);
        //Setting Moves
        Attack move1 = new Attack(config.moves.move1);
        Attack move2 = new Attack(config.moves.move2);
        Attack move3 = new Attack(config.moves.move3);
        Attack move4 = new Attack(config.moves.move4);
        pkm.getMoveset().set(0, move1);
        pkm.getMoveset().set(1, move2);
        pkm.getMoveset().set(2, move3);
        pkm.getMoveset().set(3, move4);
        //Setting Level and other stuff
        pkm.setLevel(config.level);
        pkm.setShiny(config.shiny);
        pkm.setAbility(config.ability);
        if(!(config.nickname.isEmpty()))
            pkm.setNickname(config.nickname);
        pkm.setNature(EnumNature.natureFromString(config.nature));
        pkm.setGrowth(EnumGrowth.growthFromString(config.growth));
        pkm.setHeldItem(GameRegistry.makeItemStack("pixelmon:" + config.item, 0, 1, null));
        if(config.canDynamax) {
            pkm.setGigantamaxFactor(true);
            pkm.setDynamaxLevel(10);
        }
        return pkm;
    }
}
