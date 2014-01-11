package controller;

import entities.Questionsinfo;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Questiontypeinfo;
import sessionBean.QuestionsinfoFacadeLocal;

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

@Named("questionsinfoController")
@SessionScoped
public class QuestionsinfoController implements Serializable {

    @Inject
    private KnowledgeController kc;

    private Questionsinfo current;
    private DataModel items = null;
    @EJB
    private sessionBean.QuestionsinfoFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int typeId;
    private int knowid;
    //选择题的四个选项
    private String selection1;
    private String selection2;
    private String selection3;
    private String selection4;
    private String selection5;
    //题目预览内容
    private String preContent;

    //多选题的答案列表
    private List answerList;

    public List getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List answerList) {
        this.answerList = answerList;
    }

    public String getPreContent() {
        this.preContent = current.getContent();
        return preContent;
    }

    public void setPreContent(String preContent) {
        this.preContent = preContent;
    }

    public String getSelection1() {
        return selection1;
    }

    public void setSelection1(String selection1) {
        this.selection1 = selection1;
    }

    public String getSelection2() {
        return selection2;
    }

    public void setSelection2(String selection2) {
        this.selection2 = selection2;
    }

    public String getSelection3() {
        return selection3;
    }

    public void setSelection3(String selection3) {
        this.selection3 = selection3;
    }

    public String getSelection4() {
        return selection4;
    }

    public void setSelection4(String selection4) {
        this.selection4 = selection4;
    }

    public String getSelection5() {
        return selection5;
    }

    public void setSelection5(String selection5) {
        this.selection5 = selection5;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void typeListener(ValueChangeEvent event) {
        typeId = Integer.parseInt((String) event.getNewValue());
        System.out.println("类型" + typeId);
    }
     
    public void selectQuestion(int id) {
        typeId = id;
        knowid = kc.getSelected().getId();
        if (knowid > 0) {
            items = this.getPagination().createPageDataModel();
        } else {

        }
    }

    public QuestionsinfoController() {
    }

    public void typeAnswerListener(ValueChangeEvent event) {

        System.out.println("对不对" + (String) event.getNewValue());
    }
    

    public Questionsinfo getSelected() {
        if (current == null) {
            current = new Questionsinfo();
            selectedItemIndex = -1;
        }
        return current;
    }

    private QuestionsinfoFacadeLocal getFacade() {
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
                    System.out.println("有没有啊" + typeId);
                    return new ListDataModel(getFacade().findConstrainRange(typeId, knowid, new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
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
        current = (Questionsinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Questionsinfo();
        selectedItemIndex = -1;
        return "Create";
    }

    public String addquestion() {
        try {
            Questiontypeinfo qt = new Questiontypeinfo();
            qt.setId(typeId);
            if (answerList != null) {
                StringBuffer answers = new StringBuffer();
                for (int i = 0; i < answerList.size(); i++) {
                    answers.append(answerList.get(i));
                }
                current.setAnswer(answers.toString());
                answers = null;
            } else {
                answerList = new ArrayList();
            }
            if (selection1 != null) {
                if (selection5 == null) {
                    current.setSelections(selection1 + "#" + selection2 + "#" + selection3 + "#" + selection4);
                } else {
                    current.setSelections(selection1 + "#" + selection2 + "#" + selection3 + "#" + selection4 + "#" + selection5);
                }
            }
            current.setQuestiontypeinfo(qt);
            getFacade().create(current);
            current = null;
            selection1 = null;
            selection2 = null;
            selection3 = null;
            selection4 = null;
            selection5 = null;
            answerList = null;
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionsinfoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Questionsinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String edit() {
        current = (Questionsinfo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "editQuestion";
    }

    public String updateQuestion() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionsinfoUpdated"));
            return "eduMain";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionsinfoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Questionsinfo) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionsinfoDeleted"));
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

    public Questionsinfo getQuestionsinfo(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Questionsinfo.class)
    public static class QuestionsinfoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionsinfoController controller = (QuestionsinfoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questionsinfoController");
            return controller.getQuestionsinfo(getKey(value));
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
            if (object instanceof Questionsinfo) {
                Questionsinfo o = (Questionsinfo) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Questionsinfo.class.getName());
            }
        }

    }

}
