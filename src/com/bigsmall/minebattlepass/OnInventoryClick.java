/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bigsmall.minebattlepass;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author asier
 */
public class OnInventoryClick implements Listener {

    public Main instance;

    public OnInventoryClick(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String title = e.getView().getTitle();

        if (title.equals(ChatColor.DARK_GREEN + "Battle Pass")) {
            e.setCancelled(true);
            int slot = e.getRawSlot();
            int previousLevel = instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level");
            if (previousLevel == slot) {

                boolean tieneTodo = false;

                //items
                List<ItemStack> itemsReq = (List<ItemStack>) instance.getConfig().getList("levels." + (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") + 1) + ".requirements.items");

                //UMLCoins
                int UMLCoins = Main.getEco().getUMLCoins(player);
                int UMLCoinsReq = instance.getConfig().getInt("levels." + (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") + 1) + ".requirements.money.umlcoins");

                //ECoins
                int ECoins = Main.getEco().getECOins(player);
                int ECoinsReq = instance.getConfig().getInt("levels." + (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") + 1) + ".requirements.money.ecoins");

                //xp
                int level = player.getLevel();
                int levelReq = instance.getConfig().getInt("levels." + (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") + 1) + ".requirements.xp");

                //Comprobar todo menos items
                if (UMLCoinsReq <= UMLCoins && ECoinsReq <= ECoins && levelReq <= level) {
                    //comprobar items
                    if (itemsReq == null) {
                        tieneTodo = true;
                    } else {
                        if (!player.getInventory().isEmpty()) {
                            boolean hasItems = true;
                            for (ItemStack item : itemsReq) {

                                if (item != null) {
                                    if (!player.getInventory().containsAtLeast(item, item.getAmount())) {
                                        hasItems = false;
                                    }
                                }
                            }
                            if (hasItems) {
                                tieneTodo = true;
                            }
                        }

                    }

                }

                if (tieneTodo) {

                    //quitar items, xp, money...
                    //money
                    Main.eco.addECOins(player, -ECoinsReq);
                    Main.eco.addUMLCoins(player, -UMLCoinsReq);

                    //xp
                    player.setLevel(level - levelReq);

                    //items
                    itemsReq = (List<ItemStack>) instance.getConfig().getList("levels." + (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") + 1) + ".requirements.items");
                    for (ItemStack item : itemsReq) {
                        if (item != null) {
                            player.getInventory().removeItemAnySlot(item);
                        }
                    }

                    List<ItemStack> itemsPrice = (List<ItemStack>) instance.getConfig().getList("levels." + (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") + 1) + ".prices.items");
                    int slotsNecesitados = 0;
                    for(ItemStack itemP: itemsPrice){
                        slotsNecesitados += Math.ceil(itemP.getAmount()/64);
                    }
                    
                    Inventory inv = player.getInventory();
                    int slotsLibres = getEmptySlots(inv);
                    
                    //si el jugador tiene espacio
                    
                    if (slotsLibres >= slotsNecesitados) {
                        // otorgar items, permisos, xp y dinero + subir de nivel
                        
                        //items
                        for(ItemStack itemP: itemsPrice){
                            player.getInventory().addItem(itemP);
                        }
                        player.updateInventory();
                        
                        //money
                        Main.eco.addUMLCoins(player, instance.getConfig().getInt("levels." + (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") + 1) + ".prices.money.umlcoins"));
                        Main.eco.addECOins(player, instance.getConfig().getInt("levels." + (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") + 1) + ".prices.money.ecoins"));

                        //xp
                        player.setLevel(player.getLevel() + instance.getConfig().getInt("levels." + (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") + 1) + ".prices.xp"));
                        
                        List<String> permissionsPrice = (List<String>) instance.getConfig().getList("levels." + (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") + 1) + ".prices.permissions.codes");
                        
                        for(String perm : permissionsPrice){
                            Main.getPerms().playerAdd(null, player, perm);
                        }
                        

                        instance.getConfig().set("jugadores." + player.getUniqueId().toString() + ".level", previousLevel + 1);
                        instance.saveConfig();

                        //reabre el inventario
                        player.closeInventory();
                        Main.util.openInventory(player);

                        //Mensaje y sonido
                        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + player.getName() + ChatColor.RESET + " ha subido al nivel" + ChatColor.GREEN + " " + ChatColor.BOLD + (previousLevel + 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.5f);
                        
                        player.setPlayerListName(ChatColor.DARK_AQUA+"["+ChatColor.WHITE+instance.getConfig().getInt("jugadores."+player.getUniqueId().toString()+".level")+ChatColor.DARK_AQUA+"] "+ChatColor.AQUA+player.getName()+ChatColor.RESET);
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "No tienes lo necesario para subir de nivel");
                    player.closeInventory();
                }

            }
        }
    }
    
    public int getEmptySlots(Inventory inventory) {
        int i = 0;
        for (ItemStack is : inventory.getContents()) {
            if (is == null) {
                continue;
            }
            i++;
        }
        return i;
    }
}
