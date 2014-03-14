package GA.Population.Individuals.Function;

import java.util.LinkedList;
import java.util.List;

public class F1 extends BaseFitnessFXScalar {
	public static List<Integer> scoreType = new LinkedList<Integer>();
	public static int sumScore;

	@Override
	public float getFitness(float[] xValue) {
		int tem = 0;
		for (int i = 0; i < xValue.length; i++) {
			tem += F1.scoreType.get(i) * (int) (xValue[i] + 0.5);
		}
		return 100.0f - Math.abs(F1.sumScore - tem);// �����ֵ����
	}
}
