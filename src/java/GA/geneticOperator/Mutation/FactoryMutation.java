package GA.geneticOperator.Mutation;

import GA.ControlParameters;
import GA.Population.Population;
import GA.Population.Individuals.Function.F2;

public class FactoryMutation {
	Population inPop;
    Population outPop;

    public FactoryMutation(Population pop) {
        this.inPop = pop;
    }

    public Population getPopulationAfterMutation() {
        switch (ControlParameters.mutationType) {
            case 0: {
                RegularMutation rm = new RegularMutation(this.inPop);
                rm.executeMuate();
                this.outPop = rm.getMutationResult();
            }
            break;
            case 1:
                break;//Ӧ��������������ͣ��������ҳ����
        }
        return this.outPop;
    }
        public Population getPopulationAfterMutation(F2 f2) {
        switch (ControlParameters.mutationType) {
            case 0: {
                RegularMutation rm = new RegularMutation(this.inPop);
                rm.executeMuate(f2);
                this.outPop = rm.getMutationResult();
            }
            break;
        }
        return this.outPop;
    }
}
