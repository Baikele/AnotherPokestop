package eu.mccluster.anotherpokestop.utils;

import com.pixelmonmod.pixelmon.api.dialogue.Choice;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.lang.LangConfig;
import eu.mccluster.anotherpokestop.config.lureModule.LureModuleConfig;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerBaseConfig;
import eu.mccluster.anotherpokestop.lureModule.LureScheduler;
import eu.mccluster.anotherpokestop.objects.TrainerObject;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DialogueUtils {

    public static Dialogue genRocketDialogue(UUID uuid, EntityPlayerMP p, String lootTable) {
        LangConfig _lang = AnotherPokeStop.getLang();
        ArrayList<Choice> choices = new ArrayList<>();
        Choice choice = genRocketReactions(_lang.langDialogue.choiceYes, p, uuid, lootTable);
        choices.add(choice);
        choice = genRocketReactions(_lang.langDialogue.choiceNo, p, uuid, lootTable);
        choices.add(choice);
        return Dialogue.builder()
                .setText(_lang.langDialogue.dialogueText)
                .setChoices(choices)
                .build();
    }


    public static Choice genRocketReactions(String text, EntityPlayerMP p, UUID uuid, String lootTable) {
        LangConfig _lang = AnotherPokeStop.getLang();
        return Choice.builder()
                .setText(text)
                .setHandle( e -> {
                    if (text == _lang.langDialogue.choiceYes) {

                        TrainerBaseConfig rocketTrainer = Utils.getTrainer(uuid);
                        TrainerObject trainerObject = new TrainerObject(e.player, rocketTrainer, lootTable);

                    } else if(text == _lang.langDialogue.choiceNo) {

                        RewardUtils.pokestopLoot(p, false, lootTable);
                        List<ItemStack> lootList = AnotherPokeStop.getCurrenDisplayItems().get(p.getUniqueID());
                        Utils.dropScreen(_lang.langDropMenu.header, _lang.langDropMenu.buttonText, p, lootList);
                    }
                })
                .build();
    }

    public static Dialogue genLureDialogue(EntityPokestop pokestop, EntityPlayerMP player, String lureType, ItemStack item) {
        LangConfig _lang = AnotherPokeStop.getLang();
        ArrayList<Choice> choices = new ArrayList<>();
        String text;
        LureModuleConfig settings = new LureModuleConfig(new File(AnotherPokeStop.getInstance().getLureFolder(), lureType + ".conf"));
        settings.load();
        if(!AnotherPokeStop.getInstance()._activeLure.contains(pokestop.getUniqueID())) {
            if(EconomyUtils.hasEnoughMoney(player, settings.activationCost)) {
                text = Placeholders.parseLureInfos(_lang.langDialogue.activateLureText, lureType, settings.activationCost);
                choices.add(genLureChoice(_lang.langDialogue.startLureModule, player, pokestop, settings, item));
            } else {
                text = Placeholders.parseLureInfos(_lang.langDialogue.notEnoughMoney, lureType, settings.activationCost);
            }
        } else {
            text = Placeholders.parseLureInfos(_lang.langDialogue.lureActive, lureType, settings.activationCost);
        }
        choices.add(genLureChoice(_lang.langDialogue.closeMenu, player, pokestop, settings, item));
        return Dialogue.builder()
                .setText(text)
                .setChoices(choices)
                .build();
    }

    public static Choice genLureChoice(String buttonText, EntityPlayerMP player, EntityPokestop pokestop, LureModuleConfig settings, ItemStack item) {
        LangConfig _lang = AnotherPokeStop.getLang();
        return Choice.builder()
                .setText(buttonText)
                .setHandle( e -> {

                    if(buttonText == _lang.langDialogue.startLureModule) {
                        AnotherPokeStop.getInstance()._activeLure.add(pokestop.getUniqueID());
                        LureScheduler.startScheduler(settings, pokestop, player);
                        player.inventory.clearMatchingItems(item.getItem(), item.getMetadata(), 1, item.getTagCompound());
                    } else if(buttonText == _lang.langDialogue.closeMenu) {
                        //Closes the menu
                    }
                })
                .build();
    }

}

