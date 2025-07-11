package dev.sdriver1.pridebot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SexualityTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String alias,
            String[] args
    ) {
        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            return SexualityCommand.SEXUALITIES.keySet().stream()
                    .filter(s -> s.startsWith(partial))
                    .sorted()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
