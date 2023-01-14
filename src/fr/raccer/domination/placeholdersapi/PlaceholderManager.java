package fr.raccer.domination.placeholdersapi;

import org.bukkit.Bukkit;

import fr.raccer.domination.placeholdersapi.expansions.Placeholder_Expansion;

public class PlaceholderManager {

	public PlaceholderManager() {
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new Placeholder_Expansion().register() ;
	}
	
}
