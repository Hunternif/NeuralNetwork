package hunternif.nn;

import hunternif.nn.data.IntAdapter;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class IntAdapterTest {
	public IntAdapter adapter = new IntAdapter(5);
	
	@Test
	public void forthPositive() {
		TestUtil.assertEquals(new double[]{1,0,1,2,3}, adapter.dataToSignal(123));
	}
	
	@Test
	public void backPositive() {
		List<Double> exactDigits = Arrays.asList(1.0, 0.0, 1.0, 0.0);
		Assert.assertEquals(10, adapter.dataFromSignal(exactDigits).intValue());
		List<Double> approximateDigits = Arrays.asList(1.3, -0.5, 1.1, 0.49);
		Assert.assertEquals(10, adapter.dataFromSignal(approximateDigits).intValue());
	}
	
	@Test
	public void forthNegative() {
		TestUtil.assertEquals(new double[]{-1,0,2,0,3}, adapter.dataToSignal(-203));
	}
	
	@Test
	public void backNegative() {
		List<Double> digits = Arrays.asList(-1.0, 1.0, 2.0, 3.0);
		Assert.assertEquals(-123, adapter.dataFromSignal(digits).intValue());
	}
}
