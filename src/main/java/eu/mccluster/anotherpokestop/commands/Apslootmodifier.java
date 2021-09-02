package eu.mccluster.anotherpokestop.commands;

import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class Apslootmodifier extends CommandBase {


    @Override
    public String getName() {
        return "apslootmodifier";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(4, "anotherpokestop.apslootmodifier");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/aoslootmodifier";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        AnotherPokeStop.getNewRegistry().setMultiplier(Double.parseDouble(args[0]));
        AnotherPokeStop.getNewRegistry().save();
        sender.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Loot modifier changed to &d" + args[0] + "&r." ));

    }
}

