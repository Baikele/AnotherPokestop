package eu.mccluster.anotherpokestop;

import com.pixelmonmod.pixelmon.Pixelmon;
import eu.mccluster.anotherpokestop.Listener.*;
import eu.mccluster.anotherpokestop.commands.CommandRegistry;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopMainConfig;
import eu.mccluster.anotherpokestop.config.PokeStopRegistry;
import eu.mccluster.anotherpokestop.config.loottables.LootTableStart;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerBaseConfig;
import eu.mccluster.anotherpokestop.objects.PokeStopData;
import eu.mccluster.anotherpokestop.objects.TrainerObject;
import lombok.Getter;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

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
    private static ConcurrentHashMap<UUID, List<String>> _currentCommandDrops = new ConcurrentHashMap<>();

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

    @Getter
    private static AnotherPokeStop _instance;

    public static void load() {
        if(_instance == null) {
            _instance = new AnotherPokeStop();
        }
    }

    public static void enable(FMLServerStartingEvent event){
        _instance.onEnable(event);
    }

    private void onEnable(FMLServerStartingEvent event) {
        _config = new AnotherPokeStopMainConfig(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "AnotherPokeStop.conf"));
        _lootConfig = new LootTableStart(new File(_lootFolder, "DefaultLootConfig.conf"));
        _registry = new PokeStopRegistry(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "PokestopRegistry.conf"));
        _trainer = new TrainerBaseConfig(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "RocketTrainer.conf"));
        _registry.load();
        _lootConfig.load();
        _config.load();
        _trainer.load();
        registerListeners();
        CommandRegistry.registerCommands(event);
        populatePokeStopHashMap();
        loadLoottables();
    }

    public void onReload() {
        _config.load();
        loadLoottables();
        _trainer.load();
    }



    public void saveRegistry() { _registry.save(); }

    private void registerListeners() {
        InteractEntityListener entityListener = new InteractEntityListener(this, _config.config, _registry);
        MinecraftForge.EVENT_BUS.register(new PlayerLeftListener(this));
        MinecraftForge.EVENT_BUS.register(new PokeStopFishingEvent());
        MinecraftForge.EVENT_BUS.register(entityListener);
        Pixelmon.EVENT_BUS.register(entityListener);
        Pixelmon.EVENT_BUS.register(new BattleEndListener(_config.config));
        Pixelmon.EVENT_BUS.register(new BattleStartListener());
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


    private void populatePokeStopHashMap() {
        _registry.registryList.forEach(pokeStopData -> _registeredPokeStops.put(pokeStopData.getPokeStopUniqueId(), pokeStopData));
    }

}
