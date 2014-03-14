package GA.geneticOperator.Selection;

import GA.ControlParameters;
import GA.Population.Population;
import GA.geneticOperator.Selection.triangle.CosSelection;
import GA.geneticOperator.Selection.triangle.CtanSelection;
import GA.geneticOperator.Selection.triangle.SinSelection;
import GA.geneticOperator.Selection.triangle.TanSelection;

public class FactorySelection {
	 Population inPop;
	    Population outPop;

	    public FactorySelection(Population pop) {
	        this.inPop = pop;
	    }

	    public Population getPopulationAfterSelection() {
	        switch (ControlParameters.selectionType) {
	            case 0://sin triangle function selection
	                this.outPop = new SinSelection(this.inPop).getResult();
	                break;
	            case 1://sin triangle function selection
	                this.outPop = new CosSelection(this.inPop).getResult();
	                break;
	            case 2://sin triangle function selection
	                this.outPop = new TanSelection(this.inPop).getResult();
	                break;
	            case 3://sin triangle function selection
	                this.outPop = new CtanSelection(this.inPop).getResult();
	                break;
	            case 4://Stochastic tournament model
	                this.outPop = new StochasticTournamentModel(this.inPop).getSelectionResult();
	                break;
	            case 5://Rank model
	                this.outPop = new RankSelection(this.inPop).getResult();
	                break;
	        }
	        return this.outPop;
	    }
}
