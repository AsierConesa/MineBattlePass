/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bigsmall.minebattlepass;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author asier
 */
public class OnPlayerJoin implements Listener{
    public Main instance;
    
    public OnPlayerJoin(Main instance){
        this.instance = instance;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        
        Player player = e.getPlayer();
        String uuid = player.getUniqueId().toString();
        
        // si no existe el jugador en la config
        if(!instance.getConfig().isConfigurationSection("jugadores."+uuid)){
            // lo creas
            
            instance.getConfig().set("jugadores."+e.getPlayer().getUniqueId().toString()+".level",1);
            
            instance.saveConfig();
        }
        player.setPlayerListName(ChatColor.DARK_AQUA+"["+ChatColor.WHITE+instance.getConfig().getInt("jugadores."+player.getUniqueId().toString()+".level")+ChatColor.DARK_AQUA+"] "+ChatColor.RESET+""+ChatColor.AQUA+player.getName() + ChatColor.RESET);
        
        if(!instance.getConfig().isConfigurationSection("textures")){
             instance.getConfig().set("textures.conseguido.material","EMERALD_BLOCK");
             instance.getConfig().set("textures.noConseguido.material","REDSTONE_BLOCK");
             instance.getConfig().set("textures.conseguible.material","GOLD_BLOCK");
             instance.getConfig().set("textures.noConseguible.material","REDSTONE_BLOCK");
             
             instance.getConfig().set("textures.conseguido.customModelData",-1);
             instance.getConfig().set("textures.noConseguido.customModelData",-1);
             instance.getConfig().set("textures.conseguible.customModelData",-1);
             instance.getConfig().set("textures.noConseguible.customModelData",-1);
             
             instance.saveConfig();
        }

    }
}
