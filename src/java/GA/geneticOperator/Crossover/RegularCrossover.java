package GA.geneticOperator.Crossover;

import java.util.Random;

import GA.ControlParameters;
import GA.Population.Population;
import GA.Population.Individuals.Individual;
import GA.Population.Individuals.Function.F2;

public class RegularCrossover extends BaseCrossover {
	 private int CrossNumber;
	    private boolean isOdd;
	    private Random r = new Random();

	    public RegularCrossover(Population pop) {
	        super(pop);
	        RegularPointStringSwitch rpss = new RegularPointStringSwitch();
	        this.CrossNumber = ControlParameters.populationSize / 2;
	        if (ControlParameters.populationSize % 2 == 0) {
	            this.isOdd = false;
	        } else {
	            this.isOdd = true;
	        }
	        for (int i = 0; i < this.CrossNumber; i++) {//
	            int tem1 = r.nextInt(ControlParameters.populationSize);
	            int tem2 = r.nextInt(ControlParameters.populationSize);
	            //һ�㽻���ǲ���һ����������һ�ν��棬��㽻����������������ж��һ�㽻�棻
	            if (r.nextFloat() <= ControlParameters.crossProbability) {
	                switch (ControlParameters.genecodeType) {
	                    case 0: {
	                        rpss.setString(this.populationBeforeCross.getIndividualAt(tem1).getGeneCode(),
	                                this.populationBeforeCross.getIndividualAt(tem2).getGeneCode());
	                        rpss.swapString();
	                        String temStr[] = rpss.getResultString();
	                        if (ControlParameters.problemNum == ControlParameters.F2) {
	                            synchronized (this) {
	                                temStr[0] = f2.getValideString(temStr[0]);
	                                temStr[1] = f2.getValideString(temStr[1]);
	                            }
	                        }
	                        this.populationAfterCrossAppend(temStr[0]);
	                        this.populationAfterCrossAppend(temStr[1]);
	                    }
	                    break;
	                }
	            } else {
	                switch (ControlParameters.genecodeType) {
	                    case 0: {
	                        this.populationAfterCrossAppend(this.populationBeforeCross.getIndividualAt(tem1).getGeneCode());
	                        this.populationAfterCrossAppend(this.populationBeforeCross.getIndividualAt(tem2).getGeneCode());
	                    }
	                    break;
	                }
	            }
	        }
	        switch (ControlParameters.problemNum) {
	            case ControlParameters.F0:
	            case ControlParameters.F1: {
	                if (this.isOdd) {
	                    switch (ControlParameters.genecodeType) {
	                        case 0: {
	                            int tem1 = r.nextInt(ControlParameters.populationSize);
	                            int tem2 = r.nextInt(ControlParameters.populationSize);
	                            rpss.setString(this.populationBeforeCross.getIndividualAt(tem1).getGeneCode(),
	                                    this.populationBeforeCross.getIndividualAt(tem2).getGeneCode());
	                            rpss.swapString();
	                            String temStr[] = rpss.getResultString();
	                            this.populationAfterCrossAppend(temStr[0]);
	                        }
	                        break;
	                    }

	                }
	            }
	            break;
	        }
	    }

	    public RegularCrossover(Population pop, F2 f2) {
	        super(pop);
	        RegularPointStringSwitch rpss = new RegularPointStringSwitch();
	        this.CrossNumber = ControlParameters.populationSize / 2;
	        if (ControlParameters.populationSize % 2 == 0) {
	            this.isOdd = false;
	        } else {
	            this.isOdd = true;
	        }
	        for (int i = 0; i < this.CrossNumber; i++) {//
	            int tem1 = r.nextInt(ControlParameters.populationSize);
	            int tem2 = r.nextInt(ControlParameters.populationSize);
	            //һ�㽻���ǲ���һ����������һ�ν��棬��㽻����������������ж��һ�㽻�棻
	            if (r.nextFloat() <= ControlParameters.crossProbability) {
	                switch (ControlParameters.genecodeType) {
	                    case 0: {
	                        rpss.setString(this.populationBeforeCross.getIndividualAt(tem1).getGeneCode(),
	                                this.populationBeforeCross.getIndividualAt(tem2).getGeneCode());
	                        rpss.swapString();
	                        String temStr[] = rpss.getResultString();
	                        synchronized (this) {
	                            temStr[0] = f2.getValideString(temStr[0]);
	                            temStr[1] = f2.getValideString(temStr[1]);
	                        }
	                        this.populationAfterCrossAppend(temStr[0]);
	                        this.populationAfterCrossAppend(temStr[1]);
	                    }
	                    break;
	                }
	            } else {
	                switch (ControlParameters.genecodeType) {
	                    case 0: {
	                        this.populationAfterCrossAppend(this.populationBeforeCross.getIndividualAt(tem1).getGeneCode());
	                        this.populationAfterCrossAppend(this.populationBeforeCross.getIndividualAt(tem2).getGeneCode());
	                    }
	                    break;
	                }
	            }
	        }
	    }

	    private void populationAfterCrossAppend(String str1) {
	        this.populationAfterCross.appendIndividual(new Individual(str1));
	    }
	    private F2 f2 = null;

	    public void setF2(F2 f2) {
	        this.f2 = f2;
	    }
}
