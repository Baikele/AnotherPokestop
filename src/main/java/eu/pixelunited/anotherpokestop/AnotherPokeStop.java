package eu.pixelunited.anotherpokestop;

import com.pixelmonmod.pixelmon.Pixelmon;
import eu.pixelunited.anotherpokestop.config.lureModule.LureModuleConfig;
import eu.pixelunited.anotherpokestop.commands.CommandRegistry;
import eu.pixelunited.anotherpokestop.config.PokeStopRegistry;
import eu.pixelunited.anotherpokestop.config.lang.LangConfig;
import eu.pixelunited.anotherpokestop.config.loottables.LootTableStart;
import eu.pixelunited.anotherpokestop.config.mainConfig.AnotherPokeStopConfig;
import eu.pixelunited.anotherpokestop.config.presets.PresetConfig;
import eu.pixelunited.anotherpokestop.config.trainerConfig.TrainerBaseConfig;
import eu.pixelunited.anotherpokestop.listeners.*;
import eu.pixelunited.anotherpokestop.objects.PokeStopData;
import eu.pixelunited.anotherpokestop.objects.TrainerObject;
import lombok.Getter;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AnotherPokeStop {

    public static final Path MAIN_PATH = ConfigManagement.getInstance().getPluginFolder();
    public static final Path PRESET_PATH = Paths.get(MAIN_PATH + File.separator + "presets");
    public static final Path LOOT_PATH = Paths.get(MAIN_PATH + File.separator + "loottables");
    public static final Path PLAYER_PATH = Paths.get(MAIN_PATH + File.separator + "playerdata");
    public static final Path TRAINER_PATH = Paths.get(MAIN_PATH + File.separator + "trainer");
    public static final Path LURE_PATH = Paths.get(MAIN_PATH + File.separator + "lureModules");


    @Getter
    private static AnotherPokeStopConfig _config;
    @Getter
    private static LootTableStart _lootConfig;
    @Getter
    private static PokeStopRegistry _registry;
    @Getter
    private static TrainerBaseConfig _trainer;
    @Getter
    private static LangConfig _lang;
    @Getter
    private static PresetConfig _preset;

    @Getter
    private static LureModuleConfig _normal;
    @Getter
    private static LureModuleConfig _fight;
    @Getter
    private static LureModuleConfig _fly;
    @Getter
    private static LureModuleConfig _poison;
    @Getter
    private static LureModuleConfig _ground;
    @Getter
    private static LureModuleConfig _rock;
    @Getter
    private static LureModuleConfig _bug;
    @Getter
    private static LureModuleConfig _ghost;
    @Getter
    private static LureModuleConfig _steel;
    @Getter
    private static LureModuleConfig _fire;
    @Getter
    private static LureModuleConfig _water;
    @Getter
    private static LureModuleConfig _grass;
    @Getter
    private static LureModuleConfig _electric;
    @Getter
    private static LureModuleConfig _psychic;
    @Getter
    private static LureModuleConfig _ice;
    @Getter
    private static LureModuleConfig _dragon;
    @Getter
    private static LureModuleConfig _dark;
    @Getter
    private static LureModuleConfig _fairy;

    @Getter
    private static ConcurrentHashMap<UUID, List<ItemStack>> _currentDrops = new ConcurrentHashMap<>();
    @Getter
    private static ConcurrentHashMap<UUID, List<String>> _currentCommandDrops = new ConcurrentHashMap<>();
    @Getter
    private static ConcurrentHashMap<UUID, List<ItemStack>> _currenDisplayItems = new ConcurrentHashMap<>();
    @Getter
    private static ConcurrentHashMap<UUID, Integer> _currentLootSize = new ConcurrentHashMap<>();
    @Getter
    private static ConcurrentHashMap<UUID, PokeStopData> _registeredPokeStops = new ConcurrentHashMap<>();
    @Getter
    private static ConcurrentHashMap<EntityPlayerMP, TrainerObject> _currentBattles = new ConcurrentHashMap<>();
    @Getter
    private static ConcurrentHashMap<UUID, UUID> _usedPokestop = new ConcurrentHashMap<>();
    @Getter
    private static ConcurrentHashMap<EntityPlayerMP, String> _pokestopLoot = new ConcurrentHashMap<>();
    @Getter
    private static ConcurrentHashMap<UUID, List<String>> _currentEditor = new ConcurrentHashMap<>();


    public List<UUID> _currentPokestopRemovers = new ArrayList<>();

    public List<String> _availableLoottables = new ArrayList<>();

    public List<String> _availableTrainer = new ArrayList<>();

    public List<String> _availablePresets = new ArrayList<>();

    public List<UUID> _activeLure = new ArrayList<>();


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
        _lootConfig = ConfigManagement.getInstance().loadConfig(LootTableStart.class, Paths.get(LOOT_PATH + File.separator + "DefaultLootConfig.yml"));
        _trainer = ConfigManagement.getInstance().loadConfig(TrainerBaseConfig.class, Paths.get(TRAINER_PATH + File.separator + "DefaultTrainerConfig.yml"));
        _lang = ConfigManagement.getInstance().loadConfig(LangConfig.class, Paths.get(MAIN_PATH + File.separator + "Lang.yml"));
        _preset = ConfigManagement.getInstance().loadConfig(PresetConfig.class, Paths.get(PRESET_PATH + File.separator + "DefaultPreset.yml"));
        _normal = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Normal.yml"));
        _fight = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Fight.yml"));
        _fly = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Fly.yml"));
        _poison = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Poison.yml"));
        _ground = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Ground.yml"));
        _rock = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Rock.yml"));
        _bug = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Bug.yml"));
        _ghost = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Ghost.yml"));
        _steel = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Steel.yml"));
        _fire = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Fire.yml"));
        _water = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Water.yml"));
        _grass = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Grass.yml"));
        _electric = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Electric.yml"));
        _psychic = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Psychic.yml"));
        _ice = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Ice.yml"));
        _dragon = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Dragon.yml"));
        _dark = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Dark.yml"));
        _fairy = ConfigManagement.getInstance().loadConfig(LureModuleConfig.class, Paths.get(LURE_PATH + File.separator + "Fairy.yml"));
        _config = ConfigManagement.getInstance().loadConfig(AnotherPokeStopConfig.class, Paths.get(MAIN_PATH + File.separator + "AnotherPokeStop.yml"));
        _registry = ConfigManagement.getInstance().loadConfig(PokeStopRegistry.class, Paths.get(MAIN_PATH + File.separator + "PokestopRegistry.yml"));
        loadLoottables();
        loadPresets();
        loadTrainer();
        populatePokeStopHashMap();
        registerListeners();
        CommandRegistry.registerCommands(event);
    }

    public void onReload() {
        loadLoottables();
        loadPresets();
        loadTrainer();
        populatePokeStopHashMap();
    }



    public void saveRegistry(PokeStopRegistry registry) {
        ConfigManagement.getInstance().saveConfig(registry, Paths.get(MAIN_PATH + File.separator + "PokestopRegistry.yml"));
        populatePokeStopHashMap();
    }

    private void registerListeners() {
        InteractEntityListener entityListener = new InteractEntityListener(this);
        MinecraftForge.EVENT_BUS.register(new PlayerLeftListener(this));
        MinecraftForge.EVENT_BUS.register(entityListener);
        Pixelmon.EVENT_BUS.register(entityListener);
        Pixelmon.EVENT_BUS.register(new BattleEndListener(_lang));
        Pixelmon.EVENT_BUS.register(new BattleStartListener());
    }

    private void loadLoottables() {
        File[] files = new File(String.valueOf(LOOT_PATH)).listFiles();

        if (files == null) {
            System.out.println("No Loottables found, skipping loading");
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".yml")) {
                _availableLoottables.add(file.getName().replace(".yml", ""));
            }
        }
    }

    private void loadPresets() {
        File[] files = new File(String.valueOf(PRESET_PATH)).listFiles();

        if (files == null) {
            System.out.println("No Presets found, skipping loading");
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".yml")) {
                _availablePresets.add(file.getName().replace(".yml", ""));
            }
        }
    }

    private void loadTrainer() {
        File[] files = new File(String.valueOf(TRAINER_PATH)).listFiles();

        if (files == null) {
            System.out.println("No Trainers found, skipping loading");
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".yml")) {
                _availableTrainer.add(file.getName().replace(".yml", ""));
            }
        }
    }


private void populatePokeStopHashMap() {
        PokeStopRegistry registry = ConfigManagement.getInstance().loadConfig(PokeStopRegistry.class, Paths.get(MAIN_PATH + File.separator + "PokestopRegistry.yml"));
        registry.registryList.forEach(pokeStopData -> _registeredPokeStops.put(pokeStopData.getPokeStopUniqueId(), pokeStopData));
    }

}
