package controller;

import entities.Questionknowledge;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import entities.Questionsinfo;
import entities.Questiontypeinfo;
import sessionBean.QuestionknowledgeFacadeLocal;

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
import sessionBean.Question2knowledgeFacadeLocal;
import sessionBean.QuestionsinfoFacadeLocal;

@Named("questionknowledgeController")
@SessionScoped
public class QuestionknowledgeController implements Serializable {

    @Inject
    private KnowledgeController kc;
    @EJB
    private QuestionsinfoFacadeLocal questionFacade;
    @Inject
    private QuestionsinfoController questionController;

    @EJB
    private Question2knowledgeFacadeLocal q2kFacade;
    private Questionknowledge current;
    private DataModel items = null;
    @EJB
    private QuestionknowledgeFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int typeId, knowId;
    //选择题的四个选项
    private String selection1;
    private String selection2;
    private String selection3;
    private String selection4;
    private String selection5;

    private List answerList;

    public List getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List answerList) {
        this.answerList = answerList;
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

    //将答案拆分
    public void divideSelections() {
        Questionknowledge qk = getFacade().findById(current.getId());//此处涉及数据库的lazy模式,所以执行SQL语句会更灵活
        if (current.getQuestiontype() == 1 || current.getQuestiontype() == 2) {
            selection1 = qk.getSelections().split("#")[0];
            selection2 = qk.getSelections().split("#")[1];
            selection3 = qk.getSelections().split("#")[2];
            selection4 = qk.getSelections().split("#")[3];
            if (qk.getSelections().split("#").length > 4) {
                selection5 = qk.getSelections().split("#")[4];
            }
            answerList = new ArrayList();
            if (qk.getAnswer() != null) {
                for (int i = 0; i < qk.getAnswer().toCharArray().length; i++) {
                    answerList.add(qk.getAnswer().toCharArray()[i]);
                }
            }
        }
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

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public void selectQuestion() {
        knowId = kc.getSelected().getId();
        if (knowId > 0) {
            items = this.getPagination().createPageDataModel();
        } else {

        }
    }

    public QuestionknowledgeController() {
    }

    public Questionknowledge getSelected() {
        if (current == null) {
            current = new Questionknowledge();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Questionknowledge questionknowledge) {
        this.current = questionknowledge;
        this.divideSelections();
    }

    private QuestionknowledgeFacadeLocal getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(5) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(typeId, knowId));
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
        current = (Questionknowledge) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Questionknowledge();
        selectedItemIndex = -1;
        return "Create";
    }

    public void store() {
        Questionsinfo q = new Questionsinfo();
        q.setId(current.getId());//ID千万不能忘掉，否则只会一直创建新的对象，而不是修改
        q.setContent(current.getContent());
        q.setScore(current.getScore());
        q.setDifficulty(current.getDifficulty());

        if (selection5 != null) {
            StringBuffer answers = new StringBuffer();
            for (int i = 0; i < answerList.size(); i++) {
                answers.append(answerList.get(i));
            }
            q.setAnswer(answers.toString());
            q.setSelections(selection1 + "#" + selection2 + "#" + selection3 + "#" + selection4 + "#" + selection5);
            answers = null;
        } else {
            q.setAnswer(current.getAnswer());
            q.setSelections(selection1 + "#" + selection2 + "#" + selection3 + "#" + selection4);
        }
        Questiontypeinfo qt = new Questiontypeinfo();
        qt.setId(current.getQuestiontype());
        q.setQuestiontypeinfo(qt);

        q.setAveragetime(current.getAveragetime());
        q.setCode(current.getCode());
        q.setInsequence(current.getInsequence());
        q.setCount(current.getCount());
        q.setTestcasepara(current.getTestcasepara());
        q.setTestcaseresult(current.getTestcaseresult());
        q.setAnalysis(current.getAnalysis());
        questionController.setCurrent(q);
        questionController.update();
        questionController.setCurrent(null);
        selection1 = null;
        selection2 = null;
        selection3 = null;
        selection4 = null;
        selection5 = null;
        this.recreateModel();

    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionknowledgeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Questionknowledge) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionknowledgeUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    //删除题目
    public void delete() {
        q2kFacade.remove(q2kFacade.findByQusetionId(selectedItemIndex));
        questionFacade.remove(questionFacade.find(selectedItemIndex));
        items = null;
    }

    //清空items
    public void deleteItem() {
        items = null;
        typeId = 0;
    }

    public String destroy() {
        current = (Questionknowledge) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("QuestionknowledgeDeleted"));
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
            current = getFacade().findRange(typeId, knowId).get(0);
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

    public void next() {
        getPagination().nextPage();
        recreateModel();
    }

    public void previous() {
        getPagination().previousPage();
        recreateModel();
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Questionknowledge getQuestionknowledge(int id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Questionknowledge.class)
    public static class QuestionknowledgeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionknowledgeController controller = (QuestionknowledgeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questionknowledgeController");
            return controller.getQuestionknowledge(getKey(value));
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Questionknowledge) {
                Questionknowledge o = (Questionknowledge) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Questionknowledge.class.getName());
            }
        }

    }

}
