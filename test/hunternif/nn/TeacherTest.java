package hunternif.nn;

import static junit.framework.Assert.fail;
import hunternif.nn.activation.Linear;
import hunternif.nn.data.IntAdapter;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TeacherTest {
	private NNetwork network;
	private IntAdapter adapter = new IntAdapter(3);
	Teacher teacher;
	private TeachingPattern<Integer, Integer> pattern = new TeachingPattern<Integer, Integer>(adapter, 1, adapter, 1);
	private TeachingPattern<Integer, Integer> pattern0 = new TeachingPattern<Integer, Integer>(adapter, 0, adapter, 0);
	private TeachingPattern<Integer, Integer> pattern2 = new TeachingPattern<Integer, Integer>(adapter, 2, adapter, 2);
	private TeachingPattern<Integer, Integer> pattern3 = new TeachingPattern<Integer, Integer>(adapter, 3, adapter, 3);
	private TeachingPattern<Integer, Integer> pattern4 = new TeachingPattern<Integer, Integer>(adapter, 4, adapter, 4);
	
	@Before
	public void setup() {
		try {
			network = new NNetwork(new Linear(), 3, 3, 3);
			teacher = new Teacher(network);
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void testObjectiveFunction() {
		try {
			teacher.addTeachingPattern(pattern);
			Assert.assertEquals((double)(5*5 + 6*6 + 5*5), teacher.objectiveFunction());
		} catch (NNException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void teachOnePattern() {
		try {
			teacher.addTeachingPattern(pattern);
			teacher.teach();
			Integer processed = adapter.dataFromSignal(network.process(pattern.getInputSignal()));
			Assert.assertEquals(pattern.output, processed);
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void teachTwoPatterns() {
		try {
			teacher.addTeachingPattern(pattern);
			teacher.addTeachingPattern(pattern0);
			teacher.teach();
			Integer processed = adapter.dataFromSignal(network.process(pattern.getInputSignal()));
			Assert.assertEquals(pattern.output, processed);
			processed = adapter.dataFromSignal(network.process(pattern0.getInputSignal()));
			Assert.assertEquals(pattern0.output, processed);
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void teachExtrapolation() {
		try {
			teacher.addTeachingPattern(pattern);
			teacher.addTeachingPattern(pattern3);
			teacher.teach();
			Integer processed = adapter.dataFromSignal(network.process(pattern.getInputSignal()));
			Assert.assertEquals(pattern.output, processed);
			processed = adapter.dataFromSignal(network.process(pattern2.getInputSignal()));
			Assert.assertEquals(pattern2.output, processed);
			processed = adapter.dataFromSignal(network.process(pattern3.getInputSignal()));
			Assert.assertEquals(pattern3.output, processed);
			processed = adapter.dataFromSignal(network.process(pattern4.getInputSignal()));
			Assert.assertEquals(pattern4.output, processed);
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
}
