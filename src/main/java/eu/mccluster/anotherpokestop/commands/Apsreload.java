package eu.mccluster.anotherpokestop.commands;

import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class Apsreload implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        AnotherPokeStop.getInstance().onReload();
        src.sendMessage(Utils.toText("&asuccessfully reloaded AnotherPokeStop."));
        return CommandResult.success();
    }
}

