package controller;

import entities.Chapterinfo;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Courseinfo;

import java.io.Serializable;
import java.util.ArrayList;
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
import sessionBean.ChapterinfoFacadeLocal;
import sessionBean.CourseinfoFacadeLocal;
import sessionBean.MyKnowledgeFacade;
import sessionBean.MyKnowledgeFacadeLocal;

@Named("chapterinfoController")
@SessionScoped
public class ChapterinfoController implements Serializable {

    @EJB
    private CourseinfoFacadeLocal courseinfoFacade;
    @Inject
    private CourseinfoController courseCon;
    @EJB
    private MyKnowledgeFacadeLocal knowledgeFacade;
    @Inject
    private KnowledgeController kl;

    private Chapterinfo current;
    private DataModel items = null;
    @EJB
    private sessionBean.ChapterinfoFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int courseId;
    private List<SelectItem> courses;
    //新添加的章节的名称
    private String cname;
    //该课程对应的总章节数
    private int chapterTotalNum;
    //修改之后新的章节的名字
    private List<SelectItem> chapterList;
    //选取到的章节的Id
    private int chapterId;

    public List<SelectItem> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<SelectItem> chapterList) {
        this.chapterList = chapterList;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void typeChapterListenner(ValueChangeEvent event) {
        chapterId = Integer.parseInt((String) event.getNewValue());
        this.current = ejbFacade.find(chapterId);
        System.out.println(chapterId);
    }

    public void selectAllChapter() {
        chapterList = new ArrayList<>();
        List<Chapterinfo> chapters = ejbFacade.findByCourseId(courseId);
        for (int i = 0; i < chapters.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(chapters.get(i).getName());
            selectItem.setValue(chapters.get(i).getId());
            chapterList.add(selectItem);
        }
    }

    public void typeCourseListener(ValueChangeEvent event) {
        courseId = Integer.parseInt((String) event.getNewValue());
        courses = new ArrayList<>();
        List<Courseinfo> courses = courseinfoFacade.findByCourseId(courseId);
        //获得对应的章节的集合
        this.selectAllChapter();
    }

    public ChapterinfoController() {
    }

    public Chapterinfo getSelected() {
        if (current == null) {
            current = new Chapterinfo();
            selectedItemIndex = -1;
        }
        return current;
    }

    public String setSelected(Chapterinfo sele) {
        if (sele == null) {
            System.out.println("dddddddddddddd");
        }
        this.current = sele;

        return null;
    }

    private ChapterinfoFacadeLocal getFacade() {
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
        current = (Chapterinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Chapterinfo();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ChapterinfoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    //添加新的章节信息
    public void createNewChapter() {
        try {
            Chapterinfo cinfo=new Chapterinfo();
            cinfo.setName(cname);
            cinfo.setCourseinfo(this.current.getCourseinfo());
            cinfo.setChapternum(this.current.getChapternum() + 1);
            getFacade().create(cinfo);
            //一定要重新赋值一下吗？
            this.selectAllChapter();

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ChapterinfoCreated"));

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

        }
    }

    //更新章节的名字
    public void updateChapter() {
        try {
            getFacade().edit(current);
            this.selectAllChapter();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ChapterinfoUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    //删除章节信息
    public void delete() { 
        System.out.println("ddd");
        performDestroy();
        this.cname = null;
        this.current = null;
        //对不对呢
        this.selectAllChapter();
    }


    public String prepareEdit() {
        current = (Chapterinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ChapterinfoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Chapterinfo) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ChapterinfoDeleted"));
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

    public Chapterinfo getChapterinfo(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Chapterinfo.class)
    public static class ChapterinfoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ChapterinfoController controller = (ChapterinfoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "chapterinfoController");
            return controller.getChapterinfo(getKey(value));
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
            if (object instanceof Chapterinfo) {
                Chapterinfo o = (Chapterinfo) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Chapterinfo.class.getName());
            }
        }

    }

}
