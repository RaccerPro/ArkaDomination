package fr.raccer.domination.commands;

import fr.raccer.domination.Main;
import fr.raccer.mutils.mcustom.mcommand.Command;
import fr.raccer.mutils.mcustom.mcommand.CommandArgs;

public class Cmd_Reload {

	@Command(name = "domination.reload", permission = "arka.domination.reload", 
			description = "Reload la configuration", usage = "/domination reload")
	public void onCommand(CommandArgs a) {
		Main.instance.loadMConfig();
		Main.instance.getMessagesManager().load(Main.instance);
		a.getSender().sendMessage("§aConfiguration reload avec succès.");
	}
	
}
