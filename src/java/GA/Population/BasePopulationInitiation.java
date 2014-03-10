package GA.Population;

public class BasePopulationInitiation {
	protected Population pop;

	public BasePopulationInitiation(int indNum) {// ֻҪ�ѱ���ķ�Χ��������Ȼ����ɱ��췶Χ�ڵĸ��弴�ɣ������Ǳ�Ĺ���
		this.pop = new Population(indNum);
	}

	public Population getGeneratedPopulation() {
		return pop;
	}
}
