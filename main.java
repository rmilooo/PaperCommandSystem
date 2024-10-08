package de.drache.paperCommandSystem;

import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class main extends JavaPlugin {
    public static main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new sendCommand(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getServer().getMessenger().registerOutgoingPluginChannel(this, "velocity:main");

        getServer().getMessenger().registerIncomingPluginChannel(this, "velocity:main", new PluginMessageListener());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
