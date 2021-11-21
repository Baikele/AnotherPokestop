package eu.pixelunited.anotherpokestop.config.trainerConfig;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class PokemonStatConfig {

    public PokemonStatConfig() {
    }

    public int hp = 0;

    public int atk = 0;

    public int def = 0;

    public int spAtk = 0;

    public int spDef = 0;

    public int init = 0;
}
