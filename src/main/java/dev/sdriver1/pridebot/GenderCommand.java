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
                    "An umbrella term for people whose gender identity differs from the sex they were assigned at birth. "
                            + "Trans people may socially, medically, or legally transition to live in alignment with their true gender.",
                    List.of("#5BCEFA", "#F5A9B8", "#FFFFFF", "#F5A9B8", "#5BCEFA"),
                    "trans"
            )),
            Map.entry("nonbinary", new Info(
                    "Nonbinary",
                    "A spectrum of gender identities outside the strict categories of male and female. "
                            + "Nonbinary people may identify as both, neither, or a fluctuating mix of genders.",
                    List.of("#FFF430", "#FFFFFF", "#9C59D1", "#000000"),
                    "nb"
            )),
            Map.entry("agender", new Info(
                    "Agender",
                    "People who identify as having no gender or a neutral gender. "
                            + "Often describes a lack of identification with any gender category.",
                    List.of("#000000", "#B6B6B6", "#FFFFFF", "#B6B6B6", "#000000"),
                    "agender"
            )),
            Map.entry("genderqueer", new Info(
                    "Genderqueer",
                    "An umbrella term for gender identities that are outside or between the binary. "
                            + "Includes identities like genderfluid, bigender, and others that challenge strict male/female norms.",
                    List.of("#B57EDC", "#FFFFFF", "#4A8123"),
                    "genderqueer"
            )),
            Map.entry("genderfluid", new Info(
                    "Genderfluid",
                    "People whose gender identity changes over time or in different contexts, "
                            + "shifting between two or more genders or expressions of gender.",
                    List.of("#9BD8E8", "#FBCBDE", "#FFFFFF", "#C18FD6", "#333333"),
                    "genderfluid"
            )),
            Map.entry("bigender", new Info(
                    "Bigender",
                    "People who identify as two distinct genders, either simultaneously or varying between them",
                    List.of("#D73D9A", "#C58E9F", "#FFFFFF", "#1EB2A6", "#2D75DA"),
                    "bigender"
            )),
            Map.entry("demigender", new Info(
                    "Demigender",
                    "People who feel a partial connection to a particular gender (e.g. demiboy, demigirl), "
                            + "experiencing that gender only to some extent.",
                    List.of("#808080", "#FFFFFF", "#9100F4", "#FFFFFF", "#808080"),
                    "demigender"
            )),
            Map.entry("intersex", new Info(
                    "Intersex",
                    "People born with physical sex characteristics (chromosomes, gonads, hormones, or anatomy) "
                            + "that do not fit typical binary notions of male or female. Intersex is about biology, not identity.",
                    List.of("#FCE83E", "#FCE83E", "#760089", "#FCE83E", "#FCE83E"),
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
