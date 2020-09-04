package dev.wnuke.nukestack.commands;

import dev.wnuke.nukestack.GeneralUtilities;
import dev.wnuke.nukestack.NukeStack;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Message implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            Player player = ((Player) sender).getPlayer();
            if (player == null) return false;
            Player receiver = NukeStack.PLUGIN.getServer().getPlayer(args[0]);
            if (receiver == null) {
                sender.sendMessage(ChatColor.RED + "No player with name " + args[0] + ".");
            } else {
                GeneralUtilities.sendPrivateMessage(player, receiver, Arrays.copyOfRange(args, 1, args.length));
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must specify who you want to send a message to.");
        }
        return true;
    }
}

