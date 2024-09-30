package com.saran.tms.validators;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.saran.tms.config.ColumnConfig;
import com.saran.tms.config.DataBaseConfig;
import com.saran.tms.config.TableConfig;
import com.saran.tms.enums.Constraints;
import com.saran.tms.enums.StatusCodes;
import com.saran.tms.exceptions.ResponseException;
import com.saran.tms.logger.ApplicationLogger;
import com.saran.tms.models.Model;
import com.saran.tms.utils.Accessor;

public class ModelValidator {
	
	private static boolean checkRegex(String value, String regex) {
	       Pattern pattern = Pattern.compile(regex);
	       return pattern.matcher(value).matches();
	}
	
	private static boolean validateField(Object fieldValue, Constraints constraint) {
		if(constraint == null) {
			return true;
		}
		switch(constraint) {
			case NOT_NULL:
				return fieldValue != null;
			case EMAIL:
				return checkRegex((String) fieldValue, "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
			case PASSWORD:
				return checkRegex((String) fieldValue, "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
			case PHONE_NUMBER:
				return checkRegex((String) fieldValue, "^[0-9]{10}$");
			default:
				return true;
		}
	}
	
	public static boolean validateModel(Model model) throws ResponseException {
		
		Class<?> modelClass = model.getClass();
		String modelName = modelClass.getSimpleName();
		
		Field[] fields = modelClass.getDeclaredFields();
		
		TableConfig tableConfig = DataBaseConfig.getTableConfigByModelName(modelName);
		
		for(Field field : fields) {
			String fieldName = field.getName();
			
			ColumnConfig columnConfig = tableConfig.getColumnConfigByFieldName(fieldName);
			
			Object fieldValue = null;
			try {
				fieldValue = Accessor.getValue(model, fieldName);
			} 
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				ApplicationLogger.log(Level.SEVERE, "Unable to access the method using reflection", e);
				throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the request");
			}
			
			Set<Constraints> columnConstraints = columnConfig.getConstraints();
			
			if(columnConstraints.contains(Constraints.AUTO_ASSIGN)) {
				try {
					Accessor.setValue(model, field.getType().getSimpleName(), fieldName, null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					ApplicationLogger.log(Level.SEVERE, "Unable to access the method using reflection", e);
					throw new ResponseException(StatusCodes.INTERNAL_SERVER_ERROR, "Unable to process the request");
				}
				continue;
			}
			for(Constraints constraint : columnConstraints) {
				if(!validateField(fieldValue, constraint)) {
					return false;
				}
			}
		}
		
		return true;
	}
}
