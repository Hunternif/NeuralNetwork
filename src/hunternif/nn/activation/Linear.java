package hunternif.nn.activation;

import hunternif.nn.IActivationFunction;

public class Linear implements IActivationFunction {

	@Override
	public double produce(double input) {
		return input;
	}

	@Override
	public double derivative(double input) {
		return 1;
	}

}
