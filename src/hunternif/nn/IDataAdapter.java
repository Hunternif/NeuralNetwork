package hunternif.nn;

import java.util.List;

public interface IDataAdapter<T> {
	List<Double> dataToSignal(T input);
	T dataFromSignal(List<Double> output);
	int numberOfSignals();
}
