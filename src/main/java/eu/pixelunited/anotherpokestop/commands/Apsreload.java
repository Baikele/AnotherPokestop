package eu.pixelunited.anotherpokestop.commands;

import eu.pixelunited.anotherpokestop.AnotherPokeStop;
import eu.pixelunited.anotherpokestop.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class Apsreload extends CommandBase {


    @Override
    public String getName() {
        return "apsreload";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(4, "anotherpokestop.reload");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/apsreload";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        AnotherPokeStop.getInstance().onReload();
        sender.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4Successfully reloaded AnotherPokeStop."));

    }
}

