package itsadamly.noctora.atmthings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class atmeverything {

    public static void atmcontents(Player player){

        Inventory atmgui = Bukkit.createInventory(player, 9, ChatColor.DARK_GREEN + "ATM");
        ItemStack balance = new ItemStack(Material.GOLD_BLOCK);
        ItemStack deposit = new ItemStack(Material.EMERALD_BLOCK);
        ItemStack withdraw  = new ItemStack(Material.DIAMOND_BLOCK);

        ArrayList<String> balDesc = new ArrayList<>();
        ArrayList<String> depDesc = new ArrayList<>();
        ArrayList<String> wdDesc = new ArrayList<>();

        ItemMeta balMeta = balance.getItemMeta();
            balMeta.setDisplayName(ChatColor.YELLOW + "Balance");
            balDesc.add(ChatColor.GRAY + "§oCheck your account balance.");
            balMeta.setLore(balDesc);
        balance.setItemMeta(balMeta);

        ItemMeta depMeta = deposit.getItemMeta();
            depMeta.setDisplayName(ChatColor.GREEN + "Deposit");
            depDesc.add(ChatColor.GRAY + "§oDeposit money into your bank account.");
            depMeta.setLore(depDesc);
        deposit.setItemMeta(depMeta);

        ItemMeta wdMeta = withdraw.getItemMeta();
            wdMeta.setDisplayName(ChatColor.BLUE + "Withdraw");
            wdDesc.add(ChatColor.GRAY + "§oWithdraw money from your bank account.");
            wdMeta.setLore(wdDesc);
        withdraw.setItemMeta(wdMeta);

        atmgui.setItem(1, deposit);
        atmgui.setItem(4, balance);
        atmgui.setItem(7, withdraw);
        player.openInventory(atmgui);

    }

    public static void atmdeposit(Player player){

        Inventory atmgui = Bukkit.createInventory(player, 9, ChatColor.DARK_GREEN + "ATM | Deposit");
        ItemStack ten = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemStack twenty = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemStack fifty  = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemStack hundred  = new ItemStack(Material.BROWN_STAINED_GLASS_PANE);
        ItemStack fivehundred  = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemStack thousand  = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemStack back = new ItemStack(Material.SPYGLASS);
        ItemStack exit = new ItemStack(Material.BARRIER);

        ItemStack[] atm_content = {back, ten, twenty, fifty, hundred, fivehundred, thousand};

        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.YELLOW + "Back");
        back.setItemMeta(backMeta);

        ItemMeta tenMeta = ten.getItemMeta();
        tenMeta.setDisplayName(ChatColor.GREEN + "$ 10.00");
        ten.setItemMeta(tenMeta);

        ItemMeta twentyMeta = twenty.getItemMeta();
        twentyMeta.setDisplayName(ChatColor.GREEN + "$ 20.00");
        twenty.setItemMeta(twentyMeta);

        ItemMeta fiftyMeta = fifty.getItemMeta();
        fiftyMeta.setDisplayName(ChatColor.GREEN + "$ 50.00");
        fifty.setItemMeta(fiftyMeta);

        ItemMeta hundredMeta = hundred.getItemMeta();
        hundredMeta.setDisplayName(ChatColor.GREEN + "$ 100.00");
        hundred.setItemMeta(hundredMeta);

        ItemMeta fhMeta = fivehundred.getItemMeta();
        fhMeta.setDisplayName(ChatColor.GREEN + "$ 500.00");
        fivehundred.setItemMeta(fhMeta);

        ItemMeta thousandMeta = thousand.getItemMeta();
        thousandMeta.setDisplayName(ChatColor.GREEN + "$ 1,000.00");
        thousand.setItemMeta(thousandMeta);

        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.RED + "Exit");
        exit.setItemMeta(exitMeta);

        atmgui.setContents(atm_content);
        atmgui.setItem(8, exit);
        player.openInventory(atmgui);
    }

    public static void atmwithdraw(Player player){

        Inventory atmgui = Bukkit.createInventory(player, 9, ChatColor.DARK_GREEN + "ATM | Withdraw");
        ItemStack ten = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemStack twenty = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemStack fifty  = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemStack hundred  = new ItemStack(Material.BROWN_STAINED_GLASS_PANE);
        ItemStack fivehundred  = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemStack thousand  = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemStack back = new ItemStack(Material.SPYGLASS);
        ItemStack exit = new ItemStack(Material.BARRIER);

        ItemStack[] atm_content = {back, ten, twenty, fifty, hundred, fivehundred, thousand};

        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.YELLOW + "Back");
        back.setItemMeta(backMeta);

        ItemMeta tenMeta = ten.getItemMeta();
        tenMeta.setDisplayName(ChatColor.GREEN + "$ 10.00");
        ten.setItemMeta(tenMeta);

        ItemMeta twentyMeta = twenty.getItemMeta();
        twentyMeta.setDisplayName(ChatColor.GREEN + "$ 20.00");
        twenty.setItemMeta(twentyMeta);

        ItemMeta fiftyMeta = fifty.getItemMeta();
        fiftyMeta.setDisplayName(ChatColor.GREEN + "$ 50.00");
        fifty.setItemMeta(fiftyMeta);

        ItemMeta hundredMeta = hundred.getItemMeta();
        hundredMeta.setDisplayName(ChatColor.GREEN + "$ 100.00");
        hundred.setItemMeta(hundredMeta);

        ItemMeta fhMeta = fivehundred.getItemMeta();
        fhMeta.setDisplayName(ChatColor.GREEN + "$ 500.00");
        fivehundred.setItemMeta(fhMeta);

        ItemMeta thousandMeta = thousand.getItemMeta();
        thousandMeta.setDisplayName(ChatColor.GREEN + "$ 1,000.00");
        thousand.setItemMeta(thousandMeta);

        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.RED + "Exit");
        exit.setItemMeta(exitMeta);

        atmgui.setContents(atm_content);
        atmgui.setItem(8, exit);
        player.openInventory(atmgui);
    }

    public static void atmCheckBal(Player player)
    {
        double money = atmcalc.data.getMoney(player.getUniqueId());
        Inventory atmgui = Bukkit.createInventory(player,9, ChatColor.DARK_GREEN +
                "ATM | Your balance is $ " + ChatColor.GREEN + String.format("%.2f", money) + ChatColor.DARK_GREEN + ".");

        ItemStack yes = new ItemStack(Material.LIME_CONCRETE);
        ItemStack no = new ItemStack(Material.BARRIER);

        ItemMeta yesMeta = yes.getItemMeta();
        yesMeta.setDisplayName(ChatColor.GREEN + "Continue");
        yes.setItemMeta(yesMeta);

        ItemMeta noMeta = no.getItemMeta();
        noMeta.setDisplayName(ChatColor.RED + "Close");
        no.setItemMeta(noMeta);

        atmgui.setItem(2, yes);
        atmgui.setItem(6, no);
        player.openInventory(atmgui);
    }

    public static void atmAccount(Player player){

        Inventory atmgui = Bukkit.createInventory(player, 9, ChatColor.DARK_GREEN + "Bank");
        ItemStack openAcc = new ItemStack(Material.LIME_DYE);
        ItemStack closeAcc = new ItemStack(Material.RED_DYE);

        ArrayList<String> openAccDesc = new ArrayList<>();
        ArrayList<String> closeAccDesc = new ArrayList<>();

        ItemMeta openAccMeta = openAcc.getItemMeta();
        openAccMeta.setDisplayName(ChatColor.GREEN + "Open Account");
        openAccDesc.add(ChatColor.GRAY + "§oOpen a bank account");
        openAccDesc.add(ChatColor.YELLOW + "You need at least $ 20.00 to open an account.");
        openAccMeta.setLore(openAccDesc);
        openAcc.setItemMeta(openAccMeta);

        ItemMeta closeAccMeta = closeAcc.getItemMeta();
        closeAccMeta.setDisplayName(ChatColor.BLUE + "Close Account");
        closeAccDesc.add(ChatColor.GRAY + "§oClose your bank account and retrieve all your money in your account.");
        closeAccMeta.setLore(closeAccDesc);
        closeAcc.setItemMeta(closeAccMeta);

        atmgui.setItem(2, openAcc);
        atmgui.setItem(6, closeAcc);
        player.openInventory(atmgui);
    }
}
