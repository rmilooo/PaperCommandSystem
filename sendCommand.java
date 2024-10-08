package de.drache.paperCommandSystem;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class sendCommand implements Listener {

    @EventHandler
    public void onPlayerCommandSend(AsyncPlayerChatEvent event) {
        // Check if the message starts with "!" and the player is not null
        if (event.getMessage().startsWith("!")) {
            if (event.getPlayer().hasPermission("velocity.admin")) {
                // Cancel the event to prevent further processing
                event.setCancelled(true);

                // Extract the command safely
                String[] messageParts = event.getMessage().split("!", 2);
                if (messageParts.length > 1) {
                    String command = messageParts[1];

                    // Check if the command is not null or empty
                    if (command != null && !command.trim().isEmpty()) {
                        // Send feedback to the player
                        event.getPlayer().sendMessage("Command to send: " + command);
                        if (command.startsWith("kick")){
                            // Extract the target player safely
                            String[] targetParts = command.split(" ", 2);
                            if (targetParts.length > 1) {
                                String targetPlayer = targetParts[1];
                                kick(event.getPlayer(), targetPlayer);
                            } else {
                                event.getPlayer().sendMessage("§cInvalid kick command. Usage:!kick <player>");
                            }
                        }
                        // Create the data output for the plugin message
                        ByteArrayDataOutput out = ByteStreams.newDataOutput();
                        out.writeUTF("command"); // Custom message type
                        out.writeUTF(command); // The command to send

                        // Send the command to the proxy
                        event.getPlayer().sendPluginMessage(main.plugin, "velocity:main", out.toByteArray());

                        // Inform the player that the command is being sent
                        event.getPlayer().sendMessage("§cSending command to proxy");
                    } else {
                        // Inform the player if the command is invalid
                        event.getPlayer().sendMessage("§cInvalid command.");
                    }
                }
            } else {
                // Optionally inform players without permission
                event.getPlayer().sendMessage("§cYou do not have permission to use this command.");
            }
        }
    }
    public void kick(Player player, String targetPlayer) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(targetPlayer);
        player.sendPluginMessage(main.plugin, "BungeeCord", out.toByteArray());
    }
}
