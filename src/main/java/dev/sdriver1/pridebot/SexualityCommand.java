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

    public static record Info(String display, String definition, List<String> stripes, String prideFlag) {
    }

    public static final Map<String, Info> SEXUALITIES = Map.ofEntries(
            Map.entry("gay", new Info(
                    "Gay",
                    "People who experience romantic or sexual attraction primarily to members of the same gender.",
                    List.of("#E70000","#FF8C00","#FFEF00","#00811F","#0044FF","#760089"),
                    "gay"
            )),
            Map.entry("lesbian", new Info(
                    "Lesbian",
                    "Women and girls who experience romantic or sexual attraction to other women and girls.",
                    List.of("#D52D00","#FF9A56","#FFFFFF","#D362A4","#A30262"),
                    "lesbian"
            )),
            Map.entry("bisexual", new Info(
                    "Bisexual",
                    "People who experience romantic or sexual attraction to more than one gender, "
                            + "not necessarily in equal measure or at the same time.",
                    List.of("#D60270","#9B4F96","#0038A8"),
                    "bi"
            )),
            Map.entry("pansexual", new Info(
                    "Pansexual",
                    "People whose attraction is not limited by gender; can be attracted to people regardless of gender identity.",
                    List.of("#FF218C","#FFD800","#21B1FF"),
                    "pan"
            )),
            Map.entry("asexual", new Info(
                    "Asexual",
                    "People who experience little to no sexual attraction to any gender, though they may have romantic attraction.",
                    List.of("#000000","#A4A4A4","#FFFFFF","#810081"),
                    "ace"
            )),
            Map.entry("demisexual", new Info(
                    "Demisexual",
                    "People who experience sexual attraction only after forming a strong emotional or romantic bond.",
                    List.of("#000000","#AAAAAA","#FFFFFF","#800080"),
                    "demisexual"
            )),
            Map.entry("queer", new Info(
                    "Queer",
                    "An umbrella term reclaimed by many as a positive, inclusive label for sexual and gender minorities "
                            + "outside of heterosexual and cisgender norms.",
                    List.of("#E70000","#FF8C00","#FFEF00","#00811F","#0044FF","#760089"),
                    "queer"
            )),
            Map.entry("aromantic", new Info(
                    "Aromantic",
                    "People who experience little to no romantic attraction to others, distinct from sexual orientation.",
                    List.of("#3DAE48","#A9DFBF","#FFFFFF","#B2B3B9","#1A1A1D"),
                    "aro"
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
