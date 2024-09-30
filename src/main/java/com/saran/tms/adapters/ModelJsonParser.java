package com.saran.tms.adapters;

import org.json.JSONObject;
import org.json.JSONArray;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.logging.Level;

import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.models.Model;
import com.saran.tms.utils.Accessor;


public class ModelJsonParser {
	
	public static JSONObject parse(Model model) throws ResponseException {
		
		Class<?> modelClass = model.getClass();
		Field fields[] = modelClass.getDeclaredFields();
		
		JSONObject jsonObject = new JSONObject();
		
		for(Field field : fields) {
			
			String fieldName = field.getName();
			Object fieldValue = null;
			
			try {
				fieldValue = Accessor.getValue(model, fieldName);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				ApplicationLogger.log(Level.WARNING, "Unable to invoke function with reflection", e);
				throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the content");
			}
			
			if(fieldValue == null) {
				continue;
			}
			
			jsonObject.put(fieldName, fieldValue);
		}
		
		return jsonObject;
	}
	
	public static JSONArray parse(List<Model> models) throws ResponseException {
		
		JSONArray jsonArray = new JSONArray();
		
		for(Model model : models) {
			jsonArray.put(parse(model));
		}
		
		
		return jsonArray;
	}
}
