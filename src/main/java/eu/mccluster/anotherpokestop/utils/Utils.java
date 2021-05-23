package eu.mccluster.anotherpokestop.utils;

import com.pixelmonmod.pixelmon.api.drops.CustomDropScreen;
import com.pixelmonmod.pixelmon.api.enums.EnumPositionTriState;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.loottables.LootTableConfig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.util.ArrayList;
import java.util.List;
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

    public static List<net.minecraft.item.ItemStack> listToNative(List<ItemStack> spongeStacks) {
        return spongeStacks.stream().map(ItemStackUtil::toNative).collect(Collectors.toList());
    }

    public static List<ItemStack> genPokeStopLoot() {
        LootTableConfig _loottable = AnotherPokeStop.getLootConfig().loottable;
        int raritySum;
        List<ItemStack> outList = new ArrayList<>();
            raritySum = _loottable.lootData.stream().mapToInt(lootTableData -> lootTableData.lootRarity).sum();
            for (int i = 0; i < AnotherPokeStop.getConfig().config.lootAmount; i++) {
                int pickedRarity = (int) (raritySum * Math.random());
                int listEntry = -1;

                for (int b = 0; b <= pickedRarity; ) {
                    listEntry = listEntry + 1;
                    b = _loottable.lootData.get(listEntry).lootRarity + b;

                }
                ItemStack rewardItem = Utils.itemStackFromType(Sponge.getRegistry().getType(ItemType.class, _loottable.lootData.get(listEntry).lootItem).get(), _loottable.lootData.get(listEntry).lootAmount);
                outList.add(rewardItem);
            }
            return outList;
        }
    }


