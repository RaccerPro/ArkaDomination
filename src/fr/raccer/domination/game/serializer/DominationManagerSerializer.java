package fr.raccer.domination.game.serializer;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import fr.raccer.domination.game.Domination;
import fr.raccer.domination.game.DominationManager;

public class DominationManagerSerializer implements JsonSerializer<DominationManager>, JsonDeserializer<DominationManager> {

	@Override
	public DominationManager deserialize(JsonElement ele, Type arg1, JsonDeserializationContext cont)
			throws JsonParseException {
		
		JsonObject obj = ele.getAsJsonObject() ;
		DominationManager manager = new DominationManager() ;
		JsonArray arr = obj.get("list").getAsJsonArray() ;
		
		for(int i=0 ; i < arr.size() ; i++)
			manager.addNewDomination(cont.deserialize(arr.get(i), Domination.class));
		
		return manager;
	}

	@Override
	public JsonElement serialize(DominationManager manager, Type arg1, JsonSerializationContext cont) {
		
		JsonObject json = new JsonObject() ;
		
		List<Domination> list = manager.getList() ;
		json.add("list", cont.serialize(list));

		return json;
	}

	
	
}
