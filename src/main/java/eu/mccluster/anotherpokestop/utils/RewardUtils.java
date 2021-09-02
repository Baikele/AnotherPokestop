package eu.mccluster.anotherpokestop.utils;

import com.pixelmonmod.pixelmon.config.PixelmonItems;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.loottables.LootTableData;
import eu.mccluster.anotherpokestop.config.loottables.LootTableStart;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RewardUtils {

    public static ItemStack displayItemFromName(String itemName, String displayName) {
        int meta = 0;
        ItemStack displayItem = GameRegistry.makeItemStack(itemName, meta, 1, null);
        if(itemName.contains("meta>")) {
            String[] item = itemName.split("meta>");
            itemName = item[0].trim();
            meta = Integer.parseInt(item[1].trim());
            displayItem = GameRegistry.makeItemStack(itemName, meta, 1, null);
        }
        displayItem.setStackDisplayName(displayName);
        return displayItem;
    }

    public static ItemStack makeSpriteItem(String sprite, String displayName) {
        ItemStack spriteItem = new ItemStack(PixelmonItems.itemPixelmonSprite, 1);
        NBTTagCompound tagCompound = new NBTTagCompound();
        spriteItem.setTagCompound(tagCompound);
        String spriteIndex = sprite.replace("sprite>", "");
        if(spriteIndex.contains("f:")) {
            String[] indexes = spriteIndex.split("f:");
            tagCompound.setShort("ndex", Short.parseShort(indexes[0].trim()));
            byte form = Byte.parseByte(indexes[1].trim());
            tagCompound.setByte("form", form);
        } else {
            tagCompound.setShort("ndex", Short.parseShort(spriteIndex));
        }
        spriteItem.setStackDisplayName(displayName);
        return spriteItem;
    }

    public static ItemStack makeShinySpriteItem(String sprite, String displayName) {
        ItemStack spriteItem = new ItemStack(PixelmonItems.itemPixelmonSprite, 1);
        String spriteIndex = sprite.replace("sprite>", "");
        NBTTagCompound tagCompound = new NBTTagCompound();
        spriteItem.setTagCompound(tagCompound);
        if(spriteIndex.contains("f:")) {
            String[] indexes = spriteIndex.split("f:");
            tagCompound.setShort("ndex", Short.parseShort(indexes[0].trim()));
            byte form = Byte.parseByte(indexes[1].trim());
            tagCompound.setByte("form", form);
        } else {
            tagCompound.setShort("ndex", Short.parseShort(spriteIndex));
        }
        byte shiny = 1;
        tagCompound.setByte("Shiny", shiny);
        spriteItem.setStackDisplayName(displayName);
        return spriteItem;
    }

    public static ItemStack makeEggSprite(String sprite, String displayName) {
        //A big thank you to Winglet for helping me with this :)
        //Check out her discord: https://discord.com/invite/wCgnkgE6rf

        ItemStack spriteItem = new ItemStack(PixelmonItems.itemPixelmonSprite);
        NBTTagCompound tagCompound = new NBTTagCompound();
        String eggSprite = sprite.replace("egg>", "").trim();
        String filePath;
        switch(eggSprite.toUpperCase()) {
            case "MANAPHY":
                filePath = "pixelmon:sprites/eggs/manaphy1";
                break;
            case "TOGEPI":
                filePath = "pixelmon:sprites/eggs/togepi1";
                break;
            default:
                filePath = "pixelmon:sprites/eggs/egg1";
        }
        tagCompound.setString("SpriteName", filePath);
        spriteItem.setTagCompound(tagCompound);
        spriteItem.setStackDisplayName(displayName);
        return spriteItem;
    }

    public static ItemStack itemStackFromType(String itemName, int quantity) {
        int meta = 0;
        if(itemName.contains("meta>")) {
            String[] item = itemName.split("meta>");
            itemName = item[0].trim();
            meta = Integer.parseInt(item[1].trim());
        }
        return GameRegistry.makeItemStack(itemName, meta, quantity, null);
    }

    public static LootTableStart getLoottable(String lootTable) {
        if (AnotherPokeStop.getInstance()._availableLoottables.contains(lootTable)) {
            LootTableStart lootData = new LootTableStart(new File(AnotherPokeStop.getInstance().getLootFolder(), lootTable + ".conf"));
            lootData.load();
            return lootData;
        }
        return null;
    }


    public static void pokestopLoot(EntityPlayerMP player, boolean rocket, String lootTable) {

        int raritySum;
        double lootConfigAmount;
        List<ItemStack> outList = new ArrayList<>();
        List<String> outCommandList = new ArrayList<>();
        List<ItemStack> displayList = new ArrayList<>();
        LootTableStart _lootConfig = Objects.requireNonNull(getLoottable(lootTable));
        List<LootTableData> _loot;
        ItemStack placeHolderItem = itemStackFromType("minecraft:paper", 1);

        if(!rocket) {
            _loot = Objects.requireNonNull(getLoottable(lootTable)).loottable.lootData;
            lootConfigAmount = _lootConfig.loottable.lootSize * AnotherPokeStop.getNewRegistry().lootMultiplier;
        } else {
            _loot = Objects.requireNonNull(getLoottable(lootTable)).loottable.rocketData;
            lootConfigAmount = _lootConfig.loottable.rocketLootSize * AnotherPokeStop.getNewRegistry().lootMultiplier;
        }

        int lootAmount = (int) Math.round(lootConfigAmount);

            for (int s = 0; s < lootAmount; s++) {
                outCommandList.add("Placeholder");
                outList.add(placeHolderItem);
            }


            //Calc Loot
                raritySum = _loot.stream().mapToInt(lootTableData -> lootTableData.lootRarity).sum();
                for (int i = 0; i < lootAmount; i++) {
                    int pickedRarity = (int) (raritySum * Math.random());
                    int listEntry = -1;

                    for (int b = 0; b <= pickedRarity; ) {
                        listEntry = listEntry + 1;
                        b = _loot.get(listEntry).lootRarity + b;

                    }

                    //Generates Loot
                    if (_loot.get(listEntry).loot.startsWith("command>")) {
                        String[] command = _loot.get(listEntry).loot.split("command>");
                        String parsedCommand = Placeholders.parsePlayerPlaceholder(command[1].trim(), player);
                        outCommandList.set(i, parsedCommand);
                    } else {
                        ItemStack rewardItem = itemStackFromType(_loot.get(listEntry).loot, _loot.get(listEntry).lootAmount);
                        outList.set(i, rewardItem);
                    }

                    //Generates Display Items
                    if (_loot.get(listEntry).displayItem.startsWith("sprite>")) {
                        String sprite = _loot.get(listEntry).displayItem;
                        String displayName = _loot.get(listEntry).rewardTitle;
                        ItemStack spriteItem = makeSpriteItem(sprite, displayName);
                        displayList.add(spriteItem);
                    } else if (_loot.get(listEntry).displayItem.startsWith("shsprite>")) {
                        String sprite = _loot.get(listEntry).displayItem;
                        String displayName = _loot.get(listEntry).rewardTitle;
                        ItemStack spriteItem = makeShinySpriteItem(sprite, displayName);
                        displayList.add(spriteItem);
                    } else if(_loot.get(listEntry).displayItem.startsWith("egg>")) {
                        String sprite = _loot.get(listEntry).displayItem;
                        String displayName = _loot.get(listEntry).rewardTitle;
                        ItemStack spriteItem = makeEggSprite(sprite, displayName);
                        displayList.add(spriteItem);
                    }
                    else {
                        String item = _loot.get(listEntry).displayItem;
                        String displayName = _loot.get(listEntry).rewardTitle;
                        ItemStack displayItem = displayItemFromName(item, displayName);
                        displayList.add(displayItem);
                    }
                }
        //Put Loot segements in Hashmaps
        AnotherPokeStop.getCurrentDrops().put(player.getUniqueID(), outList);
        AnotherPokeStop.getCurrentCommandDrops().put(player.getUniqueID(), outCommandList);
        AnotherPokeStop.getCurrenDisplayItems().put(player.getUniqueID(), displayList);
        AnotherPokeStop.getCurrentLootSize().put(player.getUniqueID(), lootAmount);
    }
}
