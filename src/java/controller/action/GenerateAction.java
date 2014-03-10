/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import GA.ControlParameters;
import GA.GACEO;
import GA.Population.FactoryPopulationInitiation;
import GA.Population.Individuals.Function.F1;
import GA.Population.Individuals.Function.F2;
import GA.RandomGenerator;
import entities.Classinfo;
import entities.Courseinfo;
import entities.Department;
import entities.Major;
import entities.Questionsinfo;
import entities.School;
import entities.Studentinfo;
import entities.Testassigninfom;
import entities.Testpaper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import sessionBean.QuestionsinfoFacadeLocal;
import sessionBean.SchoolFacadeLocal;
import sessionBean.StudentinfoFacadeLocal;
import sessionBean.TestassigninfomFacadeLocal;
import sessionBean.TestpaperFacadeLocal;

/**
 *
 * @author Administrator
 */
@Named
@SessionScoped
public class GenerateAction implements java.io.Serializable {

    @EJB
    private TestassigninfomFacadeLocal testAssignFacade;
    @EJB
    private SchoolFacadeLocal schoolFacade;

    @EJB
    private TestpaperFacadeLocal testPaperFacade;
    @EJB
    private StudentinfoFacadeLocal studentFacade;
    @EJB
    private QuestionsinfoFacadeLocal questionFacade;
    // 学校id
    private int schoolId;
    // 院系id
    private int instituteId;
    // 专业id
    private int majorId;
    // 班级id
    private int classId;
    private int courseId;
    // 学校信息
    private List<School> schools = new ArrayList<School>();
    // 学院信息
    private List<Department> institutes = new ArrayList<Department>();
    // 班级信息
    private List<Classinfo> classes = new ArrayList<Classinfo>();
    private List<Courseinfo> courses = new ArrayList<Courseinfo>();
    private List<Major> majors = new ArrayList<Major>();
    // 考试安排
    private Testassigninfom testAssigninfo = new Testassigninfom();

    private int result = 2;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    int scoreSum = 100;// 需要生成的试卷的分数
    // float paperDifficulty = 0.4f;// 试卷的难度--不及格人数的比例
    HashSet<Vector> numScoreSet = new HashSet<Vector>();
    // 数组中存放与和各种分数对应的试题数目，所有这些可行的组合都放在这个set中，采用set而不是list的原因是前者可以自动去除重复的内容
    // 这里用Vector，注意不能用int[]，否则Set无法判断是否已经存在，可能int[]不是对象，所以没办法比较其是否相等。
    List<Integer> scoreType;
    int[] scoreTypeNum;// 题库中各种分数的题目的个数
    // int[][] numScoreSelected;//把集合numScoreSet转化为数组numScoreArray,以便根据随机数取某列值
    int selectedPlan = 0;
    HashMap<Integer, List<Questionsinfo>> questionScoreMap;
    String questionContent = "";// 临时变量，存放每个学生的考试题目编号
    int sumTimeCost = 0;// 用来统计试卷需要的时间

