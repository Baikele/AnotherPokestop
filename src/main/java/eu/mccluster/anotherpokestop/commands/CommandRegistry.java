package eu.mccluster.anotherpokestop.commands;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandRegistry {

    public static void registerCommands(FMLServerStartingEvent event) {
        event.registerServerCommand(new Apsreload());
        event.registerServerCommand(new RemovePokeStop());
        event.registerServerCommand(new SetPokeStop());
        event.registerServerCommand(new SetPokeStopDummy());
        event.registerServerCommand(new Pokestopedit());
    }
}
