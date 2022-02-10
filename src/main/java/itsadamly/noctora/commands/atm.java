package itsadamly.noctora.commands;

import itsadamly.noctora.atmthings.atmCard;
import itsadamly.noctora.atmthings.atmOpenCloseAcc;
import itsadamly.noctora.atmthings.atmeverything;
import itsadamly.noctora.sqlthing.sqltask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class atm implements CommandExecutor {

    public sqltask data = new sqltask();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    if (sender instanceof Player)
    {

        String permission = "Noctora.atmcmd";
        Player player = (Player) sender;
        List<String> commandArgs = new ArrayList<>();

        commandArgs.add("");
        commandArgs.add("close");
        commandArgs.add("help");
        commandArgs.add("open");
        commandArgs.add("updatemoney (player) (add/set/subtract) (money)");


        if (sender.hasPermission(permission))
        {
            String code = atmCard.code();
            switch (args.length)
            {
                case 1 ->
                        {
                            if (args[0].equalsIgnoreCase("open"))
                                atmOpenCloseAcc.atmOpenAcc(player, code);

                            else if (args[0].equalsIgnoreCase("close"))
                                atmOpenCloseAcc.atmCloseAcc(player);

                            else if (args[0].equalsIgnoreCase("updatemoney"))
                            {
                                player.sendMessage(ChatColor.RED + "Error! Invalid argument.");
                                player.sendMessage(ChatColor.RED + "Syntax : /atm updatemoney (player) (add/set/subtract) (money)");
                            }

                            else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("commands"))
                            {
                                player.sendMessage(ChatColor.GOLD + "Helps for /atm");
                                player.sendMessage(ChatColor.GOLD + "§m-----------------------");

                                for (int i = 0; i <= 4; i++)
                                {
                                    player.sendMessage(ChatColor.YELLOW + "/atm " + commandArgs.get(i));

                                    if (i == 0)
                                        player.sendMessage(ChatColor.GOLD + "§oDisplay the ATM GUI. (You need to have an account first to access this.)");

                                    else if (i == 4)
                                        player.sendMessage(ChatColor.GOLD + "§oUpdate player's account by a certain amount.");

                                }
                            }

                            else
                                player.sendMessage(ChatColor.RED + "Invalid command arguments. Do /atm help for list of command arguments");
                        }

                case 2, 3 -> {
                    if (args[0].equalsIgnoreCase("updatemoney")) {
                        player.sendMessage(ChatColor.RED + "Error! Invalid argument.");
                        player.sendMessage(ChatColor.RED + "Syntax : /atm updatemoney (player) (add/set/subtract) (money)");
                    }

                    else if (args[0].equalsIgnoreCase("setmoney"))
                    {
                        player.sendMessage(ChatColor.RED + "Error! Invalid argument.");
                        player.sendMessage(ChatColor.RED + "Syntax : /atm updatemoney (player) (add/set/subtract) (money)");
                    }

                    else
                        player.sendMessage(ChatColor.RED + "Invalid command arguments. Do /atm help for list of command arguments");
                }

                case 4 -> {
                    // /atm "updatemoney" "player" "set / add / subtract" "amount" (4 args)
                    if (args[0].equalsIgnoreCase("updatemoney")) {
                        ArrayList<UUID> getAllUUID = data.getUUID();

                        Player target = Bukkit.getPlayerExact(args[1]);
                        double money = Double.parseDouble(args[3]);

                        try {
                            if (target != null && data.isPlayerOnDB(target.getUniqueId())) {

                                if (args[2].equalsIgnoreCase("add"))
                                {
                                    if (money >= 0.00)
                                    {
                                        data.updateMoney(target.getUniqueId(), money);
                                        player.sendMessage(ChatColor.GREEN + "You have updated " + target.getDisplayName() +
                                                ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                                data.getMoney(target.getUniqueId())));
                                    }

                                    else
                                        player.sendMessage(ChatColor.RED + "Error! The value must be positive.");
                                }

                                else if (args[2].equalsIgnoreCase("subtract"))
                                {
                                    if (money >= 0.00)
                                    {

                                        data.updateMoney(target.getUniqueId(), -money);
                                        player.sendMessage(ChatColor.GREEN + "You have updated " + target.getDisplayName() +
                                                ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                                data.getMoney(target.getUniqueId())));
                                    }
                                    else
                                        player.sendMessage(ChatColor.RED + "Error! The value must be positive.");
                                }

                                else if (args[2].equalsIgnoreCase("set"))
                                {
                                    data.setMoney(target.getUniqueId(), money);
                                    player.sendMessage(ChatColor.GREEN + "You have updated " + target.getDisplayName() +
                                            ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                            data.getMoney(target.getUniqueId())));
                                }

                                else
                                {
                                    player.sendMessage(ChatColor.RED + "Error! Invalid argument.");
                                    player.sendMessage(ChatColor.RED + "Syntax : /atm updatemoney (player) (add/set/subtract) (money)");
                                }

                            }
                            else {

                                int j = 0;
                                boolean match = false;
                                for (int i = 0; i <= data.countRows(); i++) {
                                    OfflinePlayer searchTarget = Bukkit.getOfflinePlayer(getAllUUID.get(i));

                                    if (args[1].equalsIgnoreCase(searchTarget.getName())) {
                                        match = true;
                                        j = i;
                                        break;
                                    }
                                }

                                if (match) {
                                    OfflinePlayer target1 = Bukkit.getOfflinePlayer(getAllUUID.get(j));

                                    if (args[2].equalsIgnoreCase("add"))
                                    {
                                        if (money >= 0.00)
                                        {
                                            data.updateMoney(target1.getUniqueId(), money);
                                            player.sendMessage(ChatColor.GREEN + "You have updated " + target1.getName() +
                                                    ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                                    data.getMoney(target1.getUniqueId())));
                                        }

                                        else
                                            player.sendMessage(ChatColor.RED + "Error! The value must be positive.");
                                    }

                                    else if (args[2].equalsIgnoreCase("subtract"))
                                    {
                                        if (money >= 0.00)
                                        {
                                            data.updateMoney(target1.getUniqueId(), -money);
                                            player.sendMessage(ChatColor.GREEN + "You have updated " + target1.getName() +
                                                    ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                                    data.getMoney(target1.getUniqueId())));
                                        }

                                        else
                                            player.sendMessage(ChatColor.RED + "Error! The value must be positive.");
                                    }

                                    else if (args[2].equalsIgnoreCase("set"))
                                    {
                                        data.setMoney(target1.getUniqueId(), money);
                                        player.sendMessage(ChatColor.GREEN + "You have updated " + target1.getName() +
                                                ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                                data.getMoney(target1.getUniqueId())));
                                    }

                                    else
                                    {
                                        player.sendMessage(ChatColor.RED + "Error! Invalid argument.");
                                        player.sendMessage(ChatColor.RED + "Syntax : /atm updatemoney (player) (add/set/subtract) (money)");
                                    }
                                }
                            }
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "Error! The player is not in the database.");
                        }
                    }

                    else
                        player.sendMessage(ChatColor.RED + "Invalid command arguments. Do /atm help for list of command arguments");
                }

                default ->
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
            }

            return true;
        }
        else
            sender.sendMessage(ChatColor.RED + "Error! You don't have the following permission : " + permission);
    }

    return true;
    }

}