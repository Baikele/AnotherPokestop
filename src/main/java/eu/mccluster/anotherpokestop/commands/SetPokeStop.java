package eu.mccluster.anotherpokestop.commands;

import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.lang.LangConfig;
import eu.mccluster.anotherpokestop.config.mainConfig.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.config.presets.PresetConfig;
import eu.mccluster.anotherpokestop.config.presets.PresetTrainer;
import eu.mccluster.anotherpokestop.objects.PokeStopData;
import eu.mccluster.anotherpokestop.objects.RGBStorage;
import eu.mccluster.anotherpokestop.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.List;


public class SetPokeStop extends CommandBase {

    LangConfig _lang = AnotherPokeStop.getLang();


    @Override
    public String getName() {
        return "setpokestop";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(4, "anotherpokestop.set");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/setpokestop <Preset>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            sender.sendMessage(Utils.toText("[AnotherPokeStop] Hello GlaDOS, get a ingame Player!"));
            return;
        }

        EntityPlayerMP p = (EntityPlayerMP) sender;
        RGBStorage color;
        RGBStorage cooldownColor;
        String loottable;
        List<PresetTrainer> trainer;
        PresetConfig _preset;

        if(args.length > 1) {
            p.sendMessage(Utils.toText("/setpokestop <Preset>"));
            return;
        }

        //Loading settings from preset
        if(args.length == 1) {
            _preset = Utils.getPreset(args[0]);

            if (_preset == null) {
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4Preset not found."));
                return;
            }
        } else {
            _preset = AnotherPokeStop.getPreset();
        }
        color = new RGBStorage(_preset.red, _preset.green, _preset.blue);
        cooldownColor = new RGBStorage(_preset.cooldownRed, _preset.cooldownGreen, _preset.cooldownBlue);

        if(AnotherPokeStop.getInstance()._availableLoottables.contains(_preset.loottable)) {
            loottable = _preset.loottable;
        } else {
            p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4Invalid Loottable, check your configs!"));
            return;
        }

        for(int i = 0; i < _preset.trainerList.size(); i++) {
            if(!AnotherPokeStop.getInstance()._availableTrainer.contains(_preset.trainerList.get(i).trainer)) {
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4Invalid Trainer, check your configs!"));
                return;
            }
        }
            trainer = _preset.trainerList;

        //Creating Pokestop
        World playerWorld = p.getEntityWorld();
        EntityPokestop pokestop = new EntityPokestop(playerWorld, p.posX, p.posY, p.posZ);
        pokestop.setColor(color.getR(), color.getG(), color.getB());
        pokestop.setAlwaysAnimate(true);
        pokestop.setNoGravity(true);
        pokestop.setCubeRange(_preset.cubeRange);
        pokestop.setSize(_preset.pokestopSize);
        playerWorld.spawnEntity(pokestop);
        p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6New Pokestop set."));

        PokeStopData newPokeStopData = new PokeStopData(pokestop.getUniqueID(), color, cooldownColor, playerWorld, p.posX, p.posY, p.posZ, loottable, trainer);
        AnotherPokeStop.getRegisteredPokeStops().put(pokestop.getUniqueID(), newPokeStopData);
        AnotherPokeStop.getRegistry().registryList.add(newPokeStopData);
        AnotherPokeStop.getInstance().saveRegistry();
    }
}

