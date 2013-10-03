package hunternif.nn;

import static junit.framework.Assert.fail;
import junit.framework.Assert;
import hunternif.nn.activation.Linear;
import hunternif.nn.data.IntAdapter;

import org.junit.Before;
import org.junit.Test;

public class TeacherTest {
	private NNetwork network;
	private Teacher teacher;
	private IntAdapter adapter = new IntAdapter(3);
	private TeachingPattern<Integer, Integer> pattern = new TeachingPattern<Integer, Integer>(adapter, 1, adapter, 1);
	
	@Before
	public void setup() {
		try {
			network = new NNetwork(new Linear(), 3, 3, 3);
			teacher = new Teacher(network);
			teacher.addTeachingPattern(pattern);
		} catch (NNException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void testObjectiveFunction() {
		try {
			Assert.assertEquals((double)(5*5 + 6*6 + 5*5), teacher.objectiveFunction());
		} catch (NNException e) {
			fail(e.toString());
		}
	}
}
