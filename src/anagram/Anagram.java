package anagram;

import static org.junit.Assume.assumeTrue;

/**
 * This utility class can test whether two strings are anagrams.
 *
 * @author Claude Anderson.
 */
public class Anagram {

	/**
	 * We say that two strings are anagrams if one can be transformed into the other
	 * by permuting the characters (and ignoring case).
	 * 
	 * For example, "Data Structure" and "Saturated Curt" are anagrams, as are
	 * "Elvis" and "Lives".
	 * 
	 * @param s1 first string
	 * @param s2 second string
	 * @return true iff s1 is an anagram of s2
	 */
	public static boolean isAnagram(String s1, String s2) {
		if (s1.length() != s2.length()) {
			return false;
		}
		boolean bol = helper("","");
		String comparingString = s1.toUpperCase();
		s2 = s2.toUpperCase();
		for (int a = 0; a < s1.length(); a++) {
			String finding = comparingString.charAt(a) + "";
			if (!s2.contains(finding)) {
				return false;
			} else if (s2.length() > 1) {
				s2 = s2.substring(0, s2.indexOf(finding)) + s2.substring(s2.indexOf(finding) + 1);
			}
		}
		return false;
	}
	public static boolean helper(String s1, String s2) {
		return false;
	}

}
