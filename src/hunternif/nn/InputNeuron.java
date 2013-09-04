package hunternif.nn;

public class InputNeuron extends Neuron {
	@Override
	public float getInputWeight(Neuron input) {
		return 1;
	}
	
	public void input(float signal) {
		this.input(null, signal);
	}
}
