/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import com.email.Mail;
import controller.StudentinfoController;
import controller.TeacherController;
import entities.Studentinfo;
import entities.Teacher;
import java.io.IOException;
import java.util.Random;
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
    private String oldpw;
    private String className;

    public String getOldpw() {
        return oldpw;
    }

    public void setOldpw(String oldpw) {
        this.oldpw = oldpw;
    }
    private int classId;
    //判断密码是否一致
    private boolean  pwFlag = false;
    //判断验证码是否对
    private boolean validateFlag = false;

    public boolean isPwFlag() {
        return pwFlag;
    }

    public void setPwFlag(boolean pwFlag) {
        this.pwFlag = pwFlag;
    }
    
    //新的密码
    private String newPassword;

    //
    private boolean flag = false;

    //登录邮箱
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
   //判断密码是否一致

    public String pwApper() {
        System.out.println(oldpw);
        System.out.println(password);
        if (oldpw == null || oldpw.isEmpty()) {
            pwFlag = false;
            return "请输入原先密码";
        } else if (oldpw.equals(password)) {
            pwFlag = true;
            return "密码正确";
        } else {
            pwFlag = false;
        }
        return "密码不匹配";

    }

    //随机生成一个新的密码
    public String getNewpassword() {
        Random random = new Random();
        String newpassword = String.valueOf(random.nextInt(1000000));
        if (Integer.parseInt(newpassword) < 100000) {
            this.getNewpassword();
        }
        return newpassword;
    }

    //用ajax验证用户是否存在
    public String isAppear() {
        if (userId == null || userId.isEmpty()) {
            flag = false;
            return "请输入编号";
        } else {
            Teacher currentTea = teaFacade.find(userId);
            Studentinfo currentStu = studentFacade.findByStuno(userId);
            if (currentStu.getName() != null || currentTea != null) {
                flag = true;
                return "";
            }
            flag = false;
            return "不存在";
        }
    }

    //验证邮箱
    public String validateEmail() {
        if (userId == null || userId.isEmpty()) {
            return "";
        } else {
            Teacher currentTea = teaFacade.find(userId);
            Studentinfo currentStu = studentFacade.findByStuno(userId);
            if (currentStu.getEmail().equals(email)) {
                return "true";
            } else {
                return "false";
            }
        }
    }

    //向邮箱发送密码
    public String sendPasswordMail() {
        newPassword = this.getNewpassword();
        Mail.sendMail(email, newPassword);
        Studentinfo stu = studentFacade.findByStuno(userId);
        stu.setPassword(newPassword);
        stuCon.updateStu(stu);
        return "sendsuccess";
    }

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

                    
                } //教务老师登陆
                else if (currentTea.getRolesinfo().getId() == Publicfields.TEACHER_ROLE) {
                   
                } //任课老师登陆
                else if (currentTea.getRolesinfo().getId() == Publicfields.EDUTEACHER_ROLE) {
                    teaCon.setCurrent(currentTea);
                    
                }
                return "/interfaces/public/welcome?faces-redirect=true";

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

    /**
     * @return the flag
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
