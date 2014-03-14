package GA.Population.Individuals;

import GA.ControlParameters;
import GA.Population.Individuals.Function.F2;
import GA.Problems.EnDecoder.BinaryEnDeCoder;

public class Individual {
	protected float fitness;
    //����������ַ�
    protected String geneCode = "";
    //��������Ǹ������͵�����
    protected float[] geneCodes;
    protected float[][] mutationScope;//�Ƕ�geneCodes�е�ÿ��code��
    protected XValue xvalue = new XValue();
    protected BasePhenoType phenotype;

    public Individual() {
        this.fitness = 0;
    }

    public Individual(String genecodePass) {
        this.geneCode = genecodePass;
    }

    public Individual(float genecodes[]) {
        this.geneCodes = new float[genecodes.length];
        this.mutationScope = new float[genecodes.length][2];
        for (int i = 0; i < genecodes.length; i++) {
            this.geneCodes[i] = genecodes[i];
            this.mutationScope[i] = new float[2];
        }
    }
    //���ڸ������͵Ļ��������ܻ��漰����������ֵ�����⣬�������Ż��У����ڴ��ڴ����������ȵĵ㣬���������˱��룬�ɴ˴�������Ҫ��������ֵ������

    public int compareTo(Individual ind) {//������Ȼ,ֻ�Ƚ���Ӧֵ��С,�����equal����
        if (this.fitness < ind.fitness) {
            return -1;
        } else {
            if (this.fitness == ind.fitness) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public void setGeneCode(String genecode) {
        this.geneCode = genecode;
    }

    public String getGeneCode() {
        return geneCode;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fit) {
        this.fitness = fit;
    }

    public XValue getPhenoValue() {
        return xvalue;
    }

    public void CalculateX() {
        switch (ControlParameters.genecodeType) {
            case 0: {
                switch (ControlParameters.problemNum) {
                    case ControlParameters.F0:
                    case ControlParameters.F1: {
                        BinaryEnDeCoder bEDCode = new BinaryEnDeCoder();
                        bEDCode.setVirtualCodeStr(new String[]{this.geneCode});
                        bEDCode.decode();
                        this.xvalue.setxFloatArray(bEDCode.getRealCodes());
                    }
                    break;
                    case ControlParameters.F2: {
                        this.xvalue.setxStr(this.geneCode);
                    }
                    break;
                }
            }
            break;
        }
    }

    public void CalculateFitness() {
        switch (ControlParameters.problemNum) {
            case ControlParameters.F0:
            case ControlParameters.F1: {
                FactoryPhenotype fx = new FactoryPhenotype(this.xvalue);
                this.fitness = fx.getFit();
            }
            break;
            case ControlParameters.F2: {
                this.xvalue.setxStr(geneCode);
                FactoryPhenotype  fx = new FactoryPhenotype(this.xvalue);
                this.fitness = fx.getFit();
            }
            default: {
            }
        }
    }
        public void CalculateFitness(F2 f2) {
                this.xvalue.setxStr(geneCode);
                FactoryPhenotype  fx = new FactoryPhenotype(this.xvalue,f2);
                this.fitness = fx.getFit();
    }

    public float[] getGeneCodes() {
        return geneCodes;
    }

    public void setGeneCodes(float[] genecodes) {
        this.geneCodes = new float[genecodes.length];
        System.arraycopy(genecodes, 0, this.geneCodes, 0, genecodes.length);
    }

    public float[][] getMutationScope() {
        return mutationScope;
    }

    public void setMutationScope(float[][] mutationScope) {
        this.mutationScope = mutationScope;
    }

    public boolean equal(Individual ind) {
        boolean equal = false;
        switch (ControlParameters.genecodeType) {
            case 0:
                equal = this.geneCode.contentEquals(ind.getGeneCode());
                break;
            case 1:
            case 10:
            case 11:
                int j = 0;
                for (int i = 0; i < geneCodes.length; i++) {
                    if (this.geneCodes[i] - ind.geneCodes[i] < Math.pow(2, -15)) {
                        j++;
                    }
                }
                equal = (j == geneCodes.length);
                break;
        }
        return equal;
    }

    public Individual getClone() throws CloneNotSupportedException {
        Individual ind = new Individual();
        ind.setFitness(fitness);
        ind.setGeneCode(geneCode);
        if (geneCodes != null) {
            ind.geneCodes = new float[geneCodes.length];
            System.arraycopy(geneCodes, 0, ind.geneCodes, 0, geneCodes.length);
            /*for (int i = 0; i < geneCodes.length; i++) {
            ind.geneCodes[i] = geneCodes[i];
            }*/
        } else {
            ind.geneCodes = null;
        }
        if (xvalue != null) {
            ind.xvalue = new XValue();
            ind.xvalue.setxFloatArray(xvalue.getxFloatArray());
            ind.xvalue.setxStr(xvalue.getxStr());

        } else {
            ind.xvalue = null;
        }
        if (mutationScope != null) {
            ind.mutationScope = new float[mutationScope.length][mutationScope[0].length];
            for (int i = 0; i < mutationScope.length; i++) {
                if (mutationScope[i] != null) {
                    System.arraycopy(mutationScope[i], 0, ind.mutationScope[i], 0, mutationScope[i].length);
                } else {
                    ind.mutationScope[i] = null;
                }
            }
        } else {
            ind.mutationScope = null;
        }
        return ind;
    }
}
