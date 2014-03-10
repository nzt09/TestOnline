package GA.Problems.EnDecoder;

import GA.ControlParameters;
import GA.Population.Individuals.Individual;

public class BaseEnDecoder {
	private String[] virtualCodeStr;//genecodes of String type
    private float[] virtualCodeFloat;//genecodes of float type
    private int[] virtualCodeLocation;
    private float[][] mutationScope;//mutation scope of each Gene-Sense-Unit(GSU)
    private float[] realCodes=new float[ControlParameters.gsuCount];//the real value of variable Xi
    private Individual ind;

    //��setVirtualCode����decode
    public void decode() {
    }

    /**
     * @return the virtualCodeStr
     */
    public String[] getVirtualCodeStr() {
        return virtualCodeStr;
    }

    /**
     * @param virtualCodeStr the virtualCodeStr to set
     */
    public void setVirtualCodeStr(String[] virtualCodeStr) {
        this.virtualCodeStr = new String[virtualCodeStr.length];
        System.arraycopy(virtualCodeStr, 0, this.virtualCodeStr, 0, virtualCodeStr.length);
    }

    /**
     * @return the virtualCodeFloat
     */
    public float[] getVirtualCodeFloat() {
        return virtualCodeFloat;
    }

    /**
     * @param virtualCodeFloat the virtualCodeFloat to set
     */
    public void setVirtualCodeFloat(float[] virtualCodeFloat) {
        this.virtualCodeFloat = new float[virtualCodeFloat.length];
        System.arraycopy(virtualCodeFloat, 0, this.virtualCodeFloat, 0, virtualCodeFloat.length);
    }

    /**
     * @return the virtualCodeLocation
     */
    public int[] getVirtualCodeLocation() {
        return virtualCodeLocation;
    }

    /**
     * @param virtualCodeLocation the virtualCodeLocation to set
     */
    public void setVirtualCodeLocation(int[] virtualCodeLocation) {
        this.virtualCodeLocation = new int[virtualCodeLocation.length];
        System.arraycopy(virtualCodeLocation, 0, this.virtualCodeLocation, 0, virtualCodeLocation.length);
    }

    /**
     * @return the mutationScope
     */
    public float[][] getMutationScope() {
        return mutationScope;
    }

    /**
     * @param mutationScope the mutationScope to set
     */
    public void setMutationScope(float[][] mutationScope) {
        this.mutationScope = new float[mutationScope.length][mutationScope[0].length];
        for (int i = 0; i < mutationScope.length; i++) {
            System.arraycopy(mutationScope[i], 0, this.mutationScope[i], 0, mutationScope[i].length);
        }
    }

    /**
     * @return the realCodes
     */
    public float[] getRealCodes() {
        return realCodes;
    }

    /**
     * @param realCodes the realCodes to set
     */
    public void setRealCodes(float[] realCodes) {
        this.realCodes = new float[realCodes.length];
        System.arraycopy(realCodes, 0, this.realCodes, 0, realCodes.length);
    }

    /**
     * @return the ind
     */
    public Individual getInd() {
        return ind;
    }

    /**
     * @param ind the ind to set
     */
    public void setInd(Individual ind) {
        this.ind = ind;
    }
}
