package eu.mccluster.anotherpokestop.objects;

import eu.mccluster.dependency.configmanager.api.Config;
import eu.mccluster.dependency.configmanager.api.annotations.Order;

import java.io.File;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class PlayerCooldowns extends Config {

    @Order(1)
    private String pokestopID;

    @Order(2)
    private long lastVisit;

    public PlayerCooldowns() {}

    public PlayerCooldowns(UUID pokestopID, Date date) {
        this.pokestopID = pokestopID.toString();
        this.lastVisit = date.getTime();
    }

    public UUID getPokestopID() { return UUID.fromString(this.pokestopID); }

    public Date getDate() { return Date.from(Instant.ofEpochMilli(this.lastVisit)); }


    @Override
    public File getFile() {
        return null;
    }
}