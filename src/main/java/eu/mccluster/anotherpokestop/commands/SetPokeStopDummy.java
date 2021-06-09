package eu.mccluster.anotherpokestop.commands;

import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.objects.RGBStorage;
import eu.mccluster.anotherpokestop.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SetPokeStopDummy extends CommandBase {

    AnotherPokeStopConfig _config = AnotherPokeStop.getConfig().config;

    @Override
    public String getName() {
        return "setpokestopdummy";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(4, "anotherpokestop.setdummy");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/setpokestopdummy";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            sender.sendMessage(Utils.toText("[AnotherPokeStop] Hello GlaDOS, get a ingame Player!"));
            return;
        }

        EntityPlayerMP p = (EntityPlayerMP) sender;
        RGBStorage rgbStorage;
        if(args.length == 3) {

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
        } else {
            rgbStorage = new RGBStorage(_config.standardColors.red, _config.standardColors.green, _config.standardColors.blue);
        }

        BlockPos playerPos = p.getPosition();
        World playerWorld = p.getEntityWorld();
        EntityPokestop pokestop = new EntityPokestop(playerWorld, playerPos.getX(), playerPos.getY(), playerPos.getZ());
        pokestop.setColor(rgbStorage.getR(), rgbStorage.getG(), rgbStorage.getB());
        pokestop.setAlwaysAnimate(true);
        pokestop.setNoGravity(true);
        pokestop.setCubeRange(_config.cubeRange);
        playerWorld.spawnEntity(pokestop);
        p.sendMessage(Utils.toText(_config.setText));

    }
}
