package eu.mccluster.anotherpokestop.utils;

import com.pixelmonmod.pixelmon.api.drops.CustomDropScreen;
import com.pixelmonmod.pixelmon.api.enums.EnumPositionTriState;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.AnotherPokeStopPlugin;
import eu.mccluster.anotherpokestop.config.PlayerData;
import eu.mccluster.anotherpokestop.config.loottables.LootTableStart;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerBaseConfig;
import eu.mccluster.anotherpokestop.objects.PlayerCooldowns;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Utils {

    public Utils() {
    }

    public static Text toText(String text) {
        return TextSerializers.FORMATTING_CODE.deserialize(text);
    }

    public static ItemStack itemStackFromType(ItemType type, int quantity) {
        return ItemStack.builder()
                .itemType(type)
                .quantity(quantity)
                .build();
    }

    public static void dropScreen(String title, String text, EntityPlayerMP p, List<net.minecraft.item.ItemStack> items) {
        CustomDropScreen.builder()
                .setTitle(new TextComponentString(title))
                .setButtonText(EnumPositionTriState.CENTER, text)
                .setItems(items)
                .sendTo(p);
    }

    public static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static TrainerBaseConfig getTrainerByName(String name) {
            TrainerBaseConfig configFile = new TrainerBaseConfig(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), name + ".conf"));
            configFile.load();
            return configFile;
    }

    public static List<net.minecraft.item.ItemStack> listToNative(List<ItemStack> spongeStacks) {
        return spongeStacks.stream().map(ItemStackUtil::toNative).collect(Collectors.toList());
    }

    public static boolean claimable(Player player, UUID pokestopID) {

        String playerID = player.getUniqueId().toString();
        final String path = AnotherPokeStop.getInstance().getPlayerFolder() + playerID + ".conf";
        Date time = new Date();

        if(Files.notExists(Paths.get(path))) {
            PlayerCooldowns playerCooldowns = new PlayerCooldowns(pokestopID, time);
            PlayerData newPlayerData = new PlayerData(new File(AnotherPokeStop.getInstance().getPlayerFolder(), playerID + ".conf"));
            newPlayerData.load();
            newPlayerData.playerCooldowns.add(playerCooldowns);
            newPlayerData.save();
            return true;
        }

        PlayerData playerData = new PlayerData(new File(AnotherPokeStop.getInstance().getPlayerFolder(), playerID + ".conf"));
        playerData.load();

        int entrySum = playerData.playerCooldowns.size();
        int index = 0;
        PlayerCooldowns playerCooldowns = new PlayerCooldowns(pokestopID, time);
        for(int i = 0; i < entrySum; i++) {
            if (playerData.playerCooldowns.get(index).getPokestopID().equals(playerCooldowns.getPokestopID())) {
                break;
            }
            index = index + 1;
            if(index >= entrySum) {
                playerData.playerCooldowns.add(playerCooldowns);
                playerData.save();
                return true;
            }
        }

          long lastVisit = playerData.playerCooldowns.get(index).getDate().getTime();
          long remainingTime = time.getTime() - lastVisit;

          if(TimeUnit.MILLISECONDS.toMinutes(remainingTime) < (AnotherPokeStop.getConfig().config.cooldown * 60L)) {
              return false;
          }

        playerData.playerCooldowns.set(index, playerCooldowns);
        playerData.save();
        return true;
    }

    public static LootTableStart getLoottable(String lootTable) {
        if(AnotherPokeStop.getInstance()._avaiableLoottables.contains(lootTable)) {
                LootTableStart lootData = new LootTableStart(new File(AnotherPokeStop.getInstance().getLootFolder(), lootTable + ".conf"));
                lootData.load();
                return lootData;
        }
        return null;
    }

    public static List<ItemStack> genPokeStopLoot(Boolean rocket, String lootTable) {

        int raritySum;
        List<ItemStack> outList = new ArrayList<>();;
        LootTableStart _loottable = Utils.getLoottable(lootTable);

        if(!rocket) {
            raritySum = _loottable.loottable.lootData.stream().mapToInt(lootTableData -> lootTableData.lootRarity).sum();
            for (int i = 0; i < AnotherPokeStop.getConfig().config.lootAmount; i++) {
                int pickedRarity = (int) (raritySum * Math.random());
                int listEntry = -1;

                for (int b = 0; b <= pickedRarity; ) {
                    listEntry = listEntry + 1;
                    b = _loottable.loottable.lootData.get(listEntry).lootRarity + b;

                }
                ItemStack rewardItem = Utils.itemStackFromType(Sponge.getRegistry().getType(ItemType.class, _loottable.loottable.lootData.get(listEntry).lootItem).get(), _loottable.loottable.lootData.get(listEntry).lootAmount);
                outList.add(rewardItem);
            }
        } else {
            raritySum = _loottable.loottable.rocketData.stream().mapToInt(RocketTableData -> RocketTableData.lootRarity).sum();
            for (int i = 0; i < AnotherPokeStop.getConfig().config.rocketSettings.rocketAmount; i++) {
                int pickedRarity = (int) (raritySum * Math.random());
                int listEntry = -1;

                for (int b = 0; b <= pickedRarity; ) {
                    listEntry = listEntry + 1;
                    b = _loottable.loottable.rocketData.get(listEntry).lootRarity + b;

                }
                ItemStack rewardItem = Utils.itemStackFromType(Sponge.getRegistry().getType(ItemType.class, _loottable.loottable.rocketData.get(listEntry).lootItem).get(), _loottable.loottable.rocketData.get(listEntry).lootAmount);
                outList.add(rewardItem);
            }

        }
        return outList;
    }

    }


