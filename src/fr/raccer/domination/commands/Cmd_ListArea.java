package fr.raccer.domination.commands;

import fr.raccer.domination.Main;
import fr.raccer.domination.game.DominationManager;
import fr.raccer.mutils.mcustom.mcommand.Command;
import fr.raccer.mutils.mcustom.mcommand.CommandArgs;

public class Cmd_ListArea {

	@Command(name = "domination.listArea", permission = "arka.domination.listArea", 
			description = "Permet de lister toutes les zones à capturer", usage = "/domination listArea")
	public void onCommand(CommandArgs a) {

		DominationManager manager = Main.instance.getDominationManager() ;
		a.getSender().sendMessage(manager.listDomination());
	}
	
}
