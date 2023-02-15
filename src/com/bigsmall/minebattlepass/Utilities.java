/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bigsmall.minebattlepass;

import static com.bigsmall.minebattlepass.Main.instance;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author asier
 */
public class Utilities {

    public Utilities() {
        //empty constructor
    }

    public void openInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "Battle Pass");

        for (int i = 0; i < 54; i++) {
            if (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") >= i && i != 0) {
                // comprobar si tiene lo requerido o no aqui

                List<String> lore = new ArrayList<>();

                int umlcoins = instance.getConfig().getInt("levels." + (i+1) + ".requirements.money.umlcoins");
                int ecoins = instance.getConfig().getInt("levels." + (i+1) + ".requirements.money.ecoins");
                int xp = instance.getConfig().getInt("levels." + (i+1) + ".requirements.xp");
                List<ItemStack> items = (List<ItemStack>) instance.getConfig().getList("levels." + (i+1) + ".requirements.items");

                if (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") < i+1) {
                    lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Requerimientos:");

                    if (umlcoins > 0) {
                        lore.add(ChatColor.DARK_AQUA + "UML Coins: " + ChatColor.AQUA + umlcoins + "⛀");
                    }
                    if (ecoins > 0) {
                        lore.add(ChatColor.DARK_GREEN + "ECoins: " + ChatColor.GREEN + ecoins + "❂");
                    }
                    if (xp > 0) {
                        lore.add(ChatColor.GREEN + "" + xp + "" + ChatColor.WHITE + " de experiencia");
                    }
                    if (items != null) {
                        if (!items.isEmpty()) {
                            for (int j = 0; j < items.size(); j++) {
                                ItemStack item = items.get(j);
                                String line = "";
                                line = line + ChatColor.BLUE + item.getAmount() + " x ";

                                boolean isCustom = false;
                                if (item.hasItemMeta()) {
                                    if (item.getItemMeta().hasDisplayName()) {
                                        isCustom = true;
                                    }
                                }
                                if (isCustom) {
                                    line = line + item.getItemMeta().getDisplayName();
                                } else {
                                    String name = item.getType().toString().replace('_', ' ').toLowerCase();
                                    String capital = item.getType().toString().substring(0, 1).toUpperCase();
                                    name = capital + name.substring(1);
                                    line = line + name;
                                }
                                lore.add(line);
                            }
                        }
                    }

                    lore.add(ChatColor.BLACK + ".");
                }

                int umlcoinsP = instance.getConfig().getInt("levels." + (i+1) + ".prices.money.umlcoins");
                int ecoinsP = instance.getConfig().getInt("levels." + (i+1) + ".prices.money.ecoins");
                int xpP = instance.getConfig().getInt("levels." + (i+1) + ".prices.xp");
                List<String> permissionsP = instance.getConfig().getStringList("levels." + (i+1) + ".prices.permissions.description");
                List<ItemStack> itemsP = (List<ItemStack>) instance.getConfig().getList("levels." + (i+1) + ".prices.items");

                if (umlcoinsP > 0 || ecoinsP > 0 || xpP > 0) {
                    lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Recompensas:");
                }
                if (umlcoinsP > 0) {
                    lore.add(ChatColor.DARK_AQUA + "UML Coins: " + ChatColor.AQUA + umlcoinsP + "⛀");
                }
                if (ecoinsP > 0) {
                    lore.add(ChatColor.DARK_GREEN + "ECoins: " + ChatColor.GREEN + ecoinsP + "❂");
                }
                if (xpP > 0) {
                    lore.add(ChatColor.GREEN + "" + xpP + "" + ChatColor.WHITE + " de experiencia");
                }

                if (itemsP != null) {
                    if (!itemsP.isEmpty()) {
                        for (int j = 0; j < itemsP.size(); j++) {
                            ItemStack item = itemsP.get(j);
                            String line = "";
                            line = line + ChatColor.BLUE + item.getAmount() + " x ";

                            boolean isCustom = false;
                            if (item.hasItemMeta()) {
                                if (item.getItemMeta().hasDisplayName()) {
                                    isCustom = true;
                                }
                            }
                            if (isCustom) {
                                line = line + item.getItemMeta().getDisplayName();
                            } else {
                                String name = item.getType().toString().replace('_', ' ').toLowerCase();
                                String capital = item.getType().toString().substring(0, 1).toUpperCase();
                                name = capital + name.substring(1);
                                line = line + name;
                            }
                            lore.add(line);
                        }
                    }
                }
                if (permissionsP != null) {
                    if (!permissionsP.isEmpty()) {
                        for (int j = 0; j < permissionsP.size(); j++) {
                            lore.add(ChatColor.AQUA + permissionsP.get(j));
                        }
                    }
                }

                if((instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level")) == i){
                    ItemStack item = new ItemStack(Material.getMaterial(instance.getConfig().getString("textures.conseguible.material")));
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.YELLOW + "Nivel " + (i + 1));
                    itemMeta.setCustomModelData(instance.getConfig().getInt("textures.conseguible.customModelData"));
                    item.setItemMeta(itemMeta);
                    item.setLore(lore);
                    inv.setItem(i, item);
                    
                }
                else{
                    ItemStack item = new ItemStack(Material.getMaterial(instance.getConfig().getString("textures.conseguido.material")));
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.GREEN + "Nivel " + (i + 1));
                    itemMeta.setCustomModelData(instance.getConfig().getInt("textures.conseguido.customModelData"));
                    item.setItemMeta(itemMeta);
                    item.setLore(lore);
                    inv.setItem(i, item);
                }
                
                
                
                

            } else if (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") < (i + 1)) {
                ItemStack item = new ItemStack(Material.getMaterial(instance.getConfig().getString("textures.noConseguido.material")));
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(ChatColor.RED + "Nivel " + (i + 1));
                itemMeta.setCustomModelData(instance.getConfig().getInt("textures.noConseguido.customModelData"));
                item.setItemMeta(itemMeta);
                inv.setItem(i, item);
            } else if (instance.getConfig().getInt("jugadores." + player.getUniqueId().toString() + ".level") >= (i + 1)) {
                ItemStack item = new ItemStack(Material.getMaterial(instance.getConfig().getString("textures.conseguido.material")));
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Nivel " + (i + 1));
                itemMeta.setCustomModelData(instance.getConfig().getInt("textures.conseguido.customModelData"));
                item.setItemMeta(itemMeta);
                inv.setItem(i, item);
            }
        }

        player.openInventory(inv);
    }
}
