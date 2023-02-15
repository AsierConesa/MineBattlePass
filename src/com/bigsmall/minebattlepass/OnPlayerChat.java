/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bigsmall.minebattlepass;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author asier
 */
public class OnPlayerChat implements Listener {

    public Main instance;

    public OnPlayerChat(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String prefix = ChatColor.DARK_AQUA+"["+ChatColor.WHITE+instance.getConfig().getInt("jugadores."+e.getPlayer().getUniqueId().toString()+".level")+ChatColor.DARK_AQUA+"] "+ChatColor.RESET;
        e.setFormat(prefix + ChatColor.AQUA+e.getPlayer().getName() + ": " + ChatColor.RESET + e.getMessage());
    }
    
}
