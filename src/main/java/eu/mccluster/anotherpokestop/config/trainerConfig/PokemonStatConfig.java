package eu.mccluster.anotherpokestop.config.trainerConfig;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;
import eu.mccluster.dependency.configmanager.api.annotations.Skip;

import java.io.File;

public class PokemonStatConfig  extends Config {

    public PokemonStatConfig(int hp, int atk, int def, int spAtk, int spDef, int init) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spAtk = spAtk;
        this.spDef = spDef;
        this.init = init;
    }

    @Skip
    File _configFile;

    @Order(1)
    public int hp;

    @Order(2)
    public int atk;

    @Order(3)
    public int def;

    @Order(4)
    public int spAtk;

    @Order(5)
    public int spDef;

    @Order(6)
    public int init;

    @Override
    public File getFile() {
        return _configFile;
    }
}
