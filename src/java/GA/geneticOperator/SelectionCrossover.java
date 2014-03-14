package GA.geneticOperator;

import java.util.Random;

import GA.ControlParameters;
import GA.Population.Population;
import GA.Population.Individuals.Individual;
import GA.geneticOperator.Crossover.RegularFloatArraySwitch;
import GA.geneticOperator.Crossover.RegularPointStringSwitch;
import GA.geneticOperator.Selection.BaseSelection;

public class SelectionCrossover extends BaseSelection {
	Random r = new Random();
    RegularPointStringSwitch opss = new RegularPointStringSwitch();
    RegularFloatArraySwitch rfas = new RegularFloatArraySwitch();

    public SelectionCrossover(Population pop) {
        super(pop);
        Individual individual = new Individual();
        populationAfterSelection = new Population(pop.getLength());
        float temd = 0, temValue = 0, sumValueTem = this.populationBeforeSelection.sumValue, temd1 = 0;
        float[][] proUpLowNo = new float[ControlParameters.populationSize][2];
        for (int i = 0; i < ControlParameters.populationSize; i++) { //calculate the probabiltiysum
            proUpLowNo[i][0] = temValue;
            proUpLowNo[i][1] = temValue +
                    (this.populationBeforeSelection.getIndividualAt(i).getFitness() / sumValueTem);
            temValue = proUpLowNo[i][1];
        }
        for (int i = 0; i < ControlParameters.populationSize; i++) {
            //compete and get one individual
            temd = r.nextFloat();
            if (temd <= ControlParameters.competeProbability) {
                //select wins
                temd1 = r.nextFloat();
                for (int k = 0; k < ControlParameters.populationSize; k++) {
                    if (temd1 <= proUpLowNo[k][1] && temd1 >= proUpLowNo[k][0]) {
                        switch (ControlParameters.genecodeType) {
                            case 0:
                                individual = new Individual(this.populationBeforeSelection.getIndividualAt(k).getGeneCode());
                                break;
                            case 1:
                                individual = new Individual(this.populationBeforeSelection.getIndividualAt(k).getGeneCodes());
                        }
                        individual = new Individual(this.populationBeforeSelection.getIndividualAt(k).getGeneCode());
                        break;
                    }
                }
            } else {//crossover wins
                int tem1 = r.nextInt(ControlParameters.populationSize);
                int tem2 = r.nextInt(ControlParameters.populationSize);
                switch (ControlParameters.genecodeType) {
                    case 0:
                         {
                            opss.setString(this.populationBeforeSelection.getIndividualAt(tem1).getGeneCode(),
                                    this.populationBeforeSelection.getIndividualAt(tem2).getGeneCode());
                            opss.swapString();
                            String temStr[] = opss.getResultString();
                            individual = new Individual(temStr[0]);
                        }
                        break;
                    case 1:
                         {
                            rfas.setFloatArray(this.populationBeforeSelection.getIndividualAt(tem1).getGeneCodes(), this.populationBeforeSelection.getIndividualAt(tem2).getGeneCodes());
                            rfas.swapFloatArray();
                            individual = new Individual(this.populationBeforeSelection.getIndividualAt(tem1).getGeneCodes());
                        }
                        break;
                }
            }

            //append the individual into the populationAfter
            this.populationAfterSelection.appendIndividual(individual);
        }
        this.getSelectionResult();
    }
}
