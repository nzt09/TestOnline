package GA.geneticOperator.Crossover;

import java.util.Random;

import GA.ControlParameters;

public class RegularPointStringSwitch {
	private String ResultString[] = {"", ""};
    private int StrLength;

    public void swapString() {
        String temStr1;
        Random r = new Random();
        for (int j = 0; j < ControlParameters.crossoverPointNum; j++) {
            int temInt = r.nextInt(StrLength); //Here we assum that length of str1 and str2 is equal
            temStr1 =  ResultString[0].substring(temInt);
           // System.out.println("StrLength is:"+StrLength+"temInt is"+temInt);
            ResultString[0] = ResultString[0].substring(0, temInt) + ResultString[1].substring(temInt);
            ResultString[1] = ResultString[1].substring(0, temInt) + temStr1;
        }
    }

    public String[] getResultString() {
        return ResultString;
    }

    public void setString(String geneString1, String geneString2) {
        this.ResultString[0] = geneString1;
        this.ResultString[1] = geneString2;
        this.StrLength = geneString1.length();
    }
}
