package GA.geneticOperator.Selection;

import GA.ControlParameters;
import GA.Population.Population;

public class RankSelection extends SelectionMediator {
	 protected float[] probability;
	    public RankSelection(){
	        
	    }

	    public RankSelection(Population pop) {
	        //���Ƚ��и��尴��Ӧֵ��С���������
	        java.util.Arrays.sort(pop.individuals);
	        this.setProbability();
	        //����������Ӧֵ,�Ա��ڵ���
	        scf=new SelectionCommonFunction(pop);
	        for(int i=0;i<ControlParameters.populationSize;i++){
	            pop.getIndividualAt(i).setFitness(probability[i]);
	        }
	        pop.sumValue=1f;
	    }

	    private void setProbability() {
	        if (ControlParameters.rankProba == null) {
	            this.calProbability();
	        } else {
	            this.probability = ControlParameters.rankProba;
	        }
	    }

	    private void calProbability() {
	        this.probability = new float[ControlParameters.populationSize];
	        for (int i = 0; i < probability.length; i++) {
	            probability[i]=(2f*i)/((float)(1+ControlParameters.populationSize)*(float)ControlParameters.populationSize);
	        }
	    }
}
