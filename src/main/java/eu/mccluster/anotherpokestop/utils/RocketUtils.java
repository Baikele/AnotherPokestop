package eu.mccluster.anotherpokestop.utils;

import com.pixelmonmod.pixelmon.api.dialogue.Choice;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerBaseConfig;
import eu.mccluster.anotherpokestop.objects.TrainerObject;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RocketUtils {

    public static Dialogue genRocketDialogue(UUID uuid, Player p, String lootTable) {
        AnotherPokeStopConfig _config = AnotherPokeStop.getConfig().config;
        ArrayList<Choice> choices = new ArrayList<>();
        Choice choice = genRocketReactions(_config.rocketSettings.choiceYes, p, uuid, lootTable);
        choices.add(choice);
        choice = genRocketReactions(_config.rocketSettings.choiceNo, p, uuid, lootTable);
        choices.add(choice);
        return Dialogue.builder()
                .setText(_config.rocketSettings.dialogueText)
                .setChoices(choices)
                .build();
    }


    public static Choice genRocketReactions(String text, Player p, UUID uuid, String lootTable) {
        AnotherPokeStopConfig _config = AnotherPokeStop.getConfig().config;
        return Choice.builder()
                .setText(text)
                .setHandle( e -> {
                    if (text == _config.rocketSettings.choiceYes) {

                        TrainerBaseConfig rocketTrainer = Utils.getTrainerByName("RocketTrainer");
                        TrainerObject trainerObject = new TrainerObject(e.player, rocketTrainer, lootTable);

                    } else if(text == _config.rocketSettings.choiceNo) {

                        List<ItemStack> lootList = Utils.listToNative(Utils.genPokeStopLoot(false, lootTable));
                        AnotherPokeStop.getCurrentDrops().put(p.getUniqueId(), lootList);
                        Utils.dropScreen(_config.menuTexts.header, _config.menuTexts.buttonText, (EntityPlayerMP) p, lootList);
                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + p.getName() + " permission settemp anotherpokestop." + uuid.toString() + ".cooldown true " + _config.cooldown + "h");

                    }
                })
                .build();
    }
}

