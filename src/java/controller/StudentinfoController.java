package controller;

import controller.action.LoginController;
import entities.Studentinfo;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Classinfo;
import entities.Courseinfo;
import entities.Knowledge;
import entities.Mistake;
import entities.Questionsinfo;
import entities.Teachercourseclass;
import entities.Testpaper;
import entities.TestpaperSimpleOfStudent;
import entities.WrongRightNum;
import java.io.IOException;
import sessionBean.StudentinfoFacadeLocal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import sessionBean.CourseinfoFacadeLocal;
import sessionBean.MyKnowledgeFacade;
import sessionBean.MyKnowledgeFacadeLocal;
import sessionBean.Question2knowledgeFacade;
import sessionBean.Question2knowledgeFacadeLocal;
import sessionBean.QuestionsinfoFacade;
import sessionBean.QuestionsinfoFacadeLocal;
import sessionBean.TeachercourseclassFacadeLocal;
import sessionBean.TestpaperFacadeLocal;
import tools.ConfigUtil;

@Named("studentinfoController")
@SessionScoped
public class StudentinfoController implements Serializable {

    @EJB
    private TeachercourseclassFacadeLocal teachercourseclassFacade;
    @Inject
    private LoginController loginController;
    @Inject
    private DepartclassController departclassController;
    private Studentinfo current;
    private DataModel items = null;
    @EJB
    private sessionBean.StudentinfoFacadeLocal ejbFacade;
    @EJB
    private CourseinfoFacadeLocal courseinfoFacadeLocal;
    @EJB
    private StudentinfoFacadeLocal studentinfoFacadeLocal;
    @EJB
    private QuestionsinfoFacadeLocal questionsinfoFacadeLocal;
    @EJB
    private Question2knowledgeFacadeLocal question2knowledgeFacadeLocal;
    @EJB
    private MyKnowledgeFacadeLocal myKnowledgeFacadeLocal;
    @EJB
    private TestpaperFacadeLocal testpaperFacadeLocal;

    private PaginationHelper pagination;

    private int selectedItemIndex;

    private String pictureData;// 用于flot制图的数据
    private List<Testpaper> templist = new ArrayList<Testpaper>();// 用于存放图片显示页面的试卷数据
    private List<Testpaper> allTestpaper = new ArrayList<Testpaper>();// 存放该学生的所有试卷信息
    private String courseName;// 课程名
    private List<SelectItem> courseList;// 用于存放该学生的所有课程名
    private Map<String, List<Mistake>> courseMap = new HashMap<String, List<Mistake>>();// 用于存放<课程名,对应课程Mistake>的数据
    private List<TestpaperSimpleOfStudent> tsosList = new ArrayList<TestpaperSimpleOfStudent>();// 处理后的学生试卷跟课程相关联的一个list集合
    private int departmentId;
    private int classId;
    private boolean flag = false;
    private boolean flag1 = false;
    private int courseId;

    //用于存放学生成绩分类的个数
    private String scoreLevelNum;

    public String getScoreLevelNum() {
        return scoreLevelNum;
    }

    public void setScoreLevelNum(String scoreLevelNum) {
        this.scoreLevelNum = scoreLevelNum;
    }

    //学生成绩的归类
    public void getScoreData() {
        scoreLevelNum = "";
        Random r = new Random();

        for (int i = 0; i < 4; i++) {
            int a = r.nextInt(100);
            scoreLevelNum = scoreLevelNum + "," + a;
        }
        System.out.println(scoreLevelNum);
    }

    public void typeCourseListener(ValueChangeEvent event) {
        courseId = Integer.parseInt((String) event.getNewValue());
    }

