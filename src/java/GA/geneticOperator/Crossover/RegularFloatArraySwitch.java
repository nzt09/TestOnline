package GA.geneticOperator.Crossover;

import java.util.Random;

import GA.ControlParameters;

public class RegularFloatArraySwitch {
	private float ResultFloatArray[][] = new float[][]{{}, {}};
    private int floatLength;

    public void swapFloatArray() {
        Random r = new Random();
        for (int j = 0; j < ControlParameters.crossoverPointNum; j++) {//
            int temInt = r.nextInt(floatLength), begin = 0, end = 0;
            if (temInt == 0) {
                temInt++;
            }
            if (temInt >= floatLength / 2) {//�������е�֮ǰ���򽻻��е�֮ǰ�����鲿�֣����򽻻��е�֮������鲿��
                begin = temInt;
                end = floatLength;
            } else {
                begin = 0;
                end = temInt;
            }
            for (int i = begin; i < end; i++) {
                float temfloat = ResultFloatArray[0][i];
                ResultFloatArray[0][i] = ResultFloatArray[1][i];
                ResultFloatArray[1][i] = temfloat;

            }
        }
    }

    public void setFloatArray(float a[], float b[]) {
        this.ResultFloatArray[0] = a;
        this.ResultFloatArray[1] = b;
        this.floatLength = a.length;
    }
}
