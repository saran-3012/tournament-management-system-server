package com.saran.tms.adapters;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Level;

import com.saran.tms.config.DataBaseConfig;
import com.saran.tms.config.TableConfig;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.models.Model;
import com.saran.tms.utils.Accessor;

public class MapModelParser {
	
	private static String packageName = DataBaseConfig.getModelPackage();

	
	public static Model convertToObject(String className, Map<String, Object> objectMap) throws ResponseException {
		
		if(objectMap == null) {
			throw new IllegalArgumentException("The map cannot be null");
		}
		
		if(objectMap.isEmpty()) {
			return null;
		}
		
		Model obj = null;
		
		try {
			obj = (Model) Class.forName(packageName + '.' + className).getDeclaredConstructor().newInstance();
		} 
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
			ApplicationLogger.log(Level.WARNING, "Unable to invoke function with reflection", e);
			throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the content");

		}
		Class<?> objectClass = obj.getClass();
		
		TableConfig tableConfig = DataBaseConfig.getTableConfigByModelName(objectClass.getSimpleName());
		
		Field[] fields = objectClass.getDeclaredFields();
		
		for(Field field : fields) {
			
			String fieldName = field.getName();
			String columnName = tableConfig.getColumnName(fieldName);
			
			Object fieldValue = objectMap.get(columnName);
			
			if(fieldValue == null) {
				continue;
			}
			
			try {
				Accessor.setValue(obj, field.getType().getSimpleName(), fieldName, fieldValue);
			} 
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				ApplicationLogger.log(Level.WARNING, "Unable to invoke function with reflection", e);
				throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the content");
			}

		}

		return obj;
		
	}
}
