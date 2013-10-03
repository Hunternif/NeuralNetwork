package hunternif.nn.data;

import hunternif.nn.IDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringBitAdapter implements IDataAdapter<String> {
	private static final Map<String, List<Double>> cache = new HashMap<>();
	
	private final int numberOfSignals;
	
	public StringBitAdapter(int numberOfSignals) {
		this.numberOfSignals = numberOfSignals;
	}
	
	@Override
	public List<Double> dataToSignal(String input) {
		List<Double> signal = cache.get(input);
		if (signal == null) {
			signal = new ArrayList<>();
			for (int i = 0; i < input.length(); i++) {
				String binary = Integer.toBinaryString(input.charAt(i));
				// Add leading zeroes:
				for (int j = 0; j < 16-binary.length(); j++) {
					signal.add(Double.valueOf(0));
				}
				for (int j = 0; j < binary.length(); j++) {
					signal.add(Double.valueOf(binary.charAt(j) == '1' ? 1.0 : 0));
				}
			}
			// Fill the empty letters to the end:
			while (signal.size() < numberOfSignals()) {
				signal.add(Double.valueOf(0));
			}
			cache.put(input, signal);
		}
		return signal;
	}
	@Override
	public String dataFromSignal(List<Double> output) {
		StringBuilder sb = new StringBuilder();
		char curChar = 0;
		int posInChar = 0;
		for (Double doubleValue : output) {
			int bit = Math.min((int)Math.abs(Math.round(doubleValue.doubleValue())), 1);
			curChar |= bit << (15 - posInChar++);
			//System.out.println(Integer.toBinaryString(curChar));
			if (posInChar == 16) {
				posInChar = 0;
				if (curChar != 0) {
					sb.append(curChar);
				}
				curChar = 0;
			}
		}
		return sb.toString();
	}
	@Override
	public int numberOfSignals() {
		return numberOfSignals;
	}
}
