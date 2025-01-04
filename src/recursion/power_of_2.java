package recursion;

public class power_of_2 {
	public static int find_power_2(int a) {
		return helper(a);
	}
	
	public static int helper(int a) {
		if (a == 0) {
			return 0;
		}
		if (a==1) {
			return 0;
		}
		return 1 + helper(a/2);
	}
}
