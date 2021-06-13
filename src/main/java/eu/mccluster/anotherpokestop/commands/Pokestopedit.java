package eu.mccluster.anotherpokestop.commands;

import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class Pokestopedit extends CommandBase {

    @Override
    public String getName() {
        return "pokestopedit";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(4, "anotherpokestop.edit");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/pokestopedit <Value to edit> <value/s>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            sender.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4Hello GlaDOS, get a ingame Player!"));
            return;
        }

        EntityPlayerMP p = (EntityPlayerMP) sender;
        if(args.length == 4) {
            String object = args[0];
            String r = args[1];
            String g = args[2];
            String b = args[3];
            if(object.equals("color")) {
                List<String> colors = new ArrayList<>();
                colors.add(object);
                colors.add(r);
                colors.add(g);
                colors.add(b);
                AnotherPokeStop.getCurrentEditor().put(p.getUniqueID(), colors);
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Click on Pokestop to edit."));
            }
        } else if(args.length == 2) {
            String object = args[0];
            String option = args[1];
            if(object.equals("rotation")) {
                List<String> rotations = new ArrayList<>();
                rotations.add(object);
                rotations.add(option);
                AnotherPokeStop.getCurrentEditor().put(p.getUniqueID(), rotations);
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Click on Pokestop to edit."));
            } else if(object.equals("loottable")) {
                List<String> loottable = new ArrayList<>();
                loottable.add(object);
                loottable.add(option);
                AnotherPokeStop.getCurrentEditor().put(p.getUniqueID(), loottable);
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Click on Pokestop to edit."));
            } else if(object.equals("size")) {
                List<String> size = new ArrayList<>();
                size.add(object);
                size.add(option);
                AnotherPokeStop.getCurrentEditor().put(p.getUniqueID(), size);
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Click on Pokestop to edit."));
            }
        } else if(args.length == 1) {
            String object = args[0];
            if(object.equals("position")) {
                List<String> position = new ArrayList<>();
                String x = Double.toString(p.posX);
                String y = Double.toString(p.posY);
                String z = Double.toString(p.posZ);
                position.add(object);
                position.add(x);
                position.add(y);
                position.add(z);
                AnotherPokeStop.getCurrentEditor().put(p.getUniqueID(), position);
                p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &6Position saved. Click on Pokestop to edit."));

            }

        } else {
            p.sendMessage(Utils.toText("[&dAnotherPokeStop&r] &4Invalid arguments!"));
        }
    }
}
