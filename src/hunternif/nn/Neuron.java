package hunternif.nn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is not thread-safe! */
public class Neuron {
	private IActivationFunction function;
	
	protected double accumulatedInput = 0;
	private int inputsProcessed = 0;
	
	private Map<Neuron, Double> inputWeights = new HashMap<>();
	protected List<Neuron> outputs = new ArrayList<>();
	
	protected double lastResult = 0;
	
	public Neuron(IActivationFunction function) {
		this.function = function;
	}
	
	public void connectInput(Neuron input) {
		setInputWeight(input, 1);
	}
	public void setInputWeight(Neuron input, double weight) {
		inputWeights.put(input, Double.valueOf(weight));
		input.connectOutput(this);
	}
	public double getInputWeight(Neuron input) {
		Double weight = inputWeights.get(input);
		return weight == null ? 0 : weight.doubleValue();
	}
	private void connectOutput(Neuron output) {
		if (!outputs.contains(output)) {
			outputs.add(output);
		}
	}
	
	public void input(Neuron source, double signal) {
		double weight = getInputWeight(source);
		accumulatedInput += signal * weight;
		inputsProcessed++;
		if (inputsProcessed >= inputWeights.size()) {
			double result = function.produce(accumulatedInput);
			lastResult = result;
			propagate(result);
		}
	}
	
	protected void reset() {
		accumulatedInput = 0;
		inputsProcessed = 0;
	}
	
	protected void propagate(double signal) {
		for (Neuron output : outputs) {
			output.input(this, signal);
		}
	}
	
	public IActivationFunction getActivationFunction() {
		return function;
	}
}
