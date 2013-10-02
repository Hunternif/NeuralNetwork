package hunternif.nn;

public interface IActivationFunction {
	double produce(double input);
	double derivative(double input);
}
