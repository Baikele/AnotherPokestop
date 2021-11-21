package eu.pixelunited.anotherpokestop;

import lombok.Getter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import java.io.*;

@Mod(
        modid = "anotherpokestop",
        name = "AnotherPokeStop",
        version = "@VERSION@",
        acceptableRemoteVersions = "*"
)
public class AnotherPokeStopPlugin {

    private static Logger logger;

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
        AnotherPokeStop.load();
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        AnotherPokeStop.enable(event);
        logger.info("AnotherPokeStop is running!");
    }

}
