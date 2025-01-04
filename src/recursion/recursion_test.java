package recursion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import testSupport.GraphVisualization;
import testSupport.CodeSaver;
public class recursion_test {
	@Test
	public void testPower() {
		try {
			assertEquals(4, power_of_2.find_power_2(32768));
	
		}
		catch (Exception e){
			GraphVisualization.createAndShowGUI();
	
		}
	}
	
	@Test
	public void testPower2() {
		try {
			assertEquals(10, power_of_2.find_power_2(1024));

		}catch(org.opentest4j.AssertionFailedError e) {
			GraphVisualization.expectPattern = new ArrayList<>(List.of(0,1,2,3,4,6,6,7,8,9,10));
			GraphVisualization.createAndShowGUI();
		}

	}
}
