package eu.mccluster.anotherpokestop.Listener;

import eu.mccluster.anotherpokestop.AnotherPokeStop;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerLeftListener {

    AnotherPokeStop _plugin;

    public PlayerLeftListener(AnotherPokeStop _plugin) {
        this._plugin = _plugin;
    }

    @Listener
    public void PlayerLeftEvent(ClientConnectionEvent.Disconnect event) {
        _plugin._currentPokestopRemovers.remove(event.getTargetEntity().getUniqueId());
    }
}
