package GA.Population;

import GA.ControlParameters;
import GA.Population.Individuals.Function.F2;

public class FactoryPopulationInitiation {
	private Population pop;
    private int individualNum = 0;

    public FactoryPopulationInitiation(int indNum) {
        this.individualNum = indNum;
        this.pop = new Population(indNum);
    }

    @SuppressWarnings("static-access")
    public Population getGeneratedPopulation() {
        switch (ControlParameters.genecodeType) {
            case 0://�ַ����ͣ�ControlParameters.genecodeType==0
                this.pop = new StringPopulationInitiation(individualNum).getGeneratedPopulation();
                break;
        }
        return this.pop;
    }
    public Population getGeneratedPopulation(F2 f2) {
        switch (ControlParameters.genecodeType) {
            case 0://�ַ����ͣ�ControlParameters.genecodeType==0
                this.pop = new StringPopulationInitiation(individualNum,f2).getGeneratedPopulation();
                break;
        }
        return this.pop;
    }
}
