package GA.geneticOperator.Selection;

import GA.Population.Population;

/**
*
* @author �¹���  HAO Guo-Sheng, HAO Guo Sheng, HAO GuoSheng
*/
//ֻҪ����������ܹ������Ⱥ��Ӧֵ�����,���ฺ�𰴱���ѡ��ִ��,��˷��ǰ���Ӧֵ���б���ѡ���,�����Լ̳������
//Ŀǰ��֪�ļ̳�����:���̶�,4�����ѡ������
public class SelectionMediator {
	 public SelectionCommonFunction scf;
     public Population getResult() {
        scf.selectAppend();
        return scf.getSelectionResult();
    }
}
