/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bigsmall.minebattlepass;

import com.bigsmall.umleconomy.UMLEconomy;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author asier
 */
public class Comandos implements CommandExecutor {

    private final Main instance;
    private final UMLEconomy eco;

    public Comandos(Main instance, UMLEconomy eco) {
        this.eco = eco;
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equals("level")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                Main.util.openInventory(player);
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MineBattlePass no permite comandos desde la consola");
            }
        }

        if (label.equals("mbp")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("minebattlepass.admin")) {
                    eco.addECOins(player, 50);

                    if (args.length == 0) {
                        player.sendMessage(ChatColor.BLUE + "/mbp reload: " + ChatColor.DARK_AQUA + "recarga la configuración del plugin");
                        player.sendMessage(ChatColor.BLUE + "/mbp setReq (type) (level) [values]: " + ChatColor.DARK_AQUA + "add values to a level");
                        player.sendMessage(ChatColor.BLUE + "/mbp setPrice (type) (level) [values]: " + ChatColor.DARK_AQUA + "add values to a level");
                    } else if (args.length == 1) {
                        if (args[0].equals("reload")) {
                            instance.reloadConfig();
                            instance.saveConfig();
                            player.sendMessage(ChatColor.GREEN + "Reloaded!");
                        }
                    } else if (args.length > 1) {

                        //mbp setReq UMLCoins (level) [number]
                        //mbp setReq ECoins (level) [number]
                        //mbp setReq XP (level) [number]
                        //mbp setReq items (level)
                        //mbp setPrice UMLCoins (level) [number]
                        //mbp setPrice ECoins (level) [number]
                        //mbp setPrice XP (level) [number]
                        //mbp setPrice Permissions (level) [code] [explanation]
                        //mbp setPrice items (level) [number]
                        comprobaciones:
                        if (args.length == 3 && args[1].equalsIgnoreCase("items")) {
                            if(isInteger(args[2])){
                                break comprobaciones;
                            }
                        } 
                        else if(args.length == 4 && !args[1].equalsIgnoreCase("Permissions")){
                            if(isInteger(args[3])){
                                if(isInteger(args[2])){
                                    break comprobaciones;
                                }
                            }
                        }
                        else if((args.length >= 5 && args[1].equalsIgnoreCase("Permissions"))){
                            if(isInteger(args[2])){
                                break comprobaciones;
                            }
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Incorrect syntax, type /mbp to see the command list");
                            return true;
                        }

                        if (args[0].equalsIgnoreCase("setReq")) {

                            boolean valid = true;
                            String path = "levels." + args[2] + ".requirements.";

                            switch (args[1].toLowerCase()) {
                                case "umlcoins" -> {
                                    path = path + "money.umlcoins";
                                }
                                case "ecoins" -> {
                                    path = path + "money.ecoins";
                                }
                                case "xp" -> {
                                    path = path + "xp";
                                }
                                case "items" -> {
                                    path = path + "items";
                                }
                                default -> {
                                    valid = false;
                                    player.sendMessage(ChatColor.RED + "Incorrect syntax, type /mbp to see the command list");
                                }
                            }

                            if (valid) {
                                if (args[1].equalsIgnoreCase("items")) {
                                    if (player.getInventory().getItemInMainHand().getAmount() > 0) {
                                        List<ItemStack> items = (List<ItemStack>) instance.getConfig().getList(path);
                                        if(items == null){
                                            items = new ArrayList<>();
                                        }
                                        
                                        ItemStack playerItem = player.getInventory().getItemInMainHand();
                                        if(args.length == 4){
                                            if(isInteger(args[3])){
                                                playerItem.setAmount(Integer.parseInt(args[3]));
                                            }
                                            
                                        }
                                        
                                                
                                        items.add(playerItem);
                                        
                                        instance.getConfig().set(path, items);
                                        
                                        
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You need to hold a stack of items in your hand");
                                        return true;
                                    }
                                } else {
                                    instance.getConfig().set(path, Integer.parseInt(args[3]));
                                    
                                }
                                
                                player.sendMessage(ChatColor.GREEN + "level updated successfully");
                                instance.saveConfig();
                            }

                        } else if (args[0].equalsIgnoreCase("setPrice")) {

                            boolean valid = true;
                            String path = "levels." + args[2] + ".prices.";
                            String path2 = null;

                            switch (args[1].toLowerCase()) {
                                case "umlcoins" -> {
                                    path = path + "money.umlcoins";
                                }
                                case "ecoins" -> {
                                    path = path + "money.ecoins";
                                }
                                case "xp" -> {
                                    path = path + "xp";
                                }
                                case "permissions" -> {
                                    path = path + "permissions.";
                                    path2 = path;
                                    path = path + "codes";
                                    path2 = path2 + "description";
                                }
                                case "items" -> {
                                    path = path + "items";
                                }
                                default -> {
                                    valid = false;
                                    player.sendMessage(ChatColor.RED + "Incorrect syntax, type /mbp to see the command list");
                                }

                            }

                            if (valid) {
                                if (args[1].equalsIgnoreCase("items")) {
                                    if (player.getInventory().getItemInMainHand().getAmount() > 0) {
                                        List<ItemStack> items = (List<ItemStack>) instance.getConfig().getList(path);
                                        if(items == null){
                                            items = new ArrayList<>();
                                        }
                                        
                                        ItemStack playerItem = player.getInventory().getItemInMainHand();
                                        if(args.length == 4){
                                            if(isInteger(args[3])){
                                                playerItem.setAmount(Integer.parseInt(args[3]));
                                            }
                                            
                                        }
                                        
                                        items.add(playerItem);
                                        instance.getConfig().set(path, items);
                                        
                                    }
                                } else {
                                    if (args[1].equalsIgnoreCase("permissions")) {

                                        List<String> perms = (List<String>) instance.getConfig().getList(path);
                                        if(perms == null){
                                            perms = new ArrayList<>();
                                        }
                                        
                                        perms.add(args[3]);
                                        instance.getConfig().set(path, perms);
                                        if (path2 != null) {
                                            List<String> perms2 = (List<String>) instance.getConfig().getList(path2);
                                            if(perms2 == null){
                                                perms2 = new ArrayList<>();
                                            }
                                            
                                            String descripcion = args[4];
                                            for (int i = 5; i < args.length; i++){
                                                descripcion = descripcion + " " + args[i];
                                            }
                                            
                                            perms2.add(descripcion);
                                            instance.getConfig().set(path2, perms2);
                                        }
                                    } else {
                                        instance.getConfig().set(path, Integer.parseInt(args[3]));
                                    }
                                }
                                player.sendMessage(ChatColor.GREEN + "level updated successfully");
                                instance.saveConfig();
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Incorrect syntax, type /mbp to see the command list");
                        }

                    }
                } else {
                    player.sendMessage(ChatColor.RED + "No tienes permiso para ejecutar ese comando");
                }
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MineBattlePass no permite comandos desde la consola");
            }
        }
        return true;
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
