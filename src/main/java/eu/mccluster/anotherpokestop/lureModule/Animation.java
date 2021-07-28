package eu.mccluster.anotherpokestop.lureModule;

import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.lureModule.LureModuleConfig;
import eu.mccluster.anotherpokestop.config.mainConfig.AnotherPokeStopConfig;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.server.FMLServerHandler;
import org.lwjgl.Sys;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Animation {

    public static void startAnimation(LureModuleConfig settings, EntityPokestop pokestop) {

        Timer timer = new Timer();
        double width = (pokestop.width / 2) + 0.3;
        double height = pokestop.height / 2;
        final int[] particles = {1};
        AnotherPokeStopConfig config = AnotherPokeStop.getConfig();
        long duration = (config.timePerRotation * 1000L) / config.numberOfParticles;


        WorldServer worldServer = FMLServerHandler.instance().getServer().getWorld(pokestop.dimension);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    if(config.numberOfParticles <= particles[0]) {
                        particles[0] = 1;
                    }

                    if(!AnotherPokeStop.getInstance()._activeLure.contains(pokestop.getUniqueID())) {
                        timer.cancel();
                        timer.purge();

                    } else {
                        double angle = Math.toRadians(((double) particles[0] / config.numberOfParticles) * 360d);
                        double x = width * Math.cos(angle);
                        double z = width * Math.sin(angle);
                        particles[0] = particles[0] + 1;
                        worldServer.spawnParticle(Objects.requireNonNull(EnumParticleTypes.getByName(settings.particleType)), true, pokestop.posX + x, pokestop.posY + height, pokestop.posZ + z, 1, 0, 0, 0, 0.0);
                    }
                }
            }, 0, duration);
        }
    }
