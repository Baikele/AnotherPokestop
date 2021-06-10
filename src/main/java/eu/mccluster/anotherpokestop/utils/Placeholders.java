package eu.mccluster.anotherpokestop.utils;

import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.PlayerData;
import eu.mccluster.anotherpokestop.config.loottables.LootTableStart;
import eu.mccluster.anotherpokestop.objects.PlayerCooldowns;
import net.minecraft.entity.player.EntityPlayerMP;

import java.io.File;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Placeholders {

    private static final String regex = "&(?=[0-9a-ff-or])";
    private static final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    public static String regex(String text) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            text = text.replaceAll(regex, "§");
        }
        return text;
    }

    public static String parsePlayerPlaceholder(String text, EntityPlayerMP player) {
        text = text.replaceAll("%player%", player.getName());
        return regex(text);
    }

    public static long parseCooldown(String cooldown) {
        String cooldownString = cooldown;
        long _cooldown;

        if(cooldownString == null) {
            cooldownString = "24h";
        }
        if(cooldownString.contains("m")) {
            _cooldown = Integer.parseInt(cooldownString.replace("m", ""));
            return _cooldown;
        } else if(cooldownString.contains("h")) {
            _cooldown = Integer.parseInt(cooldownString.replace("h", "")) * 60L;
            return _cooldown;
        } else if(cooldownString.contains("d")) {
            _cooldown = Integer.parseInt(cooldownString.replace("d", "")) * 1440L;
            return _cooldown;
        } else {
            _cooldown = Integer.parseInt(cooldown) * 60L;
        }
        return _cooldown;
    }

    public static String parseRemainingTimePlacerholder(String text, EntityPlayerMP player, UUID pokestopID, String lootTable) {

        String playerID = player.getUniqueID().toString();
        final String path = AnotherPokeStop.getInstance().getPlayerFolder() + playerID + ".conf";
        Date time = new Date();
        String cooldown;

        PlayerData playerData = new PlayerData(new File(path));
        playerData.load();
        LootTableStart lootData = new LootTableStart(new File(AnotherPokeStop.getInstance().getLootFolder(), lootTable + ".conf"));
        lootData.load();

        int index = 0;
        PlayerCooldowns playerCooldowns = new PlayerCooldowns(pokestopID, time);
        int entrySum = playerData.playerCooldowns.size();
        for(int i = 0; i < entrySum; i++) {
            if (playerData.playerCooldowns.get(index).getPokestopID().equals(playerCooldowns.getPokestopID())) {
                break;
            }
            index = index + 1;
        }

        long lastVisit = playerData.playerCooldowns.get(index).getDate().getTime();
        long remainingTime = time.getTime() - lastVisit;
        long configCooldown = Placeholders.parseCooldown(lootData.loottable.cooldown);
        long toMinutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime);
        long toHours = (configCooldown / 60L) - 1L - TimeUnit.MILLISECONDS.toHours(remainingTime);
        String hour = "";
        String minute = "";

        if(toHours > 0L) {
            String cooldownHour = Long.toString(toHours);
            String cooldownMinutes = Long.toString(60L - (toMinutes - (TimeUnit.MILLISECONDS.toHours(remainingTime) * 60L)));

            if(toHours > 1L) {
                hour = AnotherPokeStop.getConfig().config.pluralHour;
            } else {
                hour = AnotherPokeStop.getConfig().config.singularHour;
            }
            if(toMinutes < 58L && toMinutes != 0L) {
                minute = AnotherPokeStop.getConfig().config.pluralMinute;
            } else if(toMinutes == 0L) {
                toHours = toHours + 1L;
                cooldownHour = Long.toString(toHours);
                cooldown = cooldownHour + " " + hour;
                text = text.replaceAll("%cooldownpkstop%", cooldown);
                return regex(text);
            } else {
                minute = AnotherPokeStop.getConfig().config.singularMinute;
            }
            cooldown = cooldownHour + " " + hour + " and " + cooldownMinutes + " " + minute;
            text = text.replaceAll("%cooldownpkstop%", cooldown);
            return regex(text);
        }

        if(toMinutes > 1L) {
            minute = AnotherPokeStop.getConfig().config.pluralMinute;
        } else {
            minute = AnotherPokeStop.getConfig().config.singularMinute;
        }
        cooldown = Long.toString(configCooldown - toMinutes);
        text = text.replaceAll("%cooldownpkstop%", cooldown + " " + minute);
        return regex(text);
    }

    public static String parseCommand(String loot) {
        loot = loot.replaceAll("command>", "");
        return loot;
    }

    public static String parceCustomItemName(String itemname) {
        itemname = itemname.replaceAll("itemname>", "");
        return itemname;
    }

}
