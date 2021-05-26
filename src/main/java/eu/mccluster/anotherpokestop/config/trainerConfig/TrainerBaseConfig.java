package eu.mccluster.anotherpokestop.config.trainerConfig;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import eu.mccluster.dependency.configmanager.api.annotations.Skip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TrainerBaseConfig  extends Config {

    @Skip
    File _configFile;

    @Order(1)
    @Comment("Name of the NPC")
    public String name = "AmINPC";

    @Order(2)
    @Comment("Possible battleAIs: Default Random Aggressive Tactical Advanced")
    public String battleAI = "Advanced";

    @Order(3)
    @Comment("If this is true, players can use dynamax in battle - Either canDynamax or oldGen can be enabled")
    public boolean canDynamax = false;

    @Order(4)
    @Comment("If this is true, players can mega in battle - Either canDynamax or oldGen can be enabled (if both are enabled megas will be enabled)")
    public boolean oldGen = false;

    @Order(5)
    public int trainerLevel = 100;

    @Order(6)
    public TrainerBattleRulesConfig battleRules = new TrainerBattleRulesConfig();

    @Order(7)
    public List<TrainerTeamConfig> trainerTeams = new ArrayList<>();

    public TrainerBaseConfig(File file) {
        _configFile = file;
        trainerTeams.add(new TrainerTeamConfig());
    }

    @Override
    public File getFile() {
        return _configFile;
    }
}
