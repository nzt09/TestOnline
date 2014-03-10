package GA.Population;

import GA.ControlParameters;
import GA.Population.Individuals.Individual;
import GA.Population.Individuals.Function.F2;

public class Population {
	public Individual[] individuals;
    public int last;
    public float sumValue;
    public Individual bestIndividual, worstIndividual;

    public Population(int size) {
        individuals = new Individual[size];
        this.last = 0;
        sumValue = 0;
    }

    public Individual getIndividualAt(int index) {
        return individuals[index];
    }

    public void setIndividualAt(Individual ind, int i) {
        this.individuals[i] = ind;
    }

    public void appendIndividual(Individual individual) {
        individuals[last] = individual;
        last++;
    }

    public int getLength() {
        return last;
    }

    public void CalculateAll() throws CloneNotSupportedException {
        for (int i = 0; i < last; i++) {//����������Ӧֵ
            switch (ControlParameters.problemNum) {
                case ControlParameters.F0:
                case ControlParameters.F1: {
                    this.getIndividualAt(i).CalculateX();
                    this.getIndividualAt(i).CalculateFitness();
                }
                break;
                case ControlParameters.F2: {
                    this.getIndividualAt(i).CalculateFitness();
                }
                break;
            }
        }
        bestIndividual = this.getIndividualAt(0);
        worstIndividual = this.getIndividualAt(0);
        for (int i = 0; i < last; i++) {
            if (bestIndividual.getFitness() < this.getIndividualAt(i).getFitness()) {
                this.bestIndividual = this.getIndividualAt(i);
            }
            if (worstIndividual.getFitness() > this.getIndividualAt(i).getFitness()) {
                worstIndividual = this.getIndividualAt(i);
            }
        }
        ControlParameters.maxFitness = this.bestIndividual.getFitness();
    }

    public void CalculateAll(F2 f2) throws CloneNotSupportedException {
        for (int i = 0; i < last; i++) {//����������Ӧֵ
            this.getIndividualAt(i).CalculateFitness(f2);
        }
        bestIndividual = this.getIndividualAt(0);
        worstIndividual = this.getIndividualAt(0);
        for (int i = 0; i < last; i++) {
            if (bestIndividual.getFitness() < this.getIndividualAt(i).getFitness()) {
                this.bestIndividual = this.getIndividualAt(i);
            }
            if (worstIndividual.getFitness() > this.getIndividualAt(i).getFitness()) {
                worstIndividual = this.getIndividualAt(i);
            }
        }
        ControlParameters.maxFitness = this.bestIndividual.getFitness();
    }

    public void reserveBestInd(Individual bestIndi) throws CloneNotSupportedException {
        individuals[0] = bestIndi.getClone();
        this.bestIndividual = individuals[0];
    }
}
