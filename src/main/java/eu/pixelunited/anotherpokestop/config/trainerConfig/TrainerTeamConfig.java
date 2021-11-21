package eu.pixelunited.anotherpokestop.config.trainerConfig;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class TrainerTeamConfig {

    public List<TrainerPokemonConfig> trainerParty = new ArrayList<>();

    public TrainerTeamConfig() {
        trainerParty.add(new TrainerPokemonConfig());
    }
}
