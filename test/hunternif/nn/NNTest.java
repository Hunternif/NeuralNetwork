package hunternif.nn;

import static junit.framework.Assert.*;

import hunternif.nn.NNetwork.LayerIterator;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NNTest {
	
	private NNetwork network;
	
	@Before
	public void setup() {
		try {
			network = new NNetwork(3, 2, 2);
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void instantiationTest() {
		assertEquals(network.inputLayer.size(), 3);
		assertEquals(network.midLayers.size(), 1);
		assertEquals(network.midLayers.get(0).size(), 2);
		assertEquals(network.outputLayer.size(), 2);
		assertEquals(network.layersNumber(), 3);
	}
	
	@Test
	public void layerIteratorTest() {
		LayerIterator iter = new LayerIterator(network);
		while (iter.hasNext()) {
			List<? extends Neuron> layer = iter.next();
			assertNotNull(layer);
			System.out.println(layer.toString());
		}
	}
	
	@Test
	public void wiringTest() {
		Neuron n1 = network.inputLayer.get(2);
		Neuron n2 = network.midLayers.get(0).get(1);
		Neuron n3 = network.outputLayer.get(1);
		assertTrue(n1.outputs.contains(n2));
		assertTrue(n2.outputs.contains(n3));
		assertFalse(n1.outputs.contains(n3));
	}
	
	@Test
	public void processZeroesTest() {
		try {
			List<Float> outputs = network.process(Arrays.asList(0f, 0f, 0f));
			for (float result : outputs) {
				assertEquals(result, 0f);
			}
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void processOnesTest() {
		try {
			List<Float> outputs = network.process(Arrays.asList(1f, 1f, 1f));
			for (float result : outputs) {
				assertEquals(result, 6f);
			}
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void tooFewArgumentsTest() {
		try {
			network.process(Arrays.asList(1f, 2f));
			fail("Accepted 2 arguments when 3 were required.");
		} catch (NNException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void tooManyArgumentsTest() {
		try {
			network.process(Arrays.asList(1f, 2f, 3f, 4f));
			fail("Accepted 4 arguments when 3 were required.");
		} catch (NNException e) {
			assertNotNull(e);
		}
	}

}
