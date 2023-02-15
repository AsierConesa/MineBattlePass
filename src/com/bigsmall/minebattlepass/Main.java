/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bigsmall.minebattlepass;

import com.bigsmall.umleconomy.UMLEconomy;
import java.io.File;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author asier
 */
public class Main extends JavaPlugin {

    public static Main instance;
    public static UMLEconomy eco = null;
    private static Permission perms = null;
    public static final String TAG = ChatColor.WHITE + "[" + ChatColor.BLUE + "Mine" + ChatColor.DARK_AQUA + "Battle" + ChatColor.BLUE + "Pass" + ChatColor.WHITE + "]: ";
    public static Utilities util = null;

    @Override
    public void onLoad() {
        sendPluginMessage(ChatColor.GOLD, "El plugin se activado!");
    }

    @Override
    public void onDisable() {
        instance = this;
        sendPluginMessage(ChatColor.RED, "El plugin se ha deshabilitado!");
    }

    @Override
    public void onEnable() {
        instance = this;

        File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }

        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            sendPluginMessage(ChatColor.RED, "No se ha encontrado VAULT, desactivando servidor");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        
        util = new Utilities();
        
        eco = (UMLEconomy) getServer().getPluginManager().getPlugin("UMLEconomy");

        getServer().getPluginManager().registerEvents(new OnPlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new OnInventoryClick(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerChat(this), this);

        this.getCommand("mbp").setTabCompleter(new TabComplete(this));
        
        this.getCommand("level").setExecutor(new Comandos(this, eco));
        this.getCommand("mbp").setExecutor(new Comandos(this, eco));

        sendPluginMessage(ChatColor.AQUA, "El plugin se ha habilitado!");
        sendPluginMessage(ChatColor.GOLD, "PLUGIN DESARROLLADO POR BIGSMALL_8");
        sendPluginMessage(ChatColor.DARK_AQUA, "Familia de plugins de UltraMineLands");
    }

    public void sendPluginMessage(ChatColor color, String texto) {
        Bukkit.getConsoleSender().sendMessage(TAG + color + texto);
    }

    public static UMLEconomy getEco() {
        return eco;
    }
    
    public static Permission getPerms() {
        return perms;
    }
    
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}
