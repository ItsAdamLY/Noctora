package me.itsadamly.noctora.atmthings;

import me.itsadamly.noctora.sqlthing.sqltask;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class atmSign implements Listener {

    sqltask data = new sqltask();
    public static String signPrefix = "[BANK]";

    @EventHandler
    public void onSignEvent(SignChangeEvent e) {
        boolean hasPermission = e.getPlayer().hasPermission("Noctora.BankSign");

        for (int i = 0; i < 4; i++) {
            String signLine = e.getLine(i);
            if (signLine != null && !signLine.equalsIgnoreCase("")) {

                if (e.getLine(0).equalsIgnoreCase(signPrefix) ||
                e.getLine(0).equalsIgnoreCase("§a" + signPrefix)) {
                    if (hasPermission) {
                        e.setLine(0, ChatColor.GREEN + signPrefix);

                        if (e.getLine(1).equalsIgnoreCase("Account"))
                            {
                                e.setLine(1, ChatColor.AQUA + "Account");
                                e.setLine(2, e.getLine(2));
                            }
                        else if (e.getLine(1).equalsIgnoreCase("ATM"))
                            {
                                e.setLine(1, ChatColor.AQUA + "ATM");
                                e.setLine(2, e.getLine(2));
                            }
                        }
                    else {
                        e.setLine(1, ChatColor.DARK_RED + "No Perms!");
                    }
                    }
                }
            }
        }

    @EventHandler
    public void playerClickSignEvent(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        String code = data.getCardID(player.getUniqueId());

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        } else {
            if (e.getClickedBlock().getState() instanceof Sign) {
                ItemStack card = player.getInventory().getItemInMainHand();
                Sign getSign = (Sign) e.getClickedBlock().getState();

                if (getSign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + signPrefix)) {
                    if (getSign.getLine(1).equalsIgnoreCase(ChatColor.AQUA + "Account")) {
                        atmeverything.atmAccount(player);
                    }
                    else if (getSign.getLine(1).equalsIgnoreCase(ChatColor.AQUA + "ATM")) {

                        if (card.getType() == Material.NAME_TAG && card.hasItemMeta())
                                {
                                    if (card.getItemMeta().getLore().get(0).
                                            equalsIgnoreCase(ChatColor.DARK_GRAY + "§o" + code))
                                    {
                                        atmeverything.atmcontents(player);
                                    }

                                    else
                                        player.sendMessage(ChatColor.RED + "This card has invalid ID.");
                                }
                        else {
                            player.getInventory().getItemInMainHand();
                            player.sendMessage(ChatColor.YELLOW + "You need a bank card to access the ATM.");
                        }
                    }
                }
            }
        }
    }
}
