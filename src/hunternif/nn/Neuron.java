package hunternif.nn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** This class is not thread-safe! */
public class Neuron {
	private float accumulatedInput = 0;
	private int inputsProcessed = 0;
	
	private Map<Neuron, Float> inputWeights = new HashMap<>();
	
	protected Set<Neuron> outputs = new HashSet<>();
	
	public Neuron() {}
	
	public void connectInput(Neuron input) {
		setInputWeight(input, 1);
	}
	public void setInputWeight(Neuron input, float weight) {
		weight = Math.max(0, Math.min(weight, 1));
		inputWeights.put(input, Float.valueOf(weight));
		input.connectOutput(this);
	}
	public float getInputWeight(Neuron input) {
		Float weight = inputWeights.get(input);
		if (weight == null) weight = Float.valueOf(0);
		return weight;
	}
	private void connectOutput(Neuron output) {
		outputs.add(output);
	}
	
	protected float transferFunction(float input) {
		return input;
	}
	
	public void input(Neuron source, float signal) {
		float weight = getInputWeight(source);
		accumulatedInput += signal * weight;
		inputsProcessed++;
		if (inputsProcessed >= inputWeights.size()) {
			float result = transferFunction(accumulatedInput);
			propagate(result);
		}
	}
	
	protected void reset() {
		accumulatedInput = 0;
		inputsProcessed = 0;
	}
	
	protected void propagate(float signal) {
		for (Neuron output : outputs) {
			output.input(this, signal);
		}
		reset();
	}
}
