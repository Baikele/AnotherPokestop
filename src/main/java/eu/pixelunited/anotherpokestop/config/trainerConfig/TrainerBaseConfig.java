package eu.pixelunited.anotherpokestop.config.trainerConfig;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class TrainerBaseConfig {

    @Comment("Name of the NPC")
    public String name = "AmINPC";

    @Comment("Possible battleAIs: Default Random Aggressive Tactical Advanced")
    public String battleAI = "Advanced";

    @Comment("Possible bossModes: NotBoss, Common, Uncommon, Rare, Epic, Legendary, Ultimate, Spooky, Drowned, Equal")
    public String bossMode = "NotBoss";

    @Comment("If this is true, players can use dynamax in battle - Either canDynamax or oldGen can be enabled")
    public boolean canDynamax = false;

    @Comment("If this is true, players can mega in battle - Either canDynamax or oldGen can be enabled (if both are enabled megas will be enabled)")
    public boolean oldGen = false;

    public int trainerLevel = 100;

    public TrainerBattleRulesConfig battleRules = new TrainerBattleRulesConfig();

    public List<String> loseCommands = new ArrayList<>();

    public List<TrainerTeamConfig> trainerTeams = new ArrayList<>();

    public TrainerBaseConfig() {
        trainerTeams.add(new TrainerTeamConfig());
    }

}
