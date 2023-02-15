/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bigsmall.minebattlepass;

import com.bigsmall.umleconomy.UMLEconomy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

/**
 *
 * @author asier
 */
public class TabComplete implements TabCompleter {

    //mbp setReq UMLCoins (level) [number]
    //mbp setReq ECoins (level) [number]
    //mbp setReq XP (level) [number]
    //mbp setReq items (level)
    //mbp setPrice UMLCoins (level) [number]
    //mbp setPrice ECoins (level) [number]
    //mbp setPrice XP (level) [number]
    //mbp setPrice Permissions (level) [code] [explanation]
    //mbp setPrice items (level)
    private static final String[] set = {"setReq", "setPrice", "reload"};
    private static final List<String> setA = Arrays.asList(set);

    private static final String[] type = {"UMLCoins", "ECoins", "XP", "items"};
    private static final List<String> typeA = Arrays.asList(type);
    
    private static final String[] typeB = {"UMLCoins", "ECoins", "XP", "items", "permissions"};
    private static final List<String> typeBA = Arrays.asList(typeB);

    public static Main instance;

    TabComplete(Main instance) {
        this.instance = instance;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {

        final List<String> completions = new ArrayList<>();

        if (alias.equalsIgnoreCase("mbp")) {
            if (args.length == 1) {
                StringUtil.copyPartialMatches(args[0], setA, completions);
            } 
            else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("setReq") || args[0].equalsIgnoreCase("setPrice")) {
                    if (args[0].equalsIgnoreCase("setPrice")) {
                        StringUtil.copyPartialMatches(args[1], typeBA, completions);
                    }
                    else{
                        StringUtil.copyPartialMatches(args[1], typeA, completions);
                    }
                    
                }
            }
        }

        Collections.sort(completions);
        return completions;
    }

}
