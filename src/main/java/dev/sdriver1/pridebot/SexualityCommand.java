package dev.sdriver1.pridebot;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SexualityCommand implements CommandExecutor {
    private static final String PREFIX = "<#ff00ae><bold>Pridebot:</bold></#ff00ae> ";
    private final MiniMessage mm = MiniMessage.miniMessage();

    private record Info(String display, String definition, List<String> stripes, String prideFlag) {
    }

    static final Map<String, Info> SEXUALITIES = Map.ofEntries(
            Map.entry("gay", new Info(
                    "Gay",
                    "People attracted to members of the same gender.",
                    List.of("#E70000", "#FF8C00", "#FFEF00", "#00811F", "#0044FF", "#760089"),
                    "gay"
            )),
            Map.entry("lesbian", new Info(
                    "Lesbian",
                    "Women attracted to women.",
                    List.of("#D52D00", "#FF9A56", "#FFFFFF", "#D362A4", "#A30262"),
                    "lesbian"
            )),
            Map.entry("bisexual", new Info(
                    "Bisexual",
                    "People attracted to more than one gender.",
                    List.of("#D60270", "#9B4F96", "#0038A8"),
                    "bi"
            )),
            Map.entry("pansexual", new Info(
                    "Pansexual",
                    "People attracted to others regardless of gender.",
                    List.of("#FF218C", "#FFD800", "#21B1FF"),
                    "pan"
            )),
            Map.entry("asexual", new Info(
                    "Asexual",
                    "People who experience little or no sexual attraction.",
                    List.of("#000000", "#A4A4A4", "#FFFFFF", "#810081"),
                    "ace"
            ))
    );

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(PREFIX +
                    "Usage: /sexuality <" +
                    SEXUALITIES.keySet().stream().sorted().collect(Collectors.joining("/")) +
                    ">");
            return true;
        }

        String key = args[0].toLowerCase();
        Info info = SEXUALITIES.get(key);
        if (info == null) {
            sender.sendMessage(PREFIX +
                    "Unknown sexuality. Options: " +
                    SEXUALITIES.keySet().stream().sorted().collect(Collectors.joining(", ")) +
                    ".");
            return true;
        }

        // build the pride-wrapped display name (or fallback to bold)
        String displayTag = (info.prideFlag() != null)
                ? "<pride:" + info.prideFlag() + "><bold>" + info.display() + "</bold></pride>"
                : "<bold>" + info.display() + "</bold>";

        // build flag stripes
        StringBuilder flag = new StringBuilder();
        for (String c : info.stripes()) {
            flag.append("<").append(c).append(">████</").append(c).append(">");
        }

        String mmText = PREFIX + "\n" +
                displayTag + ": " +
                info.definition() + "\n" +
                "Flag: " + flag;

        Component reply = mm.deserialize(mmText);
        sender.sendMessage(reply);
        return true;
    }
}
