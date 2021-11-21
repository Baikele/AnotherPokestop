package eu.pixelunited.anotherpokestop.commands;

import eu.pixelunited.anotherpokestop.AnotherPokeStop;
import eu.pixelunited.anotherpokestop.ConfigManagement;
import eu.pixelunited.anotherpokestop.config.PokeStopRegistry;
import eu.pixelunited.anotherpokestop.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.nio.file.Paths;

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
        AnotherPokeStop.getRegistry().lootMultiplier = Double.parseDouble(args[0]);
        ConfigManagement.getInstance().loadConfig(PokeStopRegistry.class, Paths.get(AnotherPokeStop.MAIN_PATH + File.separator + "PokestopRegistry.yml"));
        sender.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Loot modifier changed to &d" + args[0] + "&r." ));

    }
}

