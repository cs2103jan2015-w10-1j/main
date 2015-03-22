package com.done.storage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.done.model.Done;
import com.done.model.DoneDeadlineTask;
import com.done.model.DoneFloatingTask;
import com.done.model.DoneTimedTask;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DoneAdapter implements JsonSerializer<List<Done>>,
		JsonDeserializer<List<Done>> {

	private static Map<String, Class> map = new TreeMap<String, Class>();
	static {
		map.put("DoneDeadLineTask", DoneDeadlineTask.class);
		map.put("DoneFloatingTask", DoneFloatingTask.class);
		map.put("DoneTimedTask", DoneTimedTask.class);
	}

	@Override
	public List<Done> deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		List<Done> result = new ArrayList<Done>();
		JsonArray jsonArray = arg0.getAsJsonArray();

		for (JsonElement out : jsonArray) {
			JsonObject jsonObj = out.getAsJsonObject();
			String classType = jsonObj.get("class").getAsString();
			JsonElement element = jsonObj.get("task");

			result.add(arg2.deserialize(element, map.get(classType)));

		}
		return result;
	}

	@Override
	public JsonElement serialize(List<Done> arg0, Type arg1,
			JsonSerializationContext arg2) {

		JsonArray result = new JsonArray();
		for (Done out : arg0) {
			JsonObject obj = new JsonObject();
			obj.addProperty("class", out.getClass().getSimpleName());
			JsonElement element = arg2.serialize(out);
			obj.add("task", element);
			result.add(obj);
		}

		return result;
	}

}
