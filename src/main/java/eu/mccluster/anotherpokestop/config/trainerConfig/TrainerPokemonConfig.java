package eu.mccluster.anotherpokestop.config.trainerConfig;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class TrainerPokemonConfig extends Config {

    @Order(1)
    public String name = "Marshadow";

    @Order(2)
    public String nickname = "";

    @Order(3)
    public boolean shiny = true;

    @Order(4)
    public int form = 1;

    @Order(5)
    public String nature = "Adamant";

    @Order(6)
    public int level = 100;

    @Order(7)
    public String item = "leftovers";

    @Order(8)
    public String ability = "Technician";

    @Order(9)
    public String growth = "Runt";

    @Order(10)
    public boolean canDynamax = true;

    @Order(11)
    public PokemonStatConfig ivs = new PokemonStatConfig(31, 31, 31 ,0, 31, 31);

    @Order(12)
    public PokemonStatConfig evs = new PokemonStatConfig(0, 252, 6, 0, 0, 252);

    @Order(13)
    public PokemonMoveConfig moves = new PokemonMoveConfig();

    @Override
    public File getFile() {
        return null;
    }
}
