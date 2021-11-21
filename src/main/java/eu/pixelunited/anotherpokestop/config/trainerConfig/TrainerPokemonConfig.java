package eu.pixelunited.anotherpokestop.config.trainerConfig;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class TrainerPokemonConfig {

    public TrainerPokemonConfig() {}

    public String name = "Marshadow";

    public String nickname = "";

    public boolean shiny = true;

    public int form = 0;

    public String gender = "Female";

    public String nature = "Adamant";

    public int level = 100;

    public String item = "leftovers";

    public String ability = "Technician";

    public String growth = "Runt";

    public boolean canDynamax = true;

    public PokemonStatConfig ivs = new PokemonStatConfig();

    public PokemonStatConfig evs = new PokemonStatConfig();

    public PokemonMoveConfig moves = new PokemonMoveConfig();
}
