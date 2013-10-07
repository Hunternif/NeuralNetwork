package hunternif.nn.data;

import hunternif.nn.IDataAdapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlphabetAdapter implements IDataAdapter<Character> {
	private final Map<Character, List<Double>> cache = new HashMap<>();
	protected final char[] alphabet;

	public AlphabetAdapter(char firstChar, char lastChar) {
		int length = lastChar - firstChar + 1;
		alphabet = new char[length];
		for (int i = 0; i < length; i++) {
			alphabet[i] = (char) (firstChar + i);
		}
	}

	@Override
	public List<Double> dataToSignal(Character input) {
		List<Double> signal = cache.get(input);
		if (signal == null) {
			Double[] signalArray = new Double[alphabet.length];
			Arrays.fill(signalArray, 0, alphabet.length, Double.valueOf(0));
			int index = input.charValue() - alphabet[0];
			signalArray[index] = Double.valueOf(1);
			signal = Arrays.asList(signalArray);
			cache.put(input, signal);
		}
		return signal;
	}

	@Override
	public Character dataFromSignal(List<Double> output) {
		int highestOutputIndex = -1;
		double minimum = output.get(0).doubleValue();
		for (Double node : output) {
			if (node.doubleValue() < minimum) {
				minimum = node.doubleValue();
			}
		}
		double highestOutput = minimum;
		for (int i = 0; i < output.size(); i++) {
			if (output.get(i).doubleValue() > highestOutput) {
				highestOutput = output.get(i).doubleValue();
				highestOutputIndex = i;
			}
		}
		if (highestOutputIndex == -1) {
			return null;
		}
		return Character.valueOf(alphabet[highestOutputIndex]);
	}

	@Override
	public int numberOfSignals() {
		return alphabet.length;
	}

}
