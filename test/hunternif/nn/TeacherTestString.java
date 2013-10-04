package hunternif.nn;

import static junit.framework.Assert.fail;
import hunternif.nn.activation.PositiveHyperbolicTangent;
import hunternif.nn.data.StringBitAdapter;

import java.util.Random;

import org.junit.Test;

public class TeacherTestString {
	public static final int CHAR_SIZE = 7;
	public static final int MAX_WORD_LENGTH = 8;
	public static final int MIN_WORD_LENGTH = 2;
	public static final int INPUTS = MAX_WORD_LENGTH * CHAR_SIZE;
	
	public StringBitAdapter adapter = new StringBitAdapter(CHAR_SIZE, INPUTS);
	public Pattern<String, String> p1 = newPattern("word", "words");
	public Pattern<String, String> p2 = newPattern("ward", "wards");
	public Pattern<String, String> p3 = newPattern("wyrd", "wyrds");
	
	@Test
	public void test() {
		try {
			NNetwork network = new NNetwork(new PositiveHyperbolicTangent(), INPUTS, 3*INPUTS, INPUTS);
			Teacher teacher = new Teacher(network);
			teacher.gradientStep = 0.01;
			teacher.minObjectiveDelta = 0.01;
			teacher.maxDiscrepancies = 100;
			/*for (int i = 0; i < 10; i ++) {
				Pattern<String, String> pattern = newRandomUnchangedPattern();
				teacher.addPattern(pattern);
				System.out.println(pattern.input);
			}*/
			/*teacher.addPattern(p1);
			teacher.addPattern(p2);
			teacher.addPattern(p3);*/
			teacher.addPattern(newPattern("001", "100"));
			teacher.addPattern(newPattern("0001", "10"));
			teacher.addPattern(newPattern("1", "0"));
			teacher.teach();
			System.out.println(adapter.dataFromSignal(network.process(adapter.dataToSignal("001"))));
			System.out.println(adapter.dataFromSignal(network.process(adapter.dataToSignal("0001"))));
			System.out.println(adapter.dataFromSignal(network.process(adapter.dataToSignal("1"))));
			//IOUtil.serializeWeights(network, new File("3words.json"));
		} catch (NNException e) {
			fail(e.toString());
		}
	}
	
	public Pattern<String, String> newPattern(String input, String output) {
		return new Pattern<String, String>(adapter, input, adapter, output);
	}
	
	public Pattern<String, String> newRandomUnchangedPattern() {
		String input = getRandomWord(MIN_WORD_LENGTH, MAX_WORD_LENGTH);
		return new Pattern<String, String>(adapter, input, adapter, input);
	}
	
	public static String getRandomWord(int minLength, int maxLength) {
		Random rand = new Random();
		int wordLength = rand.nextInt(maxLength - minLength) + minLength;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < wordLength; i++) {
			sb.append((char)('a' + rand.nextInt(26)));
		}
		return sb.toString();
	}
}
