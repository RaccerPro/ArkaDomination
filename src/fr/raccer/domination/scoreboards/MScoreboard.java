package fr.raccer.domination.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.google.gson.JsonArray;

import fr.raccer.domination.Main;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;

public class MScoreboard {

	
	@Getter @Setter private Scoreboard scoreboard ; 
	@Getter @Setter private Objective objective ;
	
	@Getter @Setter private String title ;
	@Getter @Setter private JsonArray l ;
	
	
	public MScoreboard() {
		
		ScoreboardManager manager = Bukkit.getScoreboardManager() ;
				
		setScoreboard(manager.getNewScoreboard());
		registerObjective() ;
		
		title = Main.instance.getMconfig().getAsString("scoreboard_title") ;
		l = Main.instance.getMconfig().get("scoreboard_list").getAsJsonArray() ;
		
	}

	public MScoreboard updateMScoreboard() {
		
		OfflinePlayer off = Bukkit.getOfflinePlayers()[0] ;
		
		objective.unregister();
		registerObjective() ;
		
		objective.setDisplayName(PlaceholderAPI.setPlaceholders(off, title));
		
		for(int i=l.size()-1 ; i>=0 ; i--)
			objective.getScore(PlaceholderAPI.setPlaceholders(off, l.get(i).getAsString() )).setScore(l.size()-i);
		
		return this;
	}
	
	public void registerObjective() {
		setObjective(scoreboard.registerNewObjective("arka_domination", "dummy"));
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

}
