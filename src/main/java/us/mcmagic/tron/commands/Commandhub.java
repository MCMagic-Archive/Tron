package us.mcmagic.tron.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.mcmagic.mcmagiccore.bungee.BungeeUtil;

/**
 * Created by Marc on 12/26/15
 */
public class Commandhub implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        BungeeUtil.sendToServer(player, "Arcade");
        player.sendMessage(ChatColor.AQUA + "Now returning to the " + ChatColor.BLUE + "Arcade...");
        return true;
    }
}