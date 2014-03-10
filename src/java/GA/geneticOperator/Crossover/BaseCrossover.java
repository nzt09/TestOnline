package GA.geneticOperator.Crossover;

import GA.Population.Population;

public class BaseCrossover {
	 protected Population populationBeforeCross;
	    protected Population populationAfterCross;

	    public BaseCrossover(Population pop) {
	        this.populationBeforeCross=pop;
	        populationAfterCross = new Population(pop.getLength());
	    }

	    public Population getCrossoverResult() {
	        return this.populationAfterCross;
	    }
}
