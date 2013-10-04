package hunternif.nn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teacher {
	protected final List<Pattern<?,?>> patterns = new ArrayList<>();
	protected final NNetwork network;
	
	public Teacher(NNetwork network) {
		this.network = network;
	}
	
	public void addPattern(Pattern<?,?> pair) throws NNException {
		if (pair.inputAdapter.numberOfSignals() != network.numberOfInputs()) {
			throw new NNException("Incorrect number of inputs in pattern. " +
					"Network expected " + network.numberOfInputs() + ", but was" +
					pair.inputAdapter.numberOfSignals());
		}
		if (pair.outputAdapter.numberOfSignals() != network.numberOfOutputs()) {
			throw new NNException("Incorrect number of outputs in pattern. " +
					"Network expected " + network.numberOfOutputs() + ", but was" +
					pair.outputAdapter.numberOfSignals());
		}
		patterns.add(pair);
	}
	
	/** When objective function gets below this value, teaching finishes. */
	public double minObjective = 0.1;
	/** When incremental changes in objective value get lower than this value, teaching finished. */
	public double minObjectiveDelta = 0.001;
	/**When objective function increases in value this many times in a row, teaching finishes. */
	public double maxDiscrepancies = 3;
	
	public void teach() throws NNException {
		int discrepancies = 0;
		double objectiveValue = objectiveFunction();
		while (true) {
			System.out.println("Objective value = " + objectiveValue);
			// Perform gradient descent optimization:
			for (Pattern<?,?> pattern : patterns) {
				performGradientDescent(pattern);
			}
			
			// Defensive checks:
			double newObjectiveValue = objectiveFunction();
			if (newObjectiveValue > objectiveValue) {
				discrepancies++;
			}
			if (Double.isInfinite(newObjectiveValue)) {
				System.out.println("Failed to teach due to objective value overflow.");
				break;
			}
			if (Math.abs(newObjectiveValue - objectiveValue) < minObjectiveDelta) {
				System.out.println("Finished teaching due to objective value unchanching.");
				break;
			}
			objectiveValue = newObjectiveValue;
			if (objectiveValue < minObjective) {
				System.out.println("Finished teaching due to objective value becoming low.");
				break;
			}
			if (discrepancies > maxDiscrepancies) {
				System.out.println("Failed to teach due to objective discrepancy.");
				break;
			}
		}
	}
	
	protected double objectiveFunction() throws NNException {
		double result = 0;
		for (Pattern<?,?> pattern : patterns) {
			List<Double> processed = network.process(pattern.getInputSignal());
			for (int i = 0; i < processed.size(); i++) {
				result += Math.pow(processed.get(i).doubleValue() - pattern.getOutputSignal().get(i).doubleValue(), 2);
			}
		}
		return result;
	}
	
	/** Last value of objective function's partial derivative with respect to
	 * neuron's last output value.
	 */
	private Map<Neuron, Double> derivatives = new HashMap<>();
	
	public double gradientStep = 0.01;
	
	protected void performGradientDescent(Pattern<?,?> pattern) throws NNException {
		try {
			network.process(pattern.getInputSignal());
			
			// Calculate derivatives for the final layer:
			for (int i = 0; i < network.numberOfOutputs(); i++) {
				Neuron neuron = network.outputLayer.get(i);
				Double correctValue = pattern.getOutputSignal().get(i);
				derivatives.put(neuron, (neuron.lastResult - correctValue)*2);
			}
			
			// Iterate through layers backwards and compute layer's derivatives
			// and weights from the next layer's ones.
			LayerIterator iter = new LayerIterator(network);
			// Must start from the next to last layer:
			List<? extends Neuron> curLayer = iter.previous();
			while (iter.hasPrevious()) {
				curLayer = iter.previous();
				// The gist of calculation:
				for (Neuron curNeuron : curLayer) {
					double derivative = 0;
					for (Neuron nextNeuron : curNeuron.outputs) {
						double weight = nextNeuron.getInputWeight(curNeuron);
						double nextObjDeriv = derivatives.get(nextNeuron);
						double nextActivFeriv = nextNeuron.getActivationFunction().derivative(nextNeuron.accumulatedInput);
						derivative += weight * nextObjDeriv * nextActivFeriv;
						//System.out.println("Gradient is " + nextObjDeriv * nextActivFeriv * curNeuron.lastResult);
						weight -= gradientStep * nextObjDeriv * nextActivFeriv * curNeuron.lastResult;
						nextNeuron.setInputWeight(curNeuron, weight);
					}
					derivatives.put(curNeuron, Double.valueOf(derivative));
				}
			}
		} catch (NNException e) {
			throw e;
		} catch (Exception e) {
			throw new NNException(e);
		}
	}
}
