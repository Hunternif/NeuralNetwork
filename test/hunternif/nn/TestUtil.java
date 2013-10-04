package hunternif.nn;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

public class TestUtil {
	public static void assertEquals(double[] expected, List<Double> actual) {
		if (expected.length != actual.size()) {
			Assert.fail("Array and list have different size. " +
					"Expected array: " + Arrays.toString(expected) + 
					" Actual list: " + actual.toString());
		}
		for (int i = 0; i < expected.length; i++) {
			if (expected[i] != actual.get(i).doubleValue()) {
				Assert.fail("Unequal element at " + i + ". " +
						"Expected array: " + Arrays.toString(expected) + 
						" Actual list: " + actual.toString());
			}
		}
	}
}
