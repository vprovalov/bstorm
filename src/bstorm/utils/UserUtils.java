package bstorm.utils;

import bstorm.entity.User;

public class UserUtils {
	public static boolean isInRole(final User user, String role) {
		String[] roles = user.getRole().split(",");
		for (String r : roles) {
			if (r.equals(role)) return true;
		}
		return false;
	}
}
