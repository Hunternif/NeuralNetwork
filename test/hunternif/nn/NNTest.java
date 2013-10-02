package hunternif.nn;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import hunternif.nn.NNetwork.LayerIterator;
import hunternif.nn.activation.Linear;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NNTest {
	
	private NNetwork networkLinear;
	
	@Before
	public void setup() {
		try {
			networkLinear = new NNetwork(new Linear(), 3, 2, 2);
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void instantiationTest() {
		assertEquals(networkLinear.inputLayer.size(), 3);
		assertEquals(networkLinear.midLayers.size(), 1);
		assertEquals(networkLinear.midLayers.get(0).size(), 2);
		assertEquals(networkLinear.outputLayer.size(), 2);
		assertEquals(networkLinear.layersNumber(), 3);
	}
	
	@Test
	public void layerIteratorTest() {
		LayerIterator iter = new LayerIterator(networkLinear);
		while (iter.hasNext()) {
			List<? extends Neuron> layer = iter.next();
			assertNotNull(layer);
			System.out.println(layer.toString());
		}
	}
	
	@Test
	public void wiringTest() {
		Neuron n1 = networkLinear.inputLayer.get(2);
		Neuron n2 = networkLinear.midLayers.get(0).get(1);
		Neuron n3 = networkLinear.outputLayer.get(1);
		assertTrue(n1.outputs.contains(n2));
		assertTrue(n2.outputs.contains(n3));
		assertFalse(n1.outputs.contains(n3));
	}
	
	@Test
	public void processZeroesTest() {
		try {
			List<Double> outputs = networkLinear.process(Arrays.asList(0d, 0d, 0d));
			for (double result : outputs) {
				assertEquals(0d, result);
			}
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void processOnesTest() {
		try {
			List<Double> outputs = networkLinear.process(Arrays.asList(1d, 1d, 1d));
			for (double result : outputs) {
				assertEquals(6d, result);
			}
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void tooFewArgumentsTest() {
		try {
			networkLinear.process(Arrays.asList(1d, 2d));
			fail("Accepted 2 arguments when 3 were required.");
		} catch (NNException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void tooManyArgumentsTest() {
		try {
			networkLinear.process(Arrays.asList(1d, 2d, 3d, 4d));
			fail("Accepted 4 arguments when 3 were required.");
		} catch (NNException e) {
			assertNotNull(e);
		}
	}

}
