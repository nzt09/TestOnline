package controller;

import entities.Studentinfo;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Departclass;
import entities.Department;
import entities.Rolesinfo;
import entities.Teacher;
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
import sessionBean.DepartclassFacadeLocal;
import sessionBean.TeacherFacadeLocal;
import sessionBean.TeachercourseclassFacadeLocal;
import static tools.Publicfields.EDUTEACHER_ROLE;

@Named("studentinfoController")
@SessionScoped
public class StudentinfoController implements Serializable {

    @EJB
    private TeachercourseclassFacadeLocal teachercourseclassFacade;

    @Inject
    private TeachercourseclassController teachercourseCont;
    
    @EJB
    private DepartclassFacadeLocal DepartclassFacade;
    @Inject
    private DepartclassController DepartclassController;
     @EJB
    private TeacherFacadeLocal teacherFacade;
    @Inject
    private TeacherController teacherController;
 
    
    private Studentinfo current;
    private DataModel items = null;
    @EJB
    private sessionBean.StudentinfoFacadeLocal ejbFacade;
    private PaginationHelper pagination;

    private int selectedItemIndex;

    private List<SelectItem> courseList;// 用于存放该学生的所有课程名
    private int departmentId;
     private Teacher teacher;
   
    private int roleId;
    

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
        return "list_1";
    }

    public String prepareView() {
        current = (Studentinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "list_1";
    }

    public String prepareCreate() {
        current = new Studentinfo();
        selectedItemIndex = -1;
        return "creat1";
    }

    public String create() {
        try {
            getFacade().create(current);
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
        return "edi1";
    }

    
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentinfoUpdated"));
            return "teacherMain";
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
            return "teacherMain";
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
        return "teacherMain";
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
           return "teacherMain";
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
        return "list_1";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "list_1";
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
