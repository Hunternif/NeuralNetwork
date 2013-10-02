package hunternif.nn;

import java.util.List;

public class TeachingPair<I, O> {
	public final IDataAdapter<I> inputAdapter;
	public final I input;
	public final IDataAdapter<O> outputAdapter;
	public final O output;
	
	public TeachingPair(IDataAdapter<I> inputAdapter, I input,
			IDataAdapter<O> outputAdapter, O output) {
		this.inputAdapter = inputAdapter;
		this.input = input;
		this.outputAdapter = outputAdapter;
		this.output = output;
	}
	
	public List<Double> getInputSignal() {
		return inputAdapter.dataToSignal(input);
	}
	
	public List<Double> getOutputSignal() {
		return outputAdapter.dataToSignal(output);
	}
}
