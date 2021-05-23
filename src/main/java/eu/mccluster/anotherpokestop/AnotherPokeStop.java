package eu.mccluster.anotherpokestop;

import com.pixelmonmod.pixelmon.Pixelmon;
import eu.mccluster.anotherpokestop.Listener.InteractEntityListener;
import eu.mccluster.anotherpokestop.Listener.PlayerLeftListener;
import eu.mccluster.anotherpokestop.Listener.PokeStopFishingEvent;
import eu.mccluster.anotherpokestop.commands.RemovePokeStop;
import eu.mccluster.anotherpokestop.commands.SetPokeStop;
import eu.mccluster.anotherpokestop.commands.SetPokeStopDummy;
import eu.mccluster.anotherpokestop.commands.elements.RGBElement;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopMainConfig;
import eu.mccluster.anotherpokestop.config.PokeStopRegistry;
import eu.mccluster.anotherpokestop.config.loottables.LootTableStart;
import eu.mccluster.anotherpokestop.objects.PokeStopData;
import eu.mccluster.anotherpokestop.utils.Utils;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AnotherPokeStop {

    @Getter
    private static AnotherPokeStopMainConfig _config;

    @Getter
    private static LootTableStart _lootConfig;

    @Getter
    private static PokeStopRegistry _registry;

    @Getter
    private static ConcurrentHashMap<UUID, List<ItemStack>> _currentDrops = new ConcurrentHashMap<>();

    @Getter
    private static ConcurrentHashMap<UUID, PokeStopData> _registeredPokeStops = new ConcurrentHashMap<>();

    public List<UUID> _currentPokestopRemovers = new ArrayList<>();

    @Getter
    private static AnotherPokeStop _instance;

    public static void load() {
        if(_instance == null) {
            _instance = new AnotherPokeStop();
        }
    }

    public static void enable(){
        _instance.onEnable();
    }

    private void onEnable(){
        _config = new AnotherPokeStopMainConfig(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "AnotherPokeStop.conf"));
        _lootConfig = new LootTableStart(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "Loottable.conf"));
        _registry = new PokeStopRegistry(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "PokestopRegistry.conf"));
        _registry.load();
        _lootConfig.load();
        _config.load();
        registerListeners();
        registerCommands();
        populatePokeStopHashMap();
    }


    private void registerCommands() {
        CommandSpec setPokeStop = CommandSpec.builder()
                .executor(new SetPokeStop(_config.config))
                .permission("anotherpokestop.set")
                .description(Utils.toText("&6Create a new Pokestop"))
                .arguments(GenericArguments.optional(new RGBElement(Text.of("rgb"))))
                .build();
        Sponge.getCommandManager().register(AnotherPokeStopPlugin.getInstance(), setPokeStop, "setpokestop");

        CommandSpec removePokeStop = CommandSpec.builder()
                .executor(new RemovePokeStop(this, _config.config))
                .permission("anotherpokestop.remove")
                .description(Utils.toText("&6Switch to the Remove Mode"))
                .build();
        Sponge.getCommandManager().register(AnotherPokeStopPlugin.getInstance(), removePokeStop, "removepokestop");

        CommandSpec setPokeStopDummy = CommandSpec.builder()
                .executor((new SetPokeStopDummy(_config.config)))
                .permission("anotherpokestop.setdummy")
                .description(Utils.toText("%6Create a new dummy Pokestop"))
                .arguments(GenericArguments.optional(new RGBElement(Text.of("rgb"))))
                .build();
        Sponge.getCommandManager().register(AnotherPokeStopPlugin.getInstance(), setPokeStopDummy, "setpokestopdummy");
    }


    public void saveRegistry() { _registry.save(); }

    private void registerListeners() {
        InteractEntityListener entityListener = new InteractEntityListener(this, _config.config, _registry);
        Sponge.getEventManager().registerListeners(AnotherPokeStopPlugin.getInstance(), new PlayerLeftListener(this));
        Sponge.getEventManager().registerListeners(AnotherPokeStopPlugin.getInstance(), new PokeStopFishingEvent());
        Sponge.getEventManager().registerListeners(AnotherPokeStopPlugin.getInstance(), entityListener);
        Pixelmon.EVENT_BUS.register(entityListener);
    }

    private void populatePokeStopHashMap() {
        _registry.registryList.forEach(pokeStopData -> _registeredPokeStops.put(pokeStopData.getPokeStopUniqueId(), pokeStopData));
    }

}
