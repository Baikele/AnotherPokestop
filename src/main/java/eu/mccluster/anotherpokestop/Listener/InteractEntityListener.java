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
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

    @SubscribeEvent
    public void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof EntityPokestop)) {
            return;
        }

        if (!(event.getEntityPlayer() instanceof EntityPlayerMP)) {
            return;
        }
        if(event.getHand() == EnumHand.OFF_HAND) {
            return;
        }
        EntityPlayerMP p = (EntityPlayerMP) event.getEntityPlayer();
        UUID pokeStopId = event.getTarget().getUniqueID();


        if (_plugin._currentPokestopRemovers.contains(p.getUniqueID()) && AnotherPokeStop.getRegisteredPokeStops().containsKey(pokeStopId)) {

            for (int i = 0; i < _registry.registryList.size(); i++) {
                UUID registeredUUID = _registry.registryList.get(i).getPokeStopUniqueId();
                if (registeredUUID.equals(pokeStopId)) {
                    _registry.registryList.remove(i);
                    AnotherPokeStop.getInstance().saveRegistry();
                    break;
                }
            }
            AnotherPokeStop.getRegisteredPokeStops().remove(pokeStopId);
            event.getTarget().setDead();
            p.sendMessage(Utils.toText(_config.removeText));
            return;
        } else if (_plugin._currentPokestopRemovers.contains(p.getUniqueID())) {
            event.getTarget().setDead();
            return;
        }

        if (Utils.hasPermission(p,"anotherpokestop.claimpokestop")) {
            boolean cooldown = Utils.claimable(p, pokeStopId);
            if (AnotherPokeStop.getRegisteredPokeStops().containsKey(pokeStopId) && cooldown) {
                AnotherPokeStop.getUsedPokestop().put(p.getUniqueID(), pokeStopId);
                String lootTable = AnotherPokeStop.getRegisteredPokeStops().get(pokeStopId).getLoottable().getLoottable();

                if (_config.rocketSettings.rocketEvent) {
                    int rocketEvent = _config.rocketSettings.rocketChance;
                    int rocketRoll = (int) (100 * Math.random());
                    if (rocketEvent >= rocketRoll) {
                        RocketUtils.genRocketDialogue(pokeStopId, p, lootTable)
                                .open((EntityPlayerMP) p);
                        return;
                    }
                }

                List<ItemStack> lootList = Utils.genPokeStopLoot(false, lootTable);
                AnotherPokeStop.getCurrentDrops().put(p.getUniqueID(), lootList);
                Utils.dropScreen(_config.menuTexts.header, _config.menuTexts.buttonText, (EntityPlayerMP) p, lootList);

            } else if (AnotherPokeStop.getRegisteredPokeStops().containsKey(pokeStopId)) {
                p.sendMessage(Utils.toText(_config.cooldownText));
            }
        } else {
            p.sendMessage(Utils.toText(_config.noClaimPermission));
        }
    }

    @SubscribeEvent
    public void onDropClick(CustomDropsEvent.ClickDrop event) {
        EntityPlayerMP p = event.getPlayer();
        if(AnotherPokeStop.getCurrentDrops().containsKey(p.getUniqueID())) {
            int slotIndex = event.getIndex();
            p.inventory.addItemStackToInventory(AnotherPokeStop.getCurrentDrops().get(p.getUniqueID()).get(slotIndex));
        }
    }

}
