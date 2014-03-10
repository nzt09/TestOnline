package GA;

public class ControlParameters {
	public static final int F0 = 0, F1 = 1, F2 = 2;
    public static String codeScope;//codeScope，编码所在域，如“01”是二进制编码；“0123456789”是十进制编码等； 
    public static int competeProbability = 0;//competePorbability，竞争的概率，如果选择与交叉算子属于竞争执行的情况，则采用竞争的情况；
    public static int crossoverType;//crossoverType，交叉类型，0表示多点交叉(包括一点交叉)，1表示数组中有一半的元素要被交叉，即halfCross； 
    public static int crossoverPointNum;//crossoverPointNum，交叉点数，指定是一点交叉，还是多点交叉 
    public static float crossProbability;//crossProbability,交叉概率； 
    public static int evolvingType;//evolvingType,进化的类型，0：需要计算个体长度；1：不需要计算个体长度，主要针对编码为字符型且不需要转换为实数的一类准非数值优化；
//variableSplit[],根据variableProperties[][]计算出.其格式是：[0，a1,a2,...,an]其中，a1表示第1个变量的串长，a2表示第2个变量的串开始位置是a1,终止位置是a2,依此类推，第n的变量的串开始位置是an-1,终止位置是an。
//xNum,float,自变量个数，如2；该参数与VariableProperties参数紧密相关；
    public static int genecodeType;//genecodeType，基因编码的类型，0表示一个字符串，1表示浮点类型
    public static int gsuCount;
    public static int individualLength;//individualLength,个体长度； 
    public static float maxFitness;//maxFitness,int,用来给种群一个初始的最大适应值，该值要记录当前种群的最大适应值，所以会不断地被替换
    public static float mutationProbability;//mutationProbability，变异概率； 
    public static int mutationPointNum;//mutationPointNum,变异点数，指一点变异，还是多点变异 
    public static int mutationType;//mutationType，变异算子类型，0表示常规的多点或一点变异，1表示**数组中有一半的元素要被变异； 
    public static int populationSize;//populationSize，种群规模，如100； 
    public static int problemNum;//problemNum,要解决问题的编号
    public static float[] rankProba;//rankProba,排序选择算子的概率分配表,如果要分配,则需要对其进行初始化； 
    public static boolean reappearing = false;//reappearing,boolean,是否允许出现重复个体，true，是,false，否 
    public static int selectionType;//selectionType，选择算子类型；0表示采用轮盘赌选择算子，1表示采用三角选择正弦算子,2表示采用三角选择余弦算子,3表示采用三角选择正切算子,4表示采用三角选择余切算子,5表示采用随机锦标赛算子,6表示采用排序选择算子(RankSelection通过setProbability()方法检查在ControlParameters中是否有概率分配表,若有,则用,否则,会自己计算概率分配表)；
    public static float stopFitness;//进化达到该适应值时停止进化
    public static int stopGeneration;//进化达到该代数时停止进化
    public static float variableProperties[][];
    //第1维是变量的索引，第2维是该变量取值的上下限及精度，[i][0]是上限，[i][1]是下限，[i][2]是精度,[i][3]实际取值的最大值；
    //例如float[][4]
    //对于函数优化问题，
    //第[][0]表示自变量取值的最大范围，如2；
    //第[][1]表示自变量取值的最小范围，如-1；
    //第[][2]表示自变量取值的精度，如0.0000001；
    //第[][3]表示编码对应的最大值maxCodeValue，例如一个问题用二进制表示，而对应的最大数是9，则需要4位二进制，则会带来无效编码问题。如果用16进制编码，也会出现类似的问题；这个数值不仅与问题本身相关，而且与采用的编码也相关。
    //VariableProperties(i)解决的方法：首先由(xMax-xMin)/precision求需要表示的最大数xMaxValue，然后用Math.ceil(log(xMaxValue)/log(2))求解用二进制表示的编码长度length，然后，再用xMin+((xMax-xMin)/(gMax-gMin)*(gValue-gMin)其中，g*表示编码转换成十进制后的值，这样就可顺利求出具体值，而不用maxCodeValue,这带来的一个好处是：不用处理无效解的问题。这样，实际上maxCodeValue=gMax=pow(2,length),通常来说，gMin=0.
//一般来说，对于不同的变量，其maxCodeValue不同，该值等于：pows(codeScope.length,variableSplit[i+1]-variableSplit[i]+1).
    public static int variableSplit[];//其格式是：[0，a1,a2,...,an]其中，a1表示第1个变量的串长，a2表示第2个变量的串开始位置是a1,终止位置是a2,依此类推，第n的变量的串开始位置是an-1,终止位置是an.这是根据variableProperties[][]计算出来的。

    @SuppressWarnings("static-access")
    public static void initVriableProperties() {
        variableProperties = new float[gsuCount][4];
        variableSplit = new int[gsuCount + 1];
    }

