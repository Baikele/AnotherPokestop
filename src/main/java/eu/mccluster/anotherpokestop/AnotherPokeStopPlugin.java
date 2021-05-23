package eu.mccluster.anotherpokestop;

import com.google.inject.Inject;
import eu.mccluster.dependency.deploader.api.DependencyLoader;
import eu.mccluster.dependency.deploader.api.DependencyLoaderApi;
import lombok.Getter;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import java.io.*;

@Plugin(
        id = "anotherpokestop",
        name = "AnotherPokeStop",
        dependencies = {
                @Dependency( id = "pixelmon" )
        }
)
public class AnotherPokeStopPlugin {

    @Inject
    private Logger logger;

    @Getter
    private static AnotherPokeStopPlugin _instance;

    @Getter
    @Inject
    @ConfigDir(sharedRoot = false)
    private File _dataFolder;

    @Listener
    public void load(GameInitializationEvent event) {
        _instance = this;
        initDependencies();
        AnotherPokeStop.load();
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event){
        AnotherPokeStop.enable();
        logger.info("AnotherPokeStop is running!");
    }

    private void initDependencies() {
        final DependencyLoaderApi depLoader = DependencyLoader.getInstance(this);
        depLoader.loadDependency("eu.mccluster.dependency:configmanager-dependency:1.1");

    }

}
