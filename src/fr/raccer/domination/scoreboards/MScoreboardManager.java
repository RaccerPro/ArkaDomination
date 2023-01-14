package fr.raccer.domination.scoreboards;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import lombok.Getter;
import lombok.Setter;

public class MScoreboardManager {

	@Getter @Setter private MScoreboard mscoreboard ;
	@Getter @Setter private Map<Player, Scoreboard> map ;
	
	public MScoreboardManager() {
		setMscoreboard(new MScoreboard());
		setMap(new HashMap<Player, Scoreboard>());
	}
	
	public void update() {
		mscoreboard.updateMScoreboard() ;
	}
	
	public void restoreScoreboard(Player player) {
		if(player == null) return ;
		if(!map.containsKey(player)) return ;
		player.setScoreboard(map.get(player));
		map.remove(player) ;
	}
	
	public void addScoreboardPlayer(Player player) {
		if(player == null) return ;
		if(map.containsKey(player)) return ;
		map.put(player, player.getScoreboard()) ;
	}
	
	public void setMScoreboardPlayer(Player player) {
		if(player == null) return ;
		player.setScoreboard(mscoreboard.getScoreboard());
	}
	
}
