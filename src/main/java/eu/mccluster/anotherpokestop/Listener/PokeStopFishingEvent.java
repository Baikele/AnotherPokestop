package eu.mccluster.anotherpokestop.Listener;

import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class PokeStopFishingEvent {

    @SubscribeEvent
    public void PokeStopFishEvent(LivingEvent.LivingUpdateEvent event) {
        if(event.getEntity() instanceof EntityPokestop) {
            event.setCanceled(true);
            return;
        }
        }
    }

