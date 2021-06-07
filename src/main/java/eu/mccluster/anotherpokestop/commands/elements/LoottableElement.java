package eu.mccluster.anotherpokestop.commands.elements;

import eu.mccluster.anotherpokestop.AnotherPokeStop;
import eu.mccluster.anotherpokestop.objects.LoottableStorage;
import eu.mccluster.anotherpokestop.utils.Utils;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.List;

public class LoottableElement extends CommandElement {


    public LoottableElement(Text key) {
        super(key);
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String tableInput = args.next();
        String table = parseString(tableInput, args);

        return new LoottableStorage(table);
    }

    private String parseString(String input, CommandArgs args) throws  ArgumentParseException {
        String parsed;
        boolean folderCheck;
        try {
            parsed = input;
            folderCheck = AnotherPokeStop.getInstance()._avaiableLoottables.contains(parsed);
        } catch(Exception e) {
            throw args.createError(Text.of("Error"));
        }
        if(!folderCheck) {
            throw  args.createError(Text.of("'" + input + "'is not a valid Loottable"));
        }
        return parsed;
    }


    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        return null;
    }

    @Override
    public Text getUsage(CommandSource src) { return Utils.toText("&4<Loottable>"); }
}
