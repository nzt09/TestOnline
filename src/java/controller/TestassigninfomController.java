package controller;

import entities.Testassigninfom;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import sessionBean.TestassigninfomFacadeLocal;
import java.lang.String;
import java.io.Serializable;
import static java.lang.Thread.sleep;
import java.util.Calendar;
import java.util.Date;
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
import sessionBean.ClassinfoFacadeLocal;
import sessionBean.CourseinfoFacadeLocal;
import sessionBean.StudentinfoFacadeLocal;

@Named("testassigninfomController")
@SessionScoped
public class TestassigninfomController implements Serializable {

    @EJB
    private CourseinfoFacadeLocal courseinfoFacade;
    @Inject
    private CourseinfoController courseinfoCont;
    @EJB
    private ClassinfoFacadeLocal classinfoFacade;
    @Inject
    private ClassinfoController claCon;
    @EJB
    private StudentinfoFacadeLocal studentFacade;
    @Inject
    private StudentinfoController stuCon;

    private Testassigninfom current;
    private DataModel items = null;
    private DataModel items1 = null;
    @EJB
    private sessionBean.TestassigninfomFacadeLocal ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int courseId;
    private int classId;
    private String isTesting;
    private Date time;
    private List<Testassigninfom> testAssignList;
    private int month;
    private int day;
    private int year;
    private boolean isReady=false;
    
//判断当前时间是否在考试时间内
    public boolean isTesting() {
        Calendar c = Calendar.getInstance();
        Calendar beginTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        if (testAssignList == null || testAssignList.isEmpty()) {
            return false;
        } else {
            for (Testassigninfom testa : testAssignList) {
                beginTime.setTime(testa.getTesttime());
                endTime.setTime(testa.getTesttime());
                System.out.println(testa.getTesttime());
                endTime.add(Calendar.MINUTE, testa.getTestinterval());
            }
        }
        System.out.println(beginTime.get(Calendar.MINUTE));
        System.out.println(beginTime.toString());
        System.out.println(endTime.get(Calendar.MINUTE));
        System.out.println(c.toString());
        System.out.println(endTime.toString());
        if (c.before(endTime) && c.after(beginTime)) {
            isReady=true;

        } 
        return isReady;
    }
//    public String leftTime(){
//    int minute = 0;
//        int second = 0;
//        int hour = 0;
//        String lefttime = null;
//        for (Testassigninfom testa : testAssignList) {
//            minute = testa.getTestinterval();
//            if(minute>60){
//                minute = minute - 60;
//                hour = hour + 1;
//            }
//        }
//            while (hour * minute * second >= 0) {
////            System.out.println(hour + ":" + minute + ":" + second);
//             lefttime = "hour + \":\" + minute + \":\" + second)";
//            try {
//                sleep(1000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            if (second != 0) {
//                second--;
//
//            } else {
//                second = 59;
//                if (minute > 0) {
//                    minute--;
//
//                } else {
//                    minute = 59;
//                    if (hour > 0) {
//                        hour--;
//
//                    } else {
//
//                        
//                    }
//                }
//            }
//        }
//    return lefttime;
//    }
//拿到班级ID
    public void getBanjiId() {
        classId = stuCon.getCurrent().getClassinfo().getId();
        System.out.print(classId);
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
        testAssignList = getFacade().findConstrainRange(new int[]{0, 10}, courseId, classId);
        
    }

    public TestassigninfomController() {
    }

    public Testassigninfom getSelected() {

        if (current == null) {
            current = new Testassigninfom();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TestassigninfomFacadeLocal getFacade() {
        return ejbFacade;
    }
//学生端的考试安排
    public PaginationHelper getPagination1() {
        classId = stuCon.getCurrent().getClassinfo().getId();
        System.out.print(courseId);
      System.out.print(classId);
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().findConstrainRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, courseId, classId).size();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findConstrainRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, courseId, classId));
                }
            };
        }
        return pagination;
    }
//教务老师端的考试安排
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
        return "teacher_exam";
    }

    public String prepareView() {
        current = (Testassigninfom) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "teacher_exam";
    }

    public String prepareCreate() {
        current = new Testassigninfom();
        selectedItemIndex = -1;
        return "createexam_arrange";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TestassigninfomCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Testassigninfom) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "editexam_arrange";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TestassigninfomUpdated"));
            return "teacher_exam";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Testassigninfom) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "teacher_exam";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "list_4";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "list_4";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TestassigninfomDeleted"));
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
        items = null;
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    public DataModel getItems1() {
        items1=null;
        if (items1 == null) {
            items1 = getPagination1().createPageDataModel();
        }
        return items1;
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
        return "list_4";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "list_4";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Testassigninfom getTestassigninfom(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    private Date Date(String time) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FacesConverter(forClass = Testassigninfom.class)
    public static class TestassigninfomControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TestassigninfomController controller = (TestassigninfomController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "testassigninfomController");
            return controller.getTestassigninfom(getKey(value));
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
            if (object instanceof Testassigninfom) {
                Testassigninfom o = (Testassigninfom) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Testassigninfom.class.getName());
            }
        }

    }

}
