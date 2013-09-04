package hunternif.nn;

public class OutputNeuron extends Neuron {
	public double signal = 0;
	
	public OutputNeuron() {
		super(new Linear());
	}
	
	@Override
	protected void propagate(double signal) {
		this.signal = signal;
	}
}
