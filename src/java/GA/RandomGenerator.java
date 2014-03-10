package GA;

import java.util.Random;

public class RandomGenerator {

    public static float getRandom(float lowValue, float upValue) {
        if (lowValue > upValue) {
            float tem = lowValue;
            lowValue = upValue;
            upValue = tem;
        }
        return lowValue + new Random().nextFloat() * (upValue - lowValue);
        //return 0d;
    }

    public static int getRandom(int lowValue, int upValue) {
        if (lowValue >= upValue) {
            if (lowValue > upValue) {
                int tem = lowValue;
                lowValue = upValue;
                upValue = tem;
            } else {//���ڵ����
                lowValue = 0;
                upValue = 2;//��������,ʹ�ò�����������0��1
            }
        }
        return lowValue + new Random().nextInt(upValue - lowValue);
    }
}
