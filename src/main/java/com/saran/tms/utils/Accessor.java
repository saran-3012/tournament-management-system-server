package com.saran.tms.utils;

public class Accessor {
	
	public static String getAccessor(String fieldName, String accessPrefix) {
		return new StringBuilder(accessPrefix)
					.append(Character.toUpperCase(fieldName.charAt(0)))
					.append(fieldName.substring(1))
					.toString();
	}
	
	public static void setValue(Object obj, String dataType, String fieldName, Object fieldValue) throws Exception {
		String setterName = getAccessor(fieldName, "set");
		Class<?> objClass = obj.getClass();
		
		String fieldStringValue = null;
		
		
		if(fieldValue != null) {
			fieldStringValue = fieldValue.toString();
		}
		
		if(dataType.equals("Short") || dataType.equals("short") ) {
			objClass.getDeclaredMethod(setterName, Short.class).invoke(obj, fieldValue == null? null : Short.parseShort(fieldStringValue));
		}
		else if(dataType.equals("Integer") || dataType.equals("int") ) {
			objClass.getDeclaredMethod(setterName, Integer.class).invoke(obj, fieldValue == null? null : Integer.parseInt(fieldStringValue));
		}
		else if(dataType.equals("Long") || dataType.equals("long") ) {
			objClass.getDeclaredMethod(setterName, Long.class).invoke(obj, fieldValue == null? null : Long.parseLong(fieldStringValue));
		}
		else if(dataType.equals("Float") || dataType.equals("float") ) {
			objClass.getDeclaredMethod(setterName, Float.class).invoke(obj, fieldValue == null? null : Float.parseFloat(fieldStringValue));
		}
		else if(dataType.equals("Double") || dataType.equals("double") ) {
			objClass.getDeclaredMethod(setterName, Double.class).invoke(obj, fieldValue == null? null : Double.parseDouble(fieldStringValue));
		}
		else if(dataType.equals("String")) {
			objClass.getDeclaredMethod(setterName, String.class).invoke(obj, fieldStringValue);
		}
		else {
			objClass.getDeclaredMethod(setterName, Object.class).invoke(obj, fieldValue);
		}
	}
	
	public static Object getValue(Object obj, String fieldName) throws Exception {
		String getterName = getAccessor(fieldName, "get");
		return obj.getClass().getDeclaredMethod(getterName).invoke(obj);
	}
}
