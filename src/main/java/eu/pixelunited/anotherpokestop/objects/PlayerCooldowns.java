package eu.pixelunited.anotherpokestop.objects;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@ConfigSerializable
public class PlayerCooldowns {

    private String pokestopID = "";

    private long lastVisit = 0;

    public PlayerCooldowns() {}

    public PlayerCooldowns(UUID pokestopID, Date date) {
        this.pokestopID = pokestopID.toString();
        this.lastVisit = date.getTime();
    }

    public UUID getPokestopID() { return UUID.fromString(this.pokestopID); }

    public Date getDate() { return Date.from(Instant.ofEpochMilli(this.lastVisit)); }

}