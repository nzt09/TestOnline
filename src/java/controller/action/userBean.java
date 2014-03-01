/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import entities.Studentinfo;
import entities.Teacher;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import sessionBean.StudentinfoFacadeLocal;
import sessionBean.TeacherFacadeLocal;

/**
 *
 * @author Administrator
 */
@Named
@SessionScoped
public class userBean implements java.io.Serializable {

    @EJB
    private TeacherFacadeLocal teacherFacade;
    @EJB
    private StudentinfoFacadeLocal studentFacade;
    private String userId;
    @Size(min = 5, max = 15, message = "长度应在5~15个字符之间")
    private String password;
    private String confirm;
    private String reoldPassword;
    private String status = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getReoldPassword() {
        return reoldPassword;
    }

    public void setReoldPassword(String reoldPassword) {
        this.reoldPassword = reoldPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @AssertTrue(message = "原密码不正确")
    public boolean isEdquals() {
        if (userId.length() == 4) {
            Teacher teacher = teacherFacade.find(userId);
            return reoldPassword.equals(teacher.getPassword());
        } else {
            Studentinfo student = studentFacade.findByStuno(userId);
            return reoldPassword.equals(student.getPassword());
        }
    }

    @AssertTrue(message = "两次输入密码不一致")
    public boolean isPasswordsEquals() {
        return password.equals(confirm);
    }

    public void storeNewPassword() {
        if (userId.length() == 4) {
            Teacher teacher = teacherFacade.find(userId);
            teacher.setPassword(password);
            teacherFacade.edit(teacher);
        } else {
            Studentinfo student = studentFacade.findByStuno(userId);
            student.setPassword(password);
            studentFacade.edit(student);
        }
        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "修改成功!", "修改成功!"));
    }
}
