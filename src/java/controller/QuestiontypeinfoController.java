package controller;

import entities.Questiontypeinfo;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import sessionBean.QuestiontypeinfoFacadeLocal;

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

@Named("questiontypeinfoController")
@SessionScoped
public class QuestiontypeinfoController implements Serializable {

    private Questiontypeinfo current;
    private DataModel items = null;
    @EJB
    private sessionBean.QuestiontypeinfoFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String typeName;
    //类型的Id
    private int typeId;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    
    
    

    
    public void typeListener(ValueChangeEvent event){
        typeId=Integer.parseInt((String)event.getNewValue());
        System.out.println("类型"+typeId);    
    }
    
    
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public QuestiontypeinfoController() {
    }

    public Questiontypeinfo getSelected() {
        if (current == null) {
            current = new Questiontypeinfo();
            selectedItemIndex = -1;
        }
        return current;
    }

    private QuestiontypeinfoFacadeLocal getFacade() {
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
        current = (Questiontypeinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Questiontypeinfo();
        selectedItemIndex = -1;
        return "Create";
    }
//添加新的题目类型

    public String createNewType() {
        try {
            this.current = new Questiontypeinfo();
            this.items = null;
            this.current.setName(typeName);
            typeName = null;
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestiontypeinfoCreated"));
            return "";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
//
    public void delete() {
        current = (Questiontypeinfo) getItems().getRowData();
        performDestroy();
        this.items = null;
        typeName = null;
    }

    
    
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestiontypeinfoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Questiontypeinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestiontypeinfoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Questiontypeinfo) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestiontypeinfoDeleted"));
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

    public SelectItem[] getItemsAvailableSelectMany() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Questiontypeinfo) item[i].getValue()).getName());
        }
        return item;

    }
    
     public SelectItem[] getItemsAvailableSelectAll() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Questiontypeinfo) item[i].getValue()).getName());
            item[i].setValue(((Questiontypeinfo) item[i].getValue()).getId());
        }
        return item;

    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Questiontypeinfo getQuestiontypeinfo(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Questiontypeinfo.class)
    public static class QuestiontypeinfoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestiontypeinfoController controller = (QuestiontypeinfoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questiontypeinfoController");
            return controller.getQuestiontypeinfo(getKey(value));
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
            if (object instanceof Questiontypeinfo) {
                Questiontypeinfo o = (Questiontypeinfo) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Questiontypeinfo.class.getName());
            }
        }

    }

}
