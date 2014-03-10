package GA.Problems.Transfer2_8_10_16;

public class BinaryToDecimalX extends BaseTransfer {
	public BinaryToDecimalX() {
    }
    @Override
    public void calculateX(String binary, float xMax, float xMin,float maxCodeValue) {
        // System.out.println("binary is:"+binary);
        int temInt1 = Integer.parseInt(binary.substring(0,1));
        for (int i = 1; i < binary.length(); i++) {
            temInt1 = temInt1 * 2 + Integer.parseInt(binary.substring(i, i + 1));
        }
        this.setX((float) (xMin + (xMax - xMin) * (temInt1 * (1 / maxCodeValue))));
    }
}
