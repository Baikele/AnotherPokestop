package eu.mccluster.anotherpokestop.Listener;

import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleEndCause;
import com.pixelmonmod.pixelmon.storage.TrainerPartyStorage;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.utils.Utils;
import lombok.RequiredArgsConstructor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;

import java.util.List;

@RequiredArgsConstructor
public class BattleEndListener {

    private final AnotherPokeStop _instance;
    private final AnotherPokeStopConfig _config;

    @SubscribeEvent
    public void onBattleEnd(BattleEndEvent event) {
        _instance.getCurrentBattles().forEach((playerMP, trainer) -> {
            if(event.bc.equals(trainer.getBattleController())) {
                    event.results.forEach(((battleParticipant, battleResults) -> {
                        if(battleParticipant.getEntity() instanceof EntityPlayerMP) {
                            if(battleResults == BattleResults.VICTORY) {
                                List<ItemStack> lootList = Utils.listToNative(Utils.genPokeStopLoot(true, AnotherPokeStop.getPokestopLoot().get(playerMP)));
                                AnotherPokeStop.getCurrentDrops().put(playerMP.getUniqueID(), lootList);
                                Utils.dropScreen(_config.menuTexts.header, _config.menuTexts.buttonText, playerMP, lootList);
                            }
                        }
                    }));
                }
                despawnNPC(event);
                AnotherPokeStop.getCurrentBattles().remove(playerMP, trainer);
                AnotherPokeStop.getPokestopLoot().remove(playerMP);
            }
        );
    }


    private void despawnNPC(BattleEndEvent event) {
        event.results.forEach(((battleParticipant, battleResults) -> {
            if(battleParticipant.getStorage() instanceof TrainerPartyStorage) {
                battleParticipant.getEntity().setDead();
            }
        }));
    }
}
