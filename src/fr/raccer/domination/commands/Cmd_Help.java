package fr.raccer.domination.commands;

import java.util.List;

import fr.raccer.domination.Main;
import fr.raccer.mutils.mcustom.mcommand.Command;
import fr.raccer.mutils.mcustom.mcommand.CommandArgs;

public class Cmd_Help {

	@Command(name = "domination.help", permission = "arka.domination.help", 
			description = "Affiche l'aide du plugin", usage = "/domination help")
	public void onCommand(CommandArgs a) {

		
		List<Command> list = Main.instance.getCommandManager().getListCommands() ;
		
       
		String s = "§8§m+---------------- §b§lArka§3§lCoeurFaction§8§m----------------+" + "\n" ;
		for(Command c : list) {
			if(a.getSender().hasPermission(c.permission()))
				s += "§e§l» §b"+c.usage()+"§6 : "+c.description()+ "\n" ;
		}
		s += "§8§m+------------------------------------------------+" ; 
		a.getSender().sendMessage(s);
	}
	
}
