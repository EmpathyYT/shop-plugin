package plugin.q;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;


import java.util.ArrayList;
import java.util.List;



public class Tab implements TabCompleter{
    List<String> arguments = new ArrayList<String>();
    List<String> arguments2 = new ArrayList<String>();
    List<String> special = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        if(arguments.isEmpty()) {
            arguments.add("blocks");
            arguments.add("currency");
            arguments.add("customs");
            arguments.add("tools");
            arguments.add("weapons");
            arguments.add("redstone");
        }
        if(arguments2.isEmpty()) {
            arguments2.add("add");
            arguments2.add("remove");

        }
        if (special.isEmpty()) {
            special.add("customadd");

            special.add("customrem");
        }
        List<String> result2 = new ArrayList<String>();
        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String a: arguments) {
                if(a.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(a);
            }
            return result;
        }
        else if(args.length == 2) {
            if (args[0].equalsIgnoreCase("customs")) {
                for (String a : special) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
            } else {
                for (String a : arguments2) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
            }
            return result;
        }
        return null;
    }
}
