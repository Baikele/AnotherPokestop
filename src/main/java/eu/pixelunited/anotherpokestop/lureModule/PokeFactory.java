package eu.pixelunited.anotherpokestop.lureModule;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import eu.pixelunited.anotherpokestop.AnotherPokeStop;
import eu.pixelunited.anotherpokestop.config.lureModule.LurePokeSettings;
import eu.pixelunited.anotherpokestop.config.mainConfig.AnotherPokeStopConfig;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class PokeFactory {

    public static void spawnLuredPokemon(LurePokeSettings options, EntityPokestop pokestop) {

        Pokemon pixelmon;
        int shinyRoll = (int) (Math.random() * 100) + 1;
        AnotherPokeStopConfig config = AnotherPokeStop.getConfig();

        if(options.customArgs.isEmpty()) {
            EnumSpecies species = EnumSpecies.getFromNameAnyCase(options.pokemon);
            pixelmon = Pixelmon.pokemonFactory.create(species);
            if(shinyRoll <= options.shinyChance) {
                pixelmon.setShiny(true);
            }

        } else {

            String args = options.pokemon + " " + options.customArgs;
            if (shinyRoll <= options.shinyChance) {
                args = args + " shiny";
            }
            String[] customArgList = args.trim().split("\\s+");
            PokemonSpec spec = PokemonSpec.from(customArgList);
            pixelmon = Pixelmon.pokemonFactory.create(spec);

        }
        Random random = new Random();
        double x = pokestop.posX + (random.nextInt(config.spawnRadius + config.spawnRadius) - config.spawnRadius);
        double y = pokestop.posY;
        double z = pokestop.posZ + (random.nextInt(config.spawnRadius + config.spawnRadius) - config.spawnRadius);

        BlockPos pos = new BlockPos(x, y, z);
        Material material = pokestop.getEntityWorld().getBlockState(pos).getMaterial();
        if(!(material == Material.AIR)) {
            while(!(material == Material.AIR)) {
                y = y + 1;
                pos = new BlockPos(x, y, z);
                material = pokestop.getEntityWorld().getBlockState(pos).getMaterial();
            }
        } else {
            while(material == Material.AIR) {
                y = y - 1;
                pos = new BlockPos(x, y, z);
                material = pokestop.getEntityWorld().getBlockState(pos).getMaterial();
            }
        }


        pixelmon.getOrSpawnPixelmon(pokestop.getEntityWorld(), x, y, z);

    }


}
