package fr.raccer.domination.game;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import fr.raccer.domination.Main;
import fr.raccer.domination.game.serializer.DominationManagerSerializer;
import fr.raccer.domination.game.serializer.DominationSerializer;
import fr.raccer.domination.messages.MessagesManager.M;
import fr.raccer.domination.scoreboards.MScoreboardManager;
import fr.raccer.mutils.config.configMessages.MLang;
import fr.raccer.mutils.mcustom.mevent.MEventType;
import fr.raccer.mutils.utils.MUtilsMethodsFile;
import fr.raccer.mutilsplayers.MUtilsPlayers;
import fr.raccer.mutilsplayers.mfactions.MFaction;
import fr.raccer.mutilsplayers.mplayers.MPlayer;
import lombok.Getter;
import lombok.Setter;

public class DominationManager {
	
	@Getter @Setter private MEventType type ;
	
	@Getter @Setter private List<Domination> list; 
	@Getter @Setter private LinkedHashMap<MFaction, Integer> mapFactionToPoints ;
	
	@Getter @Setter private List<Entry<MFaction, Integer>> topMFaction ;
	
	@Getter @Setter private int start_since_seconds ;
	@Getter @Setter private BukkitRunnable runnableChecker ;
	
	@Getter @Setter private MScoreboardManager mscoreboardManager ;

	public DominationManager() {
		list = new LinkedList<Domination>() ;
		this.initArguments();
		type = MEventType.Stop ;
	}
	
	public void initArguments() {
		mapFactionToPoints = new LinkedHashMap<MFaction, Integer>() ;
		start_since_seconds = 0 ;
		topMFaction = Arrays.asList(null, null, null) ;
	}
	
	public void addPointsForMFaction(MFaction mfac, int point) {
		if(!mapFactionToPoints.containsKey(mfac)) mapFactionToPoints.put(mfac, 0) ;
		
		mapFactionToPoints.replace(mfac, mapFactionToPoints.get(mfac) + point) ;
	}
	
