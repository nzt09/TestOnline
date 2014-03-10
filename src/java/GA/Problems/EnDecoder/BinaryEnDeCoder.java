package GA.Problems.EnDecoder;

import GA.ControlParameters;
import GA.Problems.Transfer2_8_10_16.TransferCEO;

public class BinaryEnDeCoder extends BaseEnDecoder {
	public BinaryEnDeCoder() {
    }

    @Override
    //���ȶԻ��������зֶΣ�������ControlParameters.variableSplit[]���õ���������Ӧ�Ļ������gi(i=1,2,....,ControlParameters.gsuCount)
    //Ȼ���gi���н��룬�õ����������飬���浽Individual.phenoValue[]
    public void decode() {//virtualCodeStr��Ԫ�ظ�����1
        //1.�Ի��������зֶΣ�������ControlParameters.variableSplit[]���õ���������Ӧ�Ļ������gi(i=1,2,....,ControlParameters.gsuCount)
        for (int i = 0; i < ControlParameters.gsuCount; i++) {
            String tem;
            tem = this.getVirtualCodeStr()[0].substring(ControlParameters.variableSplit[i], ControlParameters.variableSplit[i + 1]);
            TransferCEO tsf = new TransferCEO(tem, ControlParameters.variableProperties[i][0], ControlParameters.variableProperties[i][1], ControlParameters.variableProperties[i][3]);
            this.getRealCodes()[i] = tsf.getX();
        }
    }
}