    public String getPictureData() throws IOException {
        pictureData = new String();
        templist.clear();
        allTestpaper = testpaperFacadeLocal.findAll();
        for (Testpaper t : allTestpaper) {
            List<Courseinfo> cList = courseinfoFacadeLocal.findByCourseId(t.getCourseinfo().getId());
            if (cList.get(0).getName().equals("Java程序设计")) {
                templist.add(t);
            }
        }
        System.out.println(templist.size() + "试卷长度");
        String content = new String();
        String wrongnum = new String();
        for (Testpaper t : templist) {
            content += t.getContent() + ",";
            wrongnum += t.getWrongnum();
            t.setContent(content);
            t.setWrongnum(wrongnum);
            TestpaperSimpleOfStudent tsos = dealWithList(t, "notAdd");
            String time = tsos.getTestTime().split(" ")[0];
            System.out.println("time=" + time);
            pictureData += time + "@" + tsos.getPassedNum() + "#";
        }
        return pictureData;
    }

    public TestpaperSimpleOfStudent dealWithList(Testpaper t, String type)
            throws IOException {
        TestpaperSimpleOfStudent tsos = new TestpaperSimpleOfStudent();
        Courseinfo cList = courseinfoFacadeLocal.find(t.getCourseinfo().getId());

        tsos.setCourseName(cList.getName());
        tsos.setScore(t.getScore());
        Studentinfo sList = studentinfoFacadeLocal.find(t.getStudentinfo().getId());
        tsos.setStudentNum(sList.getStuno());
        tsos.setTestTime(t.getStarttime().toString().split(" ")[0]);
        System.out.println(t.getStarttime().toString() + "//////////////////////////////");
        Map<Integer, WrongRightNum> map = new HashMap<Integer, WrongRightNum>();
        String content = t.getContent();// 所有题目的ID
        String wrongAnswer = t.getWrongnum();// 错误题目的ID（String类型）
        String allData[] = content.split(",");// 所有题目的ID数组
        Questionsinfo q = null;
        for (String str : allData) {
            if (str.isEmpty())// 数据库中有部分数据存在"9527,8938,,1234" 此类数据
            {
                continue;
            }
            q = questionsinfoFacadeLocal.find(Integer.parseInt(str));

            int id = question2knowledgeFacadeLocal.findByQusetionId(q.getId()).getKnowledge().getId();
            WrongRightNum qrn = new WrongRightNum();
            if (map.containsKey(id)) { // 已包含
                qrn = map.get(id);
            }
            if (!wrongAnswer.contains(str)) {// 表示正确
                qrn.setRightNum(qrn.getRightNum() + 1);
            } else { // 不正确
                qrn.setWrongNum(qrn.getWrongNum() + 1);
                // 该题目错误时，将该题目拿到courseMap中对应的课程中去
                // start
                // 只显示错误的选择题
                if (q.getQuestiontypeinfo().getId() == 1 || q.getQuestiontypeinfo().getId() == 2) {
                    Mistake wq = new Mistake();
                    wq.setContext(q.getContent());
                    wq.setCourseName(cList.getName());
                    Knowledge knowledge = myKnowledgeFacadeLocal.find(id);
                    wq.setKnowledgeName(knowledge.getName());
                    wq.setScore(q.getScore());
                    String[] selections = q.getSelections().split("#");
                    String selects = new String();
                    for (int i = 0; i < selections.length; i++) {
                        selects += (char) ('A' + i) + selections[i] + "<br>";
                    }
                    wq.setSelections(selects);
                    wq.setSystemAnswer(q.getAnswer());
                    wq.setTime(t.getStarttime().toString().split(" ")[0]);
                    String allAnswer = t.getAnswer();
                    String questionId = q.getId() + "";
                    if (allAnswer.contains(questionId)) {
                        String temp = allAnswer.split(questionId)[1];
                        String data[] = temp.substring(1, temp.indexOf("@!"))
                                .split("#");
                        String userAnswer = new String();
                        for (String s : data) {
                            userAnswer += s;
                        }
                        wq.setUserAnswer(userAnswer);
                        wq.setAnalysis(q.getAnalysis());
                        if (courseMap.containsKey(wq.getCourseName())) {
                            List<Mistake> list = (List<Mistake>) courseMap
                                    .get(wq.getCourseName());
                            list.add(wq);
                            courseMap.put(wq.getCourseName(), list);
                        } else {
                            ArrayList<Mistake> list = new ArrayList<Mistake>();
                            list.add(wq);
                            courseMap.put(wq.getCourseName(), list);
                        }
                    }
                }
                // end
            }
            map.put(id, qrn);
        }
        Set<Integer> keytest = map.keySet();
        Iterator<Integer> it1 = keytest.iterator();
        int passedNum = 0, unpassedNum = 0;
        String advice = new String();
        while (it1.hasNext()) {
            int id1 = it1.next();
            WrongRightNum wrongRightNum = map.get(id1);
            int r = wrongRightNum.getRightNum(), w = wrongRightNum
                    .getWrongNum();
            double d = new ConfigUtil().getStudentRadio();
            if (((r * 1.0) / (r + w)) > d) {
                passedNum++;
            } else {
                unpassedNum++;
                advice += myKnowledgeFacadeLocal.find(id1).getName() + ",";
            }
        }
        tsos.setAdvice(advice);
        tsos.setPassedNum(passedNum);
        tsos.setUnpassedNum(unpassedNum);
        if (type.equals("add")) {
            tsosList.add(tsos);
        }
        return tsos;
    }

