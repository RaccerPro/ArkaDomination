package fr.raccer.domination.messages;

import java.util.LinkedList;
import java.util.List;

import fr.raccer.domination.Main;
import fr.raccer.mutils.config.configMessages.MLang;
import fr.raccer.mutils.config.configMessages.MMessage;
import fr.raccer.mutils.config.configMessages.MMessageEnum;
import fr.raccer.mutils.config.configMessages.MMessageImpl;
import lombok.Getter;

public class MessagesManager implements MMessageImpl {

	@Override
	public List<MMessage> defaultMessages() {
		List<MMessage> list = new LinkedList<MMessage>() ;
		
		for(M m : M.values()) {
			MMessage msg = new MMessage(m.name()) ;
			msg.setMessage(MLang.FR, m.default_fr) ;
			list.add(msg) ;
		}
			
		
		return list ;
	}
	
	public enum M implements MMessageEnum {
		
		PREFIX("§b[§3§lArkaDomination§b]§r ") ,
		
		LANCEMENT_EVENT_5_MINUTES("§8§l§m+----------------------------------+§r" + "\n"+ 
				"§6 "+"\n" + 
				"§r      §b§lArka§3§lEvent §6§l» §b§lEvent Domination !" + "\n" +
				"§e           §eDébut de l’event dans §65 minutes §e! " + "\n"+  
				"§6 "+"\n" + 
				"§8§l§m+----------------------------------+§r"),
		
		EVENT_STOPPED("§8§l§m+----------------------------------+§r" + "\n"+ 
				"§6 "+"\n" + 
				"§b1. §e§l%arka-domination_[...]%§6 avec §e§l%arka-domination_[...] points%" + "\n" +
				"§b2. §e§l%arka-domination_[...]%" + "\n" +
				"§b3. §e§l%arka-domination_[...]%" + "\n" +
				"§6 "+"\n" + 
				"§8§l§m+----------------------------------+§r"),
		
		LANCEMENT_EVENT_DEFAULT("§6Un évent Domination va débuter dans §e{time}§6."),
		
		ERROR("§cUne erreur est survenue."),
		
		;
		
		@Getter private String default_fr ;
		
		private M(String s) {
			default_fr = s ;
		}
		
		@Override
		public String toString() {
			return this.get().getMessage(MLang.FR) ;
		}
		
		public MMessage get() {
			return Main.instance.getMessagesManager().get(this.name()) ;
		}
		
	}

}