package me.itsadamly.noctora.events;

import me.itsadamly.noctora.atmthings.atmCard;
import me.itsadamly.noctora.atmthings.atmOpenCloseAcc;
import me.itsadamly.noctora.atmthings.atmcalc;
import me.itsadamly.noctora.atmthings.atmeverything;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class menuClickEvent implements Listener {

    @EventHandler
    public void menuEvent(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        double money = atmcalc.data.getMoney(player.getUniqueId());

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "ATM")) {
            switch (e.getSlot()) {
                case 1 -> {
                    atmeverything.atmdeposit(player);
                    e.setCancelled(true);
                }
                case 4 -> {
                    atmeverything.atmCheckBal(player);
                    player.sendMessage();
                    e.setCancelled(true);
                }
                case 7 -> {
                    atmeverything.atmwithdraw(player);
                    e.setCancelled(true);
                }
                default -> e.setCancelled(true);
            }
        } else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "ATM | Deposit")) {
            switch (e.getSlot()) {
                case 0 -> {
                    atmeverything.atmcontents(player);
                    e.setCancelled(true);
                }

                case 1 -> {
                    atmcalc.atmDeposit(player, 10.00);
                    e.setCancelled(true);
                }

                case 2 -> {
                    atmcalc.atmDeposit(player, 20.00);
                    e.setCancelled(true);
                }

                case 3 -> {
                    atmcalc.atmDeposit(player, 50.00);
                    e.setCancelled(true);
                }

                case 4 -> {
                    atmcalc.atmDeposit(player, 100.00);
                    e.setCancelled(true);
                }

                case 5 -> {
                    atmcalc.atmDeposit(player, 500.00);
                    e.setCancelled(true);
                }

                case 6 -> {
                    atmcalc.atmDeposit(player, 1000.00);
                    e.setCancelled(true);
                }

                case 8 -> {
                    player.closeInventory();
                    e.setCancelled(true);
                }

                default -> e.setCancelled(true);
            }
        }
            else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "ATM | Withdraw")) {
            switch (e.getSlot()) {
                case 0 -> {
                    atmeverything.atmcontents(player);
                    e.setCancelled(true);
                }

                case 1 -> {
                    atmcalc.atmWithdraw(player, 10.00);
                    e.setCancelled(true);
                }

                case 2 -> {
                    atmcalc.atmWithdraw(player, 20.00);
                    e.setCancelled(true);
                }

                case 3 -> {
                    atmcalc.atmWithdraw(player, 50.00);
                    e.setCancelled(true);
                }

                case 4 -> {
                    atmcalc.atmWithdraw(player, 100.00);
                    e.setCancelled(true);
                }

                case 5 -> {
                    atmcalc.atmWithdraw(player, 500.00);
                    e.setCancelled(true);
                }

                case 6 -> {
                    atmcalc.atmWithdraw(player, 1000.00);
                    e.setCancelled(true);
                }

                case 8 -> {
                    player.closeInventory();
                    e.setCancelled(true);
                }

                default -> e.setCancelled(true);
                }
        }
        else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN +
                "ATM | Your balance is $ " + ChatColor.GREEN + String.format("%.2f", money) + ChatColor.DARK_GREEN + "."))
        {
            switch (e.getSlot()) {
                case 2 -> {
                    atmeverything.atmcontents(player);
                    e.setCancelled(true);
                }
                case 6 -> {
                    player.closeInventory();
                    e.setCancelled(true);
                }

                default -> e.setCancelled(true);
            }
        }

        else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "Bank"))
        {
                switch (e.getSlot()) {
                    case 2 -> {
                        String code = atmCard.code();
                        atmOpenCloseAcc.atmOpenAcc(player, code);
                        e.setCancelled(true);
                        player.closeInventory();
                    }
                    case 6 -> {
                        atmOpenCloseAcc.atmCloseAcc(player);
                        e.setCancelled(true);
                        player.closeInventory();
                    }
                    default -> e.setCancelled(true);
                }
        }
    }
}
