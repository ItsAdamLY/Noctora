package itsadamly.noctora.commands;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import itsadamly.noctora.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class playerinfo extends BukkitRunnable implements CommandExecutor {

    public static boolean displayBoard = true;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                boardToggle(player);
            } else if (args[0].equalsIgnoreCase("toggle")) {
                if (args.length == 1)
                {
                    boardToggle(player);
                }
                else
                {
                    Player otherP = Bukkit.getPlayerExact(args[1]);
                    if (otherP instanceof Player)
                    {
                        boardToggle(otherP);
                        if (displayBoard)
                            player.sendMessage(ChatColor.GREEN + "You have toggled on " + otherP.getName() + "'s scoreboard!");
                        else
                            player.sendMessage(ChatColor.GREEN + "You have toggled off " + otherP.getName() + "'s scoreboard!");
                    }
                    else
                        player.sendMessage(ChatColor.RED + "Error! That person is not online.");
                }
            }
            else
            {
                Player otherP = Bukkit.getPlayerExact(args[0]);
                if (otherP instanceof Player)
                {
                    boardToggle(otherP);
                    if (displayBoard)
                        player.sendMessage(ChatColor.GREEN + "You have toggled on " + otherP.getName() + "'s scoreboard!");
                    else
                        player.sendMessage(ChatColor.GREEN + "You have toggled off " + otherP.getName() + "'s scoreboard!");
                }
                else
                    player.sendMessage(ChatColor.RED + "Error! That person is not online.");
            }
        }
        return true;

    }

    public void boardToggle(Player player) {
        taskfunction();
        if (displayBoard) {
            BPlayerBoard board = deletebox(player);
            player.sendMessage(ChatColor.GREEN + "The player infoboard has disabled!");

            displayBoard = false; // make board display = false

        } else {
            BPlayerBoard board = infobox(player);
            player.sendMessage(ChatColor.GREEN + "The player infoboard has enabled!");
            displayBoard = true; // make board display = true
        }
    }

    public BPlayerBoard infobox(Player player) {
        String rank = PlaceholderAPI.setPlaceholders(player, "%luckperms_primary_group_name%");
        String money = PlaceholderAPI.setPlaceholders(player, "%vault_eco_balance%");

        BPlayerBoard board = Netherboard.instance().createBoard(player, "§3§lNoctora");
        board.clear();
        board.set("     ", 11);
        board.set("§a§l§m-■---------■-§r", 10);
        board.set(ChatColor.DARK_AQUA + "Name : " + ChatColor.YELLOW + player.getName(), 9);
        board.set(" ", 8);
        board.set(ChatColor.DARK_AQUA + "Rank : " + rank, 7);
        board.set("", 6);
        board.set(ChatColor.DARK_AQUA + "Money : " + ChatColor.GREEN + "$ " + money, 5);
        board.set("§a§l§m-■---------■-", 3);
        board.set("    ", 2);
        board.set("§e§oIP @ Web here",1);

        return null;
    }

    public BPlayerBoard deletebox(Player player) {
        Netherboard.instance().deleteBoard(player);
        return null;
    }

    public void taskfunction()
    {
        playerinfo task = new playerinfo(); //create task instance/example

            task.runTaskTimerAsynchronously(Main.getiP(),0,50); //instancemethod(), delay, every how many Ticks
            // 20 ticks = 1s
    }

    public void run()
    {
        for (Player player : Bukkit.getOnlinePlayers()) // keep on running if there is/are player(s)
        {
            if (player != null)
            infobox(player);
        }
    }
}

