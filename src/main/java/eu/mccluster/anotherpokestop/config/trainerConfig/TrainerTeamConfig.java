package eu.mccluster.anotherpokestop.config.trainerConfig;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TrainerTeamConfig extends Config {

    @Order(1)
    public List<TrainerPokemonConfig> trainerParty = new ArrayList<>();

    public TrainerTeamConfig() {
        trainerParty.add(new TrainerPokemonConfig());
        trainerParty.add(new TrainerPokemonConfig());
    }

    @Override
    public File getFile() {
        return null;
    }
}
