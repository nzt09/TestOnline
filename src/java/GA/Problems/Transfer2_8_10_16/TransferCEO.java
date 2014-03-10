package GA.Problems.Transfer2_8_10_16;

public class TransferCEO extends BaseTransfer {
	 public TransferCEO(String genecode,float xmax,float xmin,float maxCodeValue){      
	        super();
	        //System.out.println("genecode is:"+genecode+"  , xmax is:"+xmax+"  , xmin is:"+xmin+",   maxCodeValue is:"+maxCodeValue);
	        FactoryTransfer ft=new FactoryTransfer();
	        this.setX(ft.geneToPheno(genecode, xmax, xmin,maxCodeValue));
	    }
}
