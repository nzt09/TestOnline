package GA.geneticOperator.Selection;

import GA.Population.Population;

public class BaseSelection {
	 protected Population populationAfterSelection;
	    protected Population populationBeforeSelection;

	    public BaseSelection() {
	    }

	    public BaseSelection(Population pop) {
	        this.populationAfterSelection = new Population(pop.getLength());
	        this.populationBeforeSelection = pop;
	    }

	    public Population getSelectionResult() {
	        return this.populationAfterSelection;
	    }
}
