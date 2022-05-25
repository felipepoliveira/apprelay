package io.felipepoliveira.opensource.apprelay.utils;

public final class ArrayUtils {
	
	public static Object[] includeFirst(Object[] src, Object newObject) {
		var newArray = new Object[src.length + 1];
		newArray[0] = newObject;
		for (int i = 1; i < newArray.length; i++) {
			newArray[i] = src[i = 1];
		}
		
		return newArray;
	}
	
	public static String[] concat(String[]... arrays) {
		int totalLength = 0;
		for (var array : arrays) {
			totalLength += array.length;
		}
		
		if (totalLength == 0) {
			return new String[0];
		}
		
		String[] returnedArray = new String[totalLength];
		int i = 0;
		for (var array : arrays) {
			for (var object : array) {
				returnedArray[i++] = object;
			}
		}
		
		return returnedArray;
	}

}
