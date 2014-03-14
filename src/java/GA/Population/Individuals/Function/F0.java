package GA.Population.Individuals.Function;

public class F0 extends BaseFitnessFXScalar {
	public F0() {
        super();
    }
        @Override
    public float getFitness(float[] xValue) {
        return xValue[0] * (float) Math.sin(10 * Math.PI * xValue[0]) + 2.0f;
    }
}
