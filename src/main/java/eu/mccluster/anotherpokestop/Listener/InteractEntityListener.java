package eu.mccluster.anotherpokestop.Listener;

import com.pixelmonmod.pixelmon.api.events.drops.CustomDropsEvent;
import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.config.PokeStopRegistry;
import eu.mccluster.anotherpokestop.objects.LoottableStorage;
import eu.mccluster.anotherpokestop.objects.PokeStopData;
import eu.mccluster.anotherpokestop.objects.RGBStorage;
import eu.mccluster.anotherpokestop.utils.Placeholders;
import eu.mccluster.anotherpokestop.utils.RocketUtils;
import eu.mccluster.anotherpokestop.utils.Utils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
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

        if(AnotherPokeStop.getCurrentEditor().containsKey(p.getUniqueID()) && !_plugin._currentPokestopRemovers.contains(p.getUniqueID())) {
            if (AnotherPokeStop.getCurrentEditor().get(p.getUniqueID()).get(0).equals("color")) {
                int r = Integer.parseInt(AnotherPokeStop.getCurrentEditor().get(p.getUniqueID()).get(1));
                int g = Integer.parseInt(AnotherPokeStop.getCurrentEditor().get(p.getUniqueID()).get(2));
                int b = Integer.parseInt(AnotherPokeStop.getCurrentEditor().get(p.getUniqueID()).get(3));
                if(r > 255 || g > 255 || b > 255) {
                    p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4RGB Limit is 255"));
                    AnotherPokeStop.getCurrentEditor().remove(p.getUniqueID());
                    return;
                }
                if(r < 0 || g < 0 || b < 0) {
                    p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4RGB Minimum is 0"));
                    AnotherPokeStop.getCurrentEditor().remove(p.getUniqueID());
                    return;
                }
                ((EntityPokestop) event.getTarget()).setColor(r, g, b);

                for (int i = 0; i < _registry.registryList.size(); i++) {
                    UUID registeredUUID = _registry.registryList.get(i).getPokeStopUniqueId();
                    if (registeredUUID.equals(pokeStopId)) {
                        RGBStorage rgbStorage = new RGBStorage(r, g, b);
                        PokeStopData pokeStopData = new PokeStopData(pokeStopId, rgbStorage, event.getWorld(), event.getTarget().posX, event.getTarget().posY, event.getTarget().posZ, _registry.registryList.get(i).getLoottable());
                        _registry.registryList.set(i, pokeStopData);
                        AnotherPokeStop.getInstance().saveRegistry();
                        break;
                    }
                }
                AnotherPokeStop.getCurrentEditor().remove(p.getUniqueID());
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Pokestop color changed to &c" + r + "&2 " + g + "&9 " + b + "."));
                return;

            } else if (AnotherPokeStop.getCurrentEditor().get(p.getUniqueID()).get(0).equals("rotation")) {
                float rotation = (float) Integer.parseInt(AnotherPokeStop.getCurrentEditor().get(p.getUniqueID()).get(1));
                EntityPokestop pokestop = (EntityPokestop) event.getTarget();
                pokestop.setPositionAndRotation(pokestop.posX, pokestop.posY, pokestop.posZ, rotation, pokestop.rotationPitch);
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Pokestop rotation changed to &d" + rotation + "°&6."));
                AnotherPokeStop.getCurrentEditor().remove(p.getUniqueID());
                return;

            } else if (AnotherPokeStop.getCurrentEditor().get(p.getUniqueID()).get(0).equals("loottable")) {
                for (int i = 0; i < _registry.registryList.size(); i++) {
                    UUID registeredUUID = _registry.registryList.get(i).getPokeStopUniqueId();

                    if (registeredUUID.equals(pokeStopId)) {

                        boolean checklootTable = AnotherPokeStop.getInstance()._avaiableLoottables.contains(AnotherPokeStop.getCurrentEditor().get(p.getUniqueID()).get(1));

                        if(checklootTable) {

                            LoottableStorage loottableStorage = new LoottableStorage(AnotherPokeStop.getCurrentEditor().get(p.getUniqueID()).get(1));
                            RGBStorage rgbStorage = new RGBStorage(_registry.registryList.get(i).getColor().getR(), _registry.registryList.get(i).getColor().getG(), _registry.registryList.get(i).getColor().getB());
                            PokeStopData pokeStopData = new PokeStopData(pokeStopId, rgbStorage, event.getWorld(), event.getTarget().posX, event.getTarget().posY, event.getTarget().posZ, loottableStorage);
                            _registry.registryList.set(i, pokeStopData);
                            AnotherPokeStop.getInstance().saveRegistry();
                            break;
                        } else {

                            p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4Invalid Loottable."));
                            AnotherPokeStop.getCurrentEditor().remove(p.getUniqueID());
                            return;
                        }
                    }
                }

                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Pokestop loottable changed to &d" + AnotherPokeStop.getCurrentEditor().get(p.getUniqueID()).get(1)));
                AnotherPokeStop.getCurrentEditor().remove(p.getUniqueID());
                return;
            }
        }

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
            String lootTable = AnotherPokeStop.getRegisteredPokeStops().get(pokeStopId).getLoottable().getLoottable();
            boolean cooldown = Utils.claimable(p, pokeStopId, lootTable);
            if (AnotherPokeStop.getRegisteredPokeStops().containsKey(pokeStopId) && cooldown) {
                AnotherPokeStop.getUsedPokestop().put(p.getUniqueID(), pokeStopId);

                if (_config.rocketSettings.rocketEvent) {
                    int rocketEvent = _config.rocketSettings.rocketChance;
                    int rocketRoll = (int) (100 * Math.random());
                    if (rocketEvent >= rocketRoll) {
                        RocketUtils.genRocketDialogue(pokeStopId, p, lootTable)
                                .open(p);
                        return;
                    }
                }

                List<ItemStack> lootList = Utils.genPokeStopLoot(p,false, lootTable);
                AnotherPokeStop.getCurrentDrops().put(p.getUniqueID(), lootList);
                Utils.dropScreen(_config.menuTexts.header, _config.menuTexts.buttonText, (EntityPlayerMP) p, lootList);

            } else if (AnotherPokeStop.getRegisteredPokeStops().containsKey(pokeStopId)) {
                p.sendMessage(Utils.toText(Placeholders.parseRemainingTimePlacerholder(_config.cooldownText, p, pokeStopId, lootTable)));
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
            if(AnotherPokeStop.getCurrentDrops().get(p.getUniqueID()).get(slotIndex).getItem() == Item.getByNameOrId(_config.commandItem)) {
                MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                server.getCommandManager().executeCommand(server, AnotherPokeStop.getCurrentCommandDrops().get(p.getUniqueID()).get(slotIndex));
                return;
            }
            p.inventory.addItemStackToInventory(AnotherPokeStop.getCurrentDrops().get(p.getUniqueID()).get(slotIndex));
        }
    }

    @SubscribeEvent
    public void onCloseClick(CustomDropsEvent.ClickButton event) {
        EntityPlayerMP p = event.getPlayer();
        if(AnotherPokeStop.getCurrentDrops().containsKey(p.getUniqueID()) && _config.claimRewardsOnClose) {
            for(int i = 0; i < AnotherPokeStop.getCurrentDrops().get(p.getUniqueID()).size(); i++) {
                if(AnotherPokeStop.getCurrentDrops().get(p.getUniqueID()).get(i).getItem() == Item.getByNameOrId(_config.commandItem)) {
                    MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                    server.getCommandManager().executeCommand(server, AnotherPokeStop.getCurrentCommandDrops().get(p.getUniqueID()).get(i));
                } else {
                    p.inventory.addItemStackToInventory(AnotherPokeStop.getCurrentDrops().get(p.getUniqueID()).get(i));
                }
            }
        }
    }

}
