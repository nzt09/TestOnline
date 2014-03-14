package GA.geneticOperator.Selection.triangle;

import GA.ControlParameters;
import GA.Population.Population;
import GA.geneticOperator.Selection.SelectionCommonFunction;
import GA.geneticOperator.Selection.SelectionMediator;

public class CtanSelection extends SelectionMediator {
	public CtanSelection(Population pop) {
        scf = new SelectionCommonFunction(pop);
        //����Ⱥ�ڵ������Ӧֵ����С��Ӧֵ
        if (scf.isMaxEqualMin()) {
        } else {
            float fmax = scf.getMaxfitness(), fmin = scf.getMinfitness();
            //����������Ӧֵ����Ӧֵ�ĺ�
            float sumValue = 0;
            for (int i = 0; i < ControlParameters.populationSize; i++) {
                pop.getIndividualAt(i).setFitness((float) (1 / (Math.tan(
                        Math.PI / 4 * ((fmax - pop.getIndividualAt(i).getFitness()) /
                        (fmax - fmin)) + Math.PI / 4))));
                sumValue += pop.getIndividualAt(i).getFitness();
            }
            //�������ѡ���������
            
            scf.selectAppend();
        }
    }
}
