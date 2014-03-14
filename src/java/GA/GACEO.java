package GA;

import GA.Population.Individuals.Function.F2;
import GA.Population.FactoryPopulationInitiation;
import GA.Population.Population;
import GA.Population.Individuals.Individual;

import GA.geneticOperator.SelectionCrossover;
import GA.geneticOperator.Crossover.FactoryCrossover;
import GA.geneticOperator.Mutation.FactoryMutation;
import GA.geneticOperator.Selection.FactorySelection;
import com.gkm.GA.tools.MyArray;

public class GACEO {
	 public Population pop;
	    private FactorySelection fs;
	    private FactoryCrossover fc;
	    private FactoryMutation fm;
	    private Individual bestInd;//��Ϊ���Ŵ���������У�populationҪ������Σ��������ﱣ��һ�¡�
	    private F2 f2 = null;
	    public GACEO() {
	        pop = new Population(ControlParameters.populationSize);
	        bestInd = new Individual();
	    }

	    public void setF2(F2 f2) {
	        this.f2 = f2;
	    }

	    public void GAOperators() throws CloneNotSupportedException {
	        bestInd = null;
	        bestInd = this.pop.bestIndividual.getClone();
	        //System.out.println("�Ŵ��㷨����֮ǰ,���Ÿ������ӦֵΪ:"+bestInd.getFitness());
	        this.pop.reserveBestInd(bestInd);//���Ÿ���
	        simpleOperat();
	        if (!ControlParameters.reappearing) {//����������ظ�����
	            int temInt = 0;
	            int[] reAppear = new int[ControlParameters.populationSize];
	            for (int i = 0; i < ControlParameters.populationSize - 1; i++) {
	                for (int j = i + 1; j < ControlParameters.populationSize; j++) {
	                    if (this.pop.getIndividualAt(i).equal(this.pop.getIndividualAt(j))) {
	                        if (!MyArray.isIn(j, reAppear)) {
	                            reAppear[temInt] = j;
	                            temInt++;
	                        }
	                    }
	                }
	            }
	            Population temPop = new FactoryPopulationInitiation(temInt).getGeneratedPopulation();
	            for (int i = 0; i < temInt; i++) {
	                this.pop.setIndividualAt(temPop.getIndividualAt(i), reAppear[i]);
	            }
	        }
	    }
	    public void GAOperators(F2 f2) throws CloneNotSupportedException {
	        this.f2=f2;
	        bestInd = null;
	        bestInd = this.pop.bestIndividual.getClone();
	        //System.out.println("�Ŵ��㷨����֮ǰ,���Ÿ������ӦֵΪ:"+bestInd.getFitness());
	        this.pop.reserveBestInd(bestInd);//���Ÿ���
	        simpleOperat(f2);
	        if (!ControlParameters.reappearing) {//����������ظ�����
	            int temInt = 0;
	            int[] reAppear = new int[ControlParameters.populationSize];
	            for (int i = 0; i < ControlParameters.populationSize - 1; i++) {
	                for (int j = i + 1; j < ControlParameters.populationSize; j++) {
	                    if (this.pop.getIndividualAt(i).equal(this.pop.getIndividualAt(j))) {
	                        if (!MyArray.isIn(j, reAppear)) {
	                            reAppear[temInt] = j;
	                            temInt++;
	                        }
	                    }
	                }
	            }
	            Population temPop = new FactoryPopulationInitiation(temInt).getGeneratedPopulation();
	            for (int i = 0; i < temInt; i++) {
	                this.pop.setIndividualAt(temPop.getIndividualAt(i), reAppear[i]);
	            }
	        }
	    }
	    private void simpleOperat() {
	        if (ControlParameters.competeProbability == 0) {
	            fs = new FactorySelection(this.pop);
	            fc = new FactoryCrossover(fs.getPopulationAfterSelection());
	            fm = new FactoryMutation(fc.getPopulationAfterCrossover());
	            this.pop = fm.getPopulationAfterMutation();//outPop
	        } else {
	            this.pop = new SelectionCrossover(this.pop).getSelectionResult();
	        }
	    }
	        private void simpleOperat(F2 f2) {
	            fs = new FactorySelection(this.pop);
	            fc = new FactoryCrossover(fs.getPopulationAfterSelection());
	            fm = new FactoryMutation(fc.getPopulationAfterCrossover(f2));
	            this.pop = fm.getPopulationAfterMutation(f2);//outPop
	    }
}
