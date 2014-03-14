package GA.Population.Individuals.Function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import GA.ControlParameters;
import GA.RandomGenerator;
import entities.Questionsinfo;


/*
 * @���� �����Ŀ��������Ӧֵ
 */
public class F2 extends BaseFitnessFXScalar {

    //����û������������Ҫ�����֣��Լ�����������Ŀ���ٺ�����Ƕ��ٵ�����
    public static HashMap<Integer, List<Questionsinfo>> questionScoreMap;//���ַ�ֵ���͵���Ŀ����������
    public static double paperDiffulty = 0;
    public static int testInterval = 0;
    private Vector numScoreSelected;
    public static List<Integer> scoreType;
    private List<Questionsinfo> questions = new LinkedList<Questionsinfo>();

    public float getFitness(String str) {
        //str�������
//|numScoreSelected                 |questionScoreMap
//|�Ż�����Ծ��и÷�ֵ������        |���ֵ��Ӧ������е���Ŀ
//==================================================================================================================
//|20                              |questionScoreMap.get(5)
//|15                              |questionScoreMap.get(10)
//|6                               |questionScoreMap.get(15)
//|0                               |questionScoreMap.get(20)
//|2                               |questionScoreMap.get(25)
//----------------------------------------------------------------------------------
//        ����maxNumScore��     |47 |48 |80 |74 |83   ��һ�ж�Ӧ�ڵڶ����Ż�ʱ�Ļ�����볤��
//����Ļ�����룺                010101010101010101010101010                            |              101111111111111000000                  | ...............
//               ��ֵΪ5����Ŀ�ڱ��questionScoreMap.get(5)�еı��                       |     ��ֵΪ10����Ŀ�ڱ��questionScoreMap.get(10)�еı�� |................
// �����Ӧ�ı����͵����ֵΪquestionScoreMap.get(5).size()*numScoreSelected[selectedPlan][0]| �����Ӧ�ı����͵����ֵΪquestionScoreMap.get(10).size()*numScoreSelected[selectedPlan][1]
        Iterator it = getNumScoreSelected().iterator();
        int i = 0;
        while (it.hasNext()) {//�����ַ�ֵ����Ŀ���зָ�numScoreSelected
            int numScore = (Integer) it.next();
            if (numScore != 0) {//�������͵���Ŀ��Ϊ0�����Ϊ0����ֱ�����ȥ����
                String tem = str.substring(ControlParameters.variableSplit[i], ControlParameters.variableSplit[i + 1]);
                int problemStringLength = tem.length() / numScore;//ÿ����Ŀ��Ӧ�Ķ����ƴ�����
                for (int j = 0; j < numScore; j++) {//���ַ�ֵ��Ŀ������,
                    String binary = tem.substring(j * problemStringLength, (j + 1) * problemStringLength);
                    int temInt1 = Integer.parseInt(binary.substring(0, 1));
                    for (int k = 1; k < binary.length(); k++) {//kָ�����ֵ����λ����0λ�Ѿ�ȡ������temInt1���ˣ�����k��1��ʼ
                        temInt1 = temInt1 * 2 + Integer.parseInt(binary.substring(k, k + 1));
                    }
                    //temInt1����ʽ�ϵ����ֵ������Ҫ������ʵ�ʶ�Ӧ�����ֵ
                    temInt1 = (int) (temInt1 * (questionScoreMap.get(scoreType.get(i)).size() - 1) / (Math.pow(2, binary.length()) - 1));
                    questions.add(questionScoreMap.get(scoreType.get(i)).get(temInt1));//��Ŀ��������еı��
                }
            }
            i++;
        }
        float difficultySum = 0f, timeSum = 0;
        for (i = 0; i < questions.size(); i++) {
            difficultySum += paperDiffulty - questions.get(i).getDifficulty();
            timeSum += questions.get(i).getAveragetime();
        }
        difficultySum = difficultySum / questions.size();
        this.fitness = 100f - difficultySum - (testInterval - timeSum);//��߷���100���Ѿ���ȷ����
        return this.fitness;
    }

    public synchronized String getValideString(String str) {//�жϸ�Ļ�������Ƿ�Ϊ�Ϸ��Ļ�����룺��Ŀ���ظ�,������ظ�����ֱ�Ӹ�Ϊ���ظ�
        String resutString = "";
        Iterator it = getNumScoreSelected().iterator();
        int i = 0;
        while (it.hasNext()) {//�����ַ�ֵ����Ŀ���зָ�numScoreSelected
            HashSet<String> qu = new HashSet<String>();
            int numScore = (Integer) it.next();
            if (numScore != 0) {//�������͵���Ŀ��Ϊ0�����Ϊ0����ֱ�����ȥ����
                String tem = str.substring(ControlParameters.variableSplit[i], ControlParameters.variableSplit[i + 1]);
                int problemStringLength = tem.length() / numScore;//ÿ����Ŀ��Ӧ�Ķ����ƴ�����
                for (int j = 0; j < numScore; j++) {//���ַ�ֵ��Ŀ������,
                    String binary = tem.substring(j * problemStringLength, (j + 1) * problemStringLength);
                    if (qu.add(binary)) {//��ӳɹ�
                        resutString += binary;
                    } else {//�Ѿ����ˣ���������,�����
                        resutString += generateRandomString(binary, qu);
                    }
                }
            }
            i++;
        }
        return resutString;
    }

    private synchronized String generateRandomString(String binary, HashSet<String> temHash) {
        String tem = null, resultString = null;
        int j = RandomGenerator.getRandom(0, (int) (Math.pow(2, binary.length())));//������ĳ��λ�ÿ�ʼ,����ÿ�δ�0��ʼ
        for (int i = j; i < j + (int) (Math.pow(2, binary.length())); i++) {
            tem = Integer.toBinaryString(i % ((int) (Math.pow(2, binary.length()))));
            while (tem.length() < binary.length()) {//ǰ�油0��ʹ����ɵ��ַ���ԭ�ַ�ĳ������
                tem = "0" + tem;
            }
            if (temHash.add(tem)) {
                resultString = tem;
                break;
            }
        }
        return resultString;
    }

    public Vector getNumScoreSelected() {
        return numScoreSelected;
    }

    public void setNumScoreSelected(Vector numScoreSelected) {
        this.numScoreSelected = numScoreSelected;
    }
}
