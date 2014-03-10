package GA.Problems.Transfer2_8_10_16;

import GA.ControlParameters;

public class FactoryTransfer {
	 public FactoryTransfer() {
	    }

	    public float geneToPheno(String geneCode, float xmax, float xmin, float maxCodeValue) {
	        BaseTransfer bgp = null;
	        switch (ControlParameters.codeScope.length()) {
	            case 2:
	                bgp = new BinaryToDecimalX();
	                break;
	            //����������д8��16���Ƶ�ת������
	            default:
	                bgp = new BinaryToDecimalX();
	        }
	        bgp.calculateX(geneCode, xmax, xmin, maxCodeValue);
	        return bgp.getX();
	    }
}
