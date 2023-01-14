package fr.raccer.domination;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import fr.raccer.domination.commands.Cmd_AddArea;
import fr.raccer.domination.commands.Cmd_Help;
import fr.raccer.domination.commands.Cmd_ListArea;
import fr.raccer.domination.commands.Cmd_Reload;
import fr.raccer.domination.commands.Cmd_RemoveArea;
import fr.raccer.domination.commands.Cmd_Start;
import fr.raccer.domination.commands.Cmd_StartNow;
import fr.raccer.domination.commands.Cmd_Stop;
import fr.raccer.domination.game.DominationManager;
import fr.raccer.domination.listener.ListenerPlayerJoined;
import fr.raccer.domination.messages.MessagesManager;
import fr.raccer.domination.placeholdersapi.PlaceholderManager;
import fr.raccer.mutils.MPlugin;
import fr.raccer.mutilsplayers.utils.mconfig.MConfig;
import lombok.Getter;
import lombok.Setter;

public class Main extends MPlugin {
	
	public static Main instance ;
	
	@Getter @Setter private MConfig mconfig ;
	@Getter @Setter private PlaceholderManager placeholderManager ;
	@Getter @Setter private DominationManager dominationManager ;
	
	@Override
	public void onLoad() {
		
		instance = this ;
		//MUtilsPlayers.getInstance().registerTypeAdapterGson(Template.class, new SerializerTemplate());
		
	}

	@Override
	public void onEnableCore() {
		
		this.loadMConfig();
		
		setPlaceholderManager(new PlaceholderManager());
		
		setDominationManager(new DominationManager());
		
		super.registerListener(new ListenerPlayerJoined());
		
		super.registerClassCommand(new Cmd_Help());
		super.registerClassCommand(new Cmd_Start());
		super.registerClassCommand(new Cmd_StartNow());
		super.registerClassCommand(new Cmd_Stop());
		super.registerClassCommand(new Cmd_ListArea());
		super.registerClassCommand(new Cmd_AddArea());
		super.registerClassCommand(new Cmd_RemoveArea());
		super.registerClassCommand(new Cmd_Reload());
		
		super.getMessagesManager().register(new MessagesManager());
		
		dominationManager.load();
		
	}

	@Override
	public void onDisableCore() {
		
		mconfig.save();
		dominationManager.save();
		
	}


	public void loadMConfig() {
		mconfig = new MConfig(this) ;
		
		this.initConfiguration() ;
		mconfig.load();
	}
	
	private void initConfiguration() {
		
		mconfig.add("points_to_have_for_win", 10);
		mconfig.add("duration_maximum_seconds", 30);
		
		JsonArray rewards_1 = new JsonArray() ;
		JsonArray rewards_2 = new JsonArray() ;
		JsonArray rewards_3 = new JsonArray() ;
		
		rewards_1.add(new JsonPrimitive("m Raccer reward 1.1"));
		rewards_1.add(new JsonPrimitive("m Raccer reward 1.2"));
		rewards_2.add(new JsonPrimitive("m Raccer reward 2.1"));
		rewards_2.add(new JsonPrimitive("m Raccer reward 2.2"));
		rewards_3.add(new JsonPrimitive("m Raccer reward 3.1"));
		rewards_3.add(new JsonPrimitive("m Raccer reward 3.2"));
		
		
		mconfig.add("rewards_1_mfaction", rewards_1);
		mconfig.add("rewards_2_mfaction", rewards_2);
		mconfig.add("rewards_3_mfaction", rewards_3);
		
		mconfig.add("scoreboard_title", "§b§eArkaDomination");
		JsonArray scoreboard_list = new JsonArray() ;
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_name_faction_top_1%"));
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_name_faction_top_2%"));
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_name_faction_top_3%"));
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_points_faction_top_1%"));
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_points_faction_top_2%"));
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_points_faction_top_3%"));
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_name_faction_zone_1%"));
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_name_faction_zone_2%"));
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_name_faction_zone_3%"));
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_name_player_zone_1%"));
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_name_player_zone_2%"));
		scoreboard_list.add(new JsonPrimitive("§6%arka-domination_name_player_zone_3%"));
		mconfig.add("scoreboard_list", scoreboard_list);
		
	}
	
	
}

/*

/domination addArea Red -18:4:-0 -14:8:4
/domination addArea Green -21:3:2 26:8:7
/domination addArea Blue -14:3:5 -19:8:10
 
*/