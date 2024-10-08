package de.drache.paperCommandSystem;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginLogger;
import org.jetbrains.annotations.NotNull;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] bytes) {
        // Check if the channel is the expected one
        if (!channel.equals("velocity:main")) {
            PluginLogger.getGlobal().warning("Received message on unexpected channel: " + channel);
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);

        // Read the command type and command string
        String commandType = in.readUTF();
        PluginLogger.getGlobal().info("Received command type: " + commandType); // Log the command type

        if (commandType.equals("ServerCommandResponse")) {
            String commandToSend = in.readUTF();
            PluginLogger.getGlobal().info("Command to send: " + commandToSend); // Log the command to send
            executeCommand(commandToSend);
        } else {
            PluginLogger.getGlobal().warning("Received unexpected command type: " + commandType);
        }
    }

    private void executeCommand(String command) {
        // Ensure the command is not null or empty
        if (command == null || command.trim().isEmpty()) {
            PluginLogger.getGlobal().warning("Received an empty command. Command was not executed.");
            return;
        }

        // Execute the command and log it
        try {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            PluginLogger.getGlobal().info("Executed command: " + command);
        } catch (Exception e) {
            PluginLogger.getGlobal().severe("Failed to execute command: " + command);
            e.printStackTrace();
        }
    }
}
