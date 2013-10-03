package hunternif.nn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NNetwork implements Iterable<List<? extends Neuron>>{
	protected final List<InputNeuron> inputLayer = new ArrayList<>();
	protected final List<List<Neuron>> midLayers = new ArrayList<>();
	protected final List<OutputNeuron> outputLayer = new ArrayList<>();
	
	/**
	 * @param layers specifies number of neurons per layer from input to output.
	 * <p>Example:<br>
	 * {@code new NNetwork(3, 2, 1)} produces a network with 3 input neurons,
	 * 1 middle layer with 2 neurons and 1 output neuron.
	 * </p>
	 * @throws NNException if the number of layers given is less than 2.
	 */
	public NNetwork(IActivationFunction function, int ... layers) throws NNException {
		if (layers.length < 2) {
			throw new NNException("A neural network must have at least 2 layers.");
		}
		for (int i = 0; i < layers[0]; i++) {
			inputLayer.add(new InputNeuron());
		}
		for (int i = 1; i < layers.length - 1; i++) {
			int layerSize = layers[i];
			List<Neuron> layer = new ArrayList<>();
			for (int j = 0; j < layerSize; j++) {
				layer.add(new Neuron(function));
			}
			midLayers.add(layer);
		}
		for (int i = 0; i < layers[layers.length-1]; i++) {
			outputLayer.add(new OutputNeuron(function));
		}
		wireAllLayers();
	}
	
	protected void wireAllLayers() {
		LayerIterator iter = new LayerIterator(this);
		List<? extends Neuron> inputLayer = iter.next();
		List<? extends Neuron> outputLayer = iter.next();
		while (true) {
			wireLayers(inputLayer, outputLayer);
			if (iter.hasNext()) {
				inputLayer = outputLayer;
				outputLayer = iter.next();
			} else {
				break;
			}
		}
	}
	protected void wireLayers(List<? extends Neuron> inputs, List<? extends Neuron> outputs) {
		double weight = 1d / outputs.size();
		for (Neuron input : inputs) {
			for (Neuron output : outputs) {
				output.setInputWeight(input, weight);
			}
		}
	}
	
	public int numberOfLayers() {
		return 2 + midLayers.size();
	}
	public int numberOfInputs() {
		return inputLayer.size();
	}
	public int numberOfOutputs() {
		return outputLayer.size();
	}
	
	protected List<? extends Neuron> getLayer(int index) throws IndexOutOfBoundsException {
		if (index == 0) {
			return inputLayer;
		} else if (index == numberOfLayers() - 1) {
			return outputLayer;
		} else {
			return midLayers.get(index - 1);
		}
	}
	
	public List<Double> process(List<Double> inputs) throws NNException {
		resetNeurons();
		if (inputs.size() != numberOfInputs()) {
			throw new NNException("Number of inputs doesn't match the number of input neurons");
		}
		for (int i = 0; i < inputs.size(); i++) {
			double inputSignal = inputs.get(i);
			inputLayer.get(i).input(inputSignal);
		}
		List<Double> results = new ArrayList<>();
		for (OutputNeuron outputNeuron : outputLayer) {
			results.add(outputNeuron.signal);
		}
		return results;
	}
	protected void resetNeurons() {
		for (List<? extends Neuron> list : this) {
			for (Neuron neuron : list) {
				neuron.reset();
			}
		}
	}

	@Override
	public Iterator<List<? extends Neuron>> iterator() {
		return new LayerIterator(this);
	}
}
