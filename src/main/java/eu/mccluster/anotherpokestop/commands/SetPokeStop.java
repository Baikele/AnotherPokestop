package eu.mccluster.anotherpokestop.commands;

import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.objects.LoottableStorage;
import eu.mccluster.anotherpokestop.objects.PokeStopData;
import eu.mccluster.anotherpokestop.objects.RGBStorage;
import eu.mccluster.anotherpokestop.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class SetPokeStop extends CommandBase {

    AnotherPokeStopConfig _config = AnotherPokeStop.getConfig().config;


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
        return "/setpokestop <R> <G> <B> <Loottable>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            sender.sendMessage(Utils.toText("[AnotherPokeStop] Hello GlaDOS, get a ingame Player!"));
            return;
        }

        EntityPlayerMP p = (EntityPlayerMP) sender;
        RGBStorage rgbStorage;
        LoottableStorage loottableStorage;

        if(args.length > 4 || args.length == 2) {
            p.sendMessage(Utils.toText("/setpokestop <R> <G> <B> <Loottable>"));
            return;
        }
        if(args.length == 4) {
            int _r = Integer.parseInt(args[0]);
            int _g = Integer.parseInt(args[1]);
            int _b = Integer.parseInt(args[2]);
            String lootTable = args[3];
            if(_r > 255 || _g > 255 || _b > 255) {
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4RGB Limit is 255"));
                return;
            }
            if(_r < 0 || _g < 0 || _b < 0) {
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4RGB Minimum is 0"));
                return;
            }
            rgbStorage = new RGBStorage(_r, _g, _b);

            boolean checklootTable = AnotherPokeStop.getInstance()._avaiableLoottables.contains(lootTable);
            if(checklootTable) {
                loottableStorage = new LoottableStorage(lootTable);
            } else {
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4Loottable not found."));
                return;
            }

        } else if(args.length == 3) {

            int _r = Integer.parseInt(args[0]);
            int _g = Integer.parseInt(args[1]);
            int _b = Integer.parseInt(args[2]);
            if(_r > 255 || _g > 255 || _b > 255) {
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4RGB Limit is 255"));
                return;
            }
            if(_r < 0 || _g < 0 || _b < 0) {
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4RGB Minimum is 0"));
                return;
            }
            rgbStorage = new RGBStorage(_r, _g, _b);
            loottableStorage = new LoottableStorage("DefaultLootConfig");

        } else if(args.length == 1){

            String lootTable = args[0];
            rgbStorage = new RGBStorage(_config.standardColors.red, _config.standardColors.green, _config.standardColors.blue);
            boolean checklootTable = AnotherPokeStop.getInstance()._avaiableLoottables.contains(lootTable);
            if(checklootTable) {
                loottableStorage = new LoottableStorage(lootTable);
            } else {
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4Loottable not found."));
                return;
            }

        } else {
            rgbStorage = new RGBStorage(_config.standardColors.red, _config.standardColors.green, _config.standardColors.blue);
            loottableStorage = new LoottableStorage("DefaultLootConfig");
        }

        World playerWorld = p.getEntityWorld();
        EntityPokestop pokestop = new EntityPokestop(playerWorld, p.posX, p.posY, p.posZ);
        pokestop.setColor(rgbStorage.getR(), rgbStorage.getG(), rgbStorage.getB());
        pokestop.setAlwaysAnimate(true);
        pokestop.setNoGravity(true);
        pokestop.setCubeRange(_config.cubeRange);
        playerWorld.spawnEntity(pokestop);
        p.sendMessage(Utils.toText(_config.setText));

        PokeStopData newPokeStopData = new PokeStopData(pokestop.getUniqueID(), rgbStorage, playerWorld, p.posX, p.posY, p.posZ, loottableStorage);
        AnotherPokeStop.getRegisteredPokeStops().put(pokestop.getUniqueID(), newPokeStopData);
        AnotherPokeStop.getRegistry().registryList.add(newPokeStopData);
        AnotherPokeStop.getInstance().saveRegistry();
    }
}

