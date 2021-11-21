package eu.pixelunited.anotherpokestop.utils;

import eu.pixelunited.anotherpokestop.AnotherPokeStop;
import eu.pixelunited.anotherpokestop.ConfigManagement;
import eu.pixelunited.anotherpokestop.config.PlayerData;
import eu.pixelunited.anotherpokestop.config.lang.LangConfig;
import eu.pixelunited.anotherpokestop.config.loottables.LootTableStart;
import eu.pixelunited.anotherpokestop.objects.PlayerCooldowns;
import net.minecraft.entity.player.EntityPlayerMP;

import java.io.File;
import java.nio.file.Paths;
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

    public static String parseLureInfos(String text, String lureType, int cost) {
        LangConfig _lang = AnotherPokeStop.getLang();
        String lure;
        switch(lureType.toUpperCase()) {
            case "NORMAL":
                lure = _lang.langTypes.normal;
                break;
            case "FIGHTING":
                lure = _lang.langTypes.fighting;
                break;
            case "FLYING":
                lure = _lang.langTypes.flying;
                break;
            case "POISON":
                lure = _lang.langTypes.poison;
                break;
            case "GROUND":
                lure = _lang.langTypes.ground;
                break;
            case "ROCK":
                lure = _lang.langTypes.rock;
                break;
            case "BUG":
                lure = _lang.langTypes.bug;
                break;
            case "GHOST":
                lure = _lang.langTypes.ghost;
                break;
            case "STEEL":
                lure = _lang.langTypes.steel;
                break;
            case "FIRE":
                lure = _lang.langTypes.fire;
                break;
            case "WATER":
                lure = _lang.langTypes.water;
                break;
            case "GRASS":
                lure = _lang.langTypes.grass;
                break;
            case "ELECTRIC":
                lure = _lang.langTypes.electric;
                break;
            case "PSYCHIC":
                lure = _lang.langTypes.psychic;
                break;
            case "ICE":
                lure = _lang.langTypes.ice;
                break;
            case "DRAGON":
                lure = _lang.langTypes.dragon;
                break;
            case "DARK":
                lure = _lang.langTypes.dark;
                break;
            default:
                lure = _lang.langTypes.fairy;
                break;
        }
        text = text.replaceAll("%type%", lure);
        text = text.replaceAll("%money%", String.valueOf(cost));
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
        Date time = new Date();
        String cooldown;

        PlayerData playerData = ConfigManagement.getInstance().loadConfig(PlayerData.class, Paths.get(AnotherPokeStop.PLAYER_PATH + File.separator + playerID.toString() + ".yml"));
        LootTableStart lootData = ConfigManagement.getInstance().loadConfig(LootTableStart.class, Paths.get(AnotherPokeStop.LOOT_PATH + File.separator + lootTable + ".yml"));

        LangConfig _lang = AnotherPokeStop.getLang();

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
                hour = _lang.langCooldown.pluralHour;
            } else {
                hour = _lang.langCooldown.singularHour;
            }
            if(toMinutes < 58L && toMinutes != 0L) {
                minute = _lang.langCooldown.pluralMinute;
            } else if(toMinutes == 0L) {
                toHours = toHours + 1L;
                cooldownHour = Long.toString(toHours);
                cooldown = cooldownHour + " " + hour;
                text = text.replaceAll("%cooldownpkstop%", cooldown);
                return regex(text);
            } else {
                minute = _lang.langCooldown.singularMinute;
            }
            cooldown = cooldownHour + " " + hour + " " + _lang.langCooldown.and + " " + cooldownMinutes + " " + minute;
            text = text.replaceAll("%cooldownpkstop%", cooldown);
            return regex(text);
        }

        if(toMinutes > 1L) {
            minute = _lang.langCooldown.pluralMinute;
        } else {
            minute = _lang.langCooldown.singularMinute;
        }
        cooldown = Long.toString(configCooldown - toMinutes);
        text = text.replaceAll("%cooldownpkstop%", cooldown + " " + minute);
        return regex(text);
    }


}
