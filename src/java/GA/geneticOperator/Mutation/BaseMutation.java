package GA.geneticOperator.Mutation;

import GA.Population.Population;

public class BaseMutation {
	protected Population populationBeforeMutation;
    protected Population populationAfterMutation;

    public BaseMutation(Population pop) {
        this.populationBeforeMutation = pop;
        populationAfterMutation = new Population(pop.getLength());
    }

    public Population getMutationResult() {
        return this.populationAfterMutation;
    }
}
