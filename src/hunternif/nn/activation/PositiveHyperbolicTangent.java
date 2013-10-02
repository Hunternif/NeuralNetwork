package hunternif.nn.activation;

import hunternif.nn.IActivationFunction;

public class PositiveHyperbolicTangent implements IActivationFunction {

	@Override
	public double produce(double input) {
		return 1 / (1 + Math.exp(-input));
	}

}
