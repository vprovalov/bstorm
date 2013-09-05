package bstorm;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {
	private static final ExclusionStrategy exs = new GsonExclusionStrategy(null);
	public static Gson createGson(){
		GsonBuilder gsonbuilder = new GsonBuilder();
		gsonbuilder.setExclusionStrategies(exs);
		return gsonbuilder.serializeNulls().create();
	}
}
