package GA.Population.Individuals;

import GA.ControlParameters;
import GA.Population.Individuals.Function.F0;
import GA.Population.Individuals.Function.F1;
import GA.Population.Individuals.Function.F2;

public class FactoryPhenotype {
	 BasePhenoType phenotype;
	    private float fit = 0;

	    public FactoryPhenotype(XValue x) {
	        switch (ControlParameters.problemNum) {
	            case ControlParameters.F0: {
	                F0 s1 = new F0();
	                fit = s1.getFitness(x.getxFloatArray());
	            }
	            case ControlParameters.F1: {
	                F1 s1 = new F1();
	                fit = s1.getFitness(x.getxFloatArray());
	            }
	            break;
	            case ControlParameters.F2: {
	                F2 s1 = new F2();
	                fit = s1.getFitness(x.getxStr());//�ַ�Ļ������
	            }
	            break;
	        }
	    }

	    public float getFit() {
	        return fit;
	    }

	    public void setFit(float fit) {
	        this.fit = fit;
	    }

	    public FactoryPhenotype(XValue x, F2 f2) {
	        fit = f2.getFitness(x.getxStr());//�ַ�Ļ������

	    }
}
