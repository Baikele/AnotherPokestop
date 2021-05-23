package eu.mccluster.anotherpokestop.commands.elements;

import eu.mccluster.anotherpokestop.objects.RGBStorage;
import eu.mccluster.anotherpokestop.utils.Utils;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.List;

public class RGBElement extends CommandElement {

    public RGBElement(Text key) {
        super(key);
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {

        String rInput = args.next();
        int r = parseInt(rInput, args);

        String gInput = args.next();
        int g = parseInt(gInput, args);

        String bInput = args.next();
        int b = parseInt(bInput, args);

        return new RGBStorage(r, g, b);
    }

    private int parseInt(String input, CommandArgs args) throws ArgumentParseException {
        int parsed;
        try {
            parsed = Integer.parseInt(input);
        } catch(NumberFormatException e) {
            throw args.createError(Text.of("'" + input + "' is not a valid number!"));
        }
        if(parsed > 255) {
            throw args.createError(Text.of("'" + input + "' is larger than 255. Stick to the RGB Limit"));
        }
        if (parsed < 0) {
            throw args.createError(Text.of("'" + input + "' is lower than 0. Stick to the RGB Limit"));
        }
        return parsed;
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        return null;
    }

    @Override
    public Text getUsage(CommandSource src) {
        return Utils.toText("&4<R> &a<G> &9<B>");
    }

}
