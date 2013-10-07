package hunternif.nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hunternif.nn.data.AlphabetAdapter;
import hunternif.nn.data.WordAdapter;
import junit.framework.Assert;

import org.junit.Test;

public class AlphabetTest {
	@Test
	public void testLetters() {
		AlphabetAdapter alphabet = new AlphabetAdapter('a', 'c');
		TestUtil.assertEquals(new double[]{1.0, 0.0, 0.0}, alphabet.dataToSignal('a'));
		TestUtil.assertEquals(new double[]{0.0, 1.0, 0.0}, alphabet.dataToSignal('b'));
		try {
			alphabet.dataToSignal('d');
			Assert.fail("Accepted a letter not in the alphabet");
		} catch (IndexOutOfBoundsException e) {}
	}
	
	@Test
	public void testSimpleWord() {
		AlphabetAdapter alphabet = new AlphabetAdapter('a', 'b');
		WordAdapter words = new WordAdapter(alphabet, 2);
		TestUtil.assertEquals(new double[]{1.0, 0.0, 0.0, 1.0}, words.dataToSignal("ab"));
	}
	
	@Test
	public void testComplexWord() {
		AlphabetAdapter alphabet = new AlphabetAdapter('a', 'z');
		WordAdapter words = new WordAdapter(alphabet, 16);
		Assert.assertEquals("qwerty", words.dataFromSignal(words.dataToSignal("qwerty")));
	}
	
	@Test
	public void testNoise() {
		AlphabetAdapter alphabet = new AlphabetAdapter('a', 'c');
		WordAdapter words = new WordAdapter(alphabet, 3);
		double[] signal = {0.8, 0.7, 0.7, -0.1, 0.0, -0.2, 0.5, 0.5, 0.5};
		Assert.assertEquals("ab", words.dataFromSignal(TestUtil.makeList(signal)));
	}
}
