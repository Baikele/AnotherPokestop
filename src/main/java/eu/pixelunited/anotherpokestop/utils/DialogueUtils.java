package eu.pixelunited.anotherpokestop.utils;

import com.pixelmonmod.pixelmon.api.dialogue.Choice;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import eu.pixelunited.anotherpokestop.AnotherPokeStop;
import eu.pixelunited.anotherpokestop.ConfigManagement;
import eu.pixelunited.anotherpokestop.config.lang.LangConfig;
import eu.pixelunited.anotherpokestop.config.lureModule.LureModuleConfig;
import eu.pixelunited.anotherpokestop.config.lureModule.LurePokeSettings;
import eu.pixelunited.anotherpokestop.config.trainerConfig.TrainerBaseConfig;
import eu.pixelunited.anotherpokestop.lureModule.LureScheduler;
import eu.pixelunited.anotherpokestop.objects.TrainerObject;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.nio.file.Paths;
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

    public static Dialogue genLureDialogue(EntityPokestop pokestop, EntityPlayerMP player, String lureType, ItemStack item, String toggle) {
        LangConfig _lang = AnotherPokeStop.getLang();
        ArrayList<Choice> choices = new ArrayList<>();
        String text;
        LureModuleConfig settings = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(AnotherPokeStop.LURE_PATH + File.separator + lureType + ".yml"));
        int cost;
        List<LurePokeSettings> list = new ArrayList<>();
        if (toggle.equals("Weak")) {
            cost = settings.activationCostWeak;
            list = settings.pokeListWeak;
        } else {
            cost = settings.activationCostStrong;
            list = settings.pokeList;
        }
        if(!AnotherPokeStop.getInstance()._activeLure.contains(pokestop.getUniqueID())) {
            if(EconomyUtils.hasEnoughMoney(player, cost)) {
                text = Placeholders.parseLureInfos(_lang.langDialogue.activateLureText, lureType, cost);
                choices.add(genLureChoice(_lang.langDialogue.startLureModule, player, pokestop, settings, item, toggle, cost, list));
            } else {
                text = Placeholders.parseLureInfos(_lang.langDialogue.notEnoughMoney, lureType, cost);
            }
        } else {
            text = Placeholders.parseLureInfos(_lang.langDialogue.lureActive, lureType, cost);
        }
        choices.add(genLureChoice(_lang.langDialogue.closeMenu, player, pokestop, settings, item, toggle, cost, list));
        return Dialogue.builder()
                .setText(text)
                .setChoices(choices)
                .build();
    }



    public static Choice genLureChoice(String buttonText, EntityPlayerMP player, EntityPokestop pokestop, LureModuleConfig settings, ItemStack item, String toggle, int cost, List<LurePokeSettings> list) {
        LangConfig _lang = AnotherPokeStop.getLang();
        return Choice.builder()
                .setText(buttonText)
                .setHandle( e -> {

                    if(buttonText == _lang.langDialogue.startLureModule) {
                        AnotherPokeStop.getInstance()._activeLure.add(pokestop.getUniqueID());
                        LureScheduler.startScheduler(settings, pokestop, player, toggle, cost, list);
                        player.inventory.clearMatchingItems(item.getItem(), item.getMetadata(), 1, item.getTagCompound());
                    } else if(buttonText == _lang.langDialogue.closeMenu) {
                        //Closes the menu
                    }
                })
                .build();
    }

}

