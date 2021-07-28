package eu.mccluster.anotherpokestop.config.trainerConfig;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class PokemonMoveConfig  extends Config {

    @Order(1)
    public String move1 = "Shadow Sneak";

    @Order(2)
    public String move2 = "Spectral Thief";

    @Order(3)
    public String move3 = "Close Combat";

    @Order(4)
    public String move4 = "Rock Tomb";

    @Override
    public File getFile() {
        return null;
    }

}
