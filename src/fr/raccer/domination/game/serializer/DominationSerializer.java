package fr.raccer.domination.game.serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import fr.raccer.domination.game.Domination;
import fr.raccer.mutils.mcustom.marea.MArea;

public class DominationSerializer implements JsonSerializer<Domination>, JsonDeserializer<Domination> {

	@Override
	public Domination deserialize(JsonElement ele, Type arg1, JsonDeserializationContext cont)
			throws JsonParseException {
		
		Domination dom = new Domination() ;
		JsonObject obj = ele.getAsJsonObject() ;
		
		String name = obj.get("name").getAsString() ;
		MArea marea = cont.deserialize(obj.get("area"), MArea.class) ;
		
		dom.setName(name);
		dom.setMarea(marea);
		
		return dom ;
	}

	@Override
	public JsonElement serialize(Domination dom, Type arg1, JsonSerializationContext cont) {
		
		JsonObject obj = new JsonObject() ;
		
		obj.addProperty("name", dom.getName());
		obj.add("area", cont.serialize(dom.getMarea()));
		
		return obj ;
	}

	
	
}
