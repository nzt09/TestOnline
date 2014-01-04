package controller;

import entities.Teacher;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Classinfo;
import entities.Major;
import entities.Teachercourseclass;
import sessionBean.TeacherFacadeLocal;

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
import sessionBean.TeachercourseclassFacadeLocal;

@Named("teacherController")
@SessionScoped
public class TeacherController implements Serializable {

    @EJB
    private TeachercourseclassFacadeLocal teachercourseclassFacade;

    @Inject
    private TeachercourseclassController teachercourseCont;

    @EJB
    private MajorFacadeLocal majorFacade;
    @Inject
    private MajorController majCon;
    @EJB
    private ClassinfoFacadeLocal classinfoFacade;
    @Inject
    private ClassinfoController claCon;
    //专业的列表
    private List<SelectItem> majorList;
    //班级的列表
    private List<SelectItem> classList;
    //显示该老师所交课程
    private List<SelectItem> teacherCourseList;
    private int majorId;

    //标志能否显示
    private boolean showFlag = false;
    //同上显示添加界面
    private boolean createFlag = false;

    public void isCreate() {
        current.setName("");
        showFlag = false;
        createFlag = true;
    }

    public void isShow() {
        items = null;
        showFlag = true;
        createFlag = false;
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
    private PaginationHelper pagination;
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
        return "teacherMain";
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
        return "create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeacherCreated"));
            return "adminMain";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String educreate() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeacherCreated"));
            return "teacherMain";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Teacher) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "edit";
    }

    public String adminupdate() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeacherUpdated"));
            return "adminMain.xhtml";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeacherUpdated"));
            return "teacherMain";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String updateAdminPassword() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AdminPasswordChange"));
            return "adminMain";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
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
        return "teacherMain";
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
        return "list";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "list";
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

    public Teacher getTeacher(java.lang.String id) {
        return ejbFacade.find(id);

    }

    /**
     * @return the showFlag
     */
    public boolean isShowFlag() {
        return showFlag;
    }

    /**
     * @param showFlag the showFlag to set
     */
    public void setShowFlag(boolean showFlag) {
        this.showFlag = showFlag;
    }

    /**
     * @return the createFlag
     */
    public boolean isCreateFlag() {
        return createFlag;
    }

    /**
     * @param createFlag the createFlag to set
     */
    public void setCreateFlag(boolean createFlag) {
        this.createFlag = createFlag;
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

    public String action() {
        if (roleId == 3) {
            return "list";
        } else {
            return "list_5";
        }
    }

    public String action1() {
        if (roleId == 3) {
            return "create";
        } else {
            return "create_1";
        }
    }

}
