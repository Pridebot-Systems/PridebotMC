package dev.sdriver1.pridebot;

import org.bukkit.plugin.java.JavaPlugin;

public class Pridebot extends JavaPlugin {
    @Override
    public void onEnable() {
        // Gaydar
        getCommand("gaydar").setExecutor(new GaydarCommand(this));
        getCommand("gaydar").setTabCompleter(new PlayerTabCompleter());

        // Transdar
        getCommand("transdar").setExecutor(new TransdarCommand(this));
        getCommand("transdar").setTabCompleter(new PlayerTabCompleter());

        // /gender
        getCommand("gender").setExecutor(new GenderCommand());
        getCommand("gender").setTabCompleter(new GenderTabComplete());

        // /sexuality
        getCommand("sexuality").setExecutor(new SexualityCommand());
        getCommand("sexuality").setTabCompleter(new SexualityTabComplete());

        // /list
        getCommand("list").setExecutor(new ListCommand());
        getCommand("list").setTabCompleter(new ListTabComplete());

    }
}
