package dev.wnuke.nukestack.commands;

import dev.wnuke.nukestack.NukeStack;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TeleportNo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) {
            if (args.length > 0) {
                for (Player player : NukeStack.PLUGIN.getServer().getOnlinePlayers()) {
                    if (player.getPlayerListName().equals(args[0])) {
                        if (NukeStack.teleportRequests.get(player.getUniqueId()) == ((Player) sender).getUniqueId()) {
                            player.sendMessage(ChatColor.RED + "Teleport denied.");
                            sender.sendMessage(ChatColor.GREEN + "Teleport denied.");
                            return true;
                        }
                    }
                }
                sender.sendMessage(ChatColor.RED + "No player with name " + args[0] + " has requested to teleport to you.");
            } else {
                sender.sendMessage(ChatColor.RED + "You need to specify who you want to deny.");
            }
        }
        return true;
    }
}
