package eu.mccluster.anotherpokestop.utils;

import com.pixelmonmod.pixelmon.api.drops.CustomDropScreen;
import com.pixelmonmod.pixelmon.api.enums.EnumPositionTriState;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.AnotherPokeStopPlugin;
import eu.mccluster.anotherpokestop.config.PlayerData;
import eu.mccluster.anotherpokestop.config.loottables.LootTableStart;
import eu.mccluster.anotherpokestop.config.lureModule.LureModuleConfig;
import eu.mccluster.anotherpokestop.config.presets.PresetConfig;
import eu.mccluster.anotherpokestop.config.presets.PresetTrainer;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerBaseConfig;
import eu.mccluster.anotherpokestop.objects.PlayerCooldowns;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
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

    public static TrainerBaseConfig getTrainer(UUID pokestop) {

        String trainer;
        List<PresetTrainer> _presetTrainers = AnotherPokeStop.getRegisteredPokeStops().get(pokestop).getTrainer();
        int trainerSize = _presetTrainers.size();
        int listEntry = -1;

        if(trainerSize > 1) {
            int raritySum = _presetTrainers.stream().mapToInt(presetTrainers -> presetTrainers.rarity).sum();
            int pickedRarity = (int) (raritySum * Math.random());
            for(int t = 0; t < pickedRarity;) {
                listEntry = listEntry + 1;
                t = _presetTrainers.get(listEntry).rarity + t;
            }
            trainer = _presetTrainers.get(listEntry).trainer;
        } else {
            trainer = _presetTrainers.get(0).trainer;
        }

        TrainerBaseConfig configFile = new TrainerBaseConfig(new File(AnotherPokeStop.getInstance().getTrainerFolder(), trainer + ".conf"));
        configFile.load();
        return configFile;
    }

    public static String getLureType(String lure) {

        String configName = lure.replaceAll("pixelmon:lure_", "");
        String cleanedConfigName = configName.replaceAll("_strong", "");
        return capitalizeFirstLetter(cleanedConfigName.trim());
    }

    public static boolean hasPermission(EntityPlayerMP player, String permissionNode) {
        return (PermissionAPI.hasPermission(player, permissionNode) || player.canUseCommand(4, permissionNode));
    }

    public static boolean claimable(EntityPlayerMP player, UUID pokestopID, String lootTable) {

        String playerUUID = player.getUniqueID().toString();
        final String path = AnotherPokeStop.getInstance().getPlayerFolder() + playerUUID + ".conf";
        Date time = new Date();

        //Creates a new file when the player never used a pokestop before
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

        //Scanning through the player file, if no cooldown present set a new entry
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

        //Check if the pokestop is still on cooldown
        long lastVisit = playerData.playerCooldowns.get(index).getDate().getTime();
        long remainingTime = time.getTime() - lastVisit;
        if (TimeUnit.MILLISECONDS.toMinutes(remainingTime) < Placeholders.parseCooldown(lootData.loottable.cooldown)) {
            return false;
        }

        playerData.playerCooldowns.set(index, playerCooldowns);
        playerData.save();
        return true;
    }

    public static PresetConfig getPreset(String presetConfig) {
        if (AnotherPokeStop.getInstance()._availablePresets.contains(presetConfig)) {
            PresetConfig presetData = new PresetConfig(new File(AnotherPokeStop.getInstance().getPresetFolder(), presetConfig + ".conf"));
            presetData.load();
            return presetData;
        }
        return null;
    }
}


