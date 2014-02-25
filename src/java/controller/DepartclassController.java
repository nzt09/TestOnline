package controller;

import entities.Departclass;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Classinfo;
import entities.Department;
import entities.Major;
import entities.Studentinfo;

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
import sessionBean.DepartclassFacadeLocal;
import sessionBean.MajorFacadeLocal;
import sessionBean.StudentinfoFacadeLocal;
import sessionBean.TestpaperFacade;
import sessionBean.TestpaperFacadeLocal;

@Named("departclassController")
@SessionScoped
public class DepartclassController implements Serializable {

    @EJB
    private StudentinfoFacadeLocal studentinfoFacade;

    @Inject
    private StudentinfoController studentinfoController;

    @EJB
    private TestpaperFacadeLocal testPaperFacade;

    @EJB
    private MajorFacadeLocal majorFacade;

    @EJB
    private ClassinfoFacadeLocal classinfoFacade;

    @Inject
    private TeacherController teacherController;

    @Inject
    private MajorController majorController;
    @Inject
    private MajorController majCon;
    @Inject
    private ClassinfoController claCon;
    private int majorId;
    private Major major;
    private Department department;
    //两种匹配方式
    private String stunoFilter;
    private String nameFilter;

    //专业的列表
    private List<SelectItem> majorList;
    //班级的列表
    private List<SelectItem> classList;

    public List<SelectItem> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<SelectItem> majorList) {
        this.majorList = majorList;
    }

    public List<SelectItem> getClassList() {
        return classList;
    }

    public void setClassList(List<SelectItem> classList) {
        this.classList = classList;
    }

    private int currentStuId;

    public int getCurrentStuId() {
        return currentStuId;
    }

    public void setCurrentStuId(int currentStuId) {
        this.currentStuId = currentStuId;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public String getStunoFilter() {
        return stunoFilter;
    }

    public void setStunoFilter(String stunoFilter) {
        this.stunoFilter = stunoFilter;
    }

    public void isShow() {
        items = null;
    }

    public Departclass getCurrent() {
        return current;
    }

    public void setCurrent(Departclass current) {
        this.current = current;
    }

    private Departclass current;
    private DataModel items = null;
    @EJB
    private sessionBean.DepartclassFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int departmentId;
    private int roleId;
    private int classId;

    private String deletemessage;

    public String getDeletemessage() {
        return deletemessage;
    }

    public void setDeletemessage(String deletemessage) {
        this.deletemessage = deletemessage;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void giveDepartmentId() {
        if(teacherController.getCurrent()!=null){
            departmentId = teacherController.getCurrent().getDepartment().getId();
        }
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public DepartclassController() {
    }

    //监听上一页获取学院的编号
    public void typeDepartmentListener(ValueChangeEvent event) {
        departmentId = Integer.parseInt((String) event.getNewValue());
        System.out.print(departmentId);
    }

    //获得当前学院对应的专业
    public void requireMajor() {
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

        classList = new ArrayList<>();
        List<Classinfo> currentCla = classinfoFacade.findByMajor(majorId);
        for (int i = 0; i < currentCla.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(currentCla.get(i).getClassname());
            selectItem.setValue(currentCla.get(i).getId());
            classList.add(selectItem);
        }
    }

    public Departclass getSelected() {
        if (current == null) {
            current = new Departclass();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Departclass departclass) {
        this.current = departclass;
    }

    private DepartclassFacadeLocal getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {

        System.out.println(classId);
        System.out.println(departmentId);
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().findRange(departmentId).size();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(departmentId));
                }
            };
        }
        return pagination;
    }

    public void setMajor() {
        major = new Major();
        major.setDepartment(department);
        majorController.setCurrent(major);
    }

    public void prepareList() {
        recreateModel();
    }

    public String prepareView() {
        current = (Departclass) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "view_5";
    }

    public String prepareCreate() {
        current = new Departclass();
        selectedItemIndex = -1;
        return "create_1";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartclassCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Departclass) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "edit_1";
    }

    //教务老师修改学生信息
    public String Edit() {
        current = (Departclass) getItems().getRowData();
        Studentinfo s1 = new Studentinfo();
        s1 = studentinfoFacade.find(current.getId());
        System.out.println(s1);
        studentinfoController.setCurrent(s1);
        recreateModel();
        return "studentEdit";
    }

    public String update() {
        try {

            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartclassUpdated"));
            return "manage_student";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    //教务老师删除学生信息
    public void deleted() {
        if (testPaperFacade.findByStuId(currentStuId) == null) {
            studentinfoFacade.remove(studentinfoFacade.find(currentStuId));
            items = null;
            deletemessage = "删除成功";
        } else {
            deletemessage = "删除失败，请先把与该学生相关的试卷删除！";
        }
    }

    public String destroy() {
        current = (Departclass) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "manage_student";
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartclassDeleted"));
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
            current = getFacade().findRange(departmentId).get(0);
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

    public void next() {
        getPagination().nextPage();
        recreateModel();
    }

    public void previous() {
        getPagination().previousPage();
        recreateModel();
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Departclass getDepartclass(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Departclass.class)
    public static class DepartclassControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DepartclassController controller = (DepartclassController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "departclassController");
            return controller.getDepartclass(getKey(value));
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
            if (object instanceof Departclass) {
                Departclass o = (Departclass) object;
                return getStringKey(o.getStuno());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Departclass.class.getName());
            }
        }

    }

}
