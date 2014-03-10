package GA.geneticOperator.Mutation;

import java.util.Random;

import GA.ControlParameters;
import GA.Population.Population;
import GA.Population.Individuals.Individual;
import GA.Population.Individuals.Function.F2;

public class RegularMutation extends BaseMutation {
	protected Random r = new Random();

    public RegularMutation(Population pop) {
        super(pop);
    }

    public void executeMuate() {
        for (int i = 0; i < ControlParameters.populationSize; i++) {
            if (r.nextFloat() < ControlParameters.mutationProbability) {
                for (int j = 0; j < ControlParameters.mutationPointNum; j++) {
                    this.mutate(this.populationBeforeMutation.getIndividualAt(i));
                }
            }
            this.populationAfterMutation.appendIndividual(this.populationBeforeMutation.getIndividualAt(i));
        }
    }

    protected void mutate(Individual passedind) {
        switch (ControlParameters.genecodeType) {
            case 0: {
                int mutedPoint = r.nextInt(passedind.getGeneCode().length());
                int temInt = ControlParameters.codeScope.length();
                int temInt1 = r.nextInt(temInt);
                String temchar = ControlParameters.codeScope.substring(temInt1, temInt1 + 1);
                String codeofInd = passedind.getGeneCode().substring(mutedPoint, mutedPoint + 1);
                if (temchar.equals(codeofInd)) {
                    temInt1 = (temInt + 1) % (ControlParameters.codeScope.length());
                    temchar = ControlParameters.codeScope.substring(temInt1, temInt1 + 1);
                }
                String temGenecode = passedind.getGeneCode().substring(0, mutedPoint).trim() + temchar
                        + passedind.getGeneCode().substring(mutedPoint + 1);
                if (ControlParameters.problemNum == ControlParameters.F2) {
                    synchronized (this) {
                        temGenecode = f2.getValideString(temGenecode);
                    }
                }
                passedind.setGeneCode(temGenecode);
            }
            break;
        }
    }

    public void executeMuate(F2 f2) {
        for (int i = 0; i < ControlParameters.populationSize; i++) {
            if (r.nextFloat() < ControlParameters.mutationProbability) {
                for (int j = 0; j < ControlParameters.mutationPointNum; j++) {
                    this.mutate(this.populationBeforeMutation.getIndividualAt(i), f2);
                }
            }
            this.populationAfterMutation.appendIndividual(this.populationBeforeMutation.getIndividualAt(i));
        }
    }

    protected void mutate(Individual passedind, F2 f2) {
        switch (ControlParameters.genecodeType) {
            case 0: {
                int mutedPoint = r.nextInt(passedind.getGeneCode().length());
                int temInt = ControlParameters.codeScope.length();
                int temInt1 = r.nextInt(temInt);
                String temchar = ControlParameters.codeScope.substring(temInt1, temInt1 + 1);
                String codeofInd = passedind.getGeneCode().substring(mutedPoint, mutedPoint + 1);
                if (temchar.equals(codeofInd)) {
                    temInt1 = (temInt + 1) % (ControlParameters.codeScope.length());
                    temchar = ControlParameters.codeScope.substring(temInt1, temInt1 + 1);
                }
                String temGenecode = passedind.getGeneCode().substring(0, mutedPoint).trim() + temchar
                        + passedind.getGeneCode().substring(mutedPoint + 1);
                temGenecode = f2.getValideString(temGenecode);
                passedind.setGeneCode(temGenecode);
            }
            break;
        }
    }
    private F2 f2 = null;
}
