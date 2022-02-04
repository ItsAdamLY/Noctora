package me.itsadamly.noctora.commands;

import me.itsadamly.noctora.Main;
import me.itsadamly.noctora.atmthings.atmCard;
import me.itsadamly.noctora.atmthings.atmOpenCloseAcc;
import me.itsadamly.noctora.atmthings.atmeverything;
import me.itsadamly.noctora.sqlthing.sqltask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class atm implements CommandExecutor {

    public sqltask data = new sqltask();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    if (sender instanceof Player) {

        String permission = "Noctora.atmcmd";
        Player player = (Player) sender;
        List<String> commandArgs = new ArrayList<>();

        if (sender.hasPermission(permission))
        {
            if (args.length >= 3)
            {
                if (args[0].equalsIgnoreCase("open"))
                {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    String code = atmCard.code();

                    if (args.length == 3)
                    {
                        atmOpenCloseAcc.atmOpenAcc(target, code);
                    }
                    atmOpenCloseAcc.atmOpenAcc(player, code);
                }
                else if (args[0].equalsIgnoreCase("close"))
                {
                    Player target = Bukkit.getPlayerExact(args[1]);

                    if (args.length == 3)
                    {
                        atmOpenCloseAcc.atmCloseAcc(target);
                    }

                    atmOpenCloseAcc.atmCloseAcc(player);
                }
            }
            else
            {
                if (data.isPlayerOnDB(player.getUniqueId()))
                {
                    atmeverything.atmcontents(player);
                }
                else
                {
                    player.sendMessage(ChatColor.YELLOW + "You don't have a bank account. Do " +
                            ChatColor.GOLD + "/atm open " + ChatColor.YELLOW + "first.");
                }
            }
            return true;
        }

        else
            sender.sendMessage(ChatColor.RED + "Error! You don't have the following permission : " + permission);
    }
    else
        Main.notPlayerError();

    return false;
    }

}
