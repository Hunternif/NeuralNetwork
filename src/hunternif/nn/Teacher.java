package hunternif.nn;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
	protected final List<TeachingPattern<?,?>> teachingPatterns = new ArrayList<>();
	protected final NNetwork network;
	
	public Teacher(NNetwork network) {
		this.network = network;
	}
	
	public void addTeachingPattern(TeachingPattern<?,?> pair) throws NNException {
		if (pair.inputAdapter.numberOfSignals() != network.numberOfInputs()) {
			throw new NNException("Incorrect number of inputs in TeachingPair. " +
					"Network expected " + network.numberOfInputs() + ", but was" +
					pair.inputAdapter.numberOfSignals());
		}
		if (pair.outputAdapter.numberOfSignals() != network.numberOfOutputs()) {
			throw new NNException("Incorrect number of outputs in TeachingPair. " +
					"Network expected " + network.numberOfOutputs() + ", but was" +
					pair.outputAdapter.numberOfSignals());
		}
		teachingPatterns.add(pair);
	}
	
	/** When objective function gets below this value, teaching finishes. */
	public static double minObjective = 0.1;
	/** When incremental changes in objective value get lower than this value, teaching finished. */
	public static double minObjectiveDelta = 0.01;
	/**When objective function increases in value this many times in a row, teaching finishes. */
	public static double maxDiscrepancies = 3;
	
	public void teach() throws NNException {
		int discrepancies = 0;
		double objectiveValue = objectiveFunction();
		while (true) {
			//TODO: Perform gradient descent optimization
			// Defensive checks:
			double newObjectiveValue = objectiveFunction();
			if (newObjectiveValue > objectiveValue) {
				discrepancies++;
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
				System.out.println("Finished teaching due to objective discrepancy.");
				break;
			}
		}
	}
	
	protected double objectiveFunction() throws NNException {
		double result = 0;
		for (TeachingPattern<?,?> pattern : teachingPatterns) {
			List<Double> processed = network.process(pattern.getInputSignal());
			for (int i = 0; i < processed.size(); i++) {
				result += Math.pow(processed.get(i).doubleValue() - pattern.getOutputSignal().get(i).doubleValue(), 2);
			}
		}
		return result;
	}
}
