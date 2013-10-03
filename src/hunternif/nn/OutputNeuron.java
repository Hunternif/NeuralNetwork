package hunternif.nn;


public class OutputNeuron extends Neuron {
	public double signal = 0;
	
	public OutputNeuron(IActivationFunction function) {
		super(function);
	}
	
	@Override
	protected void propagate(double signal) {
		this.signal = signal;
	}
}
