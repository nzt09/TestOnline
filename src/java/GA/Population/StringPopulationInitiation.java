package GA.Population;

import java.util.Random;

import GA.ControlParameters;
import GA.Population.Individuals.Individual;
import GA.Population.Individuals.Function.F2;

public class StringPopulationInitiation extends BasePopulationInitiation {
	private Random r = new Random();

    public StringPopulationInitiation(int indNum) {
        super(indNum);
        int temint;
        String temGenecode;
        for (int i = 0; i < indNum; i++) {
            Individual temIndi = new Individual();
            temGenecode = "";
            for (int j = 0; j < ControlParameters.individualLength; j++) {
                temint = r.nextInt(ControlParameters.codeScope.length());
                temGenecode += ControlParameters.codeScope.substring(temint, temint + 1);
            }
            if (ControlParameters.problemNum == ControlParameters.F2) {
                synchronized (this) {
                    temGenecode = f2.getValideString(temGenecode);
                }
            }
            temIndi.setGeneCode(temGenecode);
            this.pop.appendIndividual(temIndi);
        }
    }
        public StringPopulationInitiation(int indNum,F2 f2) {
        super(indNum);
        int temint;
        String temGenecode;
        for (int i = 0; i < indNum; i++) {
            Individual temIndi = new Individual();
            temGenecode = "";
            for (int j = 0; j < ControlParameters.individualLength; j++) {
                temint = r.nextInt(ControlParameters.codeScope.length());
                temGenecode += ControlParameters.codeScope.substring(temint, temint + 1);
            }
            if (ControlParameters.problemNum == ControlParameters.F2) {
                synchronized (this) {
                    temGenecode = f2.getValideString(temGenecode);
                }
            }
            temIndi.setGeneCode(temGenecode);
            this.pop.appendIndividual(temIndi);
        }
    }

    public Individual stringIndiInitiation() {
        int temint;
        String temGenecode = "";
        for (int j = 0; j < ControlParameters.individualLength; j++) {
            temint = r.nextInt(ControlParameters.codeScope.length());
            temGenecode += ControlParameters.codeScope.substring(temint, temint + 1);
        }
        return new Individual(temGenecode);
    }
    private F2 f2 = null;
}
