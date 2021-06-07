package eu.mccluster.anotherpokestop;

import com.pixelmonmod.pixelmon.Pixelmon;
import eu.mccluster.anotherpokestop.Listener.*;
import eu.mccluster.anotherpokestop.commands.*;
import eu.mccluster.anotherpokestop.commands.elements.RGBElement;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopMainConfig;
import eu.mccluster.anotherpokestop.config.PokeStopRegistry;
import eu.mccluster.anotherpokestop.config.loottables.LootTableStart;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerBaseConfig;
import eu.mccluster.anotherpokestop.commands.elements.LoottableElement;
import eu.mccluster.anotherpokestop.objects.PokeStopData;
import eu.mccluster.anotherpokestop.objects.TrainerObject;
import eu.mccluster.anotherpokestop.utils.Utils;
import lombok.Getter;
import net.minecraft.entity.player.EntityPlayerMP;
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
    private static TrainerBaseConfig _trainer;

    @Getter
    private static ConcurrentHashMap<UUID, List<ItemStack>> _currentDrops = new ConcurrentHashMap<>();

    @Getter
    private static ConcurrentHashMap<UUID, PokeStopData> _registeredPokeStops = new ConcurrentHashMap<>();

    @Getter
    private static ConcurrentHashMap<EntityPlayerMP, TrainerObject> _currentBattles = new ConcurrentHashMap<>();

    @Getter
    private static ConcurrentHashMap<EntityPlayerMP, String> _pokestopLoot = new ConcurrentHashMap<>();

    @Getter
    private static ConcurrentHashMap<UUID, UUID> _usedPokestop = new ConcurrentHashMap<>();

    @Getter
    private final String _lootFolder = AnotherPokeStopPlugin.getInstance().getDataFolder() + File.separator + "loottables" + File.separator;

    @Getter
    private final String _playerFolder = AnotherPokeStopPlugin.getInstance().getDataFolder() + File.separator + "playerdata" + File.separator;

    public List<UUID> _currentPokestopRemovers = new ArrayList<>();

    public List<String> _avaiableLoottables = new ArrayList<>();

    public List<String> _playerData = new ArrayList<>();

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

    private void onEnable() {
        _config = new AnotherPokeStopMainConfig(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "AnotherPokeStop.conf"));
        _lootConfig = new LootTableStart(new File(_lootFolder, "DefaultLootConfig.conf"));
        _registry = new PokeStopRegistry(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "PokestopRegistry.conf"));
        _trainer = new TrainerBaseConfig(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "RocketTrainer.conf"));
        _registry.load();
        _lootConfig.load();
        _config.load();
        _trainer.load();
        loadPlayers();
        registerListeners();
        registerCommands();
        populatePokeStopHashMap();
        loadLoottables();
    }

    public void onReload() {
        _config.load();
        loadLoottables();
        _trainer.load();
    }


    private void registerCommands() {
        CommandSpec setPokeStop = CommandSpec.builder()
                .executor(new SetPokeStop(_config.config))
                .permission("anotherpokestop.set")
                .description(Utils.toText("&6Create a new Pokestop"))
                .arguments(
                        GenericArguments.optionalWeak(new RGBElement(Text.of("rgb"))),
                        GenericArguments.optional(new LoottableElement(Text.of("loottable"))))
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
                .description(Utils.toText("&6Create a new dummy Pokestop"))
                .arguments(GenericArguments.optional(new RGBElement(Text.of("rgb"))))
                .build();
        Sponge.getCommandManager().register(AnotherPokeStopPlugin.getInstance(), setPokeStopDummy, "setpokestopdummy");

        CommandSpec apsReload = CommandSpec.builder()
                .executor(new Apsreload())
                .permission("anotherpokestop.reload")
                .description(Utils.toText("&6Reloads every config of the plugin"))
                .build();
        Sponge.getCommandManager().register(AnotherPokeStopPlugin.getInstance(), apsReload, "apsreload");
    }


    public void saveRegistry() { _registry.save(); }

    private void registerListeners() {
        InteractEntityListener entityListener = new InteractEntityListener(this, _config.config, _registry);
        Sponge.getEventManager().registerListeners(AnotherPokeStopPlugin.getInstance(), new PlayerLeftListener(this));
        Sponge.getEventManager().registerListeners(AnotherPokeStopPlugin.getInstance(), new PokeStopFishingEvent());
        Sponge.getEventManager().registerListeners(AnotherPokeStopPlugin.getInstance(), entityListener);
        Pixelmon.EVENT_BUS.register(entityListener);
        Pixelmon.EVENT_BUS.register(new BattleEndListener(this, _config.config));
        Pixelmon.EVENT_BUS.register(new BattleStartListener(this));
    }

    private void loadLoottables() {
        File[] files = new File(_lootFolder).listFiles();

        if (files == null) {
            System.out.println("No Loottables found, skipping loading");
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".conf")) {
                _avaiableLoottables.add(file.getName().replace(".conf", ""));
            }
        }
    }

    private void loadPlayers() {
        File[] files = new File(_playerFolder).listFiles();

        if (files == null) {
            System.out.println("No Playerdata found, skipping loading");
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".conf")) {
                _playerData.add(file.getName().replace(".conf", ""));
            }
        }
    }

    private void populatePokeStopHashMap() {
        _registry.registryList.forEach(pokeStopData -> _registeredPokeStops.put(pokeStopData.getPokeStopUniqueId(), pokeStopData));
    }

}
