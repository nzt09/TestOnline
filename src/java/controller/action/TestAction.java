package controller.action;

import controller.StudentinfoController;
import controller.TestassigninfomController;
import controller.TestpaperController;
import entities.Classinfo;
import entities.Courseinfo;
import entities.Questionsinfo;
import entities.Questiontypeinfo;
import entities.Studentinfo;
import entities.Testassigninfom;
import entities.Testpaper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.faces.context.FacesContext;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import sessionBean.QuestionsinfoFacadeLocal;
import sessionBean.QuestiontypeinfoFacadeLocal;
import sessionBean.TestpaperFacadeLocal;
import tools.Publicfields;

/**
 * *
 * 考试
 *
 * @author Administrator
 *
 */
@Named
@SessionScoped
public class TestAction implements java.io.Serializable {

    @EJB
    private QuestionsinfoFacadeLocal questinfoEjb;
    @EJB
    private QuestiontypeinfoFacadeLocal qtEJB;
    @EJB
    private TestpaperFacadeLocal testpaperEJB;
    @Inject
    private TestpaperController testP;
    @Inject
    private StudentinfoController stuCon;
    @Inject
    private TestassigninfomController testInfom;
    int questionTypeCount = 0;
    private int isTesting = 0; // 是否在考试时间
    private Testassigninfom testAssign;// 考试安排
    private List<Testassigninfom> testAssigns = new ArrayList<Testassigninfom>();
    private String studentName;
    private List<Testpaper> tp;// 试卷列表
    private Testpaper testPaper;// 考卷
    private int leftMinute1;
    private int questionNum = 0;
    List<Questiontypeinfo> listQuestionType;// 题目类型
    private List<Questionsinfo> questioninfo = new ArrayList<Questionsinfo>();// 考试题目
    private List<Integer> questionId = new ArrayList<Integer>();// 考试题目Id3
    private List<Integer> falseId = new ArrayList<Integer>();
    private List<Courseinfo> course = new ArrayList<Courseinfo>();
    private List<Classinfo> classinfo = new ArrayList<Classinfo>();
    private final HashMap<Integer, List<Questionsinfo>> allQues;//key是题目类型
    private HashMap<Integer, String> testAnswer = new LinkedHashMap<Integer, String>();
    private HashMap<Integer, String> trueAnswer = new LinkedHashMap<Integer, String>();
    private HashMap<Integer, String> testAnswer1 = new LinkedHashMap<Integer, String>();//储存除无序多项填空题的答案
    private HashMap<Integer, String> disorderAnswer = new LinkedHashMap<Integer, String>();//存储无序多项填空题的答案
    private HashMap<Integer, String> disorderTrue = new LinkedHashMap<Integer, String>();//存储无序多项填空题的正确答案
    Set<String> setStr = new HashSet<>();
    Set<String> setStr1 = new HashSet<>();
    private String[] testPaperAnswer;

    public Set getSetStr() {
        return setStr;
    }

    public void setSetStr(Set setStr) {
        this.setStr = setStr;
    }

    public Set getSetStr1() {
        return setStr1;
    }

    public void setSetStr1(Set setStr1) {
        this.setStr1 = setStr1;
    }

    public HashMap<Integer, String> getDisorderAnswer() {
        return disorderAnswer;
    }

    public void setDisorderAnswer(HashMap<Integer, String> disorderAnswer) {
        this.disorderAnswer = disorderAnswer;
    }

    public HashMap<Integer, String> getDisorderTrue() {
        return disorderTrue;
    }

    public void setDisorderTrue(HashMap<Integer, String> disorderTrue) {
        this.disorderTrue = disorderTrue;
    }
    private int biangao1;

    public HashMap<Integer, String> getTestAnswer1() {
        return testAnswer1;
    }

    public void setTestAnswer1(HashMap<Integer, String> testAnswer1) {
        this.testAnswer1 = testAnswer1;
    }

