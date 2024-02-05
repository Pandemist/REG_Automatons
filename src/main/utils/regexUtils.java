package main.utils;

public class regexUtils {

	public static boolean syntaxCheck(String regex) {
		int bracketCounter = 0;
		for(Character c : regex.toCharArray()) {
			if(c == '(') {
				bracketCounter++;
			}else if(c == ')') {
				bracketCounter--;
			}
			if(bracketCounter<0) {
				return false;
			}
		}
		if(bracketCounter!=0) {
			return false;
		}
		return true;
	}
}
