package controller;

import entities.Knowledge;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Chapterinfo;
import sessionBean.MyKnowledgeFacadeLocal;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

@Named("knowledgeController")
@SessionScoped
public class KnowledgeController implements Serializable {

    @EJB
    private sessionBean.MyKnowledgeFacadeLocal ejbFacade;
    @Inject
    private ChapterinfoController chapterController;
    private Knowledge current;
    private DataModel items = null;

    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<WrappedKnowledge> knowledgeRoot;//只指向最高层结点
    private String chapterId;

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
    

    public KnowledgeController() {
    }

    public Knowledge getSelected() {
        if (current == null) {
            current = new Knowledge();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Knowledge kl) {
        this.current = kl;
    }

    private MyKnowledgeFacadeLocal getFacade() {
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
        current = (Knowledge) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Knowledge();
        selectedItemIndex = -1;
        return "Create";
    }

    //添加子知识点
    public void createChildrenKnowledge() {
        System.out.println(current.getId());
    }

    //添加父知识点
    public String createParentKnowledge() {
        current.setChapterinfo(this.current.getChapterinfo());
        current.setName(this.getSelected().getName());
        current.setCourse(this.current.getCourse());
        getFacade().create(current);
        return null;
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("KnowledgeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Knowledge) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("KnowledgeUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    //删除当前知识点
    public String delete() {
        performDestroy();
        this.current = null;
        return "";
    }

    public String destroy() {
        current = (Knowledge) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("KnowledgeDeleted"));
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
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Knowledge getKnowledge(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    public String hh(){
        return "";
    }

    public List<WrappedKnowledge> getKnowledgeRoot() {
        if (chapterId == null) {
            System.out.println("kkkkkkkkkkkkkkk");
            return new LinkedList<WrappedKnowledge>();
        } else {
            this.knowledgeRoot = new LinkedList<>();
            List<Knowledge> paretTem = ejbFacade.findByChapterId(Integer.parseInt(chapterId));
            for (int i = 0; i < paretTem.size(); i++) {
                this.knowledgeRoot.add(new WrappedKnowledge(paretTem.get(i)));
            }
            return knowledgeRoot;
        }

    }

    public void setKnowledgeRoot(List<WrappedKnowledge> knowledgeRoot) {
        this.knowledgeRoot = knowledgeRoot;
    }

    @FacesConverter(forClass = Knowledge.class)
    public static class KnowledgeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            KnowledgeController controller = (KnowledgeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "knowledgeController");
            return controller.getKnowledge(getKey(value));
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
            if (object instanceof Knowledge) {
                Knowledge o = (Knowledge) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Knowledge.class.getName());
            }
        }

    }

}
