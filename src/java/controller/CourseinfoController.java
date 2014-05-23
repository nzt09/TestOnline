package controller;

import entities.Courseinfo;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import sessionBean.CourseinfoFacadeLocal;

import java.io.Serializable;
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
import sessionBean.MajorFacadeLocal;
import sessionBean.StudentinfoFacadeLocal;

@Named("courseinfoController")
@SessionScoped
public class CourseinfoController implements Serializable {

    @EJB
    private StudentinfoFacadeLocal studentFacade;

    @Inject
    private StudentinfoController stuCon;
    @EJB
    private MajorFacadeLocal majorFacade;
    @Inject
    private MajorController majcon;
    private Courseinfo current;
    private DataModel items = null;
    @EJB
    private sessionBean.CourseinfoFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int classId;
    private int MajorId;

    public int getMajorId() {
        return MajorId;
    }

    public void setMajorId(int MajorId) {
        this.MajorId = MajorId;
    }
    
    
    
    private int subjectTypeId;
    
    
    public void subTypeListener(ValueChangeEvent event) {
        subjectTypeId=Integer.parseInt((String) event.getNewValue());
    }
    

    public CourseinfoController() {
    }

    public Courseinfo getSelected() {
        if (current == null) {
            current = new Courseinfo();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CourseinfoFacadeLocal getFacade() {
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
        return "List";
    }

    public String prepareView() {
        current = (Courseinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Courseinfo();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CourseinfoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Courseinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CourseinfoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Courseinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CourseinfoDeleted"));
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
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    //取出对应科目类型的课程
    public SelectItem[] getItemsAvailableSelectBySubject() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findBySubject(subjectTypeId), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Courseinfo) item[i].getValue()).getName());
        }
        return item;
    }
    
    public SelectItem[] getItemsAvailableSelectBySubjectId() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findBySubject(subjectTypeId), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Courseinfo) item[i].getValue()).getName());
            item[i].setValue(((Courseinfo) item[i].getValue()).getId());
        }
        return item;
    }
    
    public SelectItem[] getItemsAvailableSelectByMajor() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findByMajor(MajorId), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Courseinfo) item[i].getValue()).getName());
        }
        return item;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsAvailableSelectA() {
        classId = stuCon.getCurrent().getClassinfo().getId();
        MajorId = stuCon.getCurrent().getClassinfo().getMajor().getId();
        System.out.println(MajorId+"majorId");
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findByMajor(MajorId), false);
//        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Courseinfo) item[i].getValue()).getName());
            item[i].setValue(((Courseinfo) item[i].getValue()).getId());
        }
        return item;
    }

    
    //获取所有的课程
    public List<Courseinfo> findAllCourses(){
        return ejbFacade.findAll();
    }
    
    public SelectItem[] getItemsAvailableSelectM() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Courseinfo) item[i].getValue()).getName());

        }
        return item;
    }

    public Courseinfo getCourseinfo(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Courseinfo.class)
    public static class CourseinfoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CourseinfoController controller = (CourseinfoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "courseinfoController");
            return controller.getCourseinfo(getKey(value));
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
            if (object instanceof Courseinfo) {
                Courseinfo o = (Courseinfo) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Courseinfo.class.getName());
            }
        }

    }

}
