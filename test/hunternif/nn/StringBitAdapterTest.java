package hunternif.nn;

import hunternif.nn.data.StringBitAdapter;
import junit.framework.Assert;

import org.junit.Test;

public class StringBitAdapterTest {
	public StringBitAdapter adapter = new StringBitAdapter(1000);
	
	@Test
	public void test() {
		Assert.assertEquals("lol", adapter.dataFromSignal(adapter.dataToSignal("lol")));
		
		Assert.assertEquals("I will eat you", adapter.dataFromSignal(adapter.dataToSignal("I will eat you")));
	}
}
