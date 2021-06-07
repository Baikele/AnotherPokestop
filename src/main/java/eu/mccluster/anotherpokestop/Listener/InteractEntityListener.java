package eu.mccluster.anotherpokestop.Listener;

import com.pixelmonmod.pixelmon.api.events.drops.CustomDropsEvent;
import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.config.PokeStopRegistry;
import eu.mccluster.anotherpokestop.utils.RocketUtils;
import eu.mccluster.anotherpokestop.utils.Utils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.util.List;
import java.util.UUID;


public class InteractEntityListener {

    final AnotherPokeStop _plugin;
    final AnotherPokeStopConfig _config;
    final PokeStopRegistry _registry;


    public InteractEntityListener(AnotherPokeStop plugin, AnotherPokeStopConfig config, PokeStopRegistry registry) {
        this._plugin = plugin;
        _config = config;
        _registry = registry;
    }

    @Listener
    public void onEntityRightClick(InteractEntityEvent.Secondary.MainHand event) {
        if (!(event.getTargetEntity() instanceof EntityPokestop)) {
            return;
        }

        if(!(event.getSource() instanceof Player)) {
            return;
        }
        Player p = (Player) event.getSource();
        UUID pokeStopId = ((EntityPokestop) event.getTargetEntity()).getUniqueID();


        if(_plugin._currentPokestopRemovers.contains(p.getUniqueId()) && AnotherPokeStop.getRegisteredPokeStops().containsKey(pokeStopId)) {

            for(int i = 0; i < _registry.registryList.size(); i++) {
                UUID registeredUUID = _registry.registryList.get(i).getPokeStopUniqueId();
                if(registeredUUID.equals(pokeStopId)) {
                    _registry.registryList.remove(i);
                    AnotherPokeStop.getInstance().saveRegistry();
                    break;
                }
            }
            AnotherPokeStop.getRegisteredPokeStops().remove(pokeStopId);
            event.getTargetEntity().remove();
            p.sendMessage(Utils.toText(_config.removeText));
            return;
        } else if(_plugin._currentPokestopRemovers.contains(p.getUniqueId())) {
            event.getTargetEntity().remove();
            return;
        }

        boolean cooldown = Utils.claimable(p, pokeStopId);
        if(AnotherPokeStop.getRegisteredPokeStops().containsKey(pokeStopId) && cooldown) {
            AnotherPokeStop.getUsedPokestop().put(p.getUniqueId(), pokeStopId);
            String lootTable = AnotherPokeStop.getRegisteredPokeStops().get(pokeStopId).getLoottable().getLoottable();

            if(_config.rocketSettings.rocketEvent) {
                int rocketEvent = _config.rocketSettings.rocketChance;
                int rocketRoll = (int) (100 * Math.random());
                if (rocketEvent >= rocketRoll) {
                    RocketUtils.genRocketDialogue(pokeStopId, p, lootTable)
                            .open((EntityPlayerMP) p);
                    return;
                }
            }

                List<ItemStack> lootList = Utils.listToNative(Utils.genPokeStopLoot(false, lootTable));
                AnotherPokeStop.getCurrentDrops().put(p.getUniqueId(), lootList);
                Utils.dropScreen(_config.menuTexts.header, _config.menuTexts.buttonText, (EntityPlayerMP) p, lootList);

        } else if(AnotherPokeStop.getRegisteredPokeStops().containsKey(pokeStopId)) {
                p.sendMessage(Utils.toText(_config.cooldownText));
            }
        }

    @SubscribeEvent
    public void onDropClick(CustomDropsEvent.ClickDrop event) {
        Player p = (Player) event.getPlayer();
        if(AnotherPokeStop.getCurrentDrops().containsKey(p.getUniqueId())) {
            int slotIndex = event.getIndex();
            p.getInventory().offer(ItemStackUtil.fromNative(AnotherPokeStop.getCurrentDrops().get(p.getUniqueId()).get(slotIndex)));
        }
    }

}
