package models;

public class ValidationModel {
	public static boolean isValidatedStringWithSpace(String inputString) {
		return inputString.matches("[A-Za-z]+([ '-][a-zA-Z]+)*"); 
	}
	
	public static boolean isValidatedString(String inputString) {
		return inputString.matches("[A-Za-z]*"); 
	}
	
	public static boolean isSpecialCharacterOrSpace(char e) {
		String specialCharactersString = "\";: '&^%{}[]~`$#,./-_=";
		if (specialCharactersString.indexOf(e) != -1) {
			return true;
		} else {
			return false;
		}
	}
}
