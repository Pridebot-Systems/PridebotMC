package dev.sdriver1.pridebot;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenderCommand implements CommandExecutor {
    private static final String PREFIX = "<#ff00ae><bold>Pridebot:</bold></#ff00ae> ";
    private final MiniMessage mm = MiniMessage.miniMessage();

    public static record Info(String display, String definition, List<String> stripes, String prideFlag) {
    }
    

    public static final Map<String, Info> GENDERS = Map.ofEntries(
            Map.entry("transgender", new Info(
                    "Transgender",
                    "People whose gender identity differs from the sex assigned at birth.",
                    List.of("#5BCEFA", "#F5A9B8", "#FFFFFF", "#F5A9B8", "#5BCEFA"),
                    "trans"
            )),
            Map.entry("nonbinary", new Info(
                    "Nonbinary",
                    "People whose gender identity isn’t exclusively male or female.",
                    List.of("#FFF430", "#FFFFFF", "#9C59D1", "#000000"),
                    "nb"
            )),
            Map.entry("agender", new Info(
                    "Agender",
                    "People who identify as having no gender.",
                    List.of("#000000", "#B6B6B6", "#FFFFFF", "#B6B6B6", "#000000"),
                    "agender"
            )),
            Map.entry("genderqueer", new Info(
                    "Genderqueer",
                    "People whose gender identity falls outside of or between the male/female binary.",
                    List.of("#B57EDC", "#FFFFFF", "#4A8123"),
                    "genderqueer"
            )),
            Map.entry("genderfluid", new Info(
                    "Genderfluid",
                    "People whose gender identity shifts over time.",
                    List.of("#9BD8E8", "#FBCBDE", "#FFFFFF", "#C18FD6", "#333333"),
                    "genderfluid"
            )),
            Map.entry("bigender", new Info(
                    "Bigender",
                    "People who identify with two genders.",
                    List.of("#D73D9A", "#C58E9F", "#FFFFFF", "#1EB2A6", "#2D75DA"),
                    "bigender"
            )),
            Map.entry("demigender", new Info(
                    "Demigender",
                    "People who partially identify with a gender.",
                    List.of("#808080", "#FFFFFF", "#9100F4", "#FFFFFF", "#808080"),
                    "demigender"
            )),
            Map.entry("intersex", new Info(
                    "Intersex",
                    "People born with sex characteristics that don’t fit typical binary definitions of male or female.",
                    // a solid‐yellow flag, so we repeat one color
                    List.of("#FCE83E", "#FCE83E", "#FCE83E", "#FCE83E", "#FCE83E"),
                    "intersex"
            ))
    );

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(PREFIX +
                    "Usage: /gender <" +
                    GENDERS.keySet().stream().sorted().collect(Collectors.joining("/")) +
                    ">");
            return true;
        }

        String key = args[0].toLowerCase();
        Info info = GENDERS.get(key);
        if (info == null) {
            sender.sendMessage(PREFIX +
                    "Unknown gender. Options: " +
                    GENDERS.keySet().stream().sorted().collect(Collectors.joining(", ")) +
                    ".");
            return true;
        }

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
