package eu.mccluster.anotherpokestop.config;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Comment;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;

public class AnotherPokeStopConfig extends Config {

    @Order(1)
    @Comment("Defines the Range new created Pokestops change to a cube")
    public int cubeRange = 20;

    @Order(2)
    @Comment("Message the player receives when the Pokestop is on cooldown. Formatting Codes supported")
    public String cooldownText = "Pokestop is available in %cooldownpkstop%";

    @Order(3)
    @Comment("Message the player receives when a Pokestop is set. Formatting Codes supported")
    public String setText = "Set new pokestop";

    @Order(4)
    @Comment("Message the player receives when a removes a Pokestop. Formatting Codes supported")
    public String removeText = "Deleted Pokestop";

    @Order(5)
    @Comment("Message when player enables the remove mode. Formatting Codes supported")
    public String enableRemover = "Enabled remove mode";

    @Order(6)
    @Comment("Message when player disables the remove mode. Formatting Codes supported")
    public String disableRemover = "Disabled remove mode";

    @Order(7)
    public AnotherPokeStopColorConfig standardColors = new AnotherPokeStopColorConfig();

    @Order(8)
    public AnotherPokeStopDropMenuConfig menuTexts = new AnotherPokeStopDropMenuConfig();

    @Order(9)
    public RocketConfig rocketSettings = new RocketConfig();

    @Order(10)
    @Comment("Message when a player lacks of the claim permission. Formatting Codes supported")
    public String noClaimPermission = "You don't have permission to claim Pokestops!";

    @Order(11)
    @Comment("For Placeholder %cooldownpkstop%: Singular for minute")
    public String singularMinute = "minute";

    @Order(12)
    @Comment("For Placerholder %cooldownpkstop%: Plural for minute")
    public String pluralMinute = "minutes";

    @Order(13)
    @Comment("For Placeholder %cooldownpkstop%: Singular for hour")
    public String singularHour = "hour";

    @Order(14)
    @Comment("For Placerholder %cooldownpkstop%: Plural for hour")
    public String pluralHour = "hours";

    @Order(15)
    @Comment("Enables / Disables getting all the loot on clicking the close button inside the drop menu")
    public boolean claimRewardsOnClose = false;

    @Order(16)
    @Comment("Item that represents commands in the drop menu. Item can't be used as a Item drop!")
    public String commandItem = "pixelmon:electirizer";

    @Override
    public File getFile() {
        return null;
    }
}
