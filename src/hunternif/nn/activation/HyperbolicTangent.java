package hunternif.nn.activation;

import hunternif.nn.IActivationFunction;

public class HyperbolicTangent implements IActivationFunction {

	@Override
	public double produce(double input) {
		return Math.tanh(input);
	}

}
