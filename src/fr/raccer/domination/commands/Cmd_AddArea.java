package fr.raccer.domination.commands;

import org.bukkit.Location;

import fr.raccer.domination.Main;
import fr.raccer.domination.game.Domination;
import fr.raccer.domination.game.DominationManager;
import fr.raccer.mutils.mcustom.marea.MArea;
import fr.raccer.mutils.mcustom.mcommand.Command;
import fr.raccer.mutils.mcustom.mcommand.CommandArgs;
import fr.raccer.mutils.utils.MUtilsMethods;

public class Cmd_AddArea {

	@Command(name = "domination.addArea", permission = "arka.domination.addArea", 
			description = "Permet d'ajouter une zone à capturer", usage = "/domination addArea [name] [x1:y1:z1] [x2:y2:z2]",
			inGameOnly = true)
	public void onCommand(CommandArgs a) {
		
		if(a.length() != 3) {
			a.getSender().sendMessage(a.getCommand().getUsage());
			return ; 
		}
		
		String name_world = a.getPlayer().getWorld().getName() ;
		Location loc0 = MUtilsMethods.getLiteLocationFromString(name_world+":"+a.getArgs(1)) ;
		Location loc1 = MUtilsMethods.getLiteLocationFromString(name_world+":"+a.getArgs(2)) ;
		
		if(loc0 == null || loc1 == null) {
			a.getPlayer().sendMessage("§cLa zone défini est null.");
			return ;
		}
		
		MArea area = new MArea(loc0, loc1) ;
		DominationManager manager = Main.instance.getDominationManager() ;
		Domination domination = new Domination() ;
		domination.setMarea(area);
		domination.setName(a.getArgs(0));
		manager.addNewDomination(domination);
		
		a.getPlayer().sendMessage("§aVous avez ajouté une nouvelle zone de capture !");
	}
	
}
