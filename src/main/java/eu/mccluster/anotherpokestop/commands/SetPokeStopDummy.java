package eu.mccluster.anotherpokestop.commands;

import com.flowpowered.math.vector.Vector3d;
import com.pixelmonmod.pixelmon.entities.EntityPokestop;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.objects.RGBStorage;
import eu.mccluster.anotherpokestop.utils.Utils;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.World;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Optional;

@RequiredArgsConstructor
public class SetPokeStopDummy implements CommandExecutor {

    final AnotherPokeStopConfig _config;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("Hello GlaDOS, get a ingame Player!"));
            return CommandResult.empty();
        }

        Optional<RGBStorage> rgbStorageOptional = args.<RGBStorage>getOne("rgb");
        RGBStorage rgbStorage = rgbStorageOptional.orElseGet(() -> new RGBStorage(_config.standardColors.red, _config.standardColors.green, _config.standardColors.blue));

        Player p = (Player) src;
        Vector3d playerPos = p.getPosition();
        World playerWorld = (World) p.getWorld();
        EntityPokestop pokestop = new EntityPokestop(playerWorld, playerPos.getX(), playerPos.getY(), playerPos.getZ());
        pokestop.setColor(rgbStorage.getR(), rgbStorage.getG(), rgbStorage.getB());
        pokestop.setAlwaysAnimate(true);
        pokestop.setNoGravity(true);
        pokestop.setCubeRange(_config.cubeRange);
        playerWorld.spawnEntity(pokestop);
        p.sendMessage(Utils.toText(_config.setText));
        return CommandResult.success();
    }
}
