package controller;

import controller.action.LoginController;
import entities.Studentinfo;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Classinfo;
import entities.Teachercourseclass;
import sessionBean.StudentinfoFacadeLocal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import sessionBean.TeachercourseclassFacadeLocal;

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
    private PaginationHelper pagination;

    private int selectedItemIndex;

    private List<SelectItem> courseList;// 用于存放该学生的所有课程名
    private int departmentId;
    private int classId;
    private String rpw;
    private boolean flag = false;
    private boolean flag1 = false;

    public boolean isRflag() {
        return rflag;
    }

    public void setRflag(boolean rflag) {
        this.rflag = rflag;
    }
    private boolean rflag = false;

    public String getRpw() {
        return rpw;
    }

    public void setRpw(String rpw) {
        this.rpw = rpw;
    }

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
            Classinfo c=new Classinfo();
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

    public void updateStu(Studentinfo stu) {
        getFacade().edit(stu);
    }

    //核对两遍新密码是否一致
    public String checkNewPassword() {
        if (rpw == null || rpw.isEmpty()) {

            return "请输入密码";
        } else if (rpw.equals(this.getSelected().getPassword())) {

            return "密码正确";
        } else {
            return "密码不匹配";
        }

    }
//为学生更新密码

    public void updateStudentPassword() {
        if (loginController.isPwFlag() == true) {
            try {
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PasswordChange"));
                System.out.println("dsfds");
                flag = true;
                flag1 = false;

            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

            }
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            System.out.println("222222");
            flag1 = true;
        }
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