    public void doGenerateTestPaper() {
        synchronized (this) {// 由于进化计算中ControlParameters中参数是public
            // static,所以不能异步进行
            try {
                FacesContext fc = FacesContext.getCurrentInstance();
                Map requestMap = fc.getExternalContext().getRequestParameterMap();
                if (classId > 0 && courseId > 0) {
                    try {
                        testAssigninfo = testAssignFacade.findCourseClass(courseId, classId).get(0);
                        System.out.println(courseId + "yyyyyyy" + classId + "yyyyyy");
                        int testassignid = 0;
                        if (testAssigninfo != null) {
                            if (testAssigninfo.getId() > 0) {
                                testassignid = testAssigninfo.getId();
                                // String type = (String)
                                // requestMap.get("optype");
                                String type = "1";
                                Testassigninfom test = testAssignFacade.find(testassignid);
                                // 如果该场考试安排中有其它notice及noticeScore不为0的话，则要同时读取averageTime出来，以便于在生成试卷时，只生成100-noticeScore和interval-averageTime的试卷
                                int noticeScroe = (null == test.getNoticescore() ? 0 : test.getNoticescore());
                                scoreSum = scoreSum - noticeScroe;
                                int noticeTime = (null == test.getAveragetime() ? 0 : test.getAveragetime());
                                if (type.equals("0")) {
                                    // 修改考试安排
                                } else if (type.equals("1")) {// 生成试卷
                                    // 从表testpaper中删除已经存在的试卷8
                                    System.out.println("daomeidao a  aaaaa");
                                    if (testPaperFacade.findByCourseBystuid(test.getCourseinfo().getId(), test.getClassinfo().getId()) != null) {
                                        testPaperFacade.findByCourseBystuid(test.getCourseinfo().getId(), test.getClassinfo().getId());
                                        for (int i = 0; i < testPaperFacade.findByCourseBystuid(test.getCourseinfo().getId(), test.getClassinfo().getId()).size(); i++) {
                                            testPaperFacade.remove(testPaperFacade.findByCourseBystuid(test.getCourseinfo().getId(), test.getClassinfo().getId()).get(i));
                                        }
                                        System.out.println("daomeidao a  aaaaa");
                                    }

//                                    // 利用遗传算法生成试卷
//                                    // 找到这门程题目的分数类别
                                    String sql = "select distinct(questionsinfo.score)"
                                            + " from questionsinfo,knowledge,chapterinfo,Question2knowledge where "
                                            + "Question2knowledge.knowid=knowledge.id and knowledge.chapter=chapterinfo.id "
                                            + "and chapterinfo.course="
                                            + test.getCourseinfo().getId();
                                    scoreType = questionFacade.executQuery(sql);
                                    System.out.println(test.getCourseinfo().getId() + "题目类型数量");
                                    scoreTypeNum = new int[scoreType.size()];
                                    for (int i = 0; i < scoreType.size(); i++) {
                                        String sql2 = "select distinct(questionsinfo.id) from questionsinfo,knowledge,chapterinfo,Question2knowledge where Question2knowledge.knowid=knowledge.id and knowledge.chapter=chapterinfo.id and chapterinfo.course="
                                                + test.getCourseinfo().getId()
                                                + " and score="
                                                + scoreType.get(i);
                                        scoreTypeNum[i] = (int)questionFacade.executQuery2(sql2);
                                    }
                                    
                                    F1.scoreType = scoreType;
                                    F1.sumScore = scoreSum;
                                    System.out.println("youmuyoudaozheli");
                                    if (scoreType.isEmpty()) {
                                    } else {
                                        for (int i = 0; i < 10; i++) {// 至少生成10种方案
                                            try {
                                                setNumofQuestionType(
                                                        ControlParameters.F1,
                                                        scoreType.size());
                                            } catch (CloneNotSupportedException e) {
                                                // TODO Auto-generated catch
                                                // block
                                                e.printStackTrace();
                                            }// 基因编码是各分值类型题目的个数
                                        }
                                    }
                                    // scoreType |scoreTypeNum |numScoreSet
                                    // |questionScoreMap
                                    // 分数类型
                                    // |与分数对应的题目个数|优化结果：试卷中该分值的题数|与分值对应的题库中的题目 |
                                    // ==================================================================================|
                                    // 5 |85 |20 |16 |40 |3 |32
                                    // |questionScoreMap.get(5) |
                                    // 10 |45 |15 |18 |12 |34 |40
                                    // |questionScoreMap.get(10) |
                                    // 15 |67 |6 |6 |15 |23 |6
                                    // |questionScoreMap.get(15) |
                                    // 20 |34 |4 |4 |6 |4 |3
                                    // |questionScoreMap.get(20) |
                                    // 25 |23 |2 |4 |7 |7 |2
                                    // |questionScoreMap.get(25) |
                                    // ----------------------------------------------------------------------------------
                                    // 总数 ： |47 |48 |80 |74 |83
                                    // 这一行对应于第二次优化时的基因编码长度
                                    // maxNumScore: |83
                                    // 首先把所有该课程的试题按分值不同拿来
                                    
                                    questionScoreMap = new HashMap<Integer, List<Questionsinfo>>();// 里层list存放与分值对应的题目，如分值为5的题目、分值10的题目，所有这些，又存放在外层的map中
                                    for (int j = 0; j < scoreType.size(); j++) {
                                        String temsql = "select  distinct(questionsinfo.id),questionsinfo.content,questionsinfo.score,questionsinfo.difficulty,selections,questionsinfo.questiontype,questionsinfo.answer,questionsinfo.averagetime,questionsinfo.code,questionsinfo.insequence,questionsinfo.count,questionsinfo.testcaseresult,questionsinfo.testcaseresult,questionsinfo.analysis from questionsinfo,knowledge,chapterinfo,Question2knowledge where Question2knowledge.knowid=knowledge.id and knowledge.chapter=chapterinfo.id and chapterinfo.course="
                                                + test.getCourseinfo().getId()
                                                + " and score="
                                                + scoreType.get(j);
                                        questionScoreMap.put(scoreType.get(j), questionFacade.findByCourseId(temsql));
                                    }
                                    F2.questionScoreMap = questionScoreMap;
                                    
                                    System.out.println("现在有没有到这个地方");
                                    // 需要注意的是questionScoreMap中的排序不一定是按5，10，15，20顺序排列的
                                    List<Studentinfo> students = studentFacade.findByClassId(test.getClassinfo().getId());
                                    
                                    Iterator it = students.iterator();
                                    F2.paperDiffulty = test.getTestdifficulty();// 试题难度
                                    F2.testInterval = test.getTestinterval()
                                            - noticeTime;// 考试时长
                                    while (it.hasNext()) {// 生成每个同学的试卷
                                        try {
                                            Studentinfo student = (Studentinfo) it
                                                    .next();
                                            boolean need2Repeat = true;// 再次用来检查是否有重复题目，这是一个权宜之计，因为在F2中就应该杜绝这种重复现象了
                                            while (need2Repeat) {
                                                selectedPlan = RandomGenerator
                                                        .getRandom(0,
                                                                numScoreSet
                                                                .size());// 随机取一个第一次优化得到的方案
                                                setNumofQuestionType(
                                                        ControlParameters.F2,
                                                        scoreType.size());// 基因编码是题目在scoreType中的下标，按题目的分值分类
                                                HashSet<String> temSet = new HashSet<String>();
                                                String[] temCont = questionContent
                                                        .split(",");
                                                int i = 0;
                                                for (; i < temCont.length; i++) {
                                                    if (!temSet.add(temCont[i])) {
                                                        need2Repeat = true;
                                                        break;
                                                    }
                                                }
                                                if (i == temCont.length) {// 全部放入集合中了，不用再重复了
                                                    need2Repeat = false;
                                                } else {
                                                    need2Repeat = true;
                                                }
                                            }
                                            
                                            Testpaper testPaper = new Testpaper();
                                            testPaper.setContent(questionContent);
                                            testPaper.setStudentinfo(student);
                                            testPaper.setSuminterval(sumTimeCost);
                                            testPaper.setCourseinfo(test.getCourseinfo());
                                            Testassigninfom testassign = new Testassigninfom();
                                            testassign.setId(testassignid);
                                            testPaper.setTestassigninfom(testassign);
                                            testPaperFacade.create(testPaper);
                                            result = 1;

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            } finally {
                // out.close();
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private synchronized void setNumofQuestionType(int problemNum, int gsuCount)
            throws CloneNotSupportedException {
        F2 f2 = null;
        // 由于进化计算中ControlParameters中参数是public static,所以不能异步进行
        int generationNum = 1;// 记录进化代数
        // 设置要优化的函数，具体的F0函数在Population.Individuals.Phenotype.Function
        // 如果要优化其它的函数，可以参照F0写法，并在ControlParameters的方法setDefaultParameters()中为其设置相关的参数
        ControlParameters.problemNum = problemNum;
        ControlParameters.setDefaultParameters();
        ControlParameters.gsuCount = gsuCount;
        ControlParameters.initVriableProperties();// 重新按新的gsuCount值重新生成相关变量
        switch (problemNum) {
            case ControlParameters.F1: {
                for (int i = 0; i < gsuCount; i++) {// 设置各基因意义单元的属性
                    ControlParameters.variableProperties[i][3] = (float) scoreTypeNum[i];// 得到的该类题目不能超过题库中该类题目的总数
                    ControlParameters.variableProperties[i][2] = 1f;
                    ControlParameters.variableProperties[i][1] = 0f;
                    ControlParameters.variableProperties[i][0] = (float) scoreTypeNum[i];// 得到的该类题目不能超过题库中该类题目的总数
                }
                ControlParameters.calIndividualLength();
            }

            break;
            case ControlParameters.F2: {
                // |numScoreSet,numScoreSelected|questionScoreMap
                // |优化结果：试卷中该分值的题数|与分值对应的题库中的题目 |sum
                // ==================================================================================================================
                // |20 |questionScoreMap.get(5) |0~19
                // |15 |questionScoreMap.get(10) |20~34
                // |6 |questionScoreMap.get(15) |35~40
                // |0 |questionScoreMap.get(20) |41~44
                // |2 |questionScoreMap.get(25) |45~46
                // ----------------------------------------------------------------------------------
                // 总数maxNumScore： |47 |48 |80 |74 |83 这一行对应于第二次优化时的基因编码长度
                // 个体的基因编码： 010101010101010101010101010 | 101111111111111000000 |
                // ...............
                // 分值为5的题目在编号questionScoreMap.get(5)中的编号 |
                // 分值为10的题目在编号questionScoreMap.get(10)中的编号 |................
                // 基因对应的表现型的最大值为questionScoreMap.get(5).size()*numScoreSelected[selectedPlan][0]|
                // 基因对应的表现型的最大值为questionScoreMap.get(10).size()*numScoreSelected[selectedPlan][1]
                //
                // 获得随机选中的题目方案

                Iterator it = numScoreSet.iterator();
                int k = 0;
                Vector selectedScoreSet = null;
                while (it.hasNext()) {
                    if (k == selectedPlan) {
                        selectedScoreSet = (Vector) it.next();
                        break;
                    }
                    it.next();// 向后走一个
                    k++;
                }
                f2 = new F2();
                f2.setNumScoreSelected(selectedScoreSet);// Vector的赋值，只读取其值，但不修改其值
                F2.scoreType = scoreType;
                it = selectedScoreSet.iterator();// 遍历选中的方案
                int i = 0;
                ControlParameters.variableSplit[0] = 0;
                ControlParameters.individualLength = 0;
                while (it.hasNext()) {
                    int num = (Integer) it.next(), temint2 = 0;
                    if (num == 0) {
                        temint2 = 0;
                        ControlParameters.variableProperties[i][0] = 0;
                    } else {
                        int temint = questionScoreMap.get(scoreType.get(i)).size();
                        if (temint == 1) {
                            temint2 = 1;
                        } else {
                            temint2 = (int) (Math.ceil(Math.log(temint)
                                    / Math.log((float) ControlParameters.codeScope
                                            .length())));
                            // 如果questionScoreMap.get(scoreType.get(i)).size()共有7道题，则其取值为0-6，所以最大值为size()-1
                            ControlParameters.variableProperties[i][0] = questionScoreMap
                                    .get(scoreType.get(i)).size() - 1;
                        }
                    }
                    ControlParameters.individualLength += temint2 * num;
                    ControlParameters.variableSplit[i + 1] = ControlParameters.variableSplit[i]
                            + (int) temint2 * num;
                    ControlParameters.variableProperties[i][3] = (float) Math.pow(
                            ControlParameters.codeScope.length(), temint2);
                    ControlParameters.variableProperties[i][2] = 1f;
                    ControlParameters.variableProperties[i][1] = 0f;
                    i++;
                }
            }
            break;
        }

        // 遗传算法进行优化
        GACEO gaceo = new GACEO();
        switch (ControlParameters.problemNum) {
            case ControlParameters.F0:
            case ControlParameters.F1: {
                gaceo.pop = new FactoryPopulationInitiation(
                        gaceo.pop.individuals.length).getGeneratedPopulation();
                gaceo.pop.CalculateAll();
            }
            break;
            case ControlParameters.F2: {
                gaceo.setF2(f2);
                gaceo.pop = new FactoryPopulationInitiation(
                        gaceo.pop.individuals.length).getGeneratedPopulation(f2);
                gaceo.pop.CalculateAll(f2);
            }
            break;
        }
        while (ControlParameters.maxFitness < ControlParameters.stopFitness
                && generationNum < ControlParameters.stopGeneration) {
            try {
                switch (ControlParameters.problemNum) {
                    case ControlParameters.F0:
                    case ControlParameters.F1: {
                        gaceo.GAOperators();
                    }
                    break;
                    case ControlParameters.F2: {
                        gaceo.GAOperators(f2);
                    }
                    break;
                }
                gaceo.pop.CalculateAll();
                gaceo.pop.bestIndividual.CalculateX();
                generationNum++;
                // System.out.println("The current generation is" +
                // generationNum + ",");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 遗传算法优化结束，可以取结果了
        switch (problemNum) {
            case ControlParameters.F1: {
                Vector temNumscore = new Vector();
                for (int i = 0; i < gaceo.pop.bestIndividual.getPhenoValue()
                        .getxFloatArray().length; i++) {
                    temNumscore.add((int) (gaceo.pop.bestIndividual.getPhenoValue().getxFloatArray()[i] + 0.5));
                }
                numScoreSet.add(temNumscore);
            }
            break;
            case ControlParameters.F2: {
                questionContent = "";
                Iterator it = f2.getNumScoreSelected().iterator();
                int i = 0;// 用来获得ControlParameters.variableSplit数组的下标
                sumTimeCost = 0;
                while (it.hasNext()) {// 按各种分值题数目进行分割numScoreSelected
                    int numScore = (Integer) it.next();
                    if (numScore != 0) {// 该种题型的数目不为0，如果为0，则直接跳过去即可
                        String str = gaceo.pop.bestIndividual.getGeneCode();
                        String tem = str.substring(
                                ControlParameters.variableSplit[i],
                                ControlParameters.variableSplit[i + 1]);
                        int problemStringLength = tem.length() / numScore;// 每道题目对应的二进制串长度
                        for (int j = 0; j < numScore; j++) {// 这种分值题目的总数,
                            String binary = tem.substring(j * problemStringLength,
                                    (j + 1) * problemStringLength);
                            int temInt1 = Integer.parseInt(binary.substring(0, 1));
                            for (int k = 1; k < binary.length(); k++) {// k指向二进值符串中位，第0位已经取出放在temInt1中了，所以k从1开始
                                temInt1 = temInt1
                                        * 2
                                        + Integer.parseInt(binary.substring(k,
                                                        k + 1));
                            }
                            // temInt1是形式上的最大值，还需要计算其实际对应的最大值
                            temInt1 = (int) (temInt1
                                    * (questionScoreMap.get(scoreType.get(i))
                                    .size() - 1) / (Math.pow(2,
                                            binary.length()) - 1));
                            questionContent += questionScoreMap
                                    .get(scoreType.get(i)).get(temInt1).getId()
                                    + ",";// 题目在试题库中的编号
                            sumTimeCost += questionScoreMap.get(scoreType.get(i))
                                    .get(temInt1).getAveragetime();
                        }
                    }
                    i++;
                }
                if (Math.abs(sumTimeCost - F2.testInterval) > 35) {// 如果时长的误差超过25分钟，就重新生成试卷
                    setNumofQuestionType(problemNum, gsuCount);
                }
                questionContent = questionContent.substring(0,
                        questionContent.length() - 1);// 去掉最后的逗号
            }
            break;
        }
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(int instituteId) {
        this.instituteId = instituteId;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public List<School> getSchools() {
        schools = schoolFacade.findAll();
        School schoolTemp = new School();
        schoolTemp.setId(0);
        schoolTemp.setName("请选择");
        schools.add(0, schoolTemp);
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

    public List<Department> getInstitutes() {
        if (schoolId == 0) {
            institutes.clear();
        }
        return institutes;
    }

    public void setInstitutes(List<Department> institutes) {
        this.institutes = institutes;
    }

    public List<Classinfo> getClasses() {
        return classes;
    }

    public void setClasses(List<Classinfo> classes) {
        this.classes = classes;
    }

    public List<Courseinfo> getCourses() {
        if (classId == 0) {
            if (courses.size() > 0) {
                courses.clear();
            }
        }
        return courses;
    }

    public void setCourses(List<Courseinfo> courses) {
        this.courses = courses;
    }

    public List<Major> getMajors() {
        if (instituteId == 0) {
            if (majors.size() > 0) {
                majors.clear();
            }
        }
        return majors;
    }

    public Testassigninfom getTestAssigninfo() {
        if (classId > 0 && courseId > 0) {
            testAssigninfo = testAssignFacade.findCourseClass(courseId, classId).get(0);
        }
        return testAssigninfo;
    }

    public void setTestAssigninfo(Testassigninfom testAssigninfo) {
        this.testAssigninfo = testAssigninfo;
    }

    public void setMajors(List<Major> majors) {
        this.majors = majors;
    }

    public int getScoreSum() {
        return scoreSum;
    }

    public void setScoreSum(int scoreSum) {
        this.scoreSum = scoreSum;
    }

    public HashSet<Vector> getNumScoreSet() {
        return numScoreSet;
    }

    public void setNumScoreSet(HashSet<Vector> numScoreSet) {
        this.numScoreSet = numScoreSet;
    }

    public List<Integer> getScoreType() {
        return scoreType;
    }

    public void setScoreType(List<Integer> scoreType) {
        this.scoreType = scoreType;
    }

    public int[] getScoreTypeNum() {
        return scoreTypeNum;
    }

    public void setScoreTypeNum(int[] scoreTypeNum) {
        this.scoreTypeNum = scoreTypeNum;
    }

    public int getSelectedPlan() {
        return selectedPlan;
    }

    public void setSelectedPlan(int selectedPlan) {
        this.selectedPlan = selectedPlan;
    }

    public HashMap<Integer, List<Questionsinfo>> getQuestionScoreMap() {
        return questionScoreMap;
    }

    public void setQuestionScoreMap(
            HashMap<Integer, List<Questionsinfo>> questionScoreMap) {
        this.questionScoreMap = questionScoreMap;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public int getSumTimeCost() {
        return sumTimeCost;
    }

    public void setSumTimeCost(int sumTimeCost) {
        this.sumTimeCost = sumTimeCost;
    }

}
