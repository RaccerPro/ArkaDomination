package fr.raccer.domination.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.raccer.domination.Main;
import fr.raccer.domination.game.DominationManager;

public class ListenerPlayerJoined implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e){
		
		DominationManager manager = Main.instance.getDominationManager() ;
		
		if(!manager.isStarted()) return ;
			
		Player p = e.getPlayer() ;
		manager.getMscoreboardManager().addScoreboardPlayer(p);
		manager.getMscoreboardManager().setMScoreboardPlayer(p);
		
	}
	
}
