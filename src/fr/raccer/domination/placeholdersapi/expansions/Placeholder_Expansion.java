package fr.raccer.domination.placeholdersapi.expansions;

import java.util.Map.Entry;

import org.bukkit.OfflinePlayer;

import fr.raccer.domination.Main;
import fr.raccer.domination.game.Domination;
import fr.raccer.domination.game.DominationManager;
import fr.raccer.mutilsplayers.mfactions.MFaction;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Placeholder_Expansion extends PlaceholderExpansion {
	
    @Override
    public String getAuthor() {
        return "Raccer";
    }
    
    @Override
    public String getIdentifier() {
        return "arka-domination";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
    
    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }
    
    @Override
    public String onRequest(OfflinePlayer player, String params) {
    		
    	params = params.toLowerCase() ;
    	DominationManager manager = Main.instance.getDominationManager() ;
    	
    	Entry<MFaction, Integer> top1 = manager.getTopMFaction(0) ;
    	Entry<MFaction, Integer> top2 = manager.getTopMFaction(1) ;
    	Entry<MFaction, Integer> top3 = manager.getTopMFaction(2) ;
    	
    	
    	if(params.equalsIgnoreCase("name_faction_top_1")) 
    		if(top1 != null) return top1.getKey().getFaction().getName() ;
    		else return "Aucun" ;
    	if(params.equalsIgnoreCase("name_faction_top_2")) 
    		if(top2 != null) return top2.getKey().getFaction().getName() ;
    		else return "Aucun" ;
    	if(params.equalsIgnoreCase("name_faction_top_3")) 
    		if(top3 != null) return top3.getKey().getFaction().getName() ;
    		else return "Aucun" ;
    	
    	if(params.equalsIgnoreCase("points_faction_top_1")) 
    		if(top1 != null) return ""+top1.getValue() ;
    		else return "Aucun" ;
    	if(params.equalsIgnoreCase("points_faction_top_2")) 
    		if(top2 != null) return ""+top2.getValue() ;
    		else return "Aucun" ;
    	if(params.equalsIgnoreCase("points_faction_top_3")) 
    		if(top3 != null) return ""+top3.getValue() ;
    		else return "Aucun" ;
    	
    	Domination dom1 = manager.getList().get(0) ;
    	Domination dom2 = manager.getList().get(1) ;
    	Domination dom3 = manager.getList().get(2) ;
    	
    	if(dom1 != null) {
    		if(params.equalsIgnoreCase("name_faction_zone_1")) 
    			if(dom1.isCaptured()) return dom1.getMfaction().getFaction().getTag() ;
    			else return "Aucun" ;
    		if(params.equalsIgnoreCase("name_player_zone_1"))
    			if(dom1.isCaptured()) return dom1.getMplayerCapture().getName() ;
    			else return "Aucun" ;
    	}
    	if(dom2 != null) {
    		if(params.equalsIgnoreCase("name_faction_zone_2")) 
    			if(dom2.isCaptured()) return dom2.getMfaction().getFaction().getTag() ;
    			else return "Aucun" ;
    		if(params.equalsIgnoreCase("name_player_zone_2"))
    			if(dom2.isCaptured()) return dom2.getMplayerCapture().getName() ;
    			else return "Aucun" ;
    	}
    	if(dom3 != null) {
    		if(params.equalsIgnoreCase("name_faction_zone_3")) 
    			if(dom3.isCaptured()) return dom3.getMfaction().getFaction().getTag() ;
    			else return "Aucun" ;
    		if(params.equalsIgnoreCase("name_player_zone_3"))
    			if(dom3.isCaptured()) return dom3.getMplayerCapture().getName() ;
    			else return "Aucun" ;
    	}

		
        return null; // Placeholder is unknown by the Expansion
    }
    
/*

%arka-domination_name_faction_top_1%
%arka-domination_name_faction_top_2%
%arka-domination_name_faction_top_3%
%arka-domination_points_faction_top_1%
%arka-domination_points_faction_top_2%
%arka-domination_points_faction_top_3%
%arka-domination_name_faction_zone_1%
%arka-domination_name_faction_zone_2%
%arka-domination_name_faction_zone_3%
%arka-domination_name_player_zone_1%
%arka-domination_name_player_zone_1%
%arka-domination_name_player_zone_1%

*/
    
}
