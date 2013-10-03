package hunternif.nn.util;

import hunternif.nn.Pattern;
import hunternif.nn.data.IntAdapter;

import java.util.HashMap;
import java.util.Map;

public class IntPattern extends Pattern<Integer, Integer> {
	private static final Map<Integer, IntAdapter> adapters = new HashMap<>();
	
	public IntPattern(int numberOfInputs, int input, int output) {
		super(getAdapter(numberOfInputs), input, getAdapter(numberOfInputs), output);
	}
	protected static IntAdapter getAdapter(int numberOfInputs) {
		IntAdapter adapter = adapters.get(Integer.valueOf(numberOfInputs));
		if (adapter == null) {
			adapter = new IntAdapter(numberOfInputs);
			adapters.put(Integer.valueOf(numberOfInputs), adapter);
		}
		return adapter;
	}
}
