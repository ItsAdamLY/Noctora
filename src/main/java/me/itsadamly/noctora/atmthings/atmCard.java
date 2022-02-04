package me.itsadamly.noctora.atmthings;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

public class atmCard {

    public static ItemStack atmCardDetails(Player player, String atmCode)
    {
        try
        {
            ItemStack atmCard = new ItemStack(Material.NAME_TAG);
            ArrayList<String> atmMeta = new ArrayList<>();

            ItemMeta atmCardMeta = atmCard.getItemMeta();
            atmCardMeta.setDisplayName(ChatColor.GREEN + player.getName() + "'s ATM Card");
            atmMeta.add(ChatColor.DARK_GRAY + "§o" + atmCode);
            atmMeta.add(ChatColor.RED + "MAST" + ChatColor.GOLD + "ER" + ChatColor.YELLOW + "CARD");
            atmCardMeta.setLore(atmMeta);
            atmCard.setItemMeta(atmCardMeta);

            return atmCard;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static String code()
    {
        Random random = new Random();

        int randomInt = (random.ints(1000,9999).findAny().getAsInt());
        int randomInt2 = (random.ints(1000,9999).findAny().getAsInt());
        int randomInt3 = (random.ints(1000,9999).findAny().getAsInt());
        char dash = '-';

        return "5" + (random.ints(100,999).findAny().getAsInt()) + dash + randomInt + dash + randomInt2 + dash + randomInt3;
    }

}
