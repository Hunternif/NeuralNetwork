package hunternif.nn.data;

import hunternif.nn.IDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The signal is composed of the input number's digits,
 * the first element represents sign (1 or -1).
 */
public class IntAdapter implements IDataAdapter<Integer> {
	private static final Map<Integer, List<Double>> cache = new HashMap<>();
	
	private final int numberOfSignals;
	
	public IntAdapter(int numberOfSignals) {
		this.numberOfSignals = numberOfSignals;
	}
	
	@Override
	public List<Double> dataToSignal(Integer input) {
		List<Double> signal = cache.get(input);
		if (signal == null) {
			signal = new ArrayList<>();
			// Fill the array with input's digits:
			int digits = Math.abs(input.intValue());
			int sign = input.intValue() > 0 ? 1 : -1;
			while (digits > 0) {
				signal.add(0, Double.valueOf(digits % 10));
				digits /= 10;
			}
			// Fill the empty digits in front with zeroes:
			while (signal.size() < numberOfSignals()) {
				signal.add(0, Double.valueOf(0));
			}
			// The first element is the sign:
			signal.set(0, Double.valueOf(sign));
			cache.put(input, signal);
		}
		return signal;
	}
	@Override
	public Integer dataFromSignal(List<Double> output) {
		int digits = 0;
		boolean isNegative = output.get(0).intValue() == -1;
		for (int i = 1; i < output.size(); i++) {
			Double digit = output.get(i);
			digits = digits*10 + (int)Math.abs(Math.round(digit.doubleValue()));
		}
		if (isNegative) {
			digits *= -1;
		}
		return Integer.valueOf(digits);
	}
	@Override
	public int numberOfSignals() {
		return numberOfSignals;
	}
}
