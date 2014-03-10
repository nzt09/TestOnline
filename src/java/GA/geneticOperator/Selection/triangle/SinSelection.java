package GA.geneticOperator.Selection.triangle;

import GA.ControlParameters;
import GA.Population.Population;
import GA.geneticOperator.Selection.SelectionCommonFunction;
import GA.geneticOperator.Selection.SelectionMediator;

public class SinSelection extends SelectionMediator {
	public SinSelection(Population pop) {
        scf = new SelectionCommonFunction(pop);
        //����Ⱥ�ڵ������Ӧֵ����С��Ӧֵ
        if (scf.isMaxEqualMin()) {
            System.out.println("The max fitness value is equal to the min fitness value!");
        } else {
            float fmax = scf.getMaxfitness(), fmin = scf.getMinfitness();
            //����������Ӧֵ����Ӧֵ�ĺ�
            float sumValuetem = 0;
            for (int i = 0; i < ControlParameters.populationSize; i++) {
                float tem=(float) (Math.sin(Math.PI / 2 * ((float)(pop.getIndividualAt(i).getFitness() - fmin) /(float)(fmax - fmin))));
                pop.getIndividualAt(i).setFitness(tem);
                sumValuetem +=tem;
            }
            pop.sumValue = sumValuetem;
        }
    }
}
