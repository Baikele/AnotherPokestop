package eu.mccluster.anotherpokestop.Listener;

import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.FishingEvent;


public class PokeStopFishingEvent {

    @Listener
    public void onPokestopGetFished(FishingEvent.HookEntity event) {
        if(event.getTargetEntity() instanceof EntityPokestop) {
            event.setCancelled(true);
        }
    }


}
