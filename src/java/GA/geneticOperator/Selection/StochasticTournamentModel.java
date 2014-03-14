package GA.geneticOperator.Selection;

import java.util.Random;

import GA.ControlParameters;
import GA.Population.Population;
import GA.Population.Individuals.Individual;

public class StochasticTournamentModel extends BaseSelection {
	 public StochasticTournamentModel(Population pop) {
	        super(pop);
	        Random r = new Random();
	        int k, j;
	        for (int i = 0; i < ControlParameters.populationSize; i++) {
	            k = r.nextInt(ControlParameters.populationSize);
	            j = r.nextInt(ControlParameters.populationSize);
	            //System.out.println("k is:"+k+" and J is:"+j);
	            Individual temInd = new Individual();
	            switch (ControlParameters.genecodeType) {
	                case 0:
	                    temInd.setGeneCode(pop.getIndividualAt(k).getFitness() > pop.getIndividualAt(k).getFitness() ? pop.getIndividualAt(k).getGeneCode() : pop.getIndividualAt(j).getGeneCode());
	                    break;
	                case 1:
	                case 10:
	                case 11:
	                    temInd.setGeneCodes(pop.getIndividualAt(k).getFitness() > pop.getIndividualAt(k).getFitness() ? pop.getIndividualAt(k).getGeneCodes() : pop.getIndividualAt(j).getGeneCodes());
	                    break;
	            }
	            this.populationAfterSelection.appendIndividual(temInd);
	        }
	        this.getSelectionResult();
	    }
}
