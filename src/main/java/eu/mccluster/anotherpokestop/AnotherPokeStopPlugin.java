package eu.mccluster.anotherpokestop;

import eu.mccluster.dependency.deploader.api.DependencyLoader;
import eu.mccluster.dependency.deploader.api.DependencyLoaderApi;
import lombok.Getter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.Logger;

import java.io.*;

@Mod(
        modid = "anotherpokestop",
        name = "AnotherPokeStop",
        version = "@VERSION@",
        acceptableRemoteVersions = "*"
)
public class AnotherPokeStopPlugin {

    private Logger logger;

    @Getter
    private static AnotherPokeStopPlugin _instance;

    @Getter
    private File _dataFolder;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        _dataFolder = new File(event.getModConfigurationDirectory(), "anotherpokestop");
        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        _instance = this;
        initDependencies();
        AnotherPokeStop.load();
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        AnotherPokeStop.enable(event);
        logger.info("AnotherPokeStop is running!");
    }

    private void initDependencies() {
        final DependencyLoaderApi depLoader = DependencyLoader.getInstance(this);
        depLoader.loadDependency("eu.mccluster.dependency:configmanager-dependency:1.1");

    }

}
