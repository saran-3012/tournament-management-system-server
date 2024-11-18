package com.saran.tms.adapters;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.saran.tms.config.DataBaseConfig;
import com.saran.tms.config.TableConfig;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.models.Model;
import com.saran.tms.utils.Accessor;

public class ModelMapParser {

	public static Map<String, Object> convertToMap(Model model) throws IllegalArgumentException {
		
		Class<?> objectClass = model.getClass();
		
		TableConfig tableConfig = DataBaseConfig.getTableConfigByModelName(objectClass.getSimpleName());
		
		Field[] fields = objectClass.getDeclaredFields();
		
		Map<String, Object> objectMap = new HashMap<>();
		
		for(Field field : fields) {
			
			String fieldName = field.getName();
			String columnName = tableConfig.getColumnName(fieldName);
			
			
			Object fieldValue = null;
			
			try {
				fieldValue = Accessor.getValue(model, fieldName);
			} 
			catch (Exception e) {
				e.printStackTrace();
				ApplicationLogger.log(Level.WARNING, "Unable to invoke function with reflection", e);
				throw new IllegalArgumentException("Failed to get " + fieldName + " value from " + objectClass.getSimpleName());
			}
			
			if(fieldValue == null) {
				continue;
			}

			objectMap.put(columnName, fieldValue);
		}
		
		
		return objectMap;
	}
}
