package eu.mccluster.anotherpokestop.utils;

import com.pixelmonmod.pixelmon.api.drops.CustomDropScreen;
import com.pixelmonmod.pixelmon.api.enums.EnumPositionTriState;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.AnotherPokeStopPlugin;
import eu.mccluster.anotherpokestop.config.PlayerData;
import eu.mccluster.anotherpokestop.config.loottables.LootTableStart;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerBaseConfig;
import eu.mccluster.anotherpokestop.objects.PlayerCooldowns;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.server.permission.PermissionAPI;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static ITextComponent toText(String text) {
        return TextSerializer.parse(text);
    }


    public static ItemStack itemStackFromType(String itemName, int quantity) {
        ItemStack itemStack = GameRegistry.makeItemStack(itemName, 0, quantity, null);
        return itemStack;
    }

    public static void dropScreen(String title, String text, EntityPlayerMP p, List<net.minecraft.item.ItemStack> items) {
        CustomDropScreen.builder()
                .setTitle(Utils.toText(Placeholders.regex(title)))
                .setButtonText(EnumPositionTriState.CENTER, Placeholders.regex(text))
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

    public static boolean hasPermission(EntityPlayerMP player, String permissionNode) {
        return (PermissionAPI.hasPermission(player, permissionNode) || player.canUseCommand(4, permissionNode));
    }

    public static boolean claimable(EntityPlayerMP player, UUID pokestopID, String lootTable) {

        String playerID = player.getUniqueID().toString();
        final String path = AnotherPokeStop.getInstance().getPlayerFolder() + playerID + ".conf";
        Date time = new Date();

        if (Files.notExists(Paths.get(path))) {
            PlayerCooldowns playerCooldowns = new PlayerCooldowns(pokestopID, time);
            PlayerData newPlayerData = new PlayerData(new File(path));
            newPlayerData.load();
            newPlayerData.playerCooldowns.add(playerCooldowns);
            newPlayerData.save();
            return true;
        }

        PlayerData playerData = new PlayerData(new File(path));
        playerData.load();
        LootTableStart lootData = new LootTableStart(new File(AnotherPokeStop.getInstance().getLootFolder(), lootTable + ".conf"));
        lootData.load();

        int entrySum = playerData.playerCooldowns.size();
        int index = 0;
        PlayerCooldowns playerCooldowns = new PlayerCooldowns(pokestopID, time);
        for (int i = 0; i < entrySum; i++) {
            if (playerData.playerCooldowns.get(index).getPokestopID().equals(playerCooldowns.getPokestopID())) {
                break;
            }
            index = index + 1;
            if (index >= entrySum) {
                playerData.playerCooldowns.add(playerCooldowns);
                playerData.save();
                return true;
            }
        }

        long lastVisit = playerData.playerCooldowns.get(index).getDate().getTime();
        long remainingTime = time.getTime() - lastVisit;

        if (TimeUnit.MILLISECONDS.toMinutes(remainingTime) < Placeholders.parseCooldown(lootData.loottable.cooldown)) {
            return false;
        }

        playerData.playerCooldowns.set(index, playerCooldowns);
        playerData.save();
        return true;
    }

    public static LootTableStart getLoottable(String lootTable) {
        if (AnotherPokeStop.getInstance()._avaiableLoottables.contains(lootTable)) {
            LootTableStart lootData = new LootTableStart(new File(AnotherPokeStop.getInstance().getLootFolder(), lootTable + ".conf"));
            lootData.load();
            return lootData;
        }
        return null;
    }

    public static List<ItemStack> genPokeStopLoot(EntityPlayerMP p, Boolean rocket, String lootTable) {

        int raritySum;
        List<ItemStack> outList = new ArrayList<>();
        List<String> outCommandList = new ArrayList<>();
        LootTableStart _loottable = Utils.getLoottable(lootTable);

        if (!rocket) {
            for (int s = 0; s < Objects.requireNonNull(_loottable).loottable.lootSize; s++) {
                outCommandList.add("Placeholder");
            }
            raritySum = _loottable.loottable.lootData.stream().mapToInt(lootTableData -> lootTableData.lootRarity).sum();
            for (int i = 0; i < _loottable.loottable.lootSize; i++) {
                int pickedRarity = (int) (raritySum * Math.random());
                int listEntry = -1;

                for (int b = 0; b <= pickedRarity; ) {
                    listEntry = listEntry + 1;
                    b = _loottable.loottable.lootData.get(listEntry).lootRarity + b;

                }

                if (!_loottable.loottable.lootData.get(listEntry).loot.startsWith("command>")) {
                    ItemStack rewardItem;
                    if (!_loottable.loottable.lootData.get(listEntry).loot.contains("itemname>") || !_loottable.loottable.lootData.get(listEntry).loot.contains("sprite>")) {
                        rewardItem = Utils.itemStackFromType(_loottable.loottable.lootData.get(listEntry).loot, _loottable.loottable.lootData.get(listEntry).lootAmount);
                    } else {
                        String[] itemName = _loottable.loottable.lootData.get(listEntry).loot.split("itemname>");
                        if (itemName[0].contains("sprite>")) {
                            String[] cleanedItem = itemName[0].split("sprite>");
                            rewardItem = Utils.itemStackFromType(cleanedItem[0].trim(), _loottable.loottable.lootData.get(listEntry).lootAmount);
                        } else {
                            rewardItem = Utils.itemStackFromType(itemName[0].trim(), _loottable.loottable.lootData.get(listEntry).lootAmount);
                        }
                        rewardItem.setStackDisplayName(Placeholders.regex(itemName[1]));
                    }
                    outList.add(rewardItem);

                } else {
                    String command;
                    String sprite;
                    ItemStack rewardCommandItem;

                    if (!_loottable.loottable.lootData.get(listEntry).loot.contains("sprite>")) {
                        rewardCommandItem = Utils.itemStackFromType(AnotherPokeStop.getConfig().config.commandItem, 1);
                    } else {
                        String[] splitOne = _loottable.loottable.lootData.get(listEntry).loot.split("itemname>");
                        String[] splitTwo = splitOne[0].split("sprite>");
                        sprite = splitTwo[1].trim();
                        rewardCommandItem = new ItemStack(PixelmonItems.itemPixelmonSprite, 1);
                        NBTTagCompound tagCompound = new NBTTagCompound();
                        rewardCommandItem.setTagCompound(tagCompound);
                        tagCompound.setShort("ndex", Short.parseShort(sprite));
                    }
                    if (!_loottable.loottable.lootData.get(listEntry).loot.contains("itemname>") && !_loottable.loottable.lootData.get(listEntry).loot.contains("sprite>")) {
                        command = Placeholders.parseCommand(Placeholders.parceCustomItemName(Placeholders.parsePlayerPlaceholder(_loottable.loottable.lootData.get(listEntry).loot, p)));
                        rewardCommandItem.setStackDisplayName(command);
                    } else {
                        String[] itemName;
                        String[] commandString;
                        String cleanedCommand;

                        if (_loottable.loottable.lootData.get(listEntry).loot.contains("sprite>")) {
                            commandString = _loottable.loottable.lootData.get(listEntry).loot.split("sprite>");
                            cleanedCommand = Placeholders.parseCommand(Placeholders.parsePlayerPlaceholder(commandString[0], p));
                        } else {
                            cleanedCommand = _loottable.loottable.lootData.get(listEntry).loot;
                        }
                        if (_loottable.loottable.lootData.get(listEntry).loot.contains("itemname>")) {
                            itemName = _loottable.loottable.lootData.get(listEntry).loot.split("itemname>");
                            rewardCommandItem.setStackDisplayName(Placeholders.regex(itemName[1]));

                            commandString = cleanedCommand.split("itemname>");
                            cleanedCommand = Placeholders.parseCommand(Placeholders.parsePlayerPlaceholder(commandString[0], p));
                        } else {
                            rewardCommandItem.setStackDisplayName(cleanedCommand);
                        }
                        command = cleanedCommand.trim();
                    }
                    outList.add(rewardCommandItem);
                    outCommandList.set(i, command);
                }
            }
        } else {
            for(int s = 0; s < Objects.requireNonNull(_loottable).loottable.rocketLootSize; s++) {
                outCommandList.add("Placeholder");
            }
            raritySum = _loottable.loottable.rocketData.stream().mapToInt(rocketTableData -> rocketTableData.lootRarity).sum();
            for (int i = 0; i < _loottable.loottable.rocketLootSize; i++) {
                int pickedRarity = (int) (raritySum * Math.random());
                int listEntry = -1;

                for (int b = 0; b <= pickedRarity; ) {
                    listEntry = listEntry + 1;
                    b = _loottable.loottable.rocketData.get(listEntry).lootRarity + b;

                }

                if(!_loottable.loottable.rocketData.get(listEntry).loot.startsWith("command>")) {
                    ItemStack rewardItem;
                    if(!_loottable.loottable.rocketData.get(listEntry).loot.contains("itemname>") || !_loottable.loottable.rocketData.get(listEntry).loot.contains("sprite>")) {
                        rewardItem = Utils.itemStackFromType(_loottable.loottable.rocketData.get(listEntry).loot, _loottable.loottable.rocketData.get(listEntry).lootAmount);
                    } else {
                        String[] itemName = _loottable.loottable.rocketData.get(listEntry).loot.split("itemname>");
                        if(itemName[0].contains("sprite>")) {
                            String[] cleanedItem = itemName[0].split("sprite>");
                            rewardItem = Utils.itemStackFromType(cleanedItem[0].trim(), _loottable.loottable.rocketData.get(listEntry).lootAmount);
                        } else {
                            rewardItem = Utils.itemStackFromType(itemName[0].trim(), _loottable.loottable.rocketData.get(listEntry).lootAmount);
                        }
                        rewardItem.setStackDisplayName(Placeholders.regex(itemName[1]));
                    }
                    outList.add(rewardItem);

                } else {
                    String command;
                    String sprite;
                    ItemStack rewardCommandItem;

                    if (!_loottable.loottable.rocketData.get(listEntry).loot.contains("sprite>")) {
                        rewardCommandItem = Utils.itemStackFromType(AnotherPokeStop.getConfig().config.commandItem, 1);
                    } else {
                        String[] splitOne = _loottable.loottable.rocketData.get(listEntry).loot.split("itemname>");
                        String[] splitTwo = splitOne[0].split("sprite>");
                        sprite = splitTwo[1].trim();
                        rewardCommandItem = new ItemStack(PixelmonItems.itemPixelmonSprite, 1);
                        NBTTagCompound tagCompound = new NBTTagCompound();
                        rewardCommandItem.setTagCompound(tagCompound);
                        tagCompound.setShort("ndex", Short.parseShort(sprite));
                    }
                    if (!_loottable.loottable.rocketData.get(listEntry).loot.contains("itemname>") && !_loottable.loottable.rocketData.get(listEntry).loot.contains("sprite>")) {
                        command = Placeholders.parseCommand(Placeholders.parceCustomItemName(Placeholders.parsePlayerPlaceholder(_loottable.loottable.rocketData.get(listEntry).loot, p)));
                        rewardCommandItem.setStackDisplayName(command);
                    } else {
                        String[] itemName;
                        String[] commandString;
                        String cleanedCommand;

                        if (_loottable.loottable.rocketData.get(listEntry).loot.contains("sprite>")) {
                            commandString = _loottable.loottable.rocketData.get(listEntry).loot.split("sprite>");
                            cleanedCommand = Placeholders.parseCommand(Placeholders.parsePlayerPlaceholder(commandString[0], p));
                        } else {
                            cleanedCommand = _loottable.loottable.rocketData.get(listEntry).loot;
                        }
                        if (_loottable.loottable.rocketData.get(listEntry).loot.contains("itemname>")) {
                            itemName = _loottable.loottable.rocketData.get(listEntry).loot.split("itemname>");
                            rewardCommandItem.setStackDisplayName(Placeholders.regex(itemName[1]));

                            commandString = cleanedCommand.split("itemname>");
                            cleanedCommand = Placeholders.parseCommand(Placeholders.parsePlayerPlaceholder(commandString[0], p));
                        } else {
                            rewardCommandItem.setStackDisplayName(cleanedCommand);
                        }
                        command = cleanedCommand.trim();
                    }
                    outList.add(rewardCommandItem);
                    outCommandList.set(i, command);
                }
            }
        }
        AnotherPokeStop.getCurrentCommandDrops().put(p.getUniqueID(), outCommandList);
        return outList;
    }
}


