/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.StudentinfoController;
import controller.TeacherController;
import entities.Studentinfo;
import entities.Teacher;
import java.io.IOException;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import sessionBean.StudentinfoFacadeLocal;
import sessionBean.TeacherFacadeLocal;
import tools.Publicfields;

/**
 *
 * @author Administrator
 */
@Named
@SessionScoped
public class LoginController implements java.io.Serializable {

    @EJB
    private StudentinfoFacadeLocal studentFacade;

    @Inject
    private StudentinfoController stuCon;

    @EJB
    private TeacherFacadeLocal teaFacade;

    @Inject
    private TeacherController teaCon;
    //验证码
    private String validate_code;

    public String getValidate_code() {
        return validate_code;
    }

    public void setValidate_code(String validate_code) {
        this.validate_code = validate_code;
    }

    // 获得login.xhtml中inputText的值，用户输入的用户编号
    private String userId;
    // 获得login.xhtml中inputSecret的值，用户输入的密码
    private String password;
    private String className;
    private int classId;
    //判断验证码是否对
    private boolean validateFlag = false;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //  用户名并且往后传递
    private String name;

    //用ajax验证验证码
    public String doValidate() {
        if (validate_code == null || validate_code.equals("")) {
            return "";
        } else {
            //取回验证码页面的session
            HttpSession tem = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String code = (String) tem.getAttribute("rand");
            System.out.println("code");
            if (validate_code.equals(code)) {
                validate_code = null;
                validateFlag = true;
                return "../image/right.png";
            } else {
                validateFlag = false;
                validate_code = null;
                return "../image/wrong.gif";
            }
        }
    }

    // 登录验证
    public String login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        Teacher currentTea = teaFacade.findByIdPassword(userId, password);
        Studentinfo currentStu = studentFacade.findByIdPassword(userId, password);
        if (validateFlag == true) {
            if (null != currentTea.getName() && null == currentStu.getName()) {
                //管理员登陆
                name = currentTea.getName();
                teaCon.setCurrent(currentTea);
                if (currentTea.getRolesinfo().getId() == Publicfields.ADMINISTRATOR_ROLE) {
                    
                    return "/interfaces/administrator/list?faces-redirect=true";
                } //教务老师登陆
                else if (currentTea.getRolesinfo().getId() == Publicfields.TEACHER_ROLE) {
                    return "/stylemodel";
//                    return "/interfaces/teacher/teacherMain";
                } //任课老师登陆
                else if (currentTea.getRolesinfo().getId() == Publicfields.EDUTEACHER_ROLE) {
                    teaCon.setCurrent(currentTea);
                    return "/interfaces/eduteacher/eduMain.xhtml";
                }

            } else if (null == currentTea.getName() && null != currentStu.getName()) {
                name = currentStu.getName();
                className = currentStu.getClassinfo().getClassname();
                classId = currentStu.getClassinfo().getId();
                stuCon.setCurrent(currentStu);
                return "/interfaces/student/main";
            }
            this.validate_code = null;
            this.password = null;
            return "/error/errorLogin.xhtml";
        } else {
            this.validate_code = null;
            this.password = null;
            return "errorLogin.xhtml";
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 取消登录，清空用户信息
    public void cancel() {
        this.userId = "";
        this.password = "";
    }

    // 退出系统，并清空Session
    public String loginout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
        this.password = "";
        return "/interfaces/login/login";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
