package eu.pixelunited.anotherpokestop.listeners;

import eu.pixelunited.anotherpokestop.AnotherPokeStop;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerLeftListener {

    AnotherPokeStop _plugin;

    public PlayerLeftListener(AnotherPokeStop _plugin) {
        this._plugin = _plugin;
    }

    @SubscribeEvent
    public void PlayerLeftEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        _plugin._currentPokestopRemovers.remove(player.getUniqueID());
        if(AnotherPokeStop.getCurrentBattles().containsKey(player)) {
            AnotherPokeStop.getCurrentBattles().get(player).getTrainer().setDead();
            AnotherPokeStop.getCurrentBattles().remove(player);
        }
        AnotherPokeStop.getPokestopLoot().remove(player);
        AnotherPokeStop.getCurrentEditor().remove(player.getUniqueID());
    }
}
