package me.itsadamly.noctora.atmthings;

import me.itsadamly.noctora.Main;
import me.itsadamly.noctora.sqlthing.sqltask;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class atmcalc {

    static double rate = 0.025;

    public static sqltask data = new sqltask(); // always make sure to get the instance method!

    public static void atmDeposit(Player player, double money) {
        Economy economy = Main.getEconomy(); //getting instance
        if (economy.getBalance(player) < money) {
            player.sendMessage(ChatColor.RED + "Insufficient money.");
        } else {
            economy.withdrawPlayer(player, money);
            double bankMoney = data.getMoney(player.getUniqueId());
            data.updateMoney(player.getUniqueId(), money);
            player.sendMessage(ChatColor.GREEN + "Transaction success! Your new bank balance is : " + ChatColor.YELLOW + "$ " + String.format("%.2f", bankMoney + money));
            player.closeInventory();
        }
    }

    public static void atmWithdraw(Player player, double money) {
        Economy economy = Main.getEconomy(); //getting instance
        double bankMoney = data.getMoney(player.getUniqueId());
        if (bankMoney < money) {
            player.sendMessage(ChatColor.RED + "There is not enough balance in your account.");
        } else {
            economy.depositPlayer(player, money);
            data.updateMoney(player.getUniqueId(), -money);
            player.sendMessage(ChatColor.GREEN + "Transaction success! Your new bank balance is : " + ChatColor.YELLOW + "$ " + String.format("%.2f", bankMoney - money));
            player.closeInventory();
        }
    }
}