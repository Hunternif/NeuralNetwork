package hunternif.nn.data;

import hunternif.nn.IDataAdapter;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter implements IDataAdapter<String> {
	protected final AlphabetAdapter alphabet;
	protected final int wordLength;
	
	public WordAdapter(AlphabetAdapter alphabet, int wordLength) {
		this.alphabet = alphabet;
		this.wordLength = wordLength;
	}
	
	@Override
	public List<Double> dataToSignal(String input) {
		List<Double> signal = new ArrayList<>();
		for (int i = 0; i < input.length(); i++) {
			signal.addAll(alphabet.dataToSignal(Character.valueOf(input.charAt(i))));
		}
		// Add trailing zeroes:
		while (signal.size() < numberOfSignals()) {
			signal.add(Double.valueOf(0));
		}
		return signal;
	}
	@Override
	public String dataFromSignal(List<Double> output) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < wordLength; i++) {
			List<Double> charOutput = output.subList(alphabet.numberOfSignals()*i, alphabet.numberOfSignals()*(i+1));
			Character ch = alphabet.dataFromSignal(charOutput);
			if (ch != null) {
				sb.append(ch.charValue());
			}
		}
		return sb.toString();
	}
	@Override
	public int numberOfSignals() {
		return wordLength * alphabet.numberOfSignals();
	}
}
