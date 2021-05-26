package eu.mccluster.anotherpokestop.config.trainerConfig;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class PokemonMoveConfig  extends Config {

    @Order(1)
    public String move1 = "Flare Blitz";

    @Order(2)
    public String move2 = "Recover";

    @Order(3)
    public String move3 = "Iron Head";

    @Order(4)
    public String move4 = "Toxic";

    @Override
    public File getFile() {
        return null;
    }

}
