package eu.pixelunited.anotherpokestop.commands;

import eu.pixelunited.anotherpokestop.AnotherPokeStop;
import eu.pixelunited.anotherpokestop.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class RemovePokeStop extends CommandBase {

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
        List<String> remover = new ArrayList<>();
        remover.add("remove");
        AnotherPokeStop.getCurrentEditor().put(p.getUniqueID(), remover);
        p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4Click Pokestop to remove."));
    }

}
