package dev.sdriver1.pridebot;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class TransdarCommand implements CommandExecutor {
    private final Pridebot plugin;
    private static final String PREFIX_TAG = "<#ff00ae><bold>[Pridebot]:</bold></#ff00ae> ";
    private final MiniMessage mm = MiniMessage.miniMessage();

    public TransdarCommand(Pridebot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player target;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(mm.deserialize(PREFIX_TAG + "Usage: /transdar <player>"));
                return true;
            }
            target = (Player) sender;
        } else if (args.length == 1) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(mm.deserialize(PREFIX_TAG + "That player is not online."));
                return true;
            }
        } else {
            sender.sendMessage(mm.deserialize(PREFIX_TAG + "Usage: /transdar [player]"));
            return true;
        }

        int meter = ThreadLocalRandom.current().nextInt(0, 101);
        if (ThreadLocalRandom.current().nextDouble() <= 0.0001) {
            meter = ThreadLocalRandom.current().nextInt(500, 2_354_582);
            if (ThreadLocalRandom.current().nextBoolean()) meter *= -1;
        }

        String body = "<yellow>" + target.getName() + "</yellow> is "
                + "<#ff00ae><bold>" + meter + "%</bold></#ff00ae> trans!";
        Component reply = mm.deserialize(PREFIX_TAG + body);

        sender.sendMessage(reply);
        return true;
    }
}
