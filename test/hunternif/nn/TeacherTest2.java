package hunternif.nn;

import static junit.framework.Assert.fail;
import hunternif.nn.activation.HyperbolicTangent;
import hunternif.nn.util.IntPattern;

import org.junit.Test;

public class TeacherTest2 {
	@Test
	public void test() {
		try {
			NNetwork network = new NNetwork(new HyperbolicTangent(), 10, 10, 10);
			Teacher teacher = new Teacher(network);
			teacher.gradientStep = 0.1;
			teacher.minObjectiveDelta = 0.00001;
			teacher.maxDiscrepancies = 100;
			teacher.addPattern(new IntPattern(10, 100, 50));
			teacher.addPattern(new IntPattern(10, 2468, 1234));
			teacher.addPattern(new IntPattern(10, 45698, 22849));
			teacher.teach();
		} catch (NNException e) {
			fail(e.toString());
		}
	}
}
