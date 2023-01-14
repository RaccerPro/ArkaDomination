package fr.raccer.domination.commands;

import fr.raccer.domination.Main;
import fr.raccer.domination.game.DominationManager;
import fr.raccer.mutils.mcustom.mcommand.Command;
import fr.raccer.mutils.mcustom.mcommand.CommandArgs;

public class Cmd_Start {

	@Command(name = "domination.start", permission = "arka.domination.start", 
			description = "Permet de lancer un Domination avec délai (en secondes)", usage = "/domination start {delai_seconds=300}")
	public void onCommand(CommandArgs a) {
		
		int i = 300 ;
		
		if(a.length() == 1) 
			i = Integer.parseInt(a.getArgs(0)) ;
		
		DominationManager manager = Main.instance.getDominationManager() ;
		manager.start(a.getSender(), i);
	}
	
}
