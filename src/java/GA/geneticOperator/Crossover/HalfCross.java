package GA.geneticOperator.Crossover;

import java.util.Random;

import GA.ControlParameters;
import GA.Population.Population;
import GA.Population.Individuals.Individual;

public class HalfCross extends BaseCrossover {
	private int CrossNumber;
    private boolean isOdd;
    private Random r = new Random();
    RegularFloatArraySwitch opfs = new RegularFloatArraySwitch();

    public HalfCross(Population pop) {
        super(pop);
        this.populationAfterCross = new Population(pop.getLength());
        this.CrossNumber = ControlParameters.populationSize / 2;
        if (ControlParameters.populationSize % 2 == 0) {
            this.isOdd = false;
        } else {
            this.isOdd = true;
        }
        for (int i = 0; i < this.CrossNumber; i++) {
            int tem1 = r.nextInt(ControlParameters.populationSize);
            int tem2 = r.nextInt(ControlParameters.populationSize);
            if (r.nextFloat() <= ControlParameters.crossProbability) {
                opfs.setFloatArray(this.populationBeforeCross.getIndividualAt(
                        tem1).getGeneCodes(), this.populationBeforeCross.getIndividualAt(
                        tem2).getGeneCodes());
                opfs.swapFloatArray();
                this.populationAfterCrossAppend(this.populationBeforeCross.getIndividualAt(
                        tem1).getGeneCodes());
                this.populationAfterCrossAppend(this.populationBeforeCross.getIndividualAt(
                        tem2).getGeneCodes());
            } else {
                this.populationAfterCrossAppend(this.populationBeforeCross.getIndividualAt(
                        tem1).getGeneCodes());
                this.populationAfterCrossAppend(this.populationBeforeCross.getIndividualAt(
                        tem2).getGeneCodes());
            }
        }
        if (this.isOdd) {
            int tem1 = r.nextInt(ControlParameters.populationSize);
            int tem2 =r.nextInt(ControlParameters.populationSize);
            opfs.setFloatArray(this.populationBeforeCross.getIndividualAt(
                    tem1).getGeneCodes(), this.populationBeforeCross.getIndividualAt(
                    tem2).getGeneCodes());
            opfs.swapFloatArray();
            this.populationAfterCrossAppend(this.populationBeforeCross.getIndividualAt(
                    tem1).getGeneCodes());
        }
    }

    private void populationAfterCrossAppend(float[] a) {
        Individual ind1 = new Individual(a);
        this.populationAfterCross.appendIndividual(ind1);
    }
}