	public void start(CommandSender sender, int delay) {
		
		if(!this.canStarted()) {
			sender.sendMessage(M.PREFIX+"§cUne zone n'a pas été définie pour le domination.");
			type = MEventType.Stop ;
			return ;
		}
		
		if(type != MEventType.Stop) {
			sender.sendMessage("§cUn Domination a déjà été lancé. (/domination stop)");
			return ;
		}
		
		if(this.list.size() != 3) {
			sender.sendMessage(M.PREFIX+"§cL'évent ne possède pas 3 zones de dominations configurées.");
			type = MEventType.Stop ;
			return ;
		}
		
		type = MEventType.Waiting ;

		sendAnnonceMessage(delay, 7200) ; // 2h 
		sendAnnonceMessage(delay, 3600) ; // 1h
		sendAnnonceMessage(delay, 1800) ; // 30min 
		sendAnnonceMessage(delay, 900) ; // 15min
		sendAnnonceMessage(delay, 600) ; // 10min
		sendAnnonceMessage(delay, 300) ; // 5min
		sendAnnonceMessage(delay, 240) ; 
		sendAnnonceMessage(delay, 180) ; 
		sendAnnonceMessage(delay, 120) ; 
		sendAnnonceMessage(delay, 60) ; 
		sendAnnonceMessage(delay, 30) ; 
		sendAnnonceMessage(delay, 15) ; 
		sendAnnonceMessage(delay, 10) ; 
		sendAnnonceMessage(delay, 5) ; 
		sendAnnonceMessage(delay, 3) ; 
		sendAnnonceMessage(delay, 2) ; 
		sendAnnonceMessage(delay, 1) ; 
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				startNow(sender);
				
			}
		}.runTaskLater(Main.instance, delay*20) ;
		
	}
	
	public boolean isStarted() {
		return type == MEventType.Start ;
	}
	
	@SuppressWarnings("deprecation")
	private void sendAnnonceMessage(int delay, final int i) {
		
		if(delay-i < 0) return ;  
		
		Bukkit.getScheduler().runTaskLater(Main.instance, new BukkitRunnable() {
			
			public void run() {
				if(type != MEventType.Waiting) return ;
				
				if(i == 300) {
					String m = M.LANCEMENT_EVENT_5_MINUTES.getDefault_fr() ; 
					Bukkit.broadcastMessage(m) ;
					return ;
				}
				
				String s = "" ;
				if(i % 3600 == 0) {
					s = (int)(i/3600)+" heure" ;
					if(i != 3600) s+="s" ;
				}
				else if(i%60 == 0) {
					s = (int)(i/60)+" minute" ;
					if(i != 60) s+="s" ;
				}
				else if(i == 1) s += i+" seconde" ;
				else s = i+" secondes" ;
				Bukkit.broadcastMessage(M.LANCEMENT_EVENT_DEFAULT.get().getMessage(MLang.FR, "\\{time\\}", s)) ;
				
			}
		}, (delay-i)*20) ; 
		
	}
	
	public boolean canStarted() {
		for(Domination d : list) {
			if(!d.canStartNow()) {
				return false ;
			}
		}
		return true ;
	}
	
	public void startNow(CommandSender sender) {
		
		if(!this.canStarted()) {
			sender.sendMessage(M.PREFIX+"§cUne zone n'a pas été définie pour le domination.");
			type = MEventType.Stop ;
			return ;
		}
		
		if(this.list.size() != 3) {
			sender.sendMessage(M.PREFIX+"§cL'évent ne possède pas 3 zones de dominations configurées.");
			type = MEventType.Stop ;
			return ;
		}
		
		this.initArguments();
		
		type = MEventType.Start ;
		
		mscoreboardManager = new MScoreboardManager() ;
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			mscoreboardManager.addScoreboardPlayer(p);
			mscoreboardManager.setMScoreboardPlayer(p);
		}
		
		runnableChecker = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Map<MFaction, Integer> mapMFactionCapture = new HashMap<MFaction, Integer>();
				
				for(Domination d : list) {
					d.updateCapture();
				
					if(d.getMfaction() == null) continue ;
					
					if(mapMFactionCapture.containsKey(d.getMfaction())) 
						mapMFactionCapture.replace(d.getMfaction(), mapMFactionCapture.get(d.getMfaction()) +1) ;
					
					else mapMFactionCapture.put(d.getMfaction(), 1) ;
				}
				
				for(Entry<MFaction, Integer> p : mapMFactionCapture.entrySet()) {
					
					MFaction mfac = p.getKey() ;
					int nb_capture = p.getValue() ;
					
					if(nb_capture == 1) addPointsForMFaction(mfac, 1);
					else if(nb_capture == 2) addPointsForMFaction(mfac, 3);
					else if(nb_capture == 3) addPointsForMFaction(mfac, 5);
				}
				
				
				
				Stream<Entry<MFaction, Integer>> stream = mapFactionToPoints.entrySet()
						.stream().sorted(Map.Entry.comparingByValue())
						.limit(3) ;
				
				Iterator<Entry<MFaction, Integer>> ite = stream.iterator() ;
				int i = 0 ; 
				while(ite.hasNext()) {
					topMFaction.set(i, ite.next()) ;
					i++ ;
				}
				
				if(getTopMFaction(0) != null) {
					int firstPoints = getTopMFaction(0).getValue() ;
					int max_points = Main.instance.getMconfig().getAsInt("points_to_have_for_win") ;
					
					if(firstPoints >= max_points) {
						stopEvent(sender) ;
						this.cancel();
						return ;
					}
				}
				
				
				
				start_since_seconds++ ;
				mscoreboardManager.update();
				
				int max_seconds = Main.instance.getMconfig().getAsInt("duration_maximum_seconds") ;
				
				if(start_since_seconds >= max_seconds) {
					stopEvent(sender); 
					this.cancel();
					return ;
				}
				
				
			}
		};
		
		runnableChecker.runTaskTimer(Main.instance, 0, 20) ;
		
	}
	
	public void giveRewards() {
		
		
		JsonArray rewards_1 = Main.instance.getMconfig().get("rewards_1_mfaction").getAsJsonArray() ;
		JsonArray rewards_2 = Main.instance.getMconfig().get("rewards_2_mfaction").getAsJsonArray() ;
		JsonArray rewards_3 = Main.instance.getMconfig().get("rewards_3_mfaction").getAsJsonArray() ;
		/*
		Entry<MFaction, Integer> mfac1 = null ;
		Entry<MFaction, Integer> mfac2 = null ;
		Entry<MFaction, Integer> mfac3 = null ;
		
		Iterator<Entry<MFaction, Integer>> ite = streamClassementTopFaction.iterator() ;
		int i = 1 ;
		while(ite.hasNext()) {
			Entry<MFaction, Integer> next = ite.next() ;
			if(i == 1) mfac1 = next ;
			else if(i == 2) mfac2 = next ;
			else if(i == 3) mfac3 = next ;
			i++ ;
		}
		*/
		
		for(int j = 0 ; j<rewards_1.size() ; j++)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewards_1.get(j).getAsString()) ;
		for(int j = 0 ; j<rewards_2.size() ; j++)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewards_2.get(j).getAsString()) ;
		for(int j = 0 ; j<rewards_3.size() ; j++)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewards_3.get(j).getAsString()) ;
		
		
		
		
	}
	
	
	public void stopEvent(CommandSender sender) {
		
		if(type == MEventType.Stop) {
			sender.sendMessage("§cAucun évent Domination n'est lancé.");
			return ;
		}
		
		this.saveBackup() ;
		
		type = MEventType.Stop ;
		if(runnableChecker != null) runnableChecker.cancel();
		runnableChecker = null ; 	
		
		this.giveRewards();
		
		for(MPlayer mp : MUtilsPlayers.getInstance().getMPlayersOnline()) { 
			mp.sendMessage(M.EVENT_STOPPED);
			mscoreboardManager.restoreScoreboard(mp.getPlayer());
		}
		
		this.initArguments();
		
	}
	
	private void saveBackup() {
		
		String pattern = "yyyy-MM-dd-HH-mm-ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String name = simpleDateFormat.format(new Date()) ;
		
		File folder = new File(Main.instance.getDataFolder(), "/Backup/") ;
		if(!folder.exists()) folder.mkdirs() ;
		File file = new File(folder, name+".json") ;
		
		Gson gson = MUtilsPlayers.getInstance().getGson();
		JsonObject obj = new JsonObject() ;
		
		obj.addProperty("start_since_seconds", start_since_seconds);
		
		Map<String, Integer> mapNameFactionToPoints = new HashMap<String, Integer>() ;
		
		for(Entry<MFaction, Integer> p : mapFactionToPoints.entrySet()) 
			mapNameFactionToPoints.put(p.getKey().getFaction().getName(), p.getValue()) ;
		
		obj.add("mapFactionPoints", gson.toJsonTree(mapNameFactionToPoints));
		
		MUtilsMethodsFile.writeFile(file, gson.toJson(obj));
	}

	public void addNewDomination(Domination dom) {
		list.add(dom) ;
	}
	public boolean removeDomination(int id) {
		if(list.size() >= id) return false ;
		list.remove(id) ;
		return true ;
	}
	public String listDomination() {
		String str = "§6Liste des zones à capturer : \n" ;
		for(int i=0 ; i < list.size() ; i++) str += "§b"+i+". §e"+list.get(i).getName()+" §6"+list.get(i).getMarea()+"\n" ;
		return str ;
	}
	
	public Entry<MFaction, Integer> getTopMFaction(int place){
		if(topMFaction.size() <= place) return null ;
		return topMFaction.get(place) ;
	}
	
	
	
	
	
	public void load() {
		
		File file = new File(Main.instance.getDataFolder(), "dominationManager.json") ;
		if(!file.exists()) return ;
		
		GsonBuilder builder = MUtilsPlayers.getInstance().getGsonBuilder() ;
		builder.registerTypeAdapter(DominationManager.class, new DominationManagerSerializer()) ;
		builder.registerTypeAdapter(Domination.class, new DominationSerializer()) ;
		Gson gson = builder.create() ;
		
		String str = MUtilsMethodsFile.readFile(file) ;
		DominationManager manager = gson.fromJson(str, DominationManager.class) ;
		
		if(manager == null) return ;
		this.setList(manager.getList());
	}
	
	public void save() {
		
		File file = new File(Main.instance.getDataFolder(), "dominationManager.json") ;
		
		GsonBuilder builder = MUtilsPlayers.getInstance().getGsonBuilder() ;
		builder.registerTypeAdapter(DominationManager.class, new DominationManagerSerializer()) ;
		builder.registerTypeAdapter(Domination.class, new DominationSerializer()) ;
		Gson gson = builder.create() ;
		
		MUtilsMethodsFile.writeFile(file, gson.toJson(this));
		
	}
	
	
	
}
