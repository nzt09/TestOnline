package controller;

import entities.Major;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Department;
import sessionBean.MajorFacadeLocal;

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

@Named("majorController")
@SessionScoped
public class MajorController implements Serializable {

    private Major current;

    public Major getCurrent() {
        return current;
    }

    public void setCurrent(Major current) {
        this.current = current;
    }
    private DataModel items = null;
    @EJB
    private sessionBean.MajorFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int department;

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public MajorController() {
    }

    public Major getSelected() {
        if (current == null) {
            current = new Major();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MajorFacadeLocal getFacade() {
        return ejbFacade;
    }

    public void departmentTypeListener(ValueChangeEvent event) {
        department = Integer.parseInt((String) event.getNewValue());
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
        current = (Major) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Major();
        selectedItemIndex = -1;
        return "Create";
    }

    public void create() {
        try {
            Department d = new Department();
            d.setId(department);
            current.setDepartment(d);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MajorCreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public String prepareEdit() {
        current = (Major) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MajorUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Major) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MajorDeleted"));
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

    public SelectItem[] getItemsAvailableSelectByDepart() {
        List<Major> majors=ejbFacade.findByDepartmentId(department);
        SelectItem[] item = JsfUtil.getSelectItems(majors, false);
        for (SelectItem item1 : item) {
            item1.setLabel(((Major) item1.getValue()).getName());
            item1.setValue(((Major) item1.getValue()).getId());
        }
        return item;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        for (SelectItem item1 : item) {
            item1.setLabel(((Major) item1.getValue()).getName());
            item1.setValue(((Major) item1.getValue()).getId());
        }
        return item;
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public String findDepartment() {
        department = this.getCurrent().getDepartment().getId();
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findByDepartmentId(department), false);
        String name = ((Major) item[0].getValue()).getDepartment().getName();
        return name;
    }

    public Major getMajor(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Major.class)
    public static class MajorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MajorController controller = (MajorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "majorController");
            return controller.getMajor(getKey(value));
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
            if (object instanceof Major) {
                Major o = (Major) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Major.class.getName());
            }
        }

    }

}
