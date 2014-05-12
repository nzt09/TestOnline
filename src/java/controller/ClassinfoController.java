package controller;

import entities.Classinfo;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Major;

import java.io.Serializable;
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

@Named("classinfoController")
@SessionScoped
public class ClassinfoController implements Serializable {

    @EJB
    private DepartclassFacadeLocal departclassFacade;
    @Inject
    private DepartclassController departclassController;
    @Inject
    private CourseinfoController courseCon;

    private Classinfo current;
    private DataModel items = null;
    @EJB
    private sessionBean.ClassinfoFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int majorId;
    private Major currentMajor;
    //显示添加是否成功标志位
    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void test() {
        System.out.println("lllllllllllllllllllllllllllllllllllllllllllll");
        className = null;
        System.out.println(className);
    }

    public Major getCurrentMajor() {
        return currentMajor;
    }

    public void setCurrentMajor(Major currentMajor) {
        this.currentMajor = currentMajor;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    private String className;

    public ClassinfoController() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Classinfo getSelected() {
        if (current == null) {
            current = new Classinfo();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void majorTypeListener(ValueChangeEvent event) {

        String hh = (String.valueOf(event.getNewValue()));
        majorId = Integer.parseInt(hh);

        courseCon.setMajorId(majorId);
    }

    private ClassinfoFacadeLocal getFacade() {
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
        current = (Classinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Classinfo();
        selectedItemIndex = -1;
        return "Create";
    }

    public void create() {
        result = 0;
        if (getFacade().findByName(className) == null || getFacade().findByName(className).isEmpty()) {
            Classinfo cinfo = new Classinfo();
            cinfo.setClassname(className);
            cinfo.setMajor(currentMajor);
            getFacade().create(cinfo);
            result = 1;
        } else {
            result = 2;
        }
        className = null;
    }

    public String prepareEdit() {
        current = (Classinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClassinfoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Classinfo) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClassinfoDeleted"));
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

    public SelectItem[] getItemsAvailableSelectByMajor() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findByMajor(majorId), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Classinfo) item[i].getValue()).getClassname());
        }
        return item;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Classinfo) item[i].getValue()).getClassname());
            item[i].setValue(((Classinfo) item[i].getValue()).getId());
        }
        return item;
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsAvailableSelectM() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Classinfo) item[i].getValue()).getClassname());
        }
        return item;
    }

    public Classinfo getClassinfo(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Classinfo.class)
    public static class ClassinfoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClassinfoController controller = (ClassinfoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "classinfoController");
            return controller.getClassinfo(getKey(value));
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
            if (object instanceof Classinfo) {
                Classinfo o = (Classinfo) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Classinfo.class.getName());
            }
        }

    }

}