    public HashMap<Integer, String> getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(HashMap<Integer, String> trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public HashMap<Integer, String> getTestAnswer() {

        return testAnswer;
    }

    public void setTestAnswer(HashMap<Integer, String> testAnswer) {
        this.testAnswer = testAnswer;
    }

    private int[] qutypeExsit;

    public String[] getTestPaperAnswer() {
        return testPaperAnswer;
    }

    public void setTestPaperAnswer(String[] testPaperAnswer) {
        this.testPaperAnswer = testPaperAnswer;
    }

    private HashMap<String, String> list1;

    private HashMap<String, String> list2;

    public HashMap<String, String> getTlist() {
        return tlist;
    }

    public void setTlist(HashMap<String, String> tlist) {
        this.tlist = tlist;
    }

    private HashMap<String, String> tlist;
    private LinkedList list3;

    public HashMap<String, String> getList2() {
        return list2;
    }

    public void setList2(HashMap<String, String> list2) {
        this.list2 = list2;
    }

    public HashMap<String, String> getList1() {
        return list1;
    }

    public void setList1(HashMap<String, String> list1) {
        this.list1 = list1;
    }

    public LinkedList getList3() {
        return list3;
    }

    public void setList3(LinkedList list3) {
        this.list3 = list3;
    }

    String[] selectionName = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};

    public String[] getSelectionName() {
        return selectionName;
    }

    public void setSelectionName(String[] selectionName) {
        this.selectionName = selectionName;
    }

    public TestAction() {
        this.allQues = new LinkedHashMap<>();
        //Iterator<Entry<Integer, List<Questionsinfo>>> it = allQues.entrySet().iterator();
    }
    int bianhao = 0;

    public String test() {
        this.getAllQues();
        return null;
    }

    public int getBianhao() {
        if (bianhao < biangao1) {
            bianhao++;
        } else {
            bianhao = 0;
        }
        return bianhao;

    }

