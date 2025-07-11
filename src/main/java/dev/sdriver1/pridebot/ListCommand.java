package dev.sdriver1.pridebot;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.stream.Collectors;

public class ListCommand implements CommandExecutor {
    private static final String PREFIX = "<#ff00ae><bold>Pridebot:</bold></#ff00ae> ";
    private final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(mm.deserialize(PREFIX + "Usage: /" + label + " <genders|sexualities|all>"));
            return true;
        }

        String opt = args[0].toLowerCase();
        Component reply;

        switch (opt) {
            case "genders" -> {
                String list = GenderCommand.GENDERS.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .map(e -> {
                            var info = e.getValue();
                            return info.prideFlag() != null
                                    ? "<pride:" + info.prideFlag() + "><bold>" + info.display() + "</bold></pride>"
                                    : "<bold>" + info.display() + "</bold>";
                        })
                        .collect(Collectors.joining(", "));
                reply = mm.deserialize(PREFIX + "\n" + "Available genders: " + list);
            }
            case "sexualities" -> {
                String list = SexualityCommand.SEXUALITIES.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .map(e -> {
                            var info = e.getValue();
                            return info.prideFlag() != null
                                    ? "<pride:" + info.prideFlag() + "><bold>" + info.display() + "</bold></pride>"
                                    : "<bold>" + info.display() + "</bold>";
                        })
                        .collect(Collectors.joining(", "));
                reply = mm.deserialize(PREFIX + "\n" + "Available sexualities: " + list);
            }
            case "all" -> {
                String genders = GenderCommand.GENDERS.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .map(e -> {
                            var info = e.getValue();
                            return info.prideFlag() != null
                                    ? "<pride:" + info.prideFlag() + "><bold>" + info.display() + "</bold></pride>"
                                    : "<bold>" + info.display() + "</bold>";
                        })
                        .collect(Collectors.joining(", "));
                String sexualities = SexualityCommand.SEXUALITIES.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .map(e -> {
                            var info = e.getValue();
                            return info.prideFlag() != null
                                    ? "<pride:" + info.prideFlag() + "><bold>" + info.display() + "</bold></pride>"
                                    : "<bold>" + info.display() + "</bold>";
                        })
                        .collect(Collectors.joining(", "));
                reply = mm.deserialize(
                        PREFIX + "\n" +
                                "Available genders: " + genders + "\n" +
                                "Available sexualities: " + sexualities
                );
            }
            default -> {
                sender.sendMessage(mm.deserialize(PREFIX + "Unknown option. Use genders, sexualities, or all."));
                sender.sendMessage(mm.deserialize(PREFIX + "Usage: /" + label + " <genders|sexualities|all>"));
                return true;
            }
        }

        sender.sendMessage(reply);
        return true;
    }
}
