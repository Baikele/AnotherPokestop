package eu.mccluster.anotherpokestop;

import com.pixelmonmod.pixelmon.Pixelmon;
import eu.mccluster.anotherpokestop.config.OldPokeStopRegistry;
import eu.mccluster.anotherpokestop.config.lureModule.LureModuleConfig;
import eu.mccluster.anotherpokestop.listeners.*;
import eu.mccluster.anotherpokestop.commands.CommandRegistry;
import eu.mccluster.anotherpokestop.config.PokeStopRegistry;
import eu.mccluster.anotherpokestop.config.lang.LangConfig;
import eu.mccluster.anotherpokestop.config.loottables.LootTableStart;
import eu.mccluster.anotherpokestop.config.mainConfig.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.config.presets.PresetConfig;
import eu.mccluster.anotherpokestop.config.trainerConfig.TrainerBaseConfig;
import eu.mccluster.anotherpokestop.objects.*;
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
    private static AnotherPokeStopConfig _config;
    @Getter
    private static LootTableStart _lootConfig;
    @Getter
    private static PokeStopRegistry _newRegistry;
    @Getter
    private static OldPokeStopRegistry _oldRegistry;
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
    @Getter
    private final String _lootFolder = AnotherPokeStopPlugin.getInstance().getDataFolder() + File.separator + "loottables" + File.separator;
    @Getter
    private final String _playerFolder = AnotherPokeStopPlugin.getInstance().getDataFolder() + File.separator + "playerdata" + File.separator;
    @Getter
    private final String _presetFolder = AnotherPokeStopPlugin.getInstance().getDataFolder() + File.separator + "presets" + File.separator;
    @Getter
    private final String _trainerFolder = AnotherPokeStopPlugin.getInstance().getDataFolder() + File.separator + "trainer" + File.separator;
    @Getter
    private final String _lureFolder = AnotherPokeStopPlugin.getInstance().getDataFolder() + File.separator + "lureModules" + File.separator;

    public List<UUID> _currentPokestopRemovers = new ArrayList<>();

    public List<String> _availableLoottables = new ArrayList<>();

    public List<String> _availableTrainer = new ArrayList<>();

    public List<String> _availablePresets = new ArrayList<>();

    public List<UUID> _activeLure = new ArrayList<>();

    public List<OldPokeStopData> _oldData = new ArrayList<>();

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
        _lootConfig = new LootTableStart(new File(_lootFolder, "DefaultLootConfig.conf"));
        _trainer = new TrainerBaseConfig(new File(_trainerFolder, "DefaultTrainerConfig.conf"));
        _lang = new LangConfig(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "Lang.conf"));
        _preset = new PresetConfig(new File(_presetFolder, "DefaultPreset.conf"));
        _normal = new LureModuleConfig(new File(_lureFolder, "Normal.conf"));
        _fight = new LureModuleConfig(new File(_lureFolder, "Fighting.conf"));
        _fly = new LureModuleConfig(new File(_lureFolder, "Flying.conf"));
        _poison = new LureModuleConfig(new File(_lureFolder, "Poison.conf"));
        _ground = new LureModuleConfig(new File(_lureFolder, "Ground.conf"));
        _rock = new LureModuleConfig(new File(_lureFolder, "Rock.conf"));
        _bug = new LureModuleConfig(new File(_lureFolder, "Bug.conf"));
        _ghost = new LureModuleConfig(new File(_lureFolder, "Ghost.conf"));
        _steel = new LureModuleConfig(new File(_lureFolder, "Steel.conf"));
        _fire = new LureModuleConfig(new File(_lureFolder, "Fire.conf"));
        _water = new LureModuleConfig(new File(_lureFolder, "Water.conf"));
        _grass = new LureModuleConfig(new File(_lureFolder, "Grass.conf"));
        _electric = new LureModuleConfig(new File(_lureFolder, "Electric.conf"));
        _psychic = new LureModuleConfig(new File(_lureFolder, "Psychic.conf"));
        _ice = new LureModuleConfig(new File(_lureFolder, "Ice.conf"));
        _dragon = new LureModuleConfig(new File(_lureFolder, "Dragon.conf"));
        _dark = new LureModuleConfig(new File(_lureFolder, "Dark.conf"));
        _fairy = new LureModuleConfig(new File(_lureFolder, "Fairy.conf"));
        _normal.load();
        _fight.load();
        _fly.load();
        _poison.load();
        _ground.load();
        _rock.load();
        _bug.load();
        _ghost.load();
        _steel.load();
        _fire.load();
        _water.load();
        _grass.load();
        _electric.load();
        _psychic.load();
        _ice.load();
        _dragon.load();
        _dark.load();
        _fairy.load();
        _lootConfig.load();
        _preset.load();
        _trainer.load();
        _lang.load();
        _config = new AnotherPokeStopConfig(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "AnotherPokeStop.conf"));
        _config.load();


        if(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "PokestopRegistry.conf").exists() && !_config.version.equals("2.0.0")) {
            _oldRegistry = new OldPokeStopRegistry(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "PokestopRegistry.conf"));
            _oldRegistry.load();
            migratePokestops();
            _config.setVersion();
            _config.save();
        } else {
            _newRegistry = new PokeStopRegistry(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "PokestopRegistry.conf"));
            _newRegistry.load();
        }

        loadLoottables();
        loadPresets();
        loadTrainer();
        populatePokeStopHashMap();
        registerListeners();
        CommandRegistry.registerCommands(event);
    }

    public void onReload() {
        _newRegistry.load();
        _config.load();
        _lootConfig.load();
        _preset.load();
        _trainer.load();
        _lang.load();
        _ghost.load();
        _normal.load();
        _fight.load();
        _fly.load();
        _poison.load();
        _ground.load();
        _rock.load();
        _bug.load();
        _ghost.load();
        _steel.load();
        _fire.load();
        _water.load();
        _grass.load();
        _electric.load();
        _psychic.load();
        _ice.load();
        _dragon.load();
        _dark.load();
        _fairy.load();
        loadLoottables();
        loadPresets();
        loadTrainer();
    }



    public void saveRegistry() {
        _newRegistry.save();
        populatePokeStopHashMap();
    }

    private void registerListeners() {
        InteractEntityListener entityListener = new InteractEntityListener(this, _config, _newRegistry, _lang);
        MinecraftForge.EVENT_BUS.register(new PlayerLeftListener(this));
        MinecraftForge.EVENT_BUS.register(new PokeStopFishingEvent());
        MinecraftForge.EVENT_BUS.register(entityListener);
        Pixelmon.EVENT_BUS.register(entityListener);
        Pixelmon.EVENT_BUS.register(new BattleEndListener(_lang));
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
                _availableLoottables.add(file.getName().replace(".conf", ""));
            }
        }
    }

    private void loadPresets() {
        File[] files = new File(_presetFolder).listFiles();

        if (files == null) {
            System.out.println("No Presets found, skipping loading");
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".conf")) {
                _availablePresets.add(file.getName().replace(".conf", ""));
            }
        }
    }

    private void loadTrainer() {
        File[] files = new File(_trainerFolder).listFiles();

        if (files == null) {
            System.out.println("No Trainers found, skipping loading");
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".conf")) {
                _availableTrainer.add(file.getName().replace(".conf", ""));
            }
        }
    }

    private void migratePokestops() {
        _oldData.addAll(_oldRegistry.registryList);
        boolean deleted = _oldRegistry.getFile().delete();
        if(deleted) {
            _newRegistry = new PokeStopRegistry(new File(AnotherPokeStopPlugin.getInstance().getDataFolder(), "PokestopRegistry.conf"));
            _newRegistry.load();
            int version = 2;
            for (OldPokeStopData oldAPS : _oldData) {
                PokeStopData newData = new PokeStopData(oldAPS.getPokeStopUniqueId(), version, oldAPS.getColor(), oldAPS.getWorld(), oldAPS.getPosX(), oldAPS.getPosY(), oldAPS.getPosZ(), oldAPS.getLoottable().getLoottable(), _preset.trainerList);
                _newRegistry.registryList.add(newData);
                _newRegistry.save();
            }
            _config.save();
            System.out.println("Migration done!");
        }
    }

private void populatePokeStopHashMap() {
        _newRegistry.registryList.forEach(pokeStopData -> _registeredPokeStops.put(pokeStopData.getPokeStopUniqueId(), pokeStopData));
    }

}
