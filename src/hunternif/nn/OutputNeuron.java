package hunternif.nn;

public class OutputNeuron extends Neuron {
	public float signal = 0;
	
	@Override
	protected void propagate(float signal) {
		this.signal = signal;
	}
}
