package GA.geneticOperator.Selection;

import java.util.Random;

import GA.ControlParameters;
import GA.Population.Population;
import GA.Population.Individuals.Individual;

public class SelectionCommonFunction extends BaseSelection {
	 private boolean maxEqualMin = false;//������е���Ӧֵ����ȣ���ֱ�Ӱ����и��彻�ɽ��洦��
	    private float maxfitness;
	    private float minfitness;

	    public SelectionCommonFunction(Population pop) {
	        super(pop);
	        calMaxMin();
	    }

	    private void calMaxMin() {
	        this.maxfitness = this.minfitness = this.populationBeforeSelection.getIndividualAt(0).getFitness();
	        for (int i = 1; i < ControlParameters.populationSize; i++) {
	            if (this.populationBeforeSelection.getIndividualAt(i).getFitness() > this.getMaxfitness()) {
	                this.maxfitness = this.populationBeforeSelection.getIndividualAt(i).getFitness();
	            }
	            if (this.populationBeforeSelection.getIndividualAt(i).getFitness() < this.getMinfitness()) {
	                this.minfitness = this.populationBeforeSelection.getIndividualAt(i).getFitness();
	            }
	        }
	        if (this.getMaxfitness() == this.getMinfitness()) {
	            this.maxEqualMin = true;
	        } else {
	            this.maxEqualMin = false;
	        }
	    }

	    public void selectAppend() {
	        if (this.isMaxEqualMin()) {
	            populationAfterSelection = this.populationBeforeSelection;
	        } else {
	            float sumValueTem = this.populationBeforeSelection.sumValue;
	            float[][] proUpLowNo = new float[ControlParameters.populationSize][2];
	            float temValue = 0;
	            for (int i = 0; i < ControlParameters.populationSize; i++) {
	                proUpLowNo[i][0] = temValue;
	                proUpLowNo[i][1] = temValue +
	                        (this.populationBeforeSelection.getIndividualAt(i).getFitness() / sumValueTem);
	                temValue = proUpLowNo[i][1];
	            }
	            proUpLowNo[ControlParameters.populationSize - 1][1] = 1.0f;
	            float temf;
	            Random r = new Random();
	            for (int i = 0; i < ControlParameters.populationSize; i++) {
	                temf = r.nextFloat();
	                for (int j = 0; j < ControlParameters.populationSize; j++) {
	                    if (temf <= proUpLowNo[j][1] && temf >= proUpLowNo[j][0]) {
	                        switch (ControlParameters.genecodeType) {
	                            case 0:
	                                populationAfterSelection.appendIndividual(new Individual(this.populationBeforeSelection.getIndividualAt(j).getGeneCode()));
	                                break;
	                            case 1:
	                            case 10:
	                            case 11:
	                                populationAfterSelection.appendIndividual(new Individual(this.populationBeforeSelection.getIndividualAt(j).getGeneCodes()));
	                                break;
	                        }
	                        break;
	                    }
	                }
	            }
	        }
	    }
	        /**
	         * @return the maxEqualMin
	         */
	        public boolean isMaxEqualMin() {
	            return maxEqualMin;
	        }

	        /**
	         * @return the maxfitness
	         */
	        public float getMaxfitness() {
	            return maxfitness;
	        }

	        /**
	         * @return the minfitness
	         */
	        public float getMinfitness() {
	            return minfitness;
	        }
}
