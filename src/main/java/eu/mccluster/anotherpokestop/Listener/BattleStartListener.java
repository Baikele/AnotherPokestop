package eu.mccluster.anotherpokestop.Listener;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RequiredArgsConstructor
public class BattleStartListener {

    private final AnotherPokeStop _instance;

    @SubscribeEvent
    public void onBattleStart(BattleStartedEvent event) {
        _instance.getCurrentBattles().forEach((playerMP, trainer) -> {
            event.bc.getPlayers().forEach(p -> {
                if (p.player.getName().equals(trainer.getPlayer().getName())) {
                    trainer.setBattleController(event.bc);
                }
            });
        });
    }
}
