package GA.geneticOperator.Selection.triangle;

import GA.ControlParameters;
import GA.Population.Population;
import GA.geneticOperator.Selection.SelectionCommonFunction;
import GA.geneticOperator.Selection.SelectionMediator;

public class CosSelection extends SelectionMediator {
	public CosSelection(Population pop) {

        scf = new SelectionCommonFunction(pop);
        //����Ⱥ�ڵ������Ӧֵ����С��Ӧֵ
        if (scf.isMaxEqualMin()) {
        } else {
            float fmax = scf.getMaxfitness(), fmin = scf.getMinfitness();
            //����������Ӧֵ����Ӧֵ�ĺ�
            float sumValueTem = 0;
            for (int i = 0; i < ControlParameters.populationSize; i++) {
                pop.getIndividualAt(i).setFitness((float) (Math.cos(
                        Math.PI / 2 * ((fmax - pop.getIndividualAt(i).getFitness()) /
                        (fmax - fmin)))));
                sumValueTem += pop.getIndividualAt(i).getFitness();
            }
            //�������ѡ���������
            pop.sumValue = sumValueTem;
        }
    }
}
