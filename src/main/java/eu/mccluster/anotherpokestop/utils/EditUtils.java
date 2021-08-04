package eu.mccluster.anotherpokestop.utils;

import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.PokeStopRegistry;
import eu.mccluster.anotherpokestop.config.presets.PresetConfig;
import eu.mccluster.anotherpokestop.objects.PokeStopData;
import eu.mccluster.anotherpokestop.objects.RGBStorage;
import net.minecraft.entity.player.EntityPlayerMP;


public class EditUtils {

    public static void editPokestop(String edit, EntityPokestop pokestop, EntityPlayerMP player, int index) {

        PokeStopRegistry _registry = AnotherPokeStop.getNewRegistry();

        switch(edit.toUpperCase()) {

            case "REMOVE":
                    _registry.registryList.remove(index);
                    AnotherPokeStop.getInstance().saveRegistry();
                    AnotherPokeStop.getRegisteredPokeStops().remove(pokestop.getUniqueID());
                    pokestop.setDead();
                    player.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Pokestop removed."));
                AnotherPokeStop.getCurrentEditor().remove(player.getUniqueID());
                break;

            case "ROTATION":
                float rotation = (float) Integer.parseInt(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(1));
                pokestop.setPositionAndRotation(pokestop.posX, pokestop.posY, pokestop.posZ, rotation, pokestop.rotationPitch);
                player.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Pokestop rotation changed to &d" + rotation + " &6degree."));
                AnotherPokeStop.getCurrentEditor().remove(player.getUniqueID());
                break;

            case "SIZE":
                float size = Float.parseFloat(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(1));
                pokestop.setSize(size);
                player.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Changed size to &d" + size + "&6."));
                AnotherPokeStop.getCurrentEditor().remove(player.getUniqueID());
                break;

            case "POSITION":
                double x = Double.parseDouble(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(1));
                double y = Double.parseDouble(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(2));
                double z = Double.parseDouble(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(3));
                RGBStorage rgbStorage = new RGBStorage(_registry.registryList.get(index).getColor().getR(), _registry.registryList.get(index).getColor().getG(), _registry.registryList.get(index).getColor().getB());
                pokestop.setPositionAndRotation(x, y ,z, pokestop.rotationYaw, pokestop.rotationPitch);
                PokeStopData pokeStopData = new PokeStopData(pokestop.getUniqueID(), _registry.registryList.get(index).getVersion(), rgbStorage, _registry.registryList.get(index).getWorld(), x, y, z, _registry.registryList.get(index).getLoottable(), _registry.registryList.get(index).getTrainer());
                _registry.registryList.set(index, pokeStopData);
                AnotherPokeStop.getInstance().saveRegistry();
                player.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Moved pokestop to saved location."));

                AnotherPokeStop.getCurrentEditor().remove(player.getUniqueID());
                break;

            case "LOOTTABLE":
                boolean checklootTable = AnotherPokeStop.getInstance()._availableLoottables.contains(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(1));
                if(checklootTable) {
                    String lootTable = AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(1);
                    rgbStorage = new RGBStorage(_registry.registryList.get(index).getColor().getR(), _registry.registryList.get(index).getColor().getG(), _registry.registryList.get(index).getColor().getB());
                    pokeStopData = new PokeStopData(pokestop.getUniqueID(), _registry.registryList.get(index).getVersion(), rgbStorage, _registry.registryList.get(index).getWorld(), pokestop.posX, pokestop.posY, pokestop.posZ, lootTable, _registry.registryList.get(index).getTrainer());
                    _registry.registryList.set(index, pokeStopData);
                    AnotherPokeStop.getInstance().saveRegistry();
                } else {
                    player.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4Invalid Loottable."));
                    AnotherPokeStop.getCurrentEditor().remove(player.getUniqueID());
                    return;
                }
                player.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Pokestop loottable changed to &d" + AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(1)));
                AnotherPokeStop.getCurrentEditor().remove(player.getUniqueID());
                break;

            case "COLOR":
                int r = Integer.parseInt(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(1));
                int g = Integer.parseInt(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(2));
                int b = Integer.parseInt(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(3));
                if(r > 255 || g > 255 || b > 255) {
                    player.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4RGB Limit is 255"));
                    AnotherPokeStop.getCurrentEditor().remove(player.getUniqueID());
                    return;
                }
                if(r < 0 || g < 0 || b < 0) {
                    player.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4RGB Minimum is 0"));
                    AnotherPokeStop.getCurrentEditor().remove(player.getUniqueID());
                    return;
                }
                pokestop.setColor(r, g, b);

                rgbStorage = new RGBStorage(r, g, b);
                pokeStopData = new PokeStopData(pokestop.getUniqueID(), _registry.registryList.get(index).getVersion(), rgbStorage, _registry.registryList.get(index).getWorld(), pokestop.posX, pokestop.posY, pokestop.posZ, _registry.registryList.get(index).getLoottable(), _registry.registryList.get(index).getTrainer());
                _registry.registryList.set(index, pokeStopData);
                AnotherPokeStop.getInstance().saveRegistry();
            AnotherPokeStop.getCurrentEditor().remove(player.getUniqueID());
            player.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Pokestop color changed to &c" + r + "&2 " + g + "&9 " + b + "."));
            break;
            case "CUBE":
                pokestop.setCubeRange(Integer.parseInt(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(0)));
                break;

            case "PRESET":
                PresetConfig presetConfig = Utils.getPreset(AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(1));
                presetConfig.load();
                rgbStorage = new RGBStorage(presetConfig.red, presetConfig.green, presetConfig.blue);
                pokestop.setCubeRange(presetConfig.cubeRange);
                pokestop.setSize(presetConfig.pokestopSize);
                pokestop.setColor(presetConfig.red, presetConfig.green, presetConfig.blue);
                pokeStopData = new PokeStopData(pokestop.getUniqueID(), _registry.registryList.get(index).getVersion(), rgbStorage, _registry.registryList.get(index).getWorld(), pokestop.posX, pokestop.posY, pokestop.posZ, presetConfig.loottable, presetConfig.trainerList);
                _registry.registryList.set(index, pokeStopData);
                AnotherPokeStop.getInstance().saveRegistry();
                player.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Changed Preset to: " + AnotherPokeStop.getCurrentEditor().get(player.getUniqueID()).get(1)));
                AnotherPokeStop.getCurrentEditor().remove(player.getUniqueID());
        }

    }
}
