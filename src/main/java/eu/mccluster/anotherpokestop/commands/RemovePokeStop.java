package eu.mccluster.anotherpokestop.commands;

import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.config.AnotherPokeStopConfig;
import eu.mccluster.anotherpokestop.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

@RequiredArgsConstructor
public class RemovePokeStop implements CommandExecutor {

    final AnotherPokeStop _plugin;
    final AnotherPokeStopConfig _config;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("Hello GlaDOS, you´re not a Player!"));
            return CommandResult.empty();
        }
        Player p = (Player) src;
        if(_plugin._currentPokestopRemovers.contains(p.getUniqueId())) {
            _plugin._currentPokestopRemovers.remove(p.getUniqueId());
            p.sendMessage(Utils.toText(_config.disableRemover));
        } else {
            _plugin._currentPokestopRemovers.add(p.getUniqueId());
            p.sendMessage(Utils.toText(_config.enableRemover));
        }
        return CommandResult.success();
    }

}
