package fr.raccer.domination.commands;

import fr.raccer.domination.Main;
import fr.raccer.domination.game.DominationManager;
import fr.raccer.mutils.mcustom.mcommand.Command;
import fr.raccer.mutils.mcustom.mcommand.CommandArgs;

public class Cmd_StartNow {

	@Command(name = "domination.startnow", permission = "arka.domination.startnow", 
			description = "Permet de lancer un Domination sans délai", usage = "/domination startnow")
	public void onCommand(CommandArgs a) {
		DominationManager manager = Main.instance.getDominationManager() ;
		manager.startNow(a.getSender());
	}
	
}
