package controller;

import entities.Knowledge;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
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
import javax.faces.event.ValueChangeEvent;
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
    @Inject
    private QuestionsinfoController questionController;
    @Inject
    private QuestionknowledgeController qkController;

    private Knowledge current;
    private DataModel items = null;

    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<WrappedKnowledge> knowledgeRoot;//只指向最高层结点
    private String chapterId;
    //新的父知识点名字
    private String newPname;
    //新的子知识点名字
    private String newName;
    //显示或隐藏标志位
    private boolean chapterIdSelected = false;

    private int chapterNumId;
    
    public String getNewPname() {
        return newPname;
    }

    public void setNewPname(String newPname) {
        this.newPname = newPname;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;

    }
    
    public void typeChapterListenner(ValueChangeEvent event) {
        chapterNumId = Integer.parseInt((String) event.getNewValue());
    }

    public KnowledgeController() {
    }

    public Knowledge getSelected() {
        if (current == null) {
            current = new Knowledge();
            current.setName("------");
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Knowledge kl) {
        System.out.println("");
        this.chapterIdSelected = true;
        this.current = kl;
        qkController.deleteItem();
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
        Knowledge myknow = new Knowledge();
        myknow.setKnowledge(current);
        myknow.setName(newName);
        myknow.setChapterinfo(current.getChapterinfo());
        getFacade().create(myknow);
        current = myknow;
    }

    //添加父知识点
    public void createParentKnowledge() {
        Knowledge myknow = new Knowledge();
        myknow.setName(newPname);
        myknow.setChapterinfo(chapterController.getSelected());
        getFacade().create(myknow);
        current = null;
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

    public void updateKnowledge() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("KnowledgeUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    //删除当前知识点
    public String delete() {
        performDestroy();
        current.setName(null);
        return null;
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

    public SelectItem[] getItemsAvailableSelectByChapter() {
        SelectItem[] item = JsfUtil.getSelectItems(ejbFacade.findByChapterId(chapterNumId), false);
        for (int i = 0; i < item.length; i++) {
            item[i].setLabel(((Knowledge) item[i].getValue()).getName());
            item[i].setValue(((Knowledge) item[i].getValue()).getId());
        }
        return item;

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

//树形结构的遍历（1）
    public List<WrappedKnowledge> getKnowledgeRoot() {
        if (chapterId == null) {
            System.out.println("kkkkkkkkkkkkkkk");
            return new LinkedList<WrappedKnowledge>();
        } else {
            this.knowledgeRoot = new LinkedList<>();
            List<Knowledge> paretTem = ejbFacade.findByChapterId(Integer.parseInt(chapterId));
            if (paretTem.isEmpty()) {

            } else {
                for (int i = 0; i < paretTem.size(); i++) {
                    Knowledge pcTem = paretTem.get(i);
                    WrappedKnowledge tem = new WrappedKnowledge(pcTem);
                    List<WrappedKnowledge> KParent = new LinkedList<>(), KChildren = new LinkedList<>();
                    List<Knowledge> kList = getFacade().nativeQuery(" parentid=" + pcTem.getId());
                    if (kList.size() > 0) {
                        for (Knowledge kl1 : kList) {
                            if (getFacade().nativeQuery(" parentid=" + kl1.getId()).size() > 0) {
                                WrappedKnowledge continueNode = new WrappedKnowledge(kl1);
                                this.addTreeNode(continueNode);
                                KParent.add(continueNode);
                            } else {
                                KChildren.add(new WrappedKnowledge(kl1));
                            }
                        }
                    }
                    tem.setKChildren(KChildren);
                    tem.setKParents(KParent);
                    this.knowledgeRoot.add(tem);
                }
            }
        }
        return this.knowledgeRoot;
    }

    public void setKnowledgeRoot(List<WrappedKnowledge> knowledgeRoot) {
        this.knowledgeRoot = knowledgeRoot;
    }

//树形结构的遍历（2）
    private void addTreeNode(WrappedKnowledge node) {
        List<Knowledge> kList = getFacade().nativeQuery(" parentid=" + node.getKl().getId());
        if (kList.size() > 0) {
            List<WrappedKnowledge> KParents = new LinkedList<>();
            List<WrappedKnowledge> KChildren = new LinkedList<>();
            for (Knowledge kl1 : kList) {
                if (getFacade().nativeQuery(" parentid=" + kl1.getId()).size() > 0) {
                    WrappedKnowledge continueNode = new WrappedKnowledge(kl1);
                    this.addTreeNode(continueNode);
                    KParents.add(continueNode);

                } else {
                    KChildren.add(new WrappedKnowledge(kl1));
                }
            }
            node.setKChildren(KChildren);
            node.setKParents(KParents);
        }

    }

    /**
     * @return the chapterIdSelected
     */
    public boolean isChapterIdSelected() {
        return chapterIdSelected;
    }

    /**
     * @param chapterIdSelected the chapterIdSelected to set
     */
    public void setChapterIdSelected(boolean chapterIdSelected) {
        this.chapterIdSelected = chapterIdSelected;
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
