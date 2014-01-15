package controller;

import controller.action.LoginController;
import entities.Teacher;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Classinfo;
import entities.Departclass;
import entities.Department;
import entities.Major;
import entities.Teachercourseclass;
import sessionBean.TeacherFacadeLocal;
import tools.Publicfields;

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
import sessionBean.ClassinfoFacadeLocal;
import sessionBean.MajorFacadeLocal;
import sessionBean.DepartclassFacadeLocal;
import sessionBean.TeachercourseclassFacadeLocal;

@Named("teacherController")
@SessionScoped
public class TeacherController implements Serializable {

    @EJB
    private TeachercourseclassFacadeLocal teachercourseclassFacade;

    @Inject
    private TeachercourseclassController teachercourseCont;
    
    @EJB
    private DepartclassFacadeLocal DepartclassFacade;
    
    @Inject
    private DepartclassController departclassController;

    @EJB
    private MajorFacadeLocal majorFacade;
    @Inject
    private MajorController majCon;
    @EJB
    private ClassinfoFacadeLocal classinfoFacade;
    @Inject
    private ClassinfoController claCon;
    @Inject
    private LoginController loginController;
    //专业的列表
    private List<SelectItem> majorList;
    //班级的列表
    private List<SelectItem> classList;
    //显示该老师所交课程
    private List<SelectItem> teacherCourseList;
    private int majorId;
    private String rpw;
    private boolean flag;
    private boolean flag1;

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
   

    public String getRpw() {
        return rpw;
    }

    public void setRpw(String rpw) {
        this.rpw = rpw;
    }
    

    public void isShow() {
        items = null;
    }

    public List<SelectItem> getTeacherCourseList() {
        return teacherCourseList;
    }

    public void setTeacherCourseList(List<SelectItem> teacherCourseList) {
        this.teacherCourseList = teacherCourseList;
    }

    public List<SelectItem> getClassList() {
        return classList;
    }

    public void setClassList(List<SelectItem> classList) {
        this.classList = classList;
    }

    public List<SelectItem> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<SelectItem> majorList) {
        this.majorList = majorList;
    }

    private Teacher current;
    private DataModel items = null;
    @EJB
    private sessionBean.TeacherFacadeLocal ejbFacade;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    private PaginationHelper pagination;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
     //核对两遍新密码是否一致
    public String checkNewPassword() {
       if(rpw == null || rpw.isEmpty()){
           
            return "请输入密码";
        }else if(rpw.equals(this.getSelected().getPassword())){
            
            return "密码正确";
        }else
            
           return "密码不匹配";
        
    }
    private int selectedItemIndex, roleId, departmentId;

    //监听上一页获取学院的编号
    public void typeDepartmentListener(ValueChangeEvent event) {
        departmentId = Integer.parseInt((String) event.getNewValue());
        System.out.print(departmentId);
        majorList = new ArrayList<SelectItem>();
        List<Major> currentMaj = majorFacade.findByDepartment(departmentId);
        for (int i = 0; i < currentMaj.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(currentMaj.get(i).getName());
            selectItem.setValue(currentMaj.get(i).getId());
            majorList.add(selectItem);
        }
    }

    public void typeMajorListener(ValueChangeEvent event) {
        majorId = Integer.parseInt((String) event.getNewValue());
        System.out.print("专业" + majorId);

        classList = new ArrayList<SelectItem>();
        List<Classinfo> currentCla = classinfoFacade.findByMajor(majorId);
        for (int i = 0; i < currentCla.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(currentCla.get(i).getClassname());
            selectItem.setValue(currentCla.get(i).getId());
            classList.add(selectItem);
        }
    }

    //监听上一页获取角色的编号
    public void typeRolesListener(ValueChangeEvent event) {

        roleId = Integer.parseInt((String) event.getNewValue());
        System.out.print(roleId);
    }

    public void showTeachercourseclass() {
        System.out.print(current.getPersonid());
        List<Teachercourseclass> currentTeachercourse = teachercourseclassFacade.findByPersonId(current.getPersonid());
        teacherCourseList = new ArrayList<SelectItem>();
        for (int i = 0; i < currentTeachercourse.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(currentTeachercourse.get(i).getCourseinfo().getName());
            selectItem.setValue(currentTeachercourse.get(i).getCourseinfo().getId());
            teacherCourseList.add(selectItem);
        }
        System.out.print(teacherCourseList.toString());
    }

    public TeacherController() {
    }

    public Teacher getCurrent() {
        return current;
    }

    public void setCurrent(Teacher current) {
        this.current = current;
    }

    public Teacher getSelected() {
        if (current == null) {
            current = new Teacher();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TeacherFacadeLocal getFacade() {
        return ejbFacade;
    }

    public void selectDepartment() {

        System.out.print("dzzzzzzzzzzzzzzzzzzz");
        System.out.print(this.getCurrent().getDepartment().getId());
        departmentId = this.getCurrent().getDepartment().getId();
        roleId = this.getCurrent().getRolesinfo().getId();
        System.out.print(this.getCurrent().getRolesinfo().getId());
    }

    public PaginationHelper getPagination() {

        if (pagination == null) {
            pagination = new PaginationHelper(2) {

                @Override
                public int getItemsCount() {
                    return getFacade().findConstrainRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, roleId, departmentId).size();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findConstrainRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, roleId, departmentId));
                }
            };
        }
        return pagination;
    }

    public String teacherprepareList() {
        recreateModel();
        return "teacherlist";
    }

    public String prepareList() {
        recreateModel();
        return "adminMain";
    }

    public String prepareView() {
        current = (Teacher) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "list";
    }

    public String prepareCreate() {
        current = new Teacher();
        selectedItemIndex = -1;
        return "teacherCreate";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeacherCreated"));
            return "";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String educreate() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeacherCreated"));
            return "";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Teacher) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "editteacher";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeacherUpdated"));
            return "teacherlist";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void updateAdminPassword() {
        if(loginController.isPwFlag()==true){
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PasswordChange"));
            flag1 = true;
            flag = false;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
           
        }
    }else{
           JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
           flag = true;
    }
    }

    public String admindestroy() {
        current = (Teacher) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "adminMain";
    }

    public String destroy() {
        current = (Teacher) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "teacherlist";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "view";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "list";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeacherDeleted"));
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
        return "teacherlist";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "teacherlist";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsAvailableSelectM() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Teacher) item[i].getValue()).getName());
        }
        return item;
    }

    public SelectItem[] getItemsAvailableSelecteduTeacher() {
        roleId = Publicfields.EDUTEACHER_ROLE;
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findByRoleId(roleId), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Teacher) item[i].getValue()).getName());
        }
        return item;
    }


    public Teacher getTeacher(java.lang.String id) {
        return ejbFacade.find(id);

    }

    @FacesConverter(forClass = Teacher.class)
    public static class TeacherControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TeacherController controller = (TeacherController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "teacherController");
            return controller.getTeacher(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Teacher) {
                Teacher o = (Teacher) object;
                return getStringKey(o.getPersonid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Teacher.class.getName());
            }
        }

    }
}
