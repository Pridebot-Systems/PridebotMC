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
    }
}
