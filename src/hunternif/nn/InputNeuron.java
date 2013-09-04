package hunternif.nn;

public class InputNeuron extends Neuron {
	public InputNeuron() {
		super(new Linear());
	}

	@Override
	public double getInputWeight(Neuron input) {
		return 1;
	}
	
	public void input(double signal) {
		this.input(null, signal);
	}
}
