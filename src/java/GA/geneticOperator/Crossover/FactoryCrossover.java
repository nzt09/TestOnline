package GA.geneticOperator.Crossover;

import GA.ControlParameters;
import GA.Population.Population;
import GA.Population.Individuals.Function.F2;

public class FactoryCrossover {
	 Population inPop;
	    Population outPop;

	    public FactoryCrossover(Population pop) {
	        this.inPop = pop;
	    }

	    public Population getPopulationAfterCrossover() {
	        switch (ControlParameters.crossoverType) {
	            case 0:
	                this.outPop = new RegularCrossover(this.inPop).getCrossoverResult();
	                break;
	            case 1:
	                this.outPop = new HalfCross(this.inPop).getCrossoverResult();
	                break;
	        }
	        return this.outPop;
	    }
	        public Population getPopulationAfterCrossover(F2 f2) {
	        switch (ControlParameters.crossoverType) {
	            case 0:
	                this.outPop = new RegularCrossover(this.inPop,f2).getCrossoverResult();
	                break;
	        }
	        return this.outPop;
	    }
}
