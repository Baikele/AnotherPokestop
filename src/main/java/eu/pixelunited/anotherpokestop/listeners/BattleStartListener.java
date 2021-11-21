package eu.pixelunited.anotherpokestop.listeners;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import eu.pixelunited.anotherpokestop.AnotherPokeStop;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RequiredArgsConstructor
public class BattleStartListener {

    @SubscribeEvent
    public void onBattleStart(BattleStartedEvent event) {
        AnotherPokeStop.getCurrentBattles().forEach((playerMP, trainer) -> {
            event.bc.getPlayers().forEach(p -> {
                if (p.player.getName().equals(trainer.getPlayer().getName())) {
                    trainer.setBattleController(event.bc);
                }
            });
        });
    }
}
