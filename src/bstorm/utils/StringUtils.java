package bstorm.utils;

public class StringUtils {
	public static boolean validateUsername(final String value) {
		for(int idx = 0; idx != value.length(); ++idx) {
			char ch = value.charAt(idx);
			if (!Character.isDigit(ch) && (Character.toLowerCase(ch) < 'a' || Character.toLowerCase(ch) > 'z')) {
				return false;
			}
		}
		return true;
	}
}
