package eu.pixelunited.anotherpokestop.listeners;

import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleEndCause;
import com.pixelmonmod.pixelmon.storage.TrainerPartyStorage;
import eu.pixelunited.anotherpokestop.AnotherPokeStop;
import eu.pixelunited.anotherpokestop.config.lang.LangConfig;
import eu.pixelunited.anotherpokestop.objects.TrainerObject;
import eu.pixelunited.anotherpokestop.utils.Placeholders;
import eu.pixelunited.anotherpokestop.utils.RewardUtils;
import eu.pixelunited.anotherpokestop.utils.Utils;
import lombok.RequiredArgsConstructor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RequiredArgsConstructor
public class BattleEndListener {

    private final LangConfig _lang;

    @SubscribeEvent
    public void onBattleEnd(BattleEndEvent event) {

        AnotherPokeStop.getCurrentBattles().forEach((player, trainer) -> {

            if(event.bc.equals(trainer.getBattleController())) {
                if (event.cause == EnumBattleEndCause.FLEE) {
                    loseCommands(trainer, player);
                } else if (event.cause == EnumBattleEndCause.FORFEIT) {
                    loseCommands(trainer, player);
                } else if (event.cause == EnumBattleEndCause.FORCE) {
                    loseCommands(trainer, player);
                } else {

                    event.results.forEach(((battleParticipant, battleResults) -> {

                        if (battleParticipant.getEntity() instanceof EntityPlayerMP) {

                            if (battleResults == BattleResults.VICTORY) {

                                RewardUtils.pokestopLoot(player, true, AnotherPokeStop.getPokestopLoot().get(player));
                                player.sendMessage(Utils.toText(Placeholders.regex(_lang.winText)));


                                if (AnotherPokeStop.getCurrentDrops().containsKey(player.getUniqueID())) {

                                    for (int i = 0; i < AnotherPokeStop.getCurrentLootSize().get(player.getUniqueID()); i++) {

                                        if (!AnotherPokeStop.getCurrentCommandDrops().get(player.getUniqueID()).get(i).equals("Placeholder")) {

                                            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                                            server.getCommandManager().executeCommand(server, AnotherPokeStop.getCurrentCommandDrops().get(player.getUniqueID()).get(i));
                                        } else {

                                            player.inventory.addItemStackToInventory(AnotherPokeStop.getCurrentDrops().get(player.getUniqueID()).get(i));
                                        }
                                    }
                                }
                            } else {
                                loseCommands(trainer, player);
                            }
                        }
                    }));
                }
            }
                despawnNPC(event);
                AnotherPokeStop.getCurrentBattles().remove(player, trainer);
                AnotherPokeStop.getPokestopLoot().remove(player);
            }
        );
    }

    private void loseCommands(TrainerObject trainerObject, EntityPlayerMP player) {
        for(int i = 0; i < trainerObject.getConfig().loseCommands.size(); i++) {
            Utils.executeFromConsole(trainerObject.getConfig().loseCommands.get(i), player);
        }
    }

    private void despawnNPC(BattleEndEvent event) {
        event.results.forEach(((battleParticipant, battleResults) -> {
            if(battleParticipant.getStorage() instanceof TrainerPartyStorage) {
                battleParticipant.getEntity().setDead();
            }
        }));
    }
}
