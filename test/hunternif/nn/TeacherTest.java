package hunternif.nn;

import static junit.framework.Assert.fail;
import hunternif.nn.activation.Linear;
import hunternif.nn.data.IntAdapter;
import hunternif.nn.util.IntPattern;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TeacherTest {
	private NNetwork network;
	private IntAdapter adapter = new IntAdapter(3);
	Teacher teacher;
	private IntPattern pattern = new IntPattern(3, 1, 1);
	private IntPattern pattern0 = new IntPattern(3, 0, 0);
	private IntPattern pattern2 = new IntPattern(3, 2, 2);
	private IntPattern pattern3 = new IntPattern(3, 3, 3);
	private IntPattern pattern4 = new IntPattern(3, 4, 4);
	
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
			teacher.addPattern(pattern);
			Assert.assertEquals((double)(5*5 + 6*6 + 5*5), teacher.objectiveFunction());
		} catch (NNException e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void teachOnePattern() {
		try {
			teacher.addPattern(pattern);
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
			teacher.addPattern(pattern);
			teacher.addPattern(pattern0);
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
			teacher.addPattern(pattern);
			teacher.addPattern(pattern3);
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
