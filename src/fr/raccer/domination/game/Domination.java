package fr.raccer.domination.game;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import fr.raccer.mutils.mcustom.marea.MArea;
import fr.raccer.mutilsplayers.MUtilsPlayers;
import fr.raccer.mutilsplayers.mfactions.MFaction;
import fr.raccer.mutilsplayers.mplayers.MPlayer;
import lombok.Getter;
import lombok.Setter;

public class Domination {

	@Getter @Setter private String name ;
	@Getter @Setter private MArea marea ;
	
	@Getter @Setter private MFaction mfaction ;
	@Getter @Setter private MPlayer mplayerCapture ;
	
	public Domination() {
		setName("");
	}
	
	
	public void updateCapture() {

		if(this.isCaptured()) {
			if(mplayerCapture.isOnline())
				if(marea.isInTheArea(mplayerCapture.getLocation())) return ;
		}
		
		mplayerCapture = null ;
		mfaction = null ;
		
		Location loc = new Location(marea.getMax().getWorld(), 
				marea.getMax().getBlockX() - marea.getMin().getBlockX(), 
				marea.getMax().getBlockY() - marea.getMin().getBlockY(), 
				marea.getMax().getBlockZ() - marea.getMin().getBlockZ()) ;
		
		Collection<Entity> coll = marea.getMax().getWorld().getNearbyEntities(marea.getMax(), 
				Math.abs(loc.getBlockX())+1, 
				Math.abs(loc.getBlockY())+1, 
				Math.abs(loc.getBlockZ())+1 ) ;
		
		for(Entity e : coll) {
			if(!(e instanceof Player)) continue ;
			
			if(marea.isInTheArea(e)) {
				Player pl = (Player) e; 
				
				
				mfaction = MUtilsPlayers.getMFaction(pl);
				if(mfaction == null) continue ;
				
				mplayerCapture = MUtilsPlayers.getMPlayer(pl);
				return ;
			}
			
		}
	}

	public boolean canStartNow() {
		return marea != null ;
	}
	
	public boolean isCaptured() {
		return mplayerCapture != null ;
	}
	
	
	
	

}
