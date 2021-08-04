package eu.mccluster.anotherpokestop.listeners;

import com.pixelmonmod.pixelmon.api.events.drops.CustomDropsEvent;
import com.pixelmonmod.pixelmon.config.PixelmonItemsLures;
import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.lang.LangConfig;
import eu.mccluster.anotherpokestop.config.mainConfig.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.config.PokeStopRegistry;
import eu.mccluster.anotherpokestop.utils.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class InteractEntityListener {

    final AnotherPokeStop _plugin;
    final AnotherPokeStopConfig _config;
    final LangConfig _lang;
    final PokeStopRegistry _registry;


    public InteractEntityListener(AnotherPokeStop plugin, AnotherPokeStopConfig config, PokeStopRegistry registry, LangConfig lang) {
        this._plugin = plugin;
        _config = config;
        _registry = registry;
        _lang = lang;
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

        EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
        UUID pokeStopId = event.getTarget().getUniqueID();
        EntityPokestop pokestop = (EntityPokestop) event.getTarget();
        Item eventItem = event.getItemStack().getItem();

        if(_config.lureModules) {
            if(eventItem == Item.getByNameOrId("lure_shiny_strong") || eventItem == Item.getByNameOrId("lure_ha_strong")) {
                return;
            }
            if (PixelmonItemsLures.strongLures.contains(eventItem)) {
                int index = PixelmonItemsLures.strongLures.indexOf(eventItem);
                String lureType = Utils.getLureType(Objects.requireNonNull(PixelmonItemsLures.strongLures.get(index).getRegistryName()).toString());
                String toggle = "Strong";
                DialogueUtils.genLureDialogue(pokestop, player, lureType, event.getItemStack(), toggle)
                        .open(player);
                return;
            } else if(PixelmonItemsLures.weakLures.contains(eventItem)) {
                int index = PixelmonItemsLures.weakLures.indexOf(eventItem);
                String lureType = Utils.getLureType(Objects.requireNonNull(PixelmonItemsLures.weakLures.get(index).getRegistryName()).toString());
                String toggle = "Weak";
                DialogueUtils.genLureDialogue(pokestop, player, lureType, event.getItemStack(), toggle)
                        .open(player);
                return;
            }
        }

        if(AnotherPokeStop.getCurrentEditor().containsKey(player.getUniqueID())) {
            if(!AnotherPokeStop.getRegisteredPokeStops().containsKey(pokeStopId)) {
                pokestop.setDead();
                player.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Removed deprecated Pokestop."));
                return;
            }
            for (int i = 0; i <= _registry.registryList.size(); i++) {
                    UUID registeredUUID = _registry.registryList.get(i).getPokeStopUniqueId();
                    if (registeredUUID.equals(pokeStopId)) {
                        EditUtils.editPokestop(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(0), pokestop, player, i);
                        return;
                    }
            }
            return;
        }

        if(!AnotherPokeStop.getRegisteredPokeStops().containsKey(pokeStopId)) {
            return;
        }

        if (Utils.hasPermission(player,"anotherpokestop.claimpokestop")) {
            String lootTable = AnotherPokeStop.getRegisteredPokeStops().get(pokeStopId).getLoottable();
            boolean cooldown = Utils.claimable(player, pokeStopId, lootTable);
            if (cooldown) {
                AnotherPokeStop.getUsedPokestop().put(player.getUniqueID(), pokeStopId);

                if (_config.rocketEvent) {
                    int rocketEvent = _config.rocketChance;
                    int rocketRoll = (int) (100 * Math.random() + 1);
                    if (rocketEvent >= rocketRoll) {
                        DialogueUtils.genRocketDialogue(pokeStopId, player, lootTable)
                                .open(player);
                        return;
                    }
                }

                RewardUtils.pokestopLoot(player, false, lootTable);
                List<ItemStack> lootList = AnotherPokeStop.getCurrenDisplayItems().get(player.getUniqueID());
                Utils.dropScreen(_lang.langDropMenu.header, _lang.langDropMenu.buttonText, player, lootList);

            } else if (AnotherPokeStop.getRegisteredPokeStops().containsKey(pokeStopId)) {
                player.sendMessage(Utils.toText(Placeholders.parseRemainingTimePlacerholder(_lang.langCooldown.cooldownText, player, pokeStopId, lootTable)));
            }
        } else {
            player.sendMessage(Utils.toText(_lang.noClaimPermission));
        }
    }

    @SubscribeEvent
    public void onDropClick(CustomDropsEvent.ClickDrop event) {

        EntityPlayerMP p = event.getPlayer();

        if(AnotherPokeStop.getCurrentDrops().containsKey(p.getUniqueID())) {

            int slotIndex = event.getIndex();

            if(!AnotherPokeStop.getCurrentCommandDrops().get(p.getUniqueID()).get(slotIndex).equals("Placeholder")) {

                MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                server.getCommandManager().executeCommand(server, AnotherPokeStop.getCurrentCommandDrops().get(p.getUniqueID()).get(slotIndex));
            } else {
                p.inventory.addItemStackToInventory(AnotherPokeStop.getCurrentDrops().get(p.getUniqueID()).get(slotIndex));
            }
            AnotherPokeStop.getCurrentLootSize().replace(p.getUniqueID(), AnotherPokeStop.getCurrentLootSize().get(p.getUniqueID()) - 1);

            if(AnotherPokeStop.getCurrentLootSize().get(p.getUniqueID()) == 0) {
                AnotherPokeStop.getCurrentDrops().remove(p.getUniqueID());
                AnotherPokeStop.getCurrentCommandDrops().remove(p.getUniqueID());
                AnotherPokeStop.getCurrenDisplayItems().remove(p.getUniqueID());
                AnotherPokeStop.getCurrentLootSize().remove(p.getUniqueID());
            }
        }
    }


    @SubscribeEvent
    public void onCloseClick(CustomDropsEvent.ClickButton event) {

        EntityPlayerMP p = event.getPlayer();

        if(AnotherPokeStop.getCurrentDrops().containsKey(p.getUniqueID()) && _config.claimRewardsOnClose) {

            for(int i = 0; i < AnotherPokeStop.getCurrentLootSize().get(p.getUniqueID()); i++) {

                if(!AnotherPokeStop.getCurrentCommandDrops().get(p.getUniqueID()).get(i).equals("Placeholder")) {
                    MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                    server.getCommandManager().executeCommand(server, AnotherPokeStop.getCurrentCommandDrops().get(p.getUniqueID()).get(i));
                }
                else {

                    p.inventory.addItemStackToInventory(AnotherPokeStop.getCurrentDrops().get(p.getUniqueID()).get(i));
                }
            }
            AnotherPokeStop.getCurrentDrops().remove(p.getUniqueID());
            AnotherPokeStop.getCurrentCommandDrops().remove(p.getUniqueID());
            AnotherPokeStop.getCurrenDisplayItems().remove(p.getUniqueID());
            AnotherPokeStop.getCurrentLootSize().remove(p.getUniqueID());
        }
    }
}