    @SuppressWarnings("static-access")
    public static void calIndividualLength() {
        switch (ControlParameters.evolvingType) {
            case 0:
                int length = 0;
 //               float lengthSearchSpace = 0,
                int temint;
                //计算搜索空间大小，并对多变量设置编码分割设定
                variableSplit[0] = 0;
                for (int i = 0; i < gsuCount; i++) {
                    temint = (int)((variableProperties[i][0] - variableProperties[i][1]) / variableProperties[i][2]+0.5f);//+0.5f实现4舍5入
                    int temint2 = 0;
  //                  lengthSearchSpace += temint;//搜索空间的大小
                    //下面计算每一个变量编码串的长度
                    if (temint == 1) {
                        temint2 = 1;
                        variableProperties[i][3] = 1;
                    } else {
                        if (variableProperties[i][0] == 0 && variableProperties[i][1] == 0) {//最大值与最小值均为0,该位基因意义单元不取任何值
                            temint2 = 0;
                        } else {
                            temint2 = (int) (Math.ceil(Math.log(temint) / Math.log((float) ControlParameters.codeScope.length())));
                            variableProperties[i][3] = (float) (Math.pow(codeScope.length(), temint2)-1);
                        }
                    }
                    variableSplit[i + 1] = variableSplit[i] + (int) temint2;
                    length += temint2;
                }
                if(length==0){System.out.println("出错：基因编码长度计算出来为0。该消息来自ControlParameters中的方法calIndividualLength()");}
                individualLength = length;
                break;
        }
    }

    public static void setDefaultParameters() {
        switch (problemNum) {
            case ControlParameters.F0: {
                ControlParameters.evolvingType = 0;
                ControlParameters.maxFitness = 0;
                ControlParameters.gsuCount = 1;
                ControlParameters.stopFitness = 3.85f;
                ControlParameters.selectionType = 0;
                ControlParameters.codeScope = "01";
                ControlParameters.crossoverPointNum = 1;
                ControlParameters.crossoverType = 0;
                ControlParameters.crossProbability = 0.5f;
                ControlParameters.genecodeType = 0;
                ControlParameters.mutationProbability = 0.1f;
                ControlParameters.mutationPointNum = 1;
                ControlParameters.mutationType = 0;
                ControlParameters.populationSize = 50;
                ControlParameters.stopGeneration = 101;
                initVriableProperties();
                ControlParameters.variableProperties[0][2] = 0.001f;
                ControlParameters.variableProperties[0][1] = -1;
                ControlParameters.variableProperties[0][0] = 2;
            }
            break;
            case ControlParameters.F1: {
                ControlParameters.evolvingType = 0;
                ControlParameters.maxFitness = 0;
                ControlParameters.gsuCount = 1;
                ControlParameters.stopFitness = 99.999f;//这里以题目的分数应该都是整数
                ControlParameters.selectionType = 0;
                ControlParameters.codeScope = "01";
                ControlParameters.crossoverPointNum = 1;
                ControlParameters.crossoverType = 0;
                ControlParameters.crossProbability = 0.5f;
                ControlParameters.genecodeType = 0;
                ControlParameters.mutationProbability = 0.1f;
                ControlParameters.mutationPointNum = 1;
                ControlParameters.mutationType = 0;
                ControlParameters.populationSize = 50;
                ControlParameters.stopGeneration = 100000001;//试卷题目必须满足分数要求，因此理论上来说，如果达不到分数要求，则不能停止进化
                initVriableProperties();
                ControlParameters.variableProperties[0][3] = 15f;
                ControlParameters.variableProperties[0][2] = 1f;
                ControlParameters.variableProperties[0][1] = 0f;
                ControlParameters.variableProperties[0][0] = 15f;
            }
            break;
            case ControlParameters.F2: {
                ControlParameters.evolvingType = 0;
                ControlParameters.maxFitness = 0;
                ControlParameters.gsuCount = 1;
                ControlParameters.stopFitness = 999.5f;//这里考虑了浮点类型数据的误差累加，所以不用10000f，所以题目的分数应该都是整数
                ControlParameters.selectionType = 0;
                ControlParameters.codeScope = "01";
                ControlParameters.crossoverPointNum = 1;
                ControlParameters.crossoverType = 0;
                ControlParameters.crossProbability = 0.5f;
                ControlParameters.genecodeType = 0;
                ControlParameters.mutationProbability = 0.1f;
                ControlParameters.mutationPointNum = 1;
                ControlParameters.mutationType = 0;
                ControlParameters.populationSize = 50;
                ControlParameters.stopGeneration = 500;//试卷题目必须满足分数要求，因此理论上来说，如果达不到分数要求，则不能停止进化
                initVriableProperties();
                ControlParameters.variableProperties[0][3] = 15f;
                ControlParameters.variableProperties[0][2] = 1f;
                ControlParameters.variableProperties[0][1] = 0f;
                ControlParameters.variableProperties[0][0] = 15f;
            }
            break;
        }
    }
}
