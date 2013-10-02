package hunternif.nn;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import hunternif.nn.activation.Linear;
import hunternif.nn.util.IOUtil;

import java.io.File;

import org.junit.Test;

public class IOTest {
	@Test
	public void regularIOTest() {
		try {
			File file = new File("test_weights.json");
			
			NNetwork network = new NNetwork(new Linear(), 3, 2);
			Neuron outNeuron = network.outputLayer.get(1);
			Neuron inNeuron = network.inputLayer.get(2);
			outNeuron.setInputWeight(inNeuron, -2.5);
			IOUtil.serializeWeights(network, file);
			
			NNetwork networkCopy = new NNetwork(new Linear(), 3, 2);
			IOUtil.deserializeWeights(networkCopy, file);
			outNeuron = networkCopy.outputLayer.get(1);
			inNeuron = networkCopy.inputLayer.get(2);
			
			assertEquals(-2.5, outNeuron.getInputWeight(inNeuron), 0);
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void wrongNumberOfNeuronsTest() {
		try {
			File file = new File("test_weights.json");
			
			NNetwork network = new NNetwork(new Linear(), 3, 2);
			IOUtil.serializeWeights(network, file);
			
			NNetwork networkCopy = new NNetwork(new Linear(), 2, 3);
			IOUtil.deserializeWeights(networkCopy, file);
			
			fail("Accepted wrong number of neurons.");
		} catch (NNException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void wrongNumberOfNeuronsTest2() {
		try {
			File file = new File("test_weights.json");
			
			NNetwork network = new NNetwork(new Linear(), 2, 3);
			IOUtil.serializeWeights(network, file);
			
			NNetwork networkCopy = new NNetwork(new Linear(), 3, 2);
			IOUtil.deserializeWeights(networkCopy, file);
			
			fail("Accepted wrong number of neurons.");
		} catch (NNException e) {
			e.printStackTrace();
		}
	}
}
