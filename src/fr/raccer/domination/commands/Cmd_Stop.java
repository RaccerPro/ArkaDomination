package fr.raccer.domination.commands;

import fr.raccer.domination.Main;
import fr.raccer.domination.game.DominationManager;
import fr.raccer.mutils.mcustom.mcommand.Command;
import fr.raccer.mutils.mcustom.mcommand.CommandArgs;

public class Cmd_Stop {

	@Command(name = "domination.stop", permission = "arka.domination.stop", 
			description = "Arrête tous les Dominations en cours", usage = "/domination stop")
	public void onCommand(CommandArgs a) {
		DominationManager manager = Main.instance.getDominationManager() ;
		manager.stopEvent(a.getSender());
	}
	
}