    public void setPictureData(String pictureData) {
        this.pictureData = pictureData;
    }

    public boolean isRflag() {
        return rflag;
    }

    public void setRflag(boolean rflag) {
        this.rflag = rflag;
    }
    private boolean rflag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public List<SelectItem> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<SelectItem> courseList) {
        this.courseList = courseList;
    }

    //监听上一页获取学院的编号
    public void typeDepartmentListener(ValueChangeEvent event) {
        departmentId = Integer.parseInt((String) event.getNewValue());
        System.out.print(departmentId);
    }

    public void action() {
        FacesContext context = FacesContext.getCurrentInstance();

        //把学生对应的课程遍历出来并在下拉列表框中显示
        System.out.print(current.getClassinfo().getId());
        List<Teachercourseclass> currentTeachercourse = teachercourseclassFacade.findById(current.getClassinfo().getId());
        courseList = new ArrayList<SelectItem>();
        for (int i = 0; i < currentTeachercourse.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(currentTeachercourse.get(i).getCourseinfo().getName());
            selectItem.setValue(currentTeachercourse.get(i).getCourseinfo().getId());
            courseList.add(selectItem);
        }
    }

    public Studentinfo getCurrent() {
        return current;
    }

    public void setCurrent(Studentinfo current) {
        this.current = current;
    }

    public StudentinfoController() {

    }

    public Studentinfo getSelected() {
        if (current == null) {
            current = new Studentinfo();
            selectedItemIndex = -1;
        }
        return current;
    }

    private StudentinfoFacadeLocal getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "studentlist";
    }

    public String prepareView() {
        current = (Studentinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "list_1";
    }

    public String prepareCreate() {
        current = new Studentinfo();
        selectedItemIndex = -1;
        return "studentCreate";
    }

    public String create() {
        try {
            Classinfo c = new Classinfo();
            c.setId(classId);
            current.setClassinfo(c);
            getFacade().create(current);
            departclassController.isShow();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentinfoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Studentinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "studentEdit";
    }

    public void updateEmail() {
        getFacade().edit(current);
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentinfoEmailUpdated"));
    }

    public void updateStu(Studentinfo stu) {
        getFacade().edit(stu);
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentinfoUpdated"));
            return "studentlist";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    //教务老师修改学生信息
    public String eduupdate() {
        try {
//            getFacade().edit();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentinfoUpdated"));
            return "studentlist";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Studentinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "studentlist";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "list_1";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "list_1";
        }
    }

    //教务老师删学生信息
    public String delete(Studentinfo s) {
        try {
            getFacade().remove(s);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentinfoDeleted"));

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return "studentlist";
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentinfoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "studentlist";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "studentlist";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Studentinfo getStudentinfo(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Studentinfo.class)
    public static class StudentinfoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            StudentinfoController controller = (StudentinfoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "studentinfoController");
            return controller.getStudentinfo(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Studentinfo) {
                Studentinfo o = (Studentinfo) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Studentinfo.class.getName());
            }
        }

    }

}
