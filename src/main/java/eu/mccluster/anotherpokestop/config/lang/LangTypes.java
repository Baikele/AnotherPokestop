package eu.mccluster.anotherpokestop.config.lang;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class LangTypes extends Config {

    @Order(1)
    public String normal = "Normal";

    @Order(2)
    public String fighting = "§4Fighting";

    @Order(3)
    public String flying = "§3Flying";

    @Order(4)
    public String poison = "§5Poison";

    @Order(5)
    public String ground = "§2Ground";

    @Order(6)
    public String rock = "§aRock";

    @Order(7)
    public String bug = "§6Bug";

    @Order(8)
    public String ghost = "§1Ghost";

    @Order(9)
    public String steel = "§7Steel";

    @Order(10)
    public String fire = "§cFire";

    @Order(11)
    public String water = "§9Water";

    @Order(12)
    public String grass = "§aGrass";

    @Order(13)
    public String electric = "§eElectric";

    @Order(14)
    public String psychic = "§3Psychic";

    @Order(15)
    public String ice = "§bIce";

    @Order(16)
    public String dragon = "§1Dragon";

    @Order(17)
    public String dark = "§0Dark";

    @Order(18)
    public String fairy = "§bFairy";

    @Override
    public File getFile() {
        return null;
    }
}
