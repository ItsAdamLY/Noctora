package itsadamly.noctora.atmthings;

import itsadamly.noctora.Main;
import itsadamly.noctora.sqlthing.sqltask;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class atmOpenCloseAcc {

    private static final sqltask data = new sqltask();
    private static final Economy economy = Main.getEconomy();

    public static double initialDeposit = 20.00;

    public static void atmOpenAcc(Player player, String cardCode)
    {

       try {

           UUID uuid = player.getUniqueId();

           double money = economy.getBalance(player);
           boolean hasAccount = data.isPlayerOnDB(uuid);

           if (!hasAccount) //if the player hasn't made a bank account yet
           {
               if (money < initialDeposit) {
                   player.sendMessage(ChatColor.RED + "Error! You don't have enough money to open an account.");
               } else {
                   economy.withdrawPlayer(player, initialDeposit); // withdraw FROM player, aka player do deposit
                   data.createPlayer(player, cardCode);
                   data.updateMoney(uuid, initialDeposit);
                   player.getInventory().addItem(atmCard.atmCardDetails(player, cardCode));
                   player.sendMessage(ChatColor.GREEN + "Success! You have opened a bank account.");
               }
           }
            else
               player.sendMessage(ChatColor.YELLOW + "You already have a bank account!");
        } catch (Exception e) {
           e.printStackTrace();
       }

    }

    public static void atmCloseAcc(Player player)
    {
        UUID uuid = player.getUniqueId();
        boolean hasAcc = data.isPlayerOnDB(uuid);

        if (hasAcc)
        {
            double getMoneyInBank = data.getMoney(uuid);
            data.deletePlayer(player);
            economy.depositPlayer(player, getMoneyInBank);
            player.sendMessage(ChatColor.GREEN + "Success! You have closed your account.");
            player.sendMessage(ChatColor.YELLOW + "$ " + String.format("%.2f", getMoneyInBank) + ChatColor.GREEN + " has been retrieved from your account.");
        }
        else
            player.sendMessage(ChatColor.YELLOW + "You don't have a bank account!");

    }

}
