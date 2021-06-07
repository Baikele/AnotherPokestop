package eu.mccluster.anotherpokestop.Listener;

import eu.mccluster.anotherpokestop.AnotherPokeStop;
import net.minecraft.entity.player.EntityPlayerMP;
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
        EntityPlayerMP player = (EntityPlayerMP) event.getTargetEntity();
        if(_plugin.getCurrentBattles().containsKey(player)) {
            _plugin.getCurrentBattles().get(player).getTrainer().setDead();
            _plugin.getCurrentBattles().remove((EntityPlayerMP) event.getTargetEntity());
        }
        if(AnotherPokeStop.getPokestopLoot().containsKey(player)) {
            AnotherPokeStop.getPokestopLoot().remove((EntityPlayerMP) event.getTargetEntity());
        }
    }
}
