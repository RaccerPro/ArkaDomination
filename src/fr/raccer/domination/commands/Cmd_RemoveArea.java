package fr.raccer.domination.commands;

import fr.raccer.domination.Main;
import fr.raccer.domination.game.DominationManager;
import fr.raccer.mutils.mcustom.mcommand.Command;
import fr.raccer.mutils.mcustom.mcommand.CommandArgs;

public class Cmd_RemoveArea {

	@Command(name = "domination.removeArea", permission = "arka.domination.removeArea", 
			description = "Permet de supprimer une zone à capturer", usage = "/domination removeArea [ID]")
	public void onCommand(CommandArgs a) {
		
		if(a.length() != 1) {
			a.getSender().sendMessage(a.getCommand().getUsage());
			return ; 
		}
		
		int i = Integer.parseInt(a.getArgs(0)) ;
		
		DominationManager manager = Main.instance.getDominationManager() ;
		if(manager.removeDomination(i))
			a.getPlayer().sendMessage("§aVous avez supprimé une zone de capture !");
		else a.getPlayer().sendMessage("§aCette ID n'existe pas.");
	}
	
}
