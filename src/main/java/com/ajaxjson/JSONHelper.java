package com.ajaxjson;


import net.sf.json.*;
import net.sf.json.util.JSONTokener;
import net.sf.json.util.JsonEventListener;

public class JSONHelper {

	public static JsonConfig JSON_CONFIG = new JsonConfig();
	static {
		JSON_CONFIG.addJsonEventListener(new DefaultJsonEventListenerImpl());
	}
	
	public static JSONNull getJSONNull() {
		return JSONNull.getInstance();
	}
	
	public static JSONTokener buildJSONTokener(String s) {
		return new JSONTokener(s);
	}
	

   /**
    * Creates a JSONArray.<br>
    * Inspects the object type to call the correct JSONArray factory method.
    * Accepts JSON formatted strings, arrays, Collections and Enums.
    *
    * @param object
    * @throws JSONException if the object can not be converted to a proper
    *         JSONArray.
    */
	public static JSONArray buildJSONArray(Object object) {
		return JSONArray.fromObject(object);
	}
	
   /**
    * Creates a JSONArray.<br>
    * Inspects the object type to call the correct JSONArray factory method.
    * Accepts JSON formatted strings, arrays, Collections and Enums.
    *
    * @param object
    * @throws JSONException if the object can not be converted to a proper
    *         JSONArray.
    */
	public static JSONArray buildJSONArray(Object object, JsonConfig jsonConfig) {
		return JSONArray.fromObject(object, jsonConfig);
	}
	
	/**
	 * buildJSONFunction
	 * @param functionStr, e.g. function (message){alert(message);}"
	 * @return
	 */
	public static JSONFunction buildJSONFunction(String functionStr) {
		return JSONFunction.parse(functionStr);
	}

   /**
    * Creates a JSONObject.<br>
    * Inspects the object type to call the correct JSONObject factory method.
    * Accepts JSON formatted strings, Maps, DynaBeans and JavaBeans.
    *
    * @param object
    * @throws JSONException if the object can not be converted to a proper
    *         JSONObject.
    */
	public static JSONObject buildJSONObject(Object object) {
		return JSONObject.fromObject(object);
	}

   /**
    * Creates a JSONObject.<br>
    * Inspects the object type to call the correct JSONObject factory method.
    * Accepts JSON formatted strings, Maps, DynaBeans and JavaBeans.
    *
    * @param object
    * @throws JSONException if the object can not be converted to a proper
    *         JSONObject.
    */
	public static JSONObject buildJSONObject(Object object, JsonConfig jsonConfig) {
		return JSONObject.fromObject(object, jsonConfig);
	}

	public static class DefaultJsonEventListenerImpl implements JsonEventListener {
		
		@Override
		public void onArrayEnd() {
		}

		@Override
		public void onArrayStart() {	
		}

		@Override
		public void onElementAdded(int index, Object element) {	
		}

		@Override
		public void onError(JSONException jsone) {
		}

		@Override
		public void onObjectEnd() {
		}

		@Override
		public void onObjectStart() {
		}

		@Override
		public void onPropertySet(String key, Object value, boolean accumulated) {
		}

		@Override
		public void onWarning(String warning) {
		}
		
	}
	
	public static void main(String[] args) {
		String json = "{codes:[{\"playType\":\"fushi\", \"codesInArea\":{\"red\":{\"_blist\":[\"28 \",\"29 \",\"30 \",\"31 \",\"32 \",\"33 \"]}, \"blue\":{\"_blist\":[\"07 \",\"08 \",\"09 \",\"13 \",\"14 \",\"15 \",\"16 \"]}}},{\"playType\":\"fushi\", \"codesInArea\":{\"red\":{\"_blist\":[\"27 \",\"28 \",\"29 \",\"30 \",\"31 \",\"32 \"]}, \"blue\":{\"_blist\":[\"13 \"]}}}]}";
		for (int i = 0; i < 2; i++) {
			JSONObject obj = buildJSONObject(json).getJSONArray("codes").getJSONObject(i);
			System.out.println(obj.get("playType"));
			System.out.println(((JSONObject)obj.get("codesInArea")).get("red"));
			System.out.println(((JSONObject)obj.get("codesInArea")).get("blue"));
		}

	}
}
