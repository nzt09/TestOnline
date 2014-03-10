package GA.Population.Individuals.Function;

import GA.Population.Individuals.BasePhenoType;
/*
 * ������ֻΪ��������Ӧֵ����,��û�п���ʸ������Ӧֵ����
 */
public class BaseFitnessFXScalar implements BasePhenoType {

    protected float fitness=0;

    public float getFitness(float[] x) {
        return this.fitness;
    }
    public void setFitness(float fitness){
        this.fitness=fitness;
    }
    public float getFitness(String str){
        return this.fitness;
    }
}
