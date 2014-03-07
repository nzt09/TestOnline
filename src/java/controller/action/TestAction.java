package controller.action;

import controller.StudentinfoController;
import controller.TestassigninfomController;
import entities.Classinfo;
import entities.Courseinfo;
import entities.Questionsinfo;
import entities.Questiontypeinfo;
import entities.Studentinfo;
import entities.Testassigninfom;
import entities.Testpaper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.faces.context.FacesContext;

import javax.inject.Inject;
import javax.inject.Named;
import sessionBean.QuestionsinfoFacadeLocal;
import sessionBean.QuestiontypeinfoFacadeLocal;
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
    private List<Courseinfo> course = new ArrayList<Courseinfo>();
    private List<Classinfo> classinfo = new ArrayList<Classinfo>();
    private final HashMap<Integer, List<Questionsinfo>> allQues;
    private int[] qutypeExsit;
    String[] selectionName = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};

    public TestAction() {
        this.allQues = new LinkedHashMap<>();
        Iterator<Entry<Integer, List<Questionsinfo>>> it=allQues.entrySet().iterator();
    }
    int bianhao=0;
    public String test(){
        this.takeAllQues();
        return null;
    }
public int getBianhao(){
    System.out.println("qqqqqqqqq");
    return bianhao++;
    
}
    @PostConstruct
    public void initilize() {
        questionTypeCount = qtEJB.findAll().size();
        qutypeExsit = new int[questionTypeCount];
        //获取题目id
        if (questionId.size() == 0) {
//            String stunp = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StudentName");
//            String[] ss = stunp.split(";");
            Studentinfo stu = stuCon.getCurrent();
            List<Testpaper> testPaperList = stu.getTestpaperList();
            for (Testpaper test : testPaperList) {
                if (test.getCourseinfo().getId() == testInfom.getCourseId()) {
                    String[] questionIds = test.getContent().split(",");
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
         if (this.getQuestionId().size() >  0) {
            for (int i = 0; i < questionId.size(); i++) {
                Questionsinfo qi = questinfoEjb.find(questionId.get(i));
                if (qi.getQuestiontypeinfo().getId() == Publicfields.SingleSelectType) {
                      List<Questionsinfo> tem;
                    if (!allQues.containsKey(Publicfields.SingleSelectType)) {
                        tem = new LinkedList<>();
                        allQues.put(Publicfields.SingleSelectType, tem);
                    } else {
                        tem = allQues.get(Publicfields.SingleSelectType);
                    }
                    tem.add(qi);
                 
                    HashMap<String, String> List1 = new LinkedHashMap<>();
                    String[] s = qi.getSelections().split("#");
                    for (int j = 0; j < s.length; j++) {
                        List1.put(selectionName[j], selectionName[j] + ". " + s[j]);
                    }
                }
            }
        }
        
    }

    public List<Integer> getQuestionId() {
        
        return questionId;
    }

    public void setQuestionId(List<Integer> questionId) {
        this.questionId = questionId;
    }

    public HashMap<Integer, List<Questionsinfo>> takeAllQues() {
        System.out.println(this.getQuestionId().size()+"==================");
       
        return this.allQues;
    }

    //获取试题List
    public List<Questionsinfo> getQuestioninfo() {
        if (questioninfo.size() == 0) {
//            //从SessionMap获取登录者
//            String stunp = (String) FacesContext.getCurrentInstance()
//                    .getExternalContext().getSessionMap().get("StudentName");
//            String[] ss = stunp.split(";");
//            //从数据库中查询学生Id
//            int stuId = MyDbUtilsTools.getIntegerListHandlerRunner(
//                    "select id from studentinfo where name ='" + ss[0]
//                    + "' and password='" + ss[1] + "'").get(0);
//            //获取该学生完整信息
//            Studentinfo stu = new Studentinfo().getBeanListHandlerRunner(
//                    "select * from studentinfo where id=" + stuId).get(0);
//            //获取学生考试安排
//            testAssign = new Testassigninfom().getBeanListHandlerRunner(
//                    "select * from Testassigninfom where classid="
//                    + stu.getClassid()).get(0);
//            //获取考生试卷
//            testPaper = new Testpaper().getBeanListHandlerRunner(
//                    "select * from testpaper where stuid=" + stuId
//                    + " and course=" + testAssign.getCourseid()).get(0);
//            //题目列表
//            String[] questionIds = testPaper.getContent().split(",");
//            List<Questionsinfo> ques = new ArrayList<Questionsinfo>();

            //处理不同题型，已备于显示考卷
            for (int i = 0; i < questionId.size(); i++) {
                Questionsinfo qi = questinfoEjb.find(questionId.get(i));

//                        qi.setNumber(i + 1);
                //选择题
                if (qi.getQuestiontypeinfo().getId() == Publicfields.SingleSelectType) {
//                            String[] s = qi.getSelections().split("#");

//                            List<StringAndBox> ls = new ArrayList<StringAndBox>();
//                            for (int j = 0; j < s.length; j++) {
//                                StringAndBox st = new StringAndBox();
//                                st.setContentstring(selectionName[j] + ". " + s[j]);
//                                st.setBoxvalue(selectionName[j]);
//                                ls.add(st);
//                            }
//                            qi.setPagecontent(ls);
                    List<Questionsinfo> tem;
                    if (!allQues.containsKey(Publicfields.SingleSelectType)) {
                        tem = new LinkedList<>();
                    } else {
                        tem = allQues.get(Publicfields.SingleSelectType);
                    }
                    tem.add(qi);
                    HashMap<String, String> List1 = new LinkedHashMap<>();
                    String[] s = qi.getSelections().split("#");
                    for (int j = 0; j < s.length; j++) {
                        List1.put(selectionName[j], selectionName[j] + ". " + s[j]);
                    }

                }
//                        //填空题
//                        if (qi.getQuestiontypeinfo().getId() == 7 || qi.getQuestiontypeinfo().getId() == 3) {
//                            if (qi.getContent() != null) {
//                                StringAndBox content = null;
//                                String[] as = qi.getContent().split("#");
//                                List<StringAndBox> ls = new ArrayList<StringAndBox>();
//                                for (int j = 0; j < as.length; j++) {
//                                    content = new StringAndBox();
//                                    content.setContentstring(as[j]);
//                                    ls.add(content);
//                                }
//                                qi.setPagecontent(ls);
//                            }
//                        }
//                        //程序设计题
//                        if (qi.getQuestiontypeinfo().getId() == 6) {
//                            if (qi.getContent() != null) {
//                                StringAndBox content = null;
//                                String[] as = qi.getContent().split("<br/><br/>");
//                                List<StringAndBox> ls = new ArrayList<StringAndBox>();
//                                for (int j = 0; j < as.length; j++) {
//                                    content = new StringAndBox();
//                                    content.setContentstring(as[j]);
//                                    ls.add(content);
//                                }
//                                qi.setPagecontent(ls);
//                            }
//                        }
//                        ques.add(qi);
//                    }
//                    questioninfo = ques;
//                    questionNum = questionId.size();
                // 设置题号
                int number = 0;
                //判断哪种题目存在于试卷
                for (int j = 1; j <= questionTypeCount; j++) {
                    for (int k = 0; k < questioninfo.size(); k++) {
                        if (j == questioninfo.get(k).getQuestiontypeinfo().getId()) {
                            number++;
                            this.qutypeExsit[j] = 1;
                        }
                    }
                }
            }

        }
        return questioninfo;
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

//    public int getIsTesting() {
//        // 检查是否是考试时间
//        String stunp = (String) FacesContext.getCurrentInstance()
//                .getExternalContext().getSessionMap().get("StudentName");
//        String[] ss = stunp.split(";");
//        int stuId = MyDbUtilsTools.getIntegerListHandlerRunner(
//                "select id from studentinfo where name ='" + ss[0]
//                + "' and password='" + ss[1] + "'").get(0);
//        Studentinfo stu = new Studentinfo().getBeanListHandlerRunner(
//                "select * from studentinfo where id=" + stuId).get(0);
//        List<Testassigninfom> lt = new Testassigninfom()
//                .getBeanListHandlerRunner("select * from Testassigninfom where classid="
//                        + stu.getClassid());
//        Iterator it = lt.iterator();
//        isTesting = 0;
//        if (lt.size() > 0) {
//            it = lt.iterator();
//            while (it.hasNext()) {
//                Calendar c = Calendar.getInstance();
//                Calendar testTimeBegin = Calendar.getInstance();
//                testAssign = (Testassigninfom) it.next();
//                testTimeBegin.setTime(testAssign.getTesttime());
//                Calendar testTimeEnd = Calendar.getInstance();
//                testTimeEnd.setTime(testAssign.getTesttime());
//                testTimeEnd.add(Calendar.MINUTE,
//                        testAssign.getTestinterval() + 10);// 预留10分钟，点击“开始考试”比较晚的学生，不能晚于10分钟
//                //考试剩余时间second
//                long milliseconds1 = c.getTimeInMillis();
//                long milliseconds2 = testTimeEnd.getTime().getTime();
//                long diff = milliseconds2 - milliseconds1;
//                long diffSeconds = diff / 1000;
//                lefttime = (int) diffSeconds;
//                // 判断是否在考试时间内
//
//                if ((c.getTime()).after(testTimeBegin.getTime())
//                        && (c.getTime()).before(testTimeEnd.getTime())) {// 在考试时间内
//                    isTesting = 1;
//                }
//            }
//        }
//        return isTesting;
//    }
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

//    public List<Questiontypeinfo> getListQuestionType() {
//        listQuestionType = new Questiontypeinfo()
//                .getBeanListHandlerRunner("select * from questiontypeinfo");
//        return listQuestionType;
//    }
    public void setListQuestionType(List<Questiontypeinfo> listQuestionType) {
        this.listQuestionType = listQuestionType;
    }

//    public List<Courseinfo> getCourse() {
//        if (course.size() == 0) {
//            course = new Courseinfo()
//                    .getBeanListHandlerRunner("select * from courseinfo");
//        }
//        return course;
//    }
    public void setCourse(List<Courseinfo> course) {
        this.course = course;
    }

//    public List<Classinfo> getClassinfo() {
//        if (classinfo.size() == 0) {
//            classinfo = new Classinfo()
//                    .getBeanListHandlerRunner("select * from classinfo");
//        }
//        return classinfo;
//    }
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

//    public void insert() {
//        if (isTesting == 1) {
//            String answers = "";
//            for (int i = 0; i < questioninfo.size(); i++) {
//                String answer = "";
//                answer = answer + questioninfo.get(i).getId() + "-";
//                if (questioninfo.get(i).getPagecontent() != null) {
//                    if (questioninfo.get(i).getQuestiontype() == 1) {
//                        if (questioninfo.get(i).getPageanswer() != null) {
//                            answer = answer + questioninfo.get(i).getPageanswer()
//                                    + "";
//                        }
//                    }
//                    if (questioninfo.get(i).getQuestiontype() == 2) {
//                        if (questioninfo.get(i).getPageanswers() != null) {
//                            for (int j = 1; j < questioninfo.get(i)
//                                    .getPageanswers().size(); j++) {
//                                answer = answer
//                                        + questioninfo.get(i).getPageanswers()
//                                        .get(j) + "#";
//                            }
//                        }
//                    }
//                    // 多项填空
//                    if (questioninfo.get(i).getQuestiontype() == 7
//                            || questioninfo.get(i).getQuestiontype() == 7) {
//                        for (int j = 1; j < questioninfo.get(i).getPagecontent()
//                                .size(); j++) {
//                            answer = answer
//                                    + questioninfo.get(i).getPagecontent().get(j)
//                                    .getBoxvalue() + "#";
//                        }
//                    }
//                    if (questioninfo.get(i).getQuestiontype() == 4
//                            || questioninfo.get(i).getQuestiontype() == 5
//                            || questioninfo.get(i).getQuestiontype() == 6
//                            || questioninfo.get(i).getQuestiontype() == 8) {
//
//                        answer = answer + questioninfo.get(i).getPageanswer()
//                                + "#";
//                    }
//                }
//                if (answer == "") {
//                    if (answers == "") {
//                        answers = answer;
//                    } else {
//                        answers = answers + "#@!";
//                    }
//                } else {
//
//                    if (answers == "") {
//                        answers = answers + answer;
//                    } else {
//                        answers = answers + "#@!" + answer;
//                    }
//                }
//            }
//
//            MyDbUtilsTools.executeUpdate("update testpaper set answer = '" + answers
//                    + "' where id = " + testPaper.getId());
//        }
//
//    }
//    public String submitPaper() {
//        if (isTesting == 1) {
//            this.insert();
//        }
//        return "testlist";
//    }
}
