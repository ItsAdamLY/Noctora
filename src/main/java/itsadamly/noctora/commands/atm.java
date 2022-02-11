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
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class atm implements CommandExecutor, TabExecutor {

    public sqltask data = new sqltask();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            String permission = "Noctora.atmcmd";
            Player player = (Player) sender;
            List<String> commandArgs = new ArrayList<>();

            commandArgs.add("");
            commandArgs.add("close");
            commandArgs.add("checkmoney (player)");
            commandArgs.add("help");
            commandArgs.add("open");
            commandArgs.add("updatemoney (player) (add/set/subtract) (money)");


            if (sender.hasPermission(permission)) {
                String code = atmCard.code();
                switch (args.length) {

                    case 1 -> {

                        if (args[0].equalsIgnoreCase("open"))
                            atmOpenCloseAcc.atmOpenAcc(player, code);

                        else if (args[0].equalsIgnoreCase("close"))
                            atmOpenCloseAcc.atmCloseAcc(player);

                        else if (args[0].equalsIgnoreCase("updatemoney")) {
                            player.sendMessage(ChatColor.RED + "Error! Invalid argument.");
                            player.sendMessage(ChatColor.RED + "Syntax : /atm updatemoney (player) (add/set/subtract) (money)");
                        }

                        else if (args[0].equalsIgnoreCase("checkmoney")) {
                            player.sendMessage(ChatColor.RED + "Error! Invalid argument.");
                            player.sendMessage(ChatColor.RED + "Syntax : /atm checkmoney (player)");
                        }

                        else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("commands")) {
                            player.sendMessage(ChatColor.GOLD + "§m-----------------------");
                            player.sendMessage(ChatColor.GOLD + "Helps for /atm");
                            player.sendMessage(ChatColor.GOLD + "§m-----------------------");

                            for (int i = 0; i <= (commandArgs.size() - 1); i++) {
                                player.sendMessage(ChatColor.YELLOW + "/atm " + commandArgs.get(i));

                                if (i == 0)
                                    player.sendMessage(ChatColor.GOLD + "§oDisplay the ATM GUI. (You need to have an account first to access this.)");

                                else if (i == 5)
                                    player.sendMessage(ChatColor.GOLD + "§oUpdate player's account by a certain amount.");

                            }
                        }
                    }

                    case 2 -> {
                        if (args[0].equalsIgnoreCase("updatemoney")) {
                            player.sendMessage(ChatColor.RED + "Error! Invalid argument.");
                            player.sendMessage(ChatColor.RED + "Syntax : /atm updatemoney (player) (add/set/subtract) (money)");

                        }

                        else if (args[0].equalsIgnoreCase("checkmoney"))
                        {
                            Player target = Bukkit.getPlayerExact(args[1]);
                            ArrayList<UUID> getAllUUID = data.getUUID();

                            try
                            {
                                if (target != null && data.isPlayerOnDB(target.getUniqueId()))
                                {
                                    double checkMoney = data.getMoney(target.getUniqueId());
                                    player.sendMessage(ChatColor.GREEN + target.getDisplayName() + ChatColor.GREEN +
                                            "'s savings money is $ " + checkMoney);
                                }

                                else
                                {
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
                                        double checkMoney = data.getMoney(target1.getUniqueId());
                                        player.sendMessage(ChatColor.GREEN + target1.getName() + "'s savings money is $ " + checkMoney);
                                    }
                                }
                            }
                            catch (Exception error)
                            {
                                player.sendMessage(ChatColor.RED + "Error! The player is not in the database.");
                            }

                        }
                    }

                    case 3 ->
                    {
                        if (args[0].equalsIgnoreCase("updatemoney")) {
                            player.sendMessage(ChatColor.RED + "Error! Invalid argument.");
                            player.sendMessage(ChatColor.RED + "Syntax : /atm updatemoney (player) (add/set/subtract) (money)");
                        }
                    }


                    case 4 -> {
                        // /atm "updatemoney" "player" "set / add / subtract" "amount" (4 args)
                        if (args[0].equalsIgnoreCase("updatemoney")) {
                            ArrayList<UUID> getAllUUID = data.getUUID();

                            Player target = Bukkit.getPlayerExact(args[1]);
                            double money = Double.parseDouble(args[3]);
                            String argument;

                            try {
                                if (target != null && data.isPlayerOnDB(target.getUniqueId())) {

                                    if (args[2].equalsIgnoreCase("add")) {

                                        argument = "add";

                                        if (money >= 0.00)
                                        {

                                        data.updateMoney(target.getUniqueId(), money, argument);
                                        player.sendMessage(ChatColor.GREEN + "You have added " + target.getDisplayName() +
                                                ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                                data.getMoney(target.getUniqueId())));
                                        }

                                        else
                                            player.sendMessage(ChatColor.RED + "Error! The value must be positive.");
                                    }

                                    else if (args[2].equalsIgnoreCase("set"))
                                    {
                                        argument = "set";

                                        if (money >= 0.00) {
                                                data.updateMoney(target.getUniqueId(), money, argument);
                                                player.sendMessage(ChatColor.GREEN + "You have set " + target.getDisplayName() +
                                                        ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                                        data.getMoney(target.getUniqueId())));
                                            }

                                        else
                                            player.sendMessage(ChatColor.RED + "Error! The value must be positive.");
                                    }

                                    else if (args[2].equalsIgnoreCase("subtract")) {

                                        argument = "subtract";

                                        if (money >= 0.00) {
                                            data.updateMoney(target.getUniqueId(), -money, argument);
                                            player.sendMessage(ChatColor.GREEN + "You have added " + target.getDisplayName() +
                                                    ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                                    data.getMoney(target.getUniqueId())));
                                        }

                                        else
                                            player.sendMessage(ChatColor.RED + "Error! The value must be positive.");
                                    }

                                    else {
                                        player.sendMessage(ChatColor.RED + "Error! Invalid argument.");
                                        player.sendMessage(ChatColor.RED + "Syntax : /atm updatemoney (player) (add/set/subtract) (money)");
                                    }

                                }

                                else if (args[1].equalsIgnoreCase("@a"))
                                {
                                    ArrayList<UUID> uuid = data.getUUID();

                                    if (args[2].equalsIgnoreCase("add"))
                                    {
                                        argument = "add";

                                        if (money >= 0.00)
                                        {
                                            for (int i = 0; i <= (data.countRows() - 1); i++) {
                                                data.updateMoneyAll(uuid, i, money, argument);
                                            }

                                            player.sendMessage(ChatColor.GREEN + "You have added everyone's " +
                                                    "savings money by $ " + String.format("%.2f", money));
                                        }

                                        else
                                            player.sendMessage(ChatColor.RED + "Error! The value must be positive.");


                                    }

                                    else if (args[2].equalsIgnoreCase("subtract"))
                                    {
                                        argument = "subtract";

                                        if (money >= 0.00)
                                        {
                                            for (int i = 0; i <= (data.countRows() - 1); i++) {
                                                data.updateMoneyAll(uuid, i, -money, argument);
                                            }

                                            player.sendMessage(ChatColor.GREEN + "You have subtracted everyone's " +
                                                    "savings money by $ " + String.format("%.2f", money));
                                        }

                                        else
                                            player.sendMessage(ChatColor.RED + "Error! The value must be positive.");
                                    }

                                    else if (args[2].equalsIgnoreCase("set")) {
                                        argument = "set";

                                        if (money >= 0.00)
                                        {
                                            for (int i = 0; i <= (data.countRows() - 1); i++) {
                                                data.updateMoneyAll(uuid, i, money, argument);
                                            }

                                            player.sendMessage(ChatColor.GREEN + "You have set everyone's " +
                                                    "savings money to $ " + String.format("%.2f", money));
                                        }
                                        else
                                            player.sendMessage(ChatColor.RED + "Error! The value must be positive.");

                                    }

                                    else {
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

                                        if (args[2].equalsIgnoreCase("add")) {

                                            argument = "add";

                                            if (money >= 0.00) {

                                                data.updateMoney(target1.getUniqueId(), money, argument);
                                                player.sendMessage(ChatColor.GREEN + "You have updated " + target1.getName() +
                                                        ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                                        data.getMoney(target1.getUniqueId())));

                                            }

                                            else
                                                player.sendMessage(ChatColor.RED + "Error! The value must be positive.");
                                        }

                                        else if (args[2].equalsIgnoreCase("subtract")) {

                                            argument = "subtract";

                                            if (money >= 0.00) {
                                                    data.updateMoney(target1.getUniqueId(), -money, argument);
                                                    player.sendMessage(ChatColor.GREEN + "You have updated " + target1.getName() +
                                                            ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                                            data.getMoney(target1.getUniqueId())));
                                            }

                                            else
                                                player.sendMessage(ChatColor.RED + "Error! The value must be positive.");
                                        }

                                        else if (args[2].equalsIgnoreCase("set")) {

                                            argument = "set";
                                            if (money >= 0.00) {

                                                    data.updateMoney(target1.getUniqueId(), money, argument);
                                                    player.sendMessage(ChatColor.GREEN + "You have set " + target1.getName() +
                                                            ChatColor.GREEN + "'s savings money. Their balance is now : $ " + String.format("%.2f",
                                                            data.getMoney(target1.getUniqueId())));

                                            }

                                            else
                                                player.sendMessage(ChatColor.RED + "Error! The value must be positive.");
                                        }

                                        else {
                                            player.sendMessage(ChatColor.RED + "Error! Invalid argument.");
                                            player.sendMessage(ChatColor.RED + "Syntax : /atm updatemoney (player) (add/set/subtract) (money)");
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                player.sendMessage(ChatColor.RED + "Error! The player is not in the database.");
                                e.printStackTrace();
                            }
                        }
                    }

                    default -> {
                        if (data.isPlayerOnDB(player.getUniqueId())) {
                            atmeverything.atmcontents(player);
                        } else {
                            player.sendMessage(ChatColor.YELLOW + "You don't have a bank account. Do " +
                                    ChatColor.GOLD + "/atm open " + ChatColor.YELLOW + "first.");
                        }
                    }
                }

                if (args.length == 1)
                {
                    if (!args[0].equalsIgnoreCase("open") && !args[0].equalsIgnoreCase("close") &&
                            !args[0].equalsIgnoreCase("commands") && !args[0].equalsIgnoreCase("help") &&
                            !args[0].equalsIgnoreCase("updatemoney") && !args[0].equalsIgnoreCase("checkmoney"))
                    {
                        player.sendMessage(ChatColor.RED + "Invalid command arguments! Type /atm help for list of command arguments.");
                    }
                }

                if (args.length >= 2)
                {
                    if (args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("close") ||
                            args[0].equalsIgnoreCase("commands") || args[0].equalsIgnoreCase("help"))
                    {
                        player.sendMessage(ChatColor.RED + "Too much arguments! Type /atm help for list of command arguments.");
                    }

                    else if (args[0].equalsIgnoreCase("updatemoney") || args[0].equalsIgnoreCase("checkmoney"));

                    else
                        player.sendMessage(ChatColor.RED + "Invalid command arguments! Type /atm help for list of command arguments.");
                }

                if (args.length >= 3)
                {
                    if (args[0].equalsIgnoreCase("checkmoney"))
                    {
                        player.sendMessage(ChatColor.RED + "Too much arguments!");
                        player.sendMessage(ChatColor.RED + "Syntax : /atm checkmoney (player)");
                    }
                }

                return true;
            } else
                sender.sendMessage(ChatColor.RED + "Error! You don't have the following permission : " + permission);
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        List<String> commandArgs = new ArrayList<>();
        List<String> updateMoneyArgs = new ArrayList<>();
        List<String> doubleYes = new ArrayList<>();
        List<String> results = new ArrayList<>();
        List<String> results1 = new ArrayList<>();
        List<String> results2 = new ArrayList<>();
        List<String> allNames = data.getPlayerFromDB();

        switch (args.length)
        {
            case 1 -> //first argument
                    {
                        commandArgs.add(0, "open");
                        commandArgs.add(0, "commands");
                        commandArgs.add(0, "checkmoney");
                        commandArgs.add(0, "help");
                        commandArgs.add(0, "close");
                        commandArgs.add(0, "updatemoney");

                        for (String argResult : commandArgs)
                        {
                            if (argResult.toLowerCase().startsWith(args[0].toLowerCase()))
                            {
                                results.add(argResult);
                            }
                        }

                        return results;
                    }

            case 2 -> // second argument
                    {
                        if (args[0].equalsIgnoreCase("updatemoney") || args[0].equalsIgnoreCase(
                                "checkmoney"))
                        { //only display if /atm updatemoney @ checkmoney
                                for (String Names : allNames)
                                {
                                    if (Names.toLowerCase().startsWith(args[1].toLowerCase()))
                                        results1.add(Names);
                                }
                            if (args[0].equalsIgnoreCase("updatemoney"))
                            {
                                results1.add("@a");
                            }
                            return results1;
                        }
                    }

            case 3 ->
                    {
                        if (args[0].equalsIgnoreCase("updatemoney"))
                        {
                            updateMoneyArgs.add("add");
                            updateMoneyArgs.add("set");
                            updateMoneyArgs.add("subtract");

                            for (String moneyArgs : updateMoneyArgs)
                            {
                                if (moneyArgs.toLowerCase().startsWith(args[2].toLowerCase()))
                                    results2.add(moneyArgs);
                            }
                            return results2;
                        }
                    }

            case 4 ->
                    {
                        if (args[0].equalsIgnoreCase("updatemoney"))
                        {
                            doubleYes.add("0.00");
                            return doubleYes;
                        }
                    }

        }

        if (args.length >= 2)
        {
            if (!args[0].equalsIgnoreCase("updatemoney"))
            {
                doubleYes.add(0, "");
                return doubleYes;
            }
        }

        return null;
    }
}