    @PostConstruct
    public void initilize() {
        questionTypeCount = qtEJB.findAll().size();
        qutypeExsit = new int[questionTypeCount];
        list1 = new LinkedHashMap<>();
        list2 = new LinkedHashMap<>();
        tlist = new LinkedHashMap<>();
        list3 = new LinkedList();
        //获取题目id
        if (questionId.size() == 0) {
            Studentinfo stu = stuCon.getCurrent();
            List<Testpaper> testPaperList = stu.getTestpaperList();
            for (Testpaper test : testPaperList) {
                if (test.getCourseinfo().getId() == testInfom.getCourseId()) {
                    String[] questionIds = test.getContent().split(",");
                    biangao1 = questionIds.length;
                    List<Integer> ques = new ArrayList<Integer>();
                    for (int i = 0; i < questionIds.length; i++) {
                        ques.add(Integer.parseInt(questionIds[i]));

                    }
                    questionId = ques;
                    break;
                }
            }
        }
        //获取题目列表
        if (this.getQuestionId().size() > 0) {
            for (int i = 0; i < questionId.size(); i++) {
                Questionsinfo qi = questinfoEjb.find(questionId.get(i));
                if (qi.getQuestiontypeinfo().getId() != Publicfields.MultiFill) {
                    trueAnswer.put(qi.getId(), qi.getAnswer());
                }
                System.out.println(trueAnswer);
                System.out.println(testAnswer);
                //判断题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.TrueorFalse) {
                    List<Questionsinfo> tem;
                    if (!allQues.containsKey(Publicfields.TrueorFalse)) {
                        tem = new LinkedList<>();
                        allQues.put(Publicfields.TrueorFalse, tem);
                    } else {
                        tem = allQues.get(Publicfields.TrueorFalse);
                    }
                    tem.add(qi);
                    String[] s = qi.getSelections().split("#");
                    for (int j = 0; j < s.length; j++) {
                        tlist.put(selectionName[j] + ". " + s[j], selectionName[j]);
                    }
                }
                //单项选择题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.SingleSelectType) {
                    List<Questionsinfo> tem;
                    if (!allQues.containsKey(Publicfields.SingleSelectType)) {
                        tem = new LinkedList<>();
                        allQues.put(Publicfields.SingleSelectType, tem);
                    } else {
                        tem = allQues.get(Publicfields.SingleSelectType);
                    }
                    tem.add(qi);
                    String[] s = qi.getSelections().split("#");
                    for (int j = 0; j < s.length; j++) {
                        list1.put(selectionName[j] + ". " + s[j], selectionName[j]);
                    }
                }
                //多项选择题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.MultiSelectType) {
                    List<Questionsinfo> tem1;
                    if (!allQues.containsKey(Publicfields.MultiSelectType)) {
                        tem1 = new LinkedList<>();
                        allQues.put(Publicfields.MultiSelectType, tem1);

                    } else {
                        tem1 = allQues.get(Publicfields.MultiSelectType);
                    }
                    tem1.add(qi);
                    String[] s1 = qi.getSelections().split("#");
                    for (int k = 0; k < s1.length; k++) {
                        list2.put(selectionName[k] + ". " + s1[k], selectionName[k]);

                    }
                }
                //单项填空题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.SingleFill) {
                    List<Questionsinfo> tem1;
                    if (!allQues.containsKey(Publicfields.SingleFill)) {
                        tem1 = new LinkedList<>();
                        allQues.put(Publicfields.SingleFill, tem1);

                    } else {
                        tem1 = allQues.get(Publicfields.SingleFill);
                    }
                    tem1.add(qi);

                }
                //多项填空题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.MultiFill) {
                    List<Questionsinfo> tem;
                    if (!allQues.containsKey(Publicfields.MultiFill)) {
                        tem = new LinkedList<>();
                        allQues.put(Publicfields.MultiFill, tem);
                    } else {
                        tem = allQues.get(Publicfields.MultiFill);
                    }
                    tem.add(qi);
                }
                //简答题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.ProgrammingProblem) {
                    List<Questionsinfo> tem;
                    if (!allQues.containsKey(Publicfields.ProgrammingProblem)) {
                        tem = new LinkedList<>();
                        allQues.put(Publicfields.ProgrammingProblem, tem);
                    } else {
                        tem = allQues.get(Publicfields.ProgrammingProblem);
                    }
                    tem.add(qi);
                }

            }
        }
        int questCount = 0;
        Iterator it = allQues.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, List<Questionsinfo>> entry = (Entry<Integer, List<Questionsinfo>>) it.next();
            questCount += entry.getValue().size();
        }
        testPaperAnswer = new String[questCount];

    }

    public String convertFill(Questionsinfo question) {
        String[] content = question.getContent().split("#");
        System.out.println(question.getContent());
        String[] answer = question.getAnswer().split("#");
        System.out.println(question.getAnswer());
        String temContent = "";
        int i = 0;
        for (; i < content.length - 1; i++) {
            String fill = this.getTestAnswer().get(question.getId());
            if (fill == null) {
                fill = "";
            }
            temContent += content[i] + "<input id=\"fill_" + i + "_" + question.getId() + "\" name=\"" + "fill_" + i + "_" + 
question.getId() + "\"  value=\"" + fill + "\" type=text size=" + answer[i].length() + "/>";
        }
        temContent += content[i];
        return temContent;
    }

    public String convertFills(Questionsinfo question) {
        String[] content = question.getContent().split("#");
        String[] answer = question.getAnswer().split("#");
        String temContent = "";
        int i = 0;
        String fill = this.getTestAnswer().get(question.getId());
        if (fill == null) {
            fill = "";
            for (; i < content.length - 1; i++) {
                temContent += content[i] + "<input id=\"fill_" + i + "_" + question.getId() + "\" name=\"" + "fill_" + i + "_" 
+ question.getId() + "\"  value=\"" + fill + "\" type=text size=" + answer[i].length() + "/>";
            }
        } else {
            System.out.println(fill + "====================");
            String[] fills = fill.split("#");
            for (; i < content.length - 1; i++) {
                temContent += content[i] + "<input id=\"fill_" + i + "_" + question.getId() + "\" name=\"" + "fill_" + i + "_" 
+ question.getId() + "\"  value=\"" + fills[i] + "\" type=text size=" + answer[i].length() + "/>";
            }
        }
        temContent += content[i];
        return temContent;

    }

    public List<Integer> getQuestionId() {

        return questionId;
    }

    public void setQuestionId(List<Integer> questionId) {
        this.questionId = questionId;
    }

    public HashMap<Integer, List<Questionsinfo>> getAllQues() {
        return this.allQues;
    }

    public void setQuestioninfo(List<Questionsinfo> questioninfo) {
        this.questioninfo = questioninfo;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public int getLeftMinute1() {
        return leftMinute1;
    }

    public void setLeftMinute1(int leftMinute1) {
        this.leftMinute1 = leftMinute1;
    }

    public String getStudentName() {
        studentName = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("StudentName");
        String[] ss = studentName.split(";");
        studentName = ss[0];
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Testassigninfom getTestAssign() {
        return testAssign;
    }

    public void setTestAssign(Testassigninfom testAssign) {
        this.testAssign = testAssign;
    }

    public void setIsTesting(int isTesting) {
        this.isTesting = isTesting;
    }

    public List<Testpaper> getTp() {
        return tp;
    }

    public void setTp(List<Testpaper> tp) {
        this.tp = tp;
    }

    public Testpaper getTestPaper() {
        return testPaper;
    }

    public void setTestPaper(Testpaper testPaper) {
        this.testPaper = testPaper;
    }

    public void setListQuestionType(List<Questiontypeinfo> listQuestionType) {
        this.listQuestionType = listQuestionType;
    }

    public void setCourse(List<Courseinfo> course) {
        this.course = course;
    }

    public void setClassinfo(List<Classinfo> classinfo) {
        this.classinfo = classinfo;
    }

    public List<Testassigninfom> getTestAssigns() {
        if (testAssigns.size() == 0 && testAssign != null) {
            List<Testassigninfom> tas = new ArrayList<Testassigninfom>();
            tas.add(testAssign);
            testAssigns = tas;
        }
        return testAssigns;
    }

    public void setTestAssigns(List<Testassigninfom> testAssigns) {
        this.testAssigns = testAssigns;
    }

    // 是否在考试时间
    public String isInTesting() {
        return "test";
    }

    public void insert() {
        allQues.clear();
        bianhao = 1;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //获取题目列表
        if (this.getQuestionId().size() > 0) {
            for (int i = 0; i < questionId.size(); i++) {
                Questionsinfo qi = questinfoEjb.find(questionId.get(i));
                //判断题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.TrueorFalse) {
                    List<Questionsinfo> tem;
                    if (!allQues.containsKey(Publicfields.TrueorFalse)) {
                        tem = new LinkedList<>();
                        allQues.put(Publicfields.TrueorFalse, tem);
                    } else {
                        tem = allQues.get(Publicfields.TrueorFalse);
                    }
                    tem.add(qi);
                    testAnswer.put(qi.getId(), request.getParameter("myform:a" + qi.getId()));
                    testAnswer1.put(qi.getId(), request.getParameter("myform:a" + qi.getId()));
                }
                //单项选择题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.SingleSelectType) {
                    List<Questionsinfo> tem;
                    if (!allQues.containsKey(Publicfields.SingleSelectType)) {
                        tem = new LinkedList<>();
                        allQues.put(Publicfields.SingleSelectType, tem);
                    } else {
                        tem = allQues.get(Publicfields.SingleSelectType);
                    }
                    tem.add(qi);
                    testAnswer.put(qi.getId(), request.getParameter("myform:a" + qi.getId()));
                    testAnswer1.put(qi.getId(), request.getParameter("myform:a" + qi.getId()));
                }
                //多项选择题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.MultiSelectType) {
                    List<Questionsinfo> tem1;
                    if (!allQues.containsKey(Publicfields.MultiSelectType)) {
                        tem1 = new LinkedList<>();
                        allQues.put(Publicfields.MultiSelectType, tem1);

                    } else {
                        tem1 = allQues.get(Publicfields.MultiSelectType);
                    }
                    tem1.add(qi);
                    int k = 0;
                    String multivalue = "";
                    String[] value = new String[5];
                    String[] values = request.getParameterValues("myform:a" + qi.getId());
                    if (values != null) {
                        for (int j = 0; j < values.length; j++) {
                            if (values[j] != null) {
                                value[k] = values[j];
                                k++;
                            }
                        }
                    }
                    for (int l = 0; l < k; l++) {
                        if (l != k - 1) {
                            multivalue = multivalue + value[l] + "#";
                        } else {
                            multivalue = multivalue + value[l];
                        }

                    }
                    testAnswer.put(qi.getId(), multivalue);
                    testAnswer1.put(qi.getId(), multivalue);
                }
                //单项填空题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.SingleFill) {
                    List<Questionsinfo> tem1;
                    if (!allQues.containsKey(Publicfields.SingleFill)) {
                        tem1 = new LinkedList<>();
                        allQues.put(Publicfields.SingleFill, tem1);

                    } else {
                        tem1 = allQues.get(Publicfields.SingleFill);
                    }
                    tem1.add(qi);
                    testAnswer.put(qi.getId(), request.getParameter("fill_0_" + qi.getId()));
                    testAnswer1.put(qi.getId(), request.getParameter("fill_0_" + qi.getId()));
                }
                //多项填空题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.MultiFill) {
                    List<Questionsinfo> tem;
                    if (!allQues.containsKey(Publicfields.MultiFill)) {
                        tem = new LinkedList<>();
                        allQues.put(Publicfields.MultiFill, tem);
                    } else {
                        tem = allQues.get(Publicfields.MultiFill);
                    }
                    tem.add(qi);
                    int k = 0;
                    String temAnswer = "";
                    String[] fill = qi.getAnswer().split("#");
                    for (int t = 0; t < fill.length; t++) {
                        if (fill[t] != null) {
                            k++;
                        }
                    }
                    for (int j = 0; j < k; j++) {
                        if (j != k - 1) {
                            temAnswer = temAnswer + request.getParameter("fill_" + j + "_" + qi.getId()) + "#";
                        } else {
                            temAnswer = temAnswer + request.getParameter("fill_" + j + "_" + qi.getId()) + "#2";
                        }
                    }
                    if (qi.getInsequence() == 1) {
                        trueAnswer.put(qi.getId(), qi.getAnswer() + "#2");
                        testAnswer1.put(qi.getId(), temAnswer);
                    } else {
                        disorderAnswer.put(qi.getId(), temAnswer);
                        disorderTrue.put(qi.getId(), qi.getAnswer());
                    }
                    testAnswer.put(qi.getId(), temAnswer);
                    System.out.println(testAnswer);
                }
                //简答题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.ProgrammingProblem) {
                    List<Questionsinfo> tem;
                    if (!allQues.containsKey(Publicfields.ProgrammingProblem)) {
                        tem = new LinkedList<>();
                        allQues.put(Publicfields.ProgrammingProblem, tem);
                    } else {
                        tem = allQues.get(Publicfields.ProgrammingProblem);
                    }
                    tem.add(qi);
                    testAnswer.put(qi.getId(), request.getParameter("myform:a" + qi.getId()));
                    testAnswer1.put(qi.getId(), request.getParameter("myform:a" + qi.getId()));
                    System.out.println(testAnswer.values());
                }
            }
        }
        String answers = "";//学生的答案
        String answer = "";//正确答案
        Set set = testAnswer1.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            answers = answers + me.getKey() + "-" + me.getValue() + "#@!";
        }
        Set set1 = trueAnswer.entrySet();
        Iterator it1 = set1.iterator();
        while (it1.hasNext()) {
            Map.Entry me = (Map.Entry) it1.next();
            answer = answer + me.getKey() + "-" + me.getValue() + "#@!";
        }
        System.out.println(answer);
        System.out.println(answers);
        String[] list = answer.split("#@!");
        
        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]);
        }
        System.out.println("list="+list.length);
        String[] list1 = answers.split("#@!");
        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i] + "==========================");
        }
        for (int i = 0; i < list1.length; i++) {
            System.out.println(list1[i] + "==========================");
        }
        System.out.println(list.length);
        String wrong = "";
        String right = "";
        double score = 0;//存放成绩
        Object[] obj = trueAnswer.keySet().toArray();
        //除无序多项填空题的客观题答案的比对
        for (int k = 0; k < list1.length; k++) {
            if (!list[k].equals(list1[k])) {
                falseId.add(k);
                wrong = wrong + obj[k] + ",";
            } else {
                right = right + obj[k] + ",";
                Questionsinfo rqi = questinfoEjb.find(obj[k]);
                score += rqi.getScore();
                System.out.println(score);
            }
        }
        //无序多项填空题的答案对比
        int l = 0;
        Object[] obj1 = disorderAnswer.keySet().toArray();
        for (int y = 0; y < obj1.length; y++) {
            l = 0;
            String[] s1 = disorderAnswer.get(obj1[y]).split("#");
            String[] s2 = disorderTrue.get(obj1[y]).split("#");
            for (int q = 0; q < s2.length; q++) {
                setStr.add(s1[q]);
                setStr1.add(s2[q]);
            }
            Iterator itor = setStr.iterator();
            Iterator itor1 = setStr1.iterator();
            while (itor.hasNext()) {
                if (setStr1.contains(itor.next())) {
                    l++;
                    System.out.println(l + "-------");
                }

            }
            if (l != s2.length) {
                wrong = wrong + obj1[y] + ",";
            } else {
                right = right + obj1[y] + ",";
            }

            Questionsinfo rqi = questinfoEjb.find(obj1[y]);
            score = score + (float) l / s2.length * rqi.getScore();
            System.out.println(((float) l / s2.length) * rqi.getScore());
            System.out.println(rqi.getScore());
            System.out.println(rqi.getId());
            System.out.println(l);

        }

        Studentinfo stu = stuCon.getCurrent();
        List<Testpaper> testPaperList = stu.getTestpaperList();
        for (Testpaper test : testPaperList) {
            if (test.getCourseinfo().getId() == testInfom.getCourseId()) {
                System.out.println(test.getId());
                test.setAnswer(answers);
                test.setWrongnum(wrong);
                int r = (int) score;
                if ((score - (int) score) >= 0.5) {
                    r++;
                }
                test.setScore(r);
                testpaperEJB.edit(test);
            }
        }
    }
}
