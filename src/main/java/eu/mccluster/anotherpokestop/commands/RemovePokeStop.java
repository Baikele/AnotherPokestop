package eu.mccluster.anotherpokestop.commands;

import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class RemovePokeStop extends CommandBase {

    AnotherPokeStop _plugin = AnotherPokeStop.getInstance();
    AnotherPokeStopConfig _config = AnotherPokeStop.getConfig().config;

    @Override
    public String getName() {
        return "removepokestop";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(4, "anotherpokestop.remove");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/removepokestop";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (!(sender instanceof EntityPlayerMP)) {
            sender.sendMessage(Utils.toText("[AnotherPokeStop] Hello GlaDOS, you´re not a Player!"));
            return;
        }
        EntityPlayerMP p = (EntityPlayerMP) sender;
        if(_plugin._currentPokestopRemovers.contains(p.getUniqueID())) {
            _plugin._currentPokestopRemovers.remove(p.getUniqueID());
            p.sendMessage(Utils.toText(_config.disableRemover));
        } else {
            _plugin._currentPokestopRemovers.add(p.getUniqueID());
            p.sendMessage(Utils.toText(_config.enableRemover));
        }

    }
}
