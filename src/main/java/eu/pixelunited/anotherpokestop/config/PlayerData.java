package eu.pixelunited.anotherpokestop.config;

import eu.pixelunited.anotherpokestop.objects.PlayerCooldowns;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class PlayerData{


    @Comment("Don't touch this!")
    public List<PlayerCooldowns> playerCooldowns = new ArrayList<>();
}
