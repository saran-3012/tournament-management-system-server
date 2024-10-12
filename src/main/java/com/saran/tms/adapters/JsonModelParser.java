package com.saran.tms.adapters;


import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.models.Model;
import com.saran.tms.utils.Accessor;

public class JsonModelParser {
	
	public static Model parse(JSONObject jsonObject, Class<? extends Model> modelClass) throws ResponseException {
		
		if(jsonObject == null) {
			return null;
		}
		
		Model model = null;
		try {
			model = modelClass.getDeclaredConstructor().newInstance();
		} 
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.WARNING, "Unable to create an instance with reflection", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the content");
		}
		
		Field fields[] = modelClass.getDeclaredFields();
		
		for(Field field : fields) {
			
			String fieldName = field.getName();
			if(!jsonObject.has(fieldName)) {
				continue;
			}
			Object fieldValue = jsonObject.get(fieldName);
			if(fieldValue == null) { 
				continue;
			}
			try {
				Accessor.setValue(model, field.getType().getSimpleName(), fieldName, fieldValue);
			} 
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				ApplicationLogger.log(Level.WARNING, "Unable to invoke function with reflection", e);
				throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the content");
			}
			
		}
		
		return model;
	}
	
	public static List<Model> parse(JSONArray jsonArray, List<Class<? extends Model>> modelClasses) throws ResponseException {
		
		List<Model> models = new ArrayList<>();
		
		int n = jsonArray.length();
		
		for(int i=0; i<n; i++) {
			models.add(parse(jsonArray.getJSONObject(i), modelClasses.get(i)));
		}
		
		return models;
	}
}